package com.dbudim.dev.tools.workshop;

import org.openqa.selenium.devtools.v91.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by dbudim on 04.06.2021
 */

public class LogDomainExample extends FixtureDevTools {

    private Logger logger = LoggerFactory.getLogger(LogDomainExample.class);

    @BeforeClass
    public void enableRuntime() {
        devTools.send(Log.enable());
    }

    @Test
    public void logConsole() {
        devTools.addListener(Log.entryAdded(), logEntry -> logger.info(logEntry.getText()));
        driver.get("https://catalog-education.oracle.com/pls/apex/f?p=1010:26:110246492646783");
    }

}
