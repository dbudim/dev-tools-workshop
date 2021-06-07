package com.dbudim.dev.tools.workshop;

import org.openqa.selenium.devtools.v91.security.Security;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by dbudim on 07.06.2021
 */

public class SecurityDomainExample extends FixtureDevTools {

    @BeforeClass
    public void enableSecurity() {
        devTools.send(Security.enable());
    }

    @Test
    public void manageSslCheck() {
        devTools.send(Security.setIgnoreCertificateErrors(true));
        driver.get("https://expired.badssl.com/");
    }

}
