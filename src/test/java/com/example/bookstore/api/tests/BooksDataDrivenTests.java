package com.example.bookstore.api.tests;

import com.example.bookstore.api.BaseApiTest;
import com.example.bookstore.api.http.RequestSpecFactory;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Bookstore API")
@Feature("Books - Data Driven")
public class BooksDataDrivenTests extends BaseApiTest {

    @Story("GET /Books/{id} for multiple ids")
    @Test(dataProvider = "bookIds", dataProviderClass = DataProviders.class,
          groups = {"regression","books"})
    public void getBook_multipleIds_returns200(int id) {
        given()
            .spec(RequestSpecFactory.spec())
        .when()
            .get("/Books/{id}", id)
        .then()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("title", notNullValue())
            .body("pageCount", greaterThanOrEqualTo(0))
            .body("publishDate", notNullValue());
    }

    @Story("GET /Books with various Accept headers")
    @Test(dataProvider = "acceptMatrix", dataProviderClass = DataProviders.class,
          groups = {"regression","books"})
    public void getBooks_acceptMatrix(String accept) {
        given()
            .spec(RequestSpecFactory.spec())
            .header("Accept", accept)
        .when()
            .get("/Books")
        .then()
            .statusCode(anyOf(is(200), is(406), is(415)))
            .header("Content-Type", anyOf(containsString("json"), containsString("xml"), anything()));
    }
}
