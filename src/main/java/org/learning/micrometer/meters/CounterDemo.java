package org.learning.micrometer.meters;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CounterDemo {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    static {
        Metrics.addRegistry(new SimpleMeterRegistry());
    }

    public static void main(String[] args) {
        Order order1 = Order.createOrder("1", 100, "channel_1", LocalDateTime.now());
        metric(order1);

        Order order2 = Order.createOrder("2", 200, "channel_2", LocalDateTime.now());
        metric(order2);

        MeterUtils.printAllMetricsDetails(Metrics.globalRegistry);
    }

    private static void metric(Order order) {
        // 两种创建counter的方式

        /*Counter counter = Metrics.counter("order.create",
                List.of(
                        Tag.of("channel", order.channel()),
                        Tag.of("createTime", FORMATTER.format(order.createTime()))
                )
        );
        counter.increment();*/

        Counter counter = Counter.builder("order.create")
                .baseUnit("unit")
                .description("description")
                .tag("channel", order.channel())
                .tag("createTime", FORMATTER.format(order.createTime()))
                .register(Metrics.globalRegistry);
        counter.increment();
    }
}
