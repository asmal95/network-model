package ip;

/**
 * Исключение позникающее при попытке создать невалидный IP адрес
 */
public class IPFormatException extends IllegalArgumentException {
    public IPFormatException() {  }
    public IPFormatException(String message) {
        super(message);
    }
}