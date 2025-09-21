package com.example.bookstore.api.http;

import com.example.bookstore.api.config.Config;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {

    private static volatile RequestSpecification cached;

    private RequestSpecFactory() {}

    public static RequestSpecification spec() {
        if (cached == null) {
            synchronized (RequestSpecFactory.class) {
                if (cached == null) {
                    RequestSpecBuilder b = new RequestSpecBuilder()
                            .setBaseUri(Config.baseUrl())
                            .setBasePath(Config.apiBasePath());

                    String mediaType = Config.useVersionedMediaType()
                            ? Config.versionedMediaType()
                            : "application/json";

                    b.addHeader("Accept", mediaType);
                    b.addHeader("Content-Type", mediaType);

                    // Allure integration + log on failure
                    AllureRestAssured allure = new AllureRestAssured();
                    b.addFilter(allure);
                    if (Config.logOnFailure()) {
                        b.addFilter(new ErrorLoggingFilter());
                    }

                    cached = b.build();
                }
            }
        }
        return cached;
    }
}
