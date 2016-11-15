package pl.wk.demo.concurrency.counting;

import java.util.Objects;

/**
 * Created by wojtek on 15.11.16.
 */
public class CounterIncrementer implements Runnable {

    private final Counter counter;
    private final int incrementLoopCount;

    public CounterIncrementer(Counter counter, int incrementLoopCount) {
        this.counter = Objects.requireNonNull(counter);
        if (incrementLoopCount < 0) {
            throw new IllegalArgumentException("Loop count cannot be less than 0");
        }
        this.incrementLoopCount = incrementLoopCount;
    }

    public void run() {
        for (int i = 0; i < incrementLoopCount && !Thread.interrupted(); i++) {
            counter.increment();
        }
    }
}
