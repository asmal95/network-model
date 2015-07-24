package network.elements;

public class Cable extends PassiveElement {
    public Cable(double cost, double timeDelay) {
        super("Cable", cost, timeDelay);
    }
    public Cable(double cost, double timeDelay, int ID) {
        super("Cable", cost, timeDelay, ID);
    }

    @Override
    public void connect(PathElement elem) {
        if (countConnections() >= 2) throw new RuntimeException("exceeded number of devices to be connected to the cable"); //Кабель может соединять только 2 устройств
        super.connect(elem);
    }
}
