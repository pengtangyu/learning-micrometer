package org.learning.micrometer.meters;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.TimeGauge;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeGaugeDemo {

    private static final MeterRegistry registry = new SimpleMeterRegistry();

    public static void main(String[] args) {
        gaugeTest();
    }

    private static void gaugeTest() {

        AtomicInteger count = new AtomicInteger(0);

        TimeGauge.Builder<AtomicInteger> timeGaugeBuilder = TimeGauge.builder("time.gauge", count, TimeUnit.SECONDS, AtomicInteger::get);
        timeGaugeBuilder.register(registry);

        count.addAndGet(1);
        MeterUtils.printAllMetricsDetails(registry);

        count.set(100);
        MeterUtils.printAllMetricsDetails(registry);


    }

}
