package ru.otus.spring.util;

public class AssertTextUtils {

    public static String notNull(String name) {
        return String.format("[%s] must not be null", name);
    }
}
