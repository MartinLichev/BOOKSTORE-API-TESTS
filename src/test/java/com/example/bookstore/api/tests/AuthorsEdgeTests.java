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
@Feature("Authors - Edge Cases")
public class AuthorsEdgeTests extends BaseApiTest {

    @Story("Filter by book id should be consistent")
    @Test(groups = {"edge","authors"})
    public void getAuthorsByBookId_allHaveSameIdBook() {
        int idBook = 1;
        given()
            .spec(RequestSpecFactory.spec())
        .when()
            .get("/Authors/authors/books/{idBook}", idBook)
        .then()
            .statusCode(200)
            .body("idBook", everyItem(equalTo(idBook)));
    }

    @Story("Invalid path param should 404")
    @Test(groups = {"edge","authors"})
    public void getAuthor_idAsString_returns404() {
        given()
            .spec(RequestSpecFactory.spec())
        .when()
            .get("/Authors/abc")
        .then()
            .statusCode(anyOf(is(404), is(400)));
    }
}
