package pl.wk.demo.concurrency.counting;

/**
 * Created by wojtek on 15.11.16.
 */
public class NotSynchronizedCounter implements Counter {

    private int counter = 0;

    @Override
    public void increment() {
        counter++;
    }

    @Override
    public Integer get() {
        return counter;
    }
}
