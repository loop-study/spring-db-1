package hello.springtx.order;

public class NotEnoughtMoneyException extends Exception {

    public NotEnoughtMoneyException(String message) {
        super(message);
    }
}
