package pl.wk.demo.concurrency.counting;

/**
 * Created by wojtek on 15.11.16.
 */
public class MonitorSynchronizedCounter implements Counter {

    private Object monitor = new Object();

    int counter = 0;

    public void increment() {
        synchronized (monitor) {
            counter++;
        }
    }

    public Integer get() {
        synchronized (monitor) {
            return counter;
        }
    }
}
