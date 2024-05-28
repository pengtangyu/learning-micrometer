package org.learning.micrometer.meters;

import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.FunctionTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class FunctionTimerDemo {

    private static final MeterRegistry registry = new SimpleMeterRegistry();

    public static void main(String[] args) {
        timerTest();

        MeterUtils.printAllMetricsDetails(registry);
    }

    private static void timerTest() {
        AtomicLong totalTimeNanos = new AtomicLong(0);
        AtomicLong totalCount = new AtomicLong(0);

        FunctionTimer.builder("function.exec.time",
                new Object(),
                p -> totalCount.get(),
                p -> totalTimeNanos.get(),
                TimeUnit.MILLISECONDS).tags("method", "methodA").register(registry);

        totalTimeNanos.addAndGet(500);
        totalCount.incrementAndGet();
    }

}
