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
@Feature("Users - Edge Cases")
public class UsersEdgeTests extends BaseApiTest {

    @Story("Create and delete user (best-effort)")
    @Test(groups = {"edge","users"})
    public void createThenDeleteUser_flow() {
        int id = (int)(System.currentTimeMillis()%100000);
        Map<String,Object> payload = new HashMap<>();
        payload.put("id", id);
        payload.put("userName", "user_" + id);
        payload.put("password", "p@ss" + id);

        given()
            .spec(RequestSpecFactory.spec())
            .body(payload)
        .when()
            .post("/Users")
        .then()
            .statusCode(anyOf(is(200), is(201), is(202)));

        given()
            .spec(RequestSpecFactory.spec())
        .when()
            .delete("/Users/{id}", id)
        .then()
            .statusCode(anyOf(is(200), is(204), is(202)));
    }
}
