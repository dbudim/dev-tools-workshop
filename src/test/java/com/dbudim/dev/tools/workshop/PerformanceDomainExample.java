package com.dbudim.dev.tools.workshop;

import org.openqa.selenium.devtools.v91.performance.Performance;
import org.openqa.selenium.devtools.v91.performance.model.Metric;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static java.util.Optional.empty;

/**
 * Created by dbudim on 07.06.2021
 */

public class PerformanceDomainExample extends FixtureDevTools {

    @BeforeClass
    public void enablePerformance() {
        devTools.send(Performance.enable(empty()));
    }

    @Test
    public void capturePerformanceLogs() {
        openGithub();
        List<Metric> metrics = devTools.send(Performance.getMetrics());
        metrics.stream().forEach(m -> System.out.println(String.format("%s: %s", m.getName(), m.getValue())));
    }
}
