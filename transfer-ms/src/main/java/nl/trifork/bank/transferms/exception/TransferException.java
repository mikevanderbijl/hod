package nl.trifork.bank.transferms.exception;

public class TransferException extends RuntimeException {

    public TransferException() {}

    public TransferException(String message) {
        super(message);
    }

    public TransferException(Throwable cause) {
        super(cause);
    }

    public TransferException(String message, Throwable cause) {
        super(message, cause);
    }

}
