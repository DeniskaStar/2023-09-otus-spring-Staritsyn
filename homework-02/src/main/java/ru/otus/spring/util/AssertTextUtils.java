package ru.otus.spring.util;

public class AssertTextUtils {

    public static String notNull(String name) {
        return String.format("[%s] must not be null", name);
    }

    public static String collectionNotEmpty(String name) {
        return String.format("Collection [%s] not must be empty", name);
    }
}
