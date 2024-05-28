package org.learning.micrometer.meters;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.search.Search;

public class MeterUtils {

    public static void printAllMetricsDetails(MeterRegistry registry) {
        Search.in(registry).meters().forEach(each -> {
            String builder = "name:" +
                    each.getId().getName() +
                    ",tags:" +
                    each.getId().getTags() +
                    ",type:" + each.getId().getType() +
                    ",value:" + each.measure();
            System.out.println(builder);
        });
    }


}
