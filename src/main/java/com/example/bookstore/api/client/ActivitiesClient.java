package com.example.bookstore.api.client;

import com.example.bookstore.api.http.RequestSpecFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ActivitiesClient {

    public Response getAll() {
        return given().spec(RequestSpecFactory.spec())
                .when().get("/Activities")
                .then().extract().response();
    }

    public Response getById(int id) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .when().get("/Activities/{id}")
                .then().extract().response();
    }

    public Response create(Object activity) {
        return given().spec(RequestSpecFactory.spec())
                .body(activity)
                .when().post("/Activities")
                .then().extract().response();
    }

    public Response update(int id, Object activity) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .body(activity)
                .when().put("/Activities/{id}")
                .then().extract().response();
    }

    public Response delete(int id) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .when().delete("/Activities/{id}")
                .then().extract().response();
    }
}
