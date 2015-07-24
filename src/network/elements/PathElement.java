package network.elements;

import java.util.ArrayList;
import java.util.List;


public abstract class PathElement {
    private static int idCount = 0;

    private double timeDelay;
    private double cost;
    private String info;
    private int ID;
    private String type;
    private List<PathElement> connections = new ArrayList<>();

    /**
     * Конструирует PathElement
     * ID элемента генерируется автоматически
     * @param type тип элемента
     * @param cost стоимость прохождения 1 байта информации
     * @param timeDelay задержка при прохождении 1 байта информации
     */
    public PathElement(String type, double cost, double timeDelay) {
        ID = ++idCount;
        this.type = type.toLowerCase();
        this.info = type + " #" + idCount;
        this.cost = cost;
        this.timeDelay = timeDelay;
    }

    /**
     * Конструирует PathElement
     * @param type тип элемента
     * @param cost стоимость прохождения 1 байта информации
     * @param timeDelay задержка при прохождении 1 байта информации
     * @param ID ID элемента
     */
    public PathElement(String type, double cost, double timeDelay, int ID) {
        this.ID = ID;
        this.type = type.toLowerCase();
        this.info = type + " #" + ID;
        this.cost = cost;
        this.timeDelay = timeDelay;
        if (idCount < ID) idCount = ID;
    }

    /**
     * Возвращает время в ms прохождения 1 байта информации через элемент
     * @return время в ms
     */
    public double getTimeDelay() {
        return timeDelay;
    }

    /**
     * Возвращает стоимость прохождения 1 байта информации через элемент
     * @return стоимость
     */
    public double getCost() {
        return cost;
    }

    /**
     * Возвращает элементы к которым подключен
     * @return коллеция PathElement'ов
     */
    public List<PathElement> getConnections() {
        return connections;
    }

    /**
     * Возвращает краткую информацию об элементе (Тип, ID)
     * @return информация об элементе
     */
    public String getInfo() {
        return info;
    }

    /**
     * Возвращает ID элемента
     * @return ID элемента
     */
    public int getID() {
        return ID;
    }

    /**
     * Возвращает тип элемента
     * @return тип элемента
     */
    public  String getType() { return  type; }

    public String getConnectInfo() {
        String res = "";
        for (PathElement el : connections) {
            res += el.getInfo() + ", ";
        }
        return res;
    }
    /**
     * Устанавливает стоимость прохождения 1 байта информации
     * @param cost стоимость
     */
    public void setCost(double cost) {
        if (cost < 0) { throw new IllegalArgumentException(); }
        this.cost = cost;
    }

    /**
     * Устанавливает время задержки в ms прохождения 1 байта информации
     * @param timeDelay время в ms
     */
    public void setTimeDelay(double timeDelay) {
        if (timeDelay < 0) { throw new IllegalArgumentException(); }
        this.timeDelay = timeDelay;
    }

    /**
     * Соединяет данный элемент с другим элементом сети
     * @param elem экземпляр наследника PathElement'а с которым необходимо соединить элемент
     */
    public void connect(PathElement elem) {
        if (elem == null)
            throw new RuntimeException();

        if (!this.isConnect(elem))
            connections.add(elem);

        if (!elem.isConnect(this))
            elem.connect(this);
    }

    /**
     * Разрывает соединение с указанным элементом сети
     * @param elem экземпляр наследника PathElement'а с которым необходимо разорвать соединение
     */
    public void disconnect(PathElement elem) {
        if (elem == null)
            throw new RuntimeException();

        if (this.isConnect(elem))
            connections.remove(elem);

        if (elem.isConnect(this))
            elem.disconnect(this);

    }

    /**
     * проверяет, соединен ли текущий элемент с другим элементом
     * @param elem экземпляр наследника PathElement'а с которым необходимо проверить соединение
     * @return true, если есть соединение, false, если соединения нет
     */
    public boolean isConnect(PathElement elem) {
        if (!(elem == null))
            for (PathElement item : connections)
                if (item == elem) return true;

        return false;
    }

    /**
     * Возвращает количество елементов, с которыми установленно соединение
     * @return количество соседних элементов
     */
    public int countConnections() {
        return connections.size();
    }
}