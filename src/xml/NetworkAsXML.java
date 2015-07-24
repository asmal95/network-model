package xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ip.IP;
import network.elements.*;
import network.Network;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import routing.Graph;
import routing.Stack;

public class NetworkAsXML implements LoadingNetwork, SavingNetwork {

    private static NetworkAsXML instance = new NetworkAsXML();  //по идее никаких исключений тут не должно возникать..

    public static SavingNetwork getSaving() {
        return instance;
    }

    public static LoadingNetwork getLoading() {
        return instance;
    }

    private NetworkAsXML() {  }

    @Override
    public Network getNetwork(String path) throws SAXException, IOException, ParserConfigurationException {
        //if (network != null) return network;
        //Network network;
        if (path == null) throw new IllegalArgumentException("path is null");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ArrayList<PathElement> pathElements = new ArrayList<PathElement>();
        File f = new File(path);

        Document doc = builder.parse(f);

        Element root = doc.getDocumentElement();    //получаем корневой элемент

        NodeList children = root.getChildNodes();
        for(int i = 0; i < children.getLength(); i++) {
            Node childNode = children.item(i);
            if(childNode instanceof Element) {
                Element childElement = (Element) childNode;

                if (childElement.getTagName().equals("elements")) { //обрабатываем элементы
                    NodeList elementsNodeList = childElement.getChildNodes();
                    for(int j = 0; j < elementsNodeList.getLength(); j++) {
                        Node elementNode = elementsNodeList.item(j);
                        if(elementNode instanceof Element) {
                            Element element = (Element)elementNode;
                            if (element.getTagName().equals("activeElement")) {
                                int id = Integer.parseInt(element.getAttribute("id"));
                                String type = element.getAttribute("type");
                                double cost = Double.parseDouble(element.getAttribute("cost"));
                                double timeDelay = Double.parseDouble(element.getAttribute("timeDelay"));

                                IP ip = new IP(element.getAttribute("ip"));

                                switch (type.toLowerCase()) {
                                    case "pc":
                                        pathElements.add(new PC(ip, cost, timeDelay, id));
                                        break;
                                    case "switch":
                                        pathElements.add(new Switch(ip, cost, timeDelay, id));
                                        break;
                                    case "firewall":
                                        pathElements.add(new Firewall(ip, cost, timeDelay, id));
                                        break;
                                    case "router":
                                        pathElements.add(new Router(ip, cost, timeDelay, id));
                                        break;
                                }
                            } else if (element.getTagName().equals("passiveElement")) {
                                int id = Integer.parseInt(element.getAttribute("id"));
                                String type = element.getAttribute("type");
                                double cost = Double.parseDouble(element.getAttribute("cost"));
                                double timeDelay = Double.parseDouble(element.getAttribute("timeDelay"));

                                switch (type.toLowerCase()) {
                                    case "cable":
                                        pathElements.add(new Cable(cost, timeDelay, id));
                                        break;
                                    case "hub":
                                        pathElements.add(new Hub(cost, timeDelay, id));
                                        break;
                                }
                            }

                        }
                    }
                }
                if (childElement.getTagName().equals("connections")) { //обрабатываем соединения между элементами
                    NodeList connectionsNodeList = childElement.getChildNodes();
                    for (int j=0; j<connectionsNodeList.getLength(); j++) {
                        Node connectNode = connectionsNodeList.item(j);
                        if(connectNode instanceof Element) {
                            Element connectElement = (Element) connectNode;
                            if (connectElement.getNodeName().equals("connect")) {
                                int firstID = Integer.parseInt(connectElement.getAttribute("firstID"));
                                int secondID = Integer.parseInt(connectElement.getAttribute("secondID"));
                                //тут надо организовать поиск элементов в сети по ID и соединять их; работаем уже с коллекцией pathElements
                                PathElement firstElement=null, secondElement=null;
                                for (PathElement pthEl : pathElements) {
                                    if (firstID == pthEl.getID()) {
                                        firstElement = pthEl;
                                    } else if (secondID == pthEl.getID()) {      //надо обдумать смысл такой оптимизации и выбрать один стиль..
                                        secondElement = pthEl;
                                    }
                                }
                                if(firstElement != null && secondElement != null)
                                    firstElement.connect(secondElement);
                            }
                        }
                    }
                }
            }
        }

        return new Network(pathElements);
    }

    @Override
    public void saveNetwork(Network network, String path) throws ParserConfigurationException,
            FileNotFoundException, TransformerException {

        if (network == null) throw new IllegalArgumentException("network is null");
        if (path == null) throw new IllegalArgumentException("path is null");

        List<PathElement> pathElements = network.getElements();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element rootElement = doc.createElement("network");
        doc.appendChild(rootElement);

        //добавляем список всех элементов с параметрами
        Element elements = doc.createElement("elements");
        for (PathElement pathElement : pathElements) {
            if (pathElement instanceof PassiveElement) {
                Element passiveElement = doc.createElement("passiveElement");
                passiveElement.setAttribute("id", "" + pathElement.getID());
                passiveElement.setAttribute("type", pathElement.getType());
                passiveElement.setAttribute("timeDelay", "" + pathElement.getTimeDelay());
                passiveElement.setAttribute("cost", "" + pathElement.getCost());
                elements.appendChild(passiveElement);
            } else if (pathElement instanceof ActiveElement) {
                Element activeElement = doc.createElement("activeElement");
                activeElement.setAttribute("id", "" + pathElement.getID());
                activeElement.setAttribute("type", pathElement.getType());
                activeElement.setAttribute("timeDelay", "" + pathElement.getTimeDelay());
                activeElement.setAttribute("cost", "" + pathElement.getCost());
                activeElement.setAttribute("ip", "" + ((ActiveElement)pathElement).getIP());
                elements.appendChild(activeElement);
            }
        }

        rootElement.appendChild(elements);

        Stack<Integer> stack = new Stack<>();
        Graph graph = new Graph(network);
        int countNode = graph.countNode();

        boolean isCheck[] = new boolean[countNode];
        for (int i=0; i<isCheck.length; i++)
            isCheck[i] = false;

        /*
        1) пробегаемся по строчкам первого элемента Если находим элемент и этот элемент не отмечен, то делаем запись.
        2) отмечаем данный элемент как пройденный
        3) сложность o (n^2)
         */
        Element connections = doc.createElement("connections");
        for (int i = 0; i < countNode; i++) {
            for (int j = 0; j < countNode; j++) {
                if (graph.isConnect(i, j) && !isCheck[i]) {
                    Element connect = doc.createElement("connect");
                    connect.setAttribute("firstID", "" + pathElements.get(i).getID());
                    connect.setAttribute("secondID", "" + pathElements.get(j).getID());
                    connections.appendChild(connect);
                    isCheck[j] = true;
                }
            }
        }
        rootElement.appendChild(connections);
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(path)));
    }
}