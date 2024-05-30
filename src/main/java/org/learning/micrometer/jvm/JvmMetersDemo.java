package org.learning.micrometer.jvm;

import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.learning.micrometer.meters.MeterUtils;

public class JvmMetersDemo {

    public static void main(String[] args) {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();

        new JvmMemoryMetrics().bindTo(registry);

        MeterUtils.printAllMetricsDetails(registry);

        /*new Thread(() -> {
            while (true) {
                MeterUtils.printAllMetricsDetails(registry);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();*/
    }
}
