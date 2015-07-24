package network.elements;

import ip.IP;

public class Firewall extends ActiveElement {
    public Firewall(IP ip, double cost, double timeDelay) {
        super("Firewall",ip , cost, timeDelay);
    }
    public Firewall(IP ip, double cost, double timeDelay, int ID) {
        super("Firewall", ip, cost, timeDelay, ID);
    }

    //public boolean isLegalPackage(network.Package aPackage);
}