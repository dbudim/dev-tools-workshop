package com.dbudim.dev.tools.workshop;


import org.openqa.selenium.devtools.v91.browser.Browser;
import org.openqa.selenium.devtools.v91.browser.model.PermissionType;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.System.getProperty;
import static java.nio.file.FileVisitOption.FOLLOW_LINKS;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.awaitility.Awaitility.await;

/**
 * Created by dbudim on 04.06.2021
 */

public class BrowserDomainExample extends FixtureDevTools {

    @Test
    public void grantPermissions() {
        devTools.send(Browser.grantPermissions(List.of(PermissionType.AUDIOCAPTURE), empty(), empty()));
        openAudioRecorder();
    }

    @Test
    public void setDownloadBehaviour() {
        var path = Paths.get(getProperty("user.home"), "Downloads", "super_AQA").toAbsolutePath().toString();
        devTools.send(Browser.setDownloadBehavior(Browser.SetDownloadBehaviorBehavior.ALLOW, Optional.empty(), Optional.of(path), of(true)));
        downloadDriver(Paths.get(path, "chromedriver_mac64.zip"));
    }

    @Test
    public void cancelDownload() throws IOException {
        var path = Paths.get(getProperty("user.home"), "Downloads").toAbsolutePath().toString();
        devTools.send(Browser.setDownloadBehavior(Browser.SetDownloadBehaviorBehavior.ALLOWANDNAME, Optional.empty(), Optional.of(path), of(true)));
        startDownloadBigFile();
        await().pollInSameThread()
                .until(() -> !Files.walk(Path.of(path), 2, FOLLOW_LINKS).collect(Collectors.toList()).isEmpty());

        String guid = Files.walk(Path.of(path), 1, FOLLOW_LINKS).filter(f -> f.toString().contains(".crdownload")).findFirst().get().getName(3).toString().replaceAll(".crdownload", "");
        devTools.send(Browser.cancelDownload(guid, empty()));
    }

}
