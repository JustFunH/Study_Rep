package com.meguru.decorator.input;

import java.io.File;
import java.io.FileInputStream;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File("/Users/meguru/Dev/code/IdeaProjects/Study/design_pattern/src/main/java/com/meguru" +
                "/decorator/BufferTest.pdf");
        long start = Instant.now().toEpochMilli();
        try (BufferedFileInputStream fileInputStream = new BufferedFileInputStream(new FileInputStream(file))) {
            while (true) {
                int read = fileInputStream.read();
                if (read == -1) {
                    break;
                }
            }
            System.out.println("用时: " + (Instant.now().toEpochMilli() - start) + "毫秒");
        }
    }
}
