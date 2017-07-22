package org.kiwi.context;

/**
 * @email jack.liu.19910921@gmail.com
 * Created by jack on 17/7/23.
 */
public class KiwiException extends RuntimeException {

    public KiwiException() {
    }

    public KiwiException(String message) {
        super(message);
    }

    public KiwiException(String message, Throwable t) {
        super(message, t);
    }

    public KiwiException(Throwable t) {
        super(t);
    }

}
