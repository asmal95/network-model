package network;

public class NotFoundPackageException extends RuntimeException {
    public NotFoundPackageException() {  }
    public NotFoundPackageException(String mess) {
        super(mess);
    }
}