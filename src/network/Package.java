package network;

import ip.IP;

/*
    Пакет должен обязательно свой размер, чтобы можно было определять стоимость прохождения по пути.
    Еще по идее какие-то критерии, которые может учитывать wirewall
    UP firewall будет фильтровать пакеты от определенных отправителей.. Для этого юзаем senderIP
 */

/**
 * Представляет собой пакет, который предназначен для передачи по сети.
 * Инкапсулирует размер данных в байтах, IP адресс отправителя и IP адресс получателя
 *
 */
public class Package {
    private final double size; //в байтах
    private final IP senderIP;
    private final IP recipientIP;
    private String name;

    public Package(String name, double size, IP senderIP, IP recipientIP) {
        if (size <= 0) throw new IllegalArgumentException("package size " + size + " unacceptable.");
        if (senderIP == null || recipientIP == null) throw new IllegalArgumentException("null value for IP unacceptable.");
        if (name == null) throw new IllegalArgumentException("null value for name unacceptable.");

        this.name = name;
        this.size = size;
        this.senderIP = senderIP;
        this.recipientIP = recipientIP;
    }

    /**
     * Возвращает размер пакета в байтах
     * @return размер пакета
     */
    public double getSize() {
        return size;
    }

    /**
     * Возвращает IP отправителя
     * @return IP отправителя
     */
    public IP getSenderIP() {
        return senderIP;
    }

    /**
     * Возвращает IP получателя
     * @return IP получателя
     */
    public IP getRecipientIP() {
        return recipientIP;
    }

    /**
     * Возвращает имя пакета
     * @return имя пакета
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getInfo() {
        return name + " size: " + size + ", senderIP: " + senderIP + ", recipientIP: " + recipientIP;
    }
}
