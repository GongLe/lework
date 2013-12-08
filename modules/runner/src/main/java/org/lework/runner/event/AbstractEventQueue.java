package org.lework.runner.event;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Abstract event queue(Observable).
 *
 * @see AbstractEventListener
 */
public abstract class AbstractEventQueue {

    /**
     * Flag of change.
     */
    private boolean changed = false;

    /**
     * Listeners.
     */
    private Map<String, List<AbstractEventListener<?>>> listeners = new HashMap<String, List<AbstractEventListener<?>>>();

    /**
     * Adds the specified listener to the set of listeners for this object,
     * provided that it is not the same as some listener already in the set.
     *
     * @param listener the specified listener
     */
    synchronized void addListener(
        final AbstractEventListener<?> listener) {
        if (null == listener) {
            throw new NullPointerException();
        }

        final String eventType = listener.getEventType();

        if (null == eventType) {
            throw new NullPointerException();
        }

        List<AbstractEventListener<?>> listenerList = listeners.get(eventType);

        if (null == listenerList) {
            listenerList = new ArrayList<AbstractEventListener<?>>();
            listeners.put(eventType, listenerList);
        }

        listenerList.add(listener);
    }

    /**
     * Deletes the specified listener from the set of listeners of this object.
     * Passing {@code null} to this method will have no effect.
     *
     * @param listener the specified listener
     */
    synchronized void deleteListener(
        final AbstractEventListener<?> listener) {
        final String eventType = listener.getEventType();

        if (null == eventType) {
            throw new NullPointerException();
        }

        final List<AbstractEventListener<?>> listenerList = listeners.get(eventType);

        if (null != listenerList) {
            listenerList.remove(listener);
        }
    }

    /**
     * If this object has changed, as indicated by the {@code hasChanged}
     * method, then notify all of its listeners and then call the
     * {@code clearChanged} method to indicate that this object has no longer
     * changed.
     *
     * @throws EventException event exception
     * @see AbstractEventListener#performAction( AbstractEventQueue, Event)
     */
    public void notifyListeners() throws EventException {
        notifyListeners(null);
    }

    /**
     * Notifies all listeners of this event queue to perform action. 
     * 
     * If this event queue object has changed, as indicated by the
     * {@code hasChanged} method, then notify all of its listeners and then
     * call the {@code clearChanged} method to indicate that this object has
     * no longer changed.
     *
     * @param event the specified event
     * @throws EventException event exception
     * @see AbstractEventListener#performAction(AbstractEventQueue, Event)
     */
    public void notifyListeners(final Event<?> event) throws EventException {

        /*
         * a temporary array buffer, used as a snapshot of the state of
         * current listeners.
         */
        AbstractEventListener<?>[] arrLocal = null;

        synchronized (this) {

            /* We don't want the listener doing callbacks into arbitrary code
             * while holding its own Monitor. The code where we extract each
             * Observable from the Vector and store the state of the listener
             * needs synchronization, but notifying listeners does not (should not).
             * The worst result of any potential race-condition here is that:
             * 1) a newly-added listener will miss a notification in progress
             * 2) a recently unregistered listener will be wrongly notified
             *    when it doesn't care
             */
            if (!changed) {
                return;
            }

            final String eventType = event.getType();
            final List<AbstractEventListener<?>> listenerList = listeners.get(eventType);

            final AbstractEventListener<?>[] types = new AbstractEventListener<?>[1];

            if (null != listenerList) {
                arrLocal = listenerList.<AbstractEventListener<?>>toArray(types);
                clearChanged();
            }
        }

        if (null != arrLocal) {
            for (int i = arrLocal.length - 1; i >= 0; i--) {
                arrLocal[i].performAction(this, event);
            }
        }
    }

    /**
     * Clears the listener list so that this object no longer has any listeners..
     */
    public synchronized void deleteListeners() {
        listeners.clear();
    }

    /**
     * Marks this {@literal Event queue} object as having been changed, the
     * {@code hasChanged} method will now return {@code true}.
     */
    protected synchronized void setChanged() {
        changed = true;
    }

    /**
     * Indicates that this object has no longer changed, or that it has
     * already notified all of its listeners of its most recent change,
     * so that the {@code hasChanged} method will now return {@code false}.
     * This method is called automatically by the
     * {@code notifyListeners} methods.
     *
     * @see #notifyListeners()
     * @see #notifyListeners(Event)
     */
    protected synchronized void clearChanged() {
        changed = false;
    }

    /**
     * Tests if this object has changed.
     *
     * @return {@code true} if and only if the {@code setChanged} method has
     * been called more recently than the {@code clearChanged} method on this
     * object; {@code false} otherwise
     *
     * @see #clearChanged()
     * @see #setChanged() 
     */
    public synchronized boolean hasChanged() {
        return changed;
    }

    /**
     * Returns the number of listeners of this {@literal Event queue} object.
     *
     * @return  the number of listeners of this object.
     */
    public synchronized int countListeners() {
        return listeners.size();
    }
}
