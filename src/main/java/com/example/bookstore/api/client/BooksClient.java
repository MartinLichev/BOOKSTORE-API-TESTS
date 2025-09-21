package com.example.bookstore.api.client;

import com.example.bookstore.api.http.RequestSpecFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BooksClient {

    public Response getAll() {
        return given().spec(RequestSpecFactory.spec())
                .when().get("/Books")
                .then().extract().response();
    }

    public Response getById(int id) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .when().get("/Books/{id}")
                .then().extract().response();
    }

    public Response create(Object book) {
        return given().spec(RequestSpecFactory.spec())
                .body(book)
                .when().post("/Books")
                .then().extract().response();
    }

    public Response update(int id, Object book) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .body(book)
                .when().put("/Books/{id}")
                .then().extract().response();
    }

    public Response delete(int id) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .when().delete("/Books/{id}")
                .then().extract().response();
    }
}
