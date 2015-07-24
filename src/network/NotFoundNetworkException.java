package network;

public class NotFoundNetworkException extends RuntimeException {
    public NotFoundNetworkException() {  }
    public NotFoundNetworkException(String mess) {
        super(mess);
    }
}