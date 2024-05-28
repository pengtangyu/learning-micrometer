package org.learning.micrometer.meters;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class TimerDemo {

    private static final MeterRegistry registry = new SimpleMeterRegistry();

    public static void main(String[] args) {
        timerTest1();

        timerTest2();

        MeterUtils.printAllMetricsDetails(registry);
    }

    private static void timerTest1() {
        Timer timer = Timer.builder("exec.speed")
                .description("description")
                .tag("method", "methodA")
                .register(registry);

        timer.record(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        timer.record(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void timerTest2() {
        Timer.Sample sample = Timer.start(registry);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        sample.stop(registry.timer("exec.speed", "method", "methodB"));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        sample.stop(registry.timer("exec.speed", "method", "methodB"));
    }

}
