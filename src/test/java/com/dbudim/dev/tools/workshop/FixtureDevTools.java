package com.dbudim.dev.tools.workshop;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.nio.file.Path;

import static java.time.Duration.ofSeconds;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Created by dbudim on 02.06.2021
 */

public class FixtureDevTools {

    protected WebDriver driver;
    protected DevTools devTools;
    protected int timeout = 10;

    @BeforeClass
    public void initDevTools() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    protected void openGithub() {
        driver.get("https://github.com/");
        new WebDriverWait(driver, ofSeconds(timeout))
                .until(visibilityOfElementLocated(cssSelector("[type='submit']")));
    }

    protected void openMaps() {
        driver.get("https://www.google.com.ua/maps/");
        new WebDriverWait(driver, ofSeconds(timeout))
                .until(visibilityOfElementLocated(cssSelector("#searchbox")));
    }

    protected void openAudioRecorder() {
        driver.get("https://vocaroo.com/");
        new WebDriverWait(driver, ofSeconds(timeout))
                .until(presenceOfElementLocated(cssSelector(".Recorder .Recorder__button")));
    }

    protected void downloadDriver(Path path) {
        By driverBtn = cssSelector("[href='/91.0.4472.19/chromedriver_mac64.zip']");
        driver.get("https://chromedriver.storage.googleapis.com/index.html?path=91.0.4472.19/");

        new WebDriverWait(driver, ofSeconds(timeout))
                .until(presenceOfElementLocated(driverBtn));

        driver.findElement(driverBtn).click();

        await().pollInSameThread()
                .timeout(10, SECONDS)
                .until(() -> path.toFile().exists());
    }

    protected void startDownloadBigFile() {
        driver.get("https://speed.hetzner.de/");
        var downloadBtn = cssSelector("[href='1GB.bin']");
        new WebDriverWait(driver, ofSeconds(timeout))
                .until(presenceOfElementLocated(downloadBtn));
        driver.findElement(downloadBtn).click();
    }

}
