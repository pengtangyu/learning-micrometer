package org.learning.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.Arrays;
import java.util.List;

public class TagAndNameDemo {

    public static void main(String[] args) {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();

        // Micrometer中，Meter的命名约定使用英文逗号(dot，也就是".")分隔单词
        Counter counter = registry.counter("http.server.requests");

        // tag: Tag必须成对出现,实际上它们以Key=Value的形式存在
        registry.counter("database.calls", "db", "users");
        registry.counter("http.requests", "uri", "/api/users");

        // 定义全局tag
        registry.config().commonTags("stack", "prod", "region", "us-east-1");
        // Tag.of()方法定义
        registry.config().commonTags(List.of(Tag.of("stack", "prod"), Tag.of("region", "us-east-1")));

        // 过滤标签
        // 表示忽略"http"标签，拒绝名称以"jvm"字符串开头的Meter
        registry.config()
                .meterFilter(MeterFilter.ignoreTags("http"))
                .meterFilter(MeterFilter.denyNameStartsWith("jvm"));

    }
}
