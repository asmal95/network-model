package network.elements;

public abstract class PassiveElement extends PathElement {
    public PassiveElement(String type, double cost, double timeDelay) {
        super(type, cost, timeDelay);
    }
    public PassiveElement(String type, double cost, double timeDelay, int ID) {
        super(type, cost, timeDelay, ID);
    }

}