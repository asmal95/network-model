package xml;

import network.Network;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface LoadingNetwork {
    Network getNetwork(String path) throws SAXException, IOException, ParserConfigurationException;
}
