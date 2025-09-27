package com.meguru.pool;

import java.nio.ByteBuffer;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BufferPool bufferPool = new BufferPool(1024, 256);
        ByteBuffer allocate1 = bufferPool.allocate(256, 1000);
        bufferPool.deallocate(allocate1);
        ByteBuffer allocate2 = bufferPool.allocate(256, 1000);
        System.out.println(allocate1 == allocate2);
    }
}
