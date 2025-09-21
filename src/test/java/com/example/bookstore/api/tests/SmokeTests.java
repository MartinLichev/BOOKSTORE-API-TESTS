package com.example.bookstore.api.tests;

import com.example.bookstore.api.BaseApiTest;
import com.example.bookstore.api.http.RequestSpecFactory;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Bookstore API")
@Feature("Smoke")
public class SmokeTests extends BaseApiTest {

    @Story("Books list available")
    @Test(groups = {"smoke","books"})
    public void booksList_smoke() {
        given().spec(RequestSpecFactory.spec()).when().get("/Books").then().statusCode(200).body("size()", greaterThan(0));
    }

    @Story("Books by id available")
    @Test(groups = {"smoke","books"})
    public void booksById_smoke() {
        given().spec(RequestSpecFactory.spec()).when().get("/Books/{id}", 1).then().statusCode(200).body("id", equalTo(1));
    }

    @Story("Authors list available")
    @Test(groups = {"smoke","authors"})
    public void authorsList_smoke() {
        given().spec(RequestSpecFactory.spec()).when().get("/Authors").then().statusCode(200);
    }

    @Story("Activities list available")
    @Test(groups = {"smoke","activities"})
    public void activitiesList_smoke() {
        given().spec(RequestSpecFactory.spec()).when().get("/Activities").then().statusCode(200);
    }

    @Story("CoverPhotos list available")
    @Test(groups = {"smoke","covers"})
    public void coversList_smoke() {
        given().spec(RequestSpecFactory.spec()).when().get("/CoverPhotos").then().statusCode(200);
    }

    @Story("Users list available")
    @Test(groups = {"smoke","users"})
    public void usersList_smoke() {
        given().spec(RequestSpecFactory.spec()).when().get("/Users").then().statusCode(200);
    }
}
