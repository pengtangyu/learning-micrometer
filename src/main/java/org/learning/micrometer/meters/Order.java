package org.learning.micrometer.meters;

import java.time.LocalDateTime;

public record Order(
        String orderId,
        Integer amount,
        String channel,
        LocalDateTime createTime
) {
    public static Order createOrder(String orderId, Integer amount, String channel, LocalDateTime createTime) {
        return new Order(orderId, amount, channel, createTime);
    }
}
