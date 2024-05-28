package org.learning.micrometer.meters;

import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class LongTaskTimerDemo {

    private static final MeterRegistry registry = new SimpleMeterRegistry();

    public static void main(String[] args) {
        timerTest1();

        timerTest2();

        MeterUtils.printAllMetricsDetails(registry);
    }

    private static void timerTest1() {


        LongTaskTimer longTaskTimer = LongTaskTimer.builder("longTaskTimer.exec.speed")
                .description("description")
                .tag("method", "methodA")
                .register(registry);

        longTaskTimer.record(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        longTaskTimer.record(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void timerTest2() {
        LongTaskTimer longTaskTimer = registry.more().longTaskTimer("longTaskTimer.exec.speed", "method", "methodB");

        longTaskTimer.record(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        longTaskTimer.record(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
