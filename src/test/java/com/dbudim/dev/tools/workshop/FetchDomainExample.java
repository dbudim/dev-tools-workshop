package com.dbudim.dev.tools.workshop;


import org.openqa.selenium.devtools.v91.fetch.Fetch;
import org.openqa.selenium.devtools.v91.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v91.network.model.ErrorReason;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Created by dbudim on 04.06.2021
 */

public class FetchDomainExample extends FixtureDevTools {

    @Test
    public void changeRequests() {
        Optional<List<RequestPattern>> patterns = of(asList(new RequestPattern(of("*/posts"), empty(), empty())));
        devTools.send(Fetch.enable(patterns, empty()));
        devTools.addListener(Fetch.requestPaused(),
                request -> {
                    var url = request.getRequest().getUrl().contains("/posts")
                            ? request.getRequest().getUrl().replaceAll("/posts", "/users")
                            : request.getRequest().getUrl();

                    devTools.send(
                            Fetch.continueRequest(
                                    request.getRequestId(),
                                    Optional.of(url),
                                    Optional.of(request.getRequest().getMethod()),
                                    request.getRequest().getPostData(),
                                    request.getResponseHeaders()
                            ));
                });
        driver.get("https://jsonplaceholder.typicode.com/posts");
    }

    @Test
    public void failRequests() {
        Optional<List<RequestPattern>> patterns = of(asList(new RequestPattern(of("*.json"), empty(), empty())));
        devTools.send(Fetch.enable(patterns, empty()));
        devTools.addListener(Fetch.requestPaused(), request -> devTools.send(Fetch.failRequest(request.getRequestId(), ErrorReason.FAILED)));
        openGithub();
    }

}
