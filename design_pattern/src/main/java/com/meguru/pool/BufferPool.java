package com.meguru.pool;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferPool {

    private final int totalSize;

    private final int slotSize;
    private final Deque<ByteBuffer> slotQueue = new ArrayDeque<>();
    private final Deque<Condition> waiters = new ArrayDeque<>();
    private final Lock lock = new ReentrantLock();
    private int free;

    public BufferPool(int totalSize, int slotSize) {
        this.totalSize = totalSize;
        this.slotSize = slotSize;
        this.free = totalSize;
    }

    public ByteBuffer allocate(int size, long timeout) throws InterruptedException {
        if (size <= 0 && size > totalSize) {
            throw new RuntimeException("申请容量异常");
        }
        lock.lock();
        try {
            if (size == slotSize && !slotQueue.isEmpty()) {
                return slotQueue.pollFirst();
            }
            if (free + slotQueue.size() * slotSize >= size) {
                freeUp(size);
                free -= size;
                return ByteBuffer.allocate(size);
            }
            Condition condition = lock.newCondition();
            waiters.addLast(condition);
            long remainTime = timeout;
            try {
                while (true) {
                    long start = System.currentTimeMillis();
                    boolean wakeup = condition.await(remainTime, TimeUnit.MILLISECONDS);
                    if (!wakeup) {
                        throw new RuntimeException("规定时间内未申请到内存");
                    }
                    if (size == slotSize && !slotQueue.isEmpty()) {
                        return slotQueue.pollFirst();
                    }
                    if (free + slotQueue.size() * slotSize >= size) {
                        freeUp(size);
                        free -= size;
                        return ByteBuffer.allocate(size);
                    }
                    remainTime -= System.currentTimeMillis() - start;
                }
            } finally {
                waiters.remove(condition);
            }

        } finally {
            if (!waiters.isEmpty() && !(free == 0 && slotQueue.isEmpty())) {
                waiters.peekFirst().signal();
            }
            lock.unlock();
        }
    }

    public void deallocate(ByteBuffer byteBuffer) {
        lock.lock();
        try {
            if (byteBuffer.capacity() == this.slotSize) {
                this.slotQueue.addLast(byteBuffer);
            } else {
                free += byteBuffer.capacity();
            }
            if (!waiters.isEmpty()) {
                waiters.peekFirst().signal();
            }
        } finally {
            lock.unlock();
        }
    }

    private void freeUp(int size) {
        while (free < size && !slotQueue.isEmpty()) {
            free += slotQueue.pollFirst().capacity();
        }
    }
}
