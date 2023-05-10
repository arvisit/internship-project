package by.itacademy.profiler.util;

import java.util.Random;

public final class SecondaryGenerator {

    private SecondaryGenerator() {}

    private static final Random RND = new Random();

    public static String generateRndStr(int length) {

        int leftLimit = 97;
        int rightLimit = 122;

        return RND.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

    }
}
