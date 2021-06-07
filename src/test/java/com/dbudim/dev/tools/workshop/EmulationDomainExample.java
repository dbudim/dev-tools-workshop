package com.dbudim.dev.tools.workshop;

import org.openqa.selenium.devtools.v91.emulation.Emulation;
import org.testng.annotations.Test;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Created by dbudim on 04.06.2021
 */

public class EmulationDomainExample extends FixtureDevTools {

    @Test
    public void overrideUserAgentOnFly() {
        devTools.send(Emulation.setUserAgentOverride("super QA", empty(), empty(), empty()));
        openGithub();
    }

    @Test
    public void emulateScreenDimensions() {
        devTools.send(Emulation.setDeviceMetricsOverride(360, 640, 3, true, empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty()));
        openGithub();
    }

    //check -> Intl.DateTimeFormat().resolvedOptions().timeZone
    @Test
    public void emulateGeoAndTimeZone() {
        devTools.send(Emulation.setGeolocationOverride(of(21.35807834017623), of(-157.84336469514923), of(12.78)));
        devTools.send(Emulation.setTimezoneOverride("Pacific/Honolulu"));
        openMaps();
    }

    @Test
    public void emulatePageScale() {
        devTools.send(Emulation.setPageScaleFactor(250));
        openGithub();
    }
}
