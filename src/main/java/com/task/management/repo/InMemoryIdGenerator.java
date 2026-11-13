package com.task.management.repo;

import java.util.concurrent.atomic.AtomicLong;

public class InMemoryIdGenerator {
    private final AtomicLong counter = new AtomicLong(1);
    public long next() { return counter.getAndIncrement(); }
}
