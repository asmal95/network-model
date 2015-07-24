package network.elements;

import ip.IP;

public class Switch extends ActiveElement {
    public Switch(IP ip, double cost, double timeDelay) {
        super("Switch", ip, cost, timeDelay);
    }
    public Switch(IP ip, double cost, double timeDelay, int ID) {
        super("Switch", ip, cost, timeDelay, ID);
    }
}