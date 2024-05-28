package org.learning.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class MeterRegistryDemo {

    public static void main(String[] args) {

        createSimpleMeterRegistry();

        createCompositeMeterRegistry();

        createGlobalMeterRegistry();
    }

    /**
     * 每个Meter的最新数据可以收集到SimpleMeterRegistry实例中，但是这些数据不会发布到其他系统，也就是数据是位于应用的内存中的。
     */
    private static void createSimpleMeterRegistry() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();

        Counter counter = registry.counter("counter");
        counter.increment();

        System.out.println(counter.count());
    }

    /**
     * 多个MeterRegistry聚合，内部维护了一个MeterRegistry的列表。
     */
    private static void createCompositeMeterRegistry() {
        CompositeMeterRegistry compositeMeterRegistry = new CompositeMeterRegistry();

        Counter counter = compositeMeterRegistry.counter("counter");
        // 操作无效，CompositeMeterRegistry例初始化的时候，内部持有的MeterRegistry列表是空的，如果此时用它新增一个Meter实例，Meter实例的操作是无效的
        counter.increment();
        System.out.println(counter.count());

        SimpleMeterRegistry simpleMeterRegistry = new SimpleMeterRegistry();
        compositeMeterRegistry.add(simpleMeterRegistry);

        counter.increment();
        System.out.println(counter.count());
    }

    private static void createGlobalMeterRegistry() {
        Metrics.addRegistry(new SimpleMeterRegistry());

        Counter counter = Metrics.counter("counter");
        counter.increment();
        System.out.println(counter.count());
    }
}
