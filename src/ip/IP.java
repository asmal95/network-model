package ip;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Представляет собой IP для любого активного элемента в сети
 */
public class IP {
    private final String ip;

    public IP (String ip) {
        if (!checkIP(ip)) throw new IPFormatException(ip);
        this.ip = ip;
    }

    /**
     * Позволяет проверить, является ли переданная строка валидным IP
     * @param ip строка представляющая IP
     * @return true, если строка является IP, fase, если строка не прошла проверку
     */
    public static boolean checkIP(String ip) {
        if (ip == null) return false;
        Pattern p = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        Matcher m = p.matcher(ip);
        return m.matches();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (obj instanceof IP) {
            IP ipAddr = (IP)obj;
            return this.ip.equals(ipAddr.ip);
        }
        return false;
    }

    /*@Override
    public int hashCode() {
        return 0;
    }*/

    @Override
    public String toString() {
        return ip;
    }
}