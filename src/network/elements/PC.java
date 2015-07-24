package network.elements;

import ip.IP;

public class PC extends ActiveElement {
    public PC(IP ip, double cost, double timeDelay) {
        super("PC", ip, cost, timeDelay);
    }
    public PC(IP ip, double cost, double timeDelay, int ID) {
        super("PC", ip, cost, timeDelay, ID);
    }
}