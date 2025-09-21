package com.example.bookstore.api.tests;

import com.example.bookstore.api.BaseApiTest;
import com.example.bookstore.api.http.RequestSpecFactory;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Bookstore API")
@Feature("CoverPhotos - Edge Cases")
public class CoverPhotosEdgeTests extends BaseApiTest {

    @Story("Filter by book id should be consistent")
    @Test(groups = {"edge","covers"})
    public void getCoversByBookId_allHaveSameIdBook() {
        int idBook = 1;
        given()
            .spec(RequestSpecFactory.spec())
        .when()
            .get("/CoverPhotos/books/covers/{idBook}", idBook)
        .then()
            .statusCode(200)
            .body("idBook", everyItem(equalTo(idBook)));
    }

    @Story("Invalid URL payload yields non-2xx or echo (demo API)")
    @Test(groups = {"edge","covers"})
    public void postCoverPhoto_withInvalidUrl_expect4xxOrEcho() {
        Map<String,Object> payload = new HashMap<>();
        payload.put("id", (int)(System.currentTimeMillis()%100000));
        payload.put("idBook", 1);
        payload.put("url", "not-a-url");

        given()
            .spec(RequestSpecFactory.spec())
            .body(payload)
        .when()
            .post("/CoverPhotos")
        .then()
            .statusCode(anyOf(greaterThanOrEqualTo(400), is(200)));
    }
}
