package com.example.bookstore.api;

import com.example.bookstore.api.config.Config;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public abstract class BaseApiTest {

    @BeforeClass(alwaysRun = true)
    public void configure() {
        if (Config.logOnFailure()) {
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        }
    }
}
