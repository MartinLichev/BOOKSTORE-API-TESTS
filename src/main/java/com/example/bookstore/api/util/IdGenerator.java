package com.example.bookstore.api.util;

import java.util.concurrent.ThreadLocalRandom;

public final class IdGenerator {
    private IdGenerator() {}

    public static int largeId() {
        return ThreadLocalRandom.current().nextInt(1_000_000, Integer.MAX_VALUE);
    }
}
