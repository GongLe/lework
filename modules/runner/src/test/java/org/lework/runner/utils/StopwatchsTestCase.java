package org.lework.runner.utils;


import org.junit.BeforeClass;
import org.junit.Test;

/**
 * {@link Stopwatchs} test case.
 */
public final class StopwatchsTestCase {


    /**
     * Releases the current thread-local variable after each test method.
     */
    @BeforeClass
    public void afterMethod() {
        Stopwatchs.release();
    }

    /**
     * Tests method {@link Stopwatchs#getTimingStat()}.
     *
     * @throws Exception exception
     */
    @Test
    public void getTimingStat() throws Exception {
        System.out.println("getTimingStat");
        Stopwatchs.start("task 1");

        Stopwatchs.start("task 1.1");
        final long task11Time = 50;
        Thread.sleep(task11Time);
        Stopwatchs.end(); // Ends 1.1


        Stopwatchs.start("task 1.2");
        Stopwatchs.start("task 1.2.1");
        Stopwatchs.start("task 1.2.1.1");
        Stopwatchs.start("task 1.2.1.1.1");
        Stopwatchs.start("task 1.2.1.1.1.1");
        Stopwatchs.start("task 1.2.1.1.1.1.1");
        Stopwatchs.end(); // Ends 1.2.1.1.1.1.1
        Stopwatchs.end(); // Ends 1.2.1.1.1.1
        Stopwatchs.end(); // Ends 1.2.1.1.1
        Stopwatchs.end(); // Ends 1.2.1.1
        Stopwatchs.end(); // Ends 1.2.1

        Stopwatchs.start("task 1.2.2");
        final long task122Time = 20;
        Thread.sleep(task122Time);
        Stopwatchs.end(); // Ends 1.2.2

        Stopwatchs.end(); // Ends 1.2

        Stopwatchs.start("task 1.3");
        final long task13Time = 10;
        Thread.sleep(task13Time);
        Stopwatchs.end(); // Ends 1.3

        Stopwatchs.end(); // Ends 1

        System.out.println(Stopwatchs.getTimingStat());
    }

    /**
     * Tests method {@link Stopwatchs#getTimingStat()}.
     *
     * @throws Exception exception
     */
    @Test(expected = RuntimeException.class)
    public void getTimingStatWhileException() throws Exception {
        System.out.println("getTimingStatWhileException");
        Stopwatchs.start("task 1");

        Stopwatchs.start("task 1.1");
        try {
            throw new RuntimeException();
        } finally {
            final long task11Time = 50;
            Thread.sleep(task11Time);
            Stopwatchs.end(); // Ends 1.1


            Stopwatchs.start("task 1.2");
            Stopwatchs.start("task 1.2.1");
            Stopwatchs.end(); // Ends 1.2.1

            Stopwatchs.start("task 1.2.2");
            final long task122Time = 20;
            Thread.sleep(task122Time);
            Stopwatchs.end(); // Ends 1.2.2

            //Stopwatchs.end(); // Ends 1.2, NOTE

            Stopwatchs.start("task 1.3");
            final long task13Time = 10;
            Thread.sleep(task13Time);

            //Stopwatchs.end(); // Ends 1, NOTE

            System.out.println(Stopwatchs.getTimingStat());
        }
    }
}
