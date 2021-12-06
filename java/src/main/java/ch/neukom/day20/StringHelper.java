package ch.neukom.day20;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringHelper {
    private StringHelper() {
    }

    public static String reverseString(String string) {
        return new StringBuilder(string).reverse().toString();
    }

    public static String turnStringLeft(String string) {
        String[] split = string.split("\n");
        return IntStream.range(0, split.length)
                .mapToObj(i -> Arrays.stream(split)
                        .map(line -> line.charAt(split.length - 1 - i))
                        .reduce("", (s, character) -> s + character, String::concat))
                .collect(Collectors.joining("\n"));
    }

    public static String flipStringHorizontally(String string) {
        return Arrays.stream(string.split("\n"))
                .map(StringHelper::reverseString)
                .collect(Collectors.joining("\n"));
    }
}
