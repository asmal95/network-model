package network.elements;

public class Hub extends PassiveElement {
    public Hub(double cost, double timeDelay) {
        super("Hub", cost, timeDelay);
    }
    public Hub(double cost, double timeDelay, int ID) {
        super("Hub", cost, timeDelay, ID);
    }
}
