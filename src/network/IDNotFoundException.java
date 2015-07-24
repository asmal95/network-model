package network;

/**
 * Возникает когаа в сети нет устройсва с искомым ID
 */
public class IDNotFoundException extends RuntimeException {
    public IDNotFoundException() {  }
    public IDNotFoundException(String message) {
        super(message);
    }
}
