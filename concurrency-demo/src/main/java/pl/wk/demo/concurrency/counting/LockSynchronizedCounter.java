package pl.wk.demo.concurrency.counting;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by wojtek on 15.11.16.
 */
public class LockSynchronizedCounter implements Counter {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private int counter = 0;

    public void increment() {
        final Lock writeLock = readWriteLock.writeLock();
        try {
            writeLock.lock();
            counter++;
        } finally {
            writeLock.unlock();
        }
    }

    public Integer get() {
        final Lock readLock = readWriteLock.readLock();
        try {
            readLock.lock();
            return counter;
        } finally {
            readLock.unlock();
        }
    }
}
