package com.dbudim.dev.tools.workshop;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.openqa.selenium.devtools.v91.log.Log;
import org.openqa.selenium.devtools.v91.network.Network;
import org.openqa.selenium.devtools.v91.network.model.ConnectionType;
import org.openqa.selenium.devtools.v91.network.model.Cookie;
import org.openqa.selenium.devtools.v91.network.model.Headers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Created by dbudim on 02.06.2021
 */

public class NetworkDomainExample extends FixtureDevTools {

    @BeforeClass
    public void enableNetwork() {
        devTools.send(Network.enable(empty(), empty(), empty()));
    }

    @Test
    public void emulateConnectionMode() {
        devTools.send(Network.emulateNetworkConditions(false, 100, 50000, 50000, of(ConnectionType.CELLULAR3G)));
        openGithub();
    }

    @Test
    public void setExtraHeaders() {
        devTools.send(Network.setExtraHTTPHeaders(
                new Headers(Map.of(
                        "my-first-header", "John WIck",
                        "my-second-header", "second value"
                ))));
        openGithub();
    }

    @Test
    public void manageCookies() {
        openGithub();
        List<Cookie> cookies = devTools.send(Network.getCookies(empty()));
        devTools.send(Network.clearBrowserCookies());
    }

    @Test
    public void blockTraffic() {
        devTools.send(Network.setBlockedURLs(List.of("*assets*")));
        devTools.send(Log.enable());
        devTools.addListener(Log.entryAdded(), logEntry -> System.out.println(logEntry));
        openGithub();
    }

    @Test
    public void getResponseData() {
        var data = new AtomicReference<List<Data>>();
        devTools.addListener(Network.responseReceived(), r ->
                {
                    if (r.getResponse().getUrl().contains("data.json")) {
                        Type type = new TypeToken<List<Data>>() {
                        }.getType();
                        var response = devTools.send(Network.getResponseBody(r.getRequestId())).getBody();
                        data.set(new Gson().fromJson(response, type));
                    }

                }
        );
        openGithub();
    }

    class Data {
        public String uml;
        public Coordinates gm;
        public Coordinates gop;
        public String nwo;
        public String ma;
        public String oa;

        class Coordinates {
            public Double lat;
            public Double lon;
        }

    }

}
