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
@Feature("Authors - Data Driven")
public class AuthorsDataDrivenTests extends BaseApiTest {

    @Story("GET /Authors/{id} for multiple ids")
    @Test(dataProvider = "authorIds", dataProviderClass = DataProviders.class,
          groups = {"regression","authors"})
    public void getAuthor_multipleIds_returns200(int id) {
        given()
            .spec(RequestSpecFactory.spec())
        .when()
            .get("/Authors/{id}", id)
        .then()
            .statusCode(200)
            .body("id", equalTo(id));
    }

    @Story("GET /Authors/authors/books/{idBook} for multiple idBook values")
    @Test(dataProvider = "idBooks", dataProviderClass = DataProviders.class,
          groups = {"regression","authors"})
    public void getAuthorsByBookId_multiple(int idBook) {
        given()
            .spec(RequestSpecFactory.spec())
        .when()
            .get("/Authors/authors/books/{idBook}", idBook)
        .then()
            .statusCode(200)
            .body("idBook", everyItem(equalTo(idBook)));
    }
}
