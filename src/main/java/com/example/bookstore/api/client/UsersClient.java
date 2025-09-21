package com.example.bookstore.api.client;

import com.example.bookstore.api.http.RequestSpecFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UsersClient {

    public Response getAll() {
        return given().spec(RequestSpecFactory.spec())
                .when().get("/Users")
                .then().extract().response();
    }

    public Response getById(int id) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .when().get("/Users/{id}")
                .then().extract().response();
    }

    public Response create(Object user) {
        return given().spec(RequestSpecFactory.spec())
                .body(user)
                .when().post("/Users")
                .then().extract().response();
    }

    public Response update(int id, Object user) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .body(user)
                .when().put("/Users/{id}")
                .then().extract().response();
    }

    public Response delete(int id) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .when().delete("/Users/{id}")
                .then().extract().response();
    }
}
