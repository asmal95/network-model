package ip;

/**
 * Возникает когда в сети нет устройства с искомым IP
 */
public class IPNotFoundException extends RuntimeException {
    public IPNotFoundException() {  }
    public IPNotFoundException(String message) {
        super(message);
    }
}
