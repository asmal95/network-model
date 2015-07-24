package network.elements;

import ip.IP;

public abstract class ActiveElement extends PathElement {

    protected IP ip;


    public ActiveElement(String type, IP ip, double cost, double timeDelay) {
        super(type, cost, timeDelay);
        if (ip == null) throw new IllegalArgumentException("null value for IP unacceptable");
        this.ip = ip;
    }

    public ActiveElement(String type,IP ip, double cost, double timeDelay, int ID) {
        super(type, cost, timeDelay, ID);
        if (ip == null) throw new IllegalArgumentException("null value for IP unacceptable");
        this.ip = ip;
    }

    /**
     * Возвращает краткую информацию об элементе (Тип, ID, IP)
     * @return информация об элементе
     */
    @Override
    public String getInfo() {
        return super.getInfo() + " IP: " + ip;
    }

    /**
     * Возвращает IP элемента
     * @return объект типа IP
     */
    public IP getIP() {
        return ip;
    }

    /**
     * Устанавливает IP
     * @param ip объект типа IP
     */
    public void setIP(IP ip) {
        if (ip == null) throw new IllegalArgumentException("null value for IP unacceptable");
        this.ip = ip;
    }
}
