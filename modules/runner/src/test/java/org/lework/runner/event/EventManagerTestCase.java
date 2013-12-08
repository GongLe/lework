
package org.lework.runner.event;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

/**
 * 事件驱动(观察者模式)测试用例.
 * {@link EventManager} test case.
 */
public final class EventManagerTestCase {

    /**
     * @throws Exception exception
     */
    @Test
    public void test() throws Exception {
        System.out.println("===事件驱动(观察者模式)测试用例====");
        final EventManager eventManager = EventManager.getInstance();
        final TestEventListener1 testEventListener1 = new TestEventListener1();
        eventManager.registerListener(testEventListener1);
        final TestEventListener2 testEventListener2 = new TestEventListener2();
        eventManager.registerListener(testEventListener2);
        final TestEventAsyncListener1 testEventAsyncListener1 = new TestEventAsyncListener1();
        eventManager.registerListener(testEventAsyncListener1);

        final Map<Object, Object> eventData = Maps.newHashMap();
        eventData.put("sync event key", "+sync event information.....");
        final Map<Object, Object> asyncEventData = Maps.newHashMap();
        eventData.put("async event key", "-async event information.....");

        eventManager.fireEventSynchronously(new Event<Map<Object, Object>>("Test sync listener1", eventData));
        eventManager.fireEventSynchronously(new Event<Map<Object, Object>>("Test sync listener2", eventData));

        eventManager.<String>fireEventAsynchronously(new Event<Map<Object, Object>>("Test async listener1", asyncEventData));
        System.out.println("Doing somthing in main thread....");
        final long sleepTime = 101;
        final long loopCnt = 40;
        try {
            for (int i = 0; i < loopCnt; i++) {
                System.out.println("In main thread: " + i);
                Thread.sleep(sleepTime);
            }
        } catch (final InterruptedException e) {
            throw new EventException(e);
        }
        System.out.println("Done in main thread");

    }

    /**
     * Test event listener 1.
     */
    private final class TestEventListener1 extends AbstractEventListener<Map<Object, Object>> {

        @Override
        public void action(final Event<Map<Object, Object>> event) {
            System.out.println("Listener1 is processing a event[type=" + event.getType() + ", data=" + event.getData() + "]");
        }

        @Override
        public String getEventType() {
            return "Test sync listener1";
        }
    }

    /**
     * Test event listener 2.
     */
    private final class TestEventListener2 extends AbstractEventListener<Map<Object, Object>> {

        @Override
        public void action(final Event<Map<Object, Object>> event) {
            System.out.println("Listener2 is processing a event[type=" + event.getType() + ", data=" + event.getData() + "]");
        }

        @Override
        public String getEventType() {
            return "Test sync listener2";
        }
    }

    /**
     * Test event asynchronous listener 1.
     */
    private final class TestEventAsyncListener1 extends AbstractEventListener<Map<Object, Object>> {

        @Override
        public void action(final Event<Map<Object, Object>> event) throws EventException {
            System.out.println("Asynchonous listener1 is processing a event[type=" + event.getType() + ", data=" + event.getData() + "]");
            final long sleepTime = 100;
            final long loopCnt = 40;
            try {
                for (int i = 0; i < loopCnt; i++) {
                    System.out.println("In listener: " + i);
                    Thread.sleep(sleepTime);
                }
            } catch (final InterruptedException e) {
                throw new EventException(e);
            }
        }

        @Override
        public String getEventType() {
            return "Test async listener1";
        }
    }
}
