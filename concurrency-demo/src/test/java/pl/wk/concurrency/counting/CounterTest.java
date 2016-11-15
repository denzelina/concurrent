package pl.wk.concurrency.counting;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.wk.demo.concurrency.counting.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * Created by wojtek on 15.11.16.
 */
public class CounterTest {

    private ExecutorService executorService;

    @Before
    public void setup() {
        executorService = Executors.newFixedThreadPool(5);
    }

    @After
    public void shutdown() {
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }

    @Test
    public void testMultipleThreadsIncrementMonitorSynchronizedCounter() {
        // setup:
        List<Future> futureList = new ArrayList<>();

        // given:
        Counter counter = new MonitorSynchronizedCounter();
        int incrementLoopCount = 10000;
        int threadCount = 4;

        // when: counting is performed
        performAndValidateCounting(futureList, counter, incrementLoopCount, threadCount);
    }

    @Test
    public void testMultipleThreadsIncrementLockSynchronizedCounter() {
        // setup:
        List<Future> futureList = new ArrayList<>();

        // given:
        Counter counter = new LockSynchronizedCounter();
        int incrementLoopCount = 10000;
        int threadCount = 4;

        // when: counting is performed
        performAndValidateCounting(futureList, counter, incrementLoopCount, threadCount);
    }

    @Test
    public void testMultipleThreadsIncrementNotSynchronizedCounter() {
        // setup:
        List<Future> futureList = new ArrayList<>();

        // given:
        Counter counter = new NotSynchronizedCounter();
        int incrementLoopCount = 10000;
        int threadCount = 4;

        // when: this actually might fail for some incrementLoopCount and threadCount combination in case of NotSynchronizedCounter
        performAndValidateCounting(futureList, counter, incrementLoopCount, threadCount);
    }

    private void performAndValidateCounting(List<Future> futureList, Counter counter, int incrementLoopCount, int threadCount) {
        IntStream.range(0, threadCount).forEach(i -> futureList.add(executorService.submit(new CounterIncrementer(counter, incrementLoopCount))));
        futureList.forEach(this::waitForTheFuture);
        Assert.assertEquals(incrementLoopCount * futureList.size(), counter.get().intValue());
    }

    private void waitForTheFuture(Future f) {
        try {
            f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
