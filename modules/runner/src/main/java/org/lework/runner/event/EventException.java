package org.lework.runner.event;


/**
 * Event exception.
 *
 * @author Gongle
 */
public final class EventException extends Exception {

    /**
     * Public default constructor.
     */
    public EventException() {
        super("Event exception!");
    }

    /**
     * Public constructor with {@link Throwable}.
     *
     * @param throwable the specified throwable object
     */
    public EventException(final Throwable throwable) {
        super(throwable);
    }

    /**
     * Public constructor with message.
     *
     * @param msg the specified message
     */
    public EventException(final String msg) {
        super(msg);
    }
}
