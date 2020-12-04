package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
//      List<String> strings = new ArrayList<String>();
//      strings.add("blue");
//      strings.add("red");
//      System.out.println(strings);

//      List<String> list = List.of("mad", "play");
//      System.out.println(list);

//      String[] arr = {"blue", "red"};
//      Stream<String> specificStream = Arrays.stream(arr, 0, 1);
//      // mad
//      specificStream.forEach(System.out::println);

//      List<String> list = List.of("blue", "red", "...");
//      Stream<String> stringStream = list.parallelStream();
//
//      stringStream.forEach(System.out::println);

        /*
         * 스트림과 병렬 처리.
         * */
        List<String> list = Arrays.asList("John", "Simon", "Andy", "Andrew", "Bill");

        // 순차 처리
        Stream<String> stream = list.stream();
        stream.forEach(Main::print);

        System.out.println();

        // 병렬 처리
        Stream<String> parallelStream = list.parallelStream();
        parallelStream.forEach(Main::print);
    }

    public static void print(String str) {
        System.out.println(str + " : " + Thread.currentThread().getName());
    }

}
