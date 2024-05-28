package org.learning.micrometer.meters;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.LinkedList;

public class GaugeDemo {

    private static final MeterRegistry registry = new SimpleMeterRegistry();

    public static void main(String[] args) {
        gaugeTest();
    }

    private static void gaugeTest() {
        LinkedList<String> queue = new LinkedList<>();

        LinkedList<String> gaugeLinkedList = registry.gauge("gauge.queue", queue, LinkedList::size);

        // 打印
        new Thread(() -> {
            for (int i = 0; i < 220; i++) {
                MeterUtils.printAllMetricsDetails(registry);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        // 模拟生产
        new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                gaugeLinkedList.add("test" + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();


        // 模拟消费
        new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                gaugeLinkedList.poll();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

}
