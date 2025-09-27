package com.meguru.decorator.input;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferedFileInputStream extends InputStream {

    private final FileInputStream fileInputStream;

    private final byte[] buffer = new byte[8192];

    private int position = -1;

    private int capacity = -1;

    public BufferedFileInputStream(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    @Override
    public int read() throws IOException {
        if (bufferCanRead())
            return readFromBuffer();
        refreshBuffer();
        if (!bufferCanRead())
            return -1;
        return readFromBuffer();
    }

    private int readFromBuffer() {
        return buffer[position++] & 0xFF;
    }

    private void refreshBuffer() throws IOException {
        capacity = this.fileInputStream.read(buffer);
        position = 0;
    }

    private boolean bufferCanRead() {
        if (capacity == -1 || position == capacity)
            return false;
        return true;
    }

    @Override
    public void close() throws IOException {
        this.fileInputStream.close();
    }
}
