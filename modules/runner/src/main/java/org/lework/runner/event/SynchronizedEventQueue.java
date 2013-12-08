package org.lework.runner.event;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Synchronized event queue.
 *
 */
final class SynchronizedEventQueue extends AbstractEventQueue {
    
    /**
     * Synchronized event queue.
     */
    private Map<String, List<Event<?>>> synchronizedEvents = new HashMap<String, List<Event<?>>>();

    /**
     * Event manager.
     */
    private EventManager eventManager;

    /**
     * Constructs a {@link org.lework.runner.event.SynchronizedEventQueue} object with the specified
     * event manager.
     *
     * @param eventManager the specified event manager
     */
    SynchronizedEventQueue(final EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * Fires the specified event.
     *
     * @param event the specified event
     * @throws EventException event exception
     */
    synchronized void fireEvent(final Event<?> event) throws EventException {
        final String eventType = event.getType();
        List<Event<?>> events = synchronizedEvents.get(eventType);

        if (null == events) {
            events = new ArrayList<Event<?>>();
            synchronizedEvents.put(eventType, events);
        }

        events.add(event);
        setChanged();
        notifyListeners(event);
    }

    /**
     * Removes the specified event from this event queue.
     *
     * @param event the specified event
     */
    void removeEvent(final Event<?> event) {
        synchronizedEvents.get(event.getType()).remove(event);
    }
}
