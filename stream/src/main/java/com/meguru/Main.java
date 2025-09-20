package com.meguru;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    /*
    1. 创建流(Stream Creation)
    2. 中间操作(Intermediate Operations)
    3. 终端操作(Terminal Operations)
     */

    public static void main(String[] args) {


    }

    public void streamCreation() throws IOException {
        // 1. 从集合创建流
        List<String> list = List.of("a", "b", "c");
        Stream<String> stream1 = list.stream();
        // 2. 从数组创建流
        String[] strs = {"a", "b", "c"};
        Stream<String> stream2 = Arrays.stream(strs);
        // 3. 使用Stream.of创建流
        Stream<String> stream3 = Stream.of("a", "b", "c");
        // 4. 使用StreamBuilder创建流
        Stream.Builder<Object> builder = Stream.builder();
        builder.add("a")
                .add("b")
                .add("c");
        Stream<Object> stream4 = builder.build();
        // 5. 从文件创建流
        Path path = Paths.get("file.txt");
        try (Stream<String> stream5 = Files.lines(path)) {
            stream5.forEach(System.out::println);
        } catch (Exception e) {
            e.getStackTrace();
        }
        // 6. 基础流的创建
        IntStream stream6 = IntStream.of(1, 2, 3);
        // 7. 无限流的创建
        Stream<String> stream7 = Stream.generate(() -> "Meguru").limit(5);
        // 8. 并行流的创建
        Stream<String> stream8 = stream7.parallel();
    }

    public void intermediateOperations() {
        /*
        1. 筛选和切片(Filtering and Slicing)
        2. 映射(Mapping)
        3. 排序(Sorting)
         */
        List<List<String>> list = List.of(
                List.of(
                        "a",
                        "b",
                        "c"
                ),
                List.of(
                        "ae",
                        "c"
                ),
                List.of(
                        "bad",
                        "c"
                ),
                List.of(
                        "ea",
                        "d"
                )
        );
        Stream<List<String>> stream = list.stream();
        Stream<String> result = stream.flatMap(Collection::stream)
                .distinct()
                .filter(c -> c.length() <= 2)
                .sorted(Comparator.comparingInt(String::length).reversed())
                .map(e -> e + "MapAfter")
                .limit(3)
                .skip(1);
    }

    public void terminalOperations() {
        /*
        1. 查找与匹配(Search and Match)
        2. 集合操作(Aggregation)
        3. 规约操作(Reduction)
        4. 收集操作(Collection)
        5. 迭代操作(Iteration)
         */
        ArrayList<Object> collect = List.of("M", "e", "G", "u", "R", "u").parallelStream()
                .map(String::toLowerCase)
                .collect(
                        Collector.of(
                                // 供应器(Supplier)
                                () -> {
                                    System.out.println("Supplier: new ArrayList");
                                    return new ArrayList<>();
                                },
                                // 累加器(Accumulator)
                                (list, item) -> {
                                    System.out.println("Accumulator: " + item);
                                    list.add(item);
                                },
                                // 组合器(Combiner)
                                (left, right) -> {
                                    System.out.println("Combiner: " + left + "+" + right);
                                    left.addAll(right);
                                    return left;
                                },
                                // 特性(Characteristics)
                                Collector.Characteristics.IDENTITY_FINISH
                        )
                );
    }


}
