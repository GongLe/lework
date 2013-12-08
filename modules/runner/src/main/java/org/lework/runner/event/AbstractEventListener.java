package org.lework.runner.event;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract event listener (Observer).
 *
 * @param <T> the type of event data
 */
public abstract class AbstractEventListener<T> {

    /**
     * Logger.
     */
    final static Logger logger = LoggerFactory.getLogger(AbstractEventListener.class.getClass());

    /**
     * Gets the event type of this listener could handle.
     *
     * @return event type
     */
    public abstract String getEventType();

    /**
     * Performs the listener {@code action} method with the specified event
     * queue and event.
     *
     * @param eventQueue the specified event
     * @param event      the specified event
     * @throws EventException event exception
     * @see Event
     */
    final void performAction(final AbstractEventQueue eventQueue, final Event<?> event) throws EventException {
        @SuppressWarnings("unchecked")
        final Event<T> eventObject = (Event<T>) event;

        try {
            action(eventObject);
        } catch (final Exception e) {
            logger.warn("Event perform failed:{}", e);
        } finally { // remove event from event queue
            if (eventQueue instanceof SynchronizedEventQueue) {
                final SynchronizedEventQueue synchronizedEventQueue = (SynchronizedEventQueue) eventQueue;

                synchronizedEventQueue.removeEvent(eventObject);
            }
        }
    }

    /**
     * Processes the specified event.
     *
     * @param event the specified event
     * @throws EventException event exception
     */
    public abstract void action(final Event<T> event) throws EventException;
}
