package by.itacademy.profiler.util;

import java.util.Random;
import java.util.stream.Collector;

public final class StringTestData {

    private static final int A_LOWERCASE = 97;
    private static final int Z_LOWERCASE = 122;

    private static final Random RANDOM = new Random();

    private StringTestData() {}

    public static String createLowerCaseASCIIString(int length) {
        return RANDOM.ints(length, A_LOWERCASE, Z_LOWERCASE + 1)
                .mapToObj(i -> Character.valueOf((char) i))
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }
}
