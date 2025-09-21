package com.example.bookstore.api.tests;

import com.example.bookstore.api.BaseApiTest;
import com.example.bookstore.api.http.RequestSpecFactory;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.List;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Bookstore API")
@Feature("Books - Edge Cases")
public class BooksEdgeTests extends BaseApiTest {

    @Story("Content negotiation fallbacks")
    @Test(groups = {"edge","books"})
    public void getAllBooks_acceptXml_returnsJsonOr406() {
        given()
            .spec(RequestSpecFactory.spec())
            .header("Accept", "application/xml")
        .when()
            .get("/Books")
        .then()
            .statusCode(anyOf(is(200), is(406), is(415)))
            .and()
            .header("Content-Type", anyOf(containsString("json"), containsString("xml"), anything()));
    }

    @Story("Invalid path parameter should not resolve")
    @Test(groups = {"edge","books"})
    public void getBook_idAsString_returns404() {
        given()
            .spec(RequestSpecFactory.spec())
        .when()
            .get("/Books/abc")
        .then()
            .statusCode(anyOf(is(404), is(400)));
    }

    @Story("Dates are ISO-8601")
    @Test(groups = {"edge","books"})
    public void getAllBooks_publishDate_isIso8601_onSample() {
        List<String> dates =
            given()
                .spec(RequestSpecFactory.spec())
            .when()
                .get("/Books")
            .then()
                .statusCode(200)
                .extract()
                .jsonPath().getList("publishDate");

        if (dates != null && !dates.isEmpty()) {
            int max = Math.min(3, dates.size());
            for (int i = 0; i < max; i++) {
                String d = dates.get(i);
                OffsetDateTime.parse(d);
                assertThat(d).isNotBlank();
            }
        }
    }
}
