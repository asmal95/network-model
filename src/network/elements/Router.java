package network.elements;

import ip.IP;

public class Router extends ActiveElement {

    public Router(IP ip, double cost, double timeDelay) {
        super("Router", ip, cost, timeDelay);
    }
    public Router(IP ip, double cost, double timeDelay, int ID) {
        super("Router", ip, cost, timeDelay, ID);
    }
}
