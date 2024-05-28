package org.learning.micrometer.meters;

import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * FunctionCounter使用的一个明显的好处是，我们不需要感知FunctionCounter实例的存在，实际上我们只需要操作作为FunctionCounter实例构建元素之一的AtomicInteger实例即可
 */
public class FunctionCounterDemo {

    public static void main(String[] args) {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();

        AtomicInteger atomicInteger = new AtomicInteger(0);
        FunctionCounter.builder("functionCounter", atomicInteger, AtomicInteger::get)
                .baseUnit("unit")
                .description("function counter")
                .tag("key", "value")
                .register(registry);

        atomicInteger.incrementAndGet();
        atomicInteger.incrementAndGet();
        atomicInteger.incrementAndGet();

        MeterUtils.printAllMetricsDetails(registry);
    }
}
