package com.example.bookstore.api.client;

import com.example.bookstore.api.http.RequestSpecFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthorsClient {

    public Response getAll() {
        return given().spec(RequestSpecFactory.spec())
                .when().get("/Authors")
                .then().extract().response();
    }

    public Response getById(int id) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .when().get("/Authors/{id}")
                .then().extract().response();
    }

    public Response getByBookId(int bookId) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("idBook", bookId)
                .when().get("/Authors/authors/books/{idBook}")
                .then().extract().response();
    }

    public Response create(Object author) {
        return given().spec(RequestSpecFactory.spec())
                .body(author)
                .when().post("/Authors")
                .then().extract().response();
    }

    public Response update(int id, Object author) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .body(author)
                .when().put("/Authors/{id}")
                .then().extract().response();
    }

    public Response delete(int id) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .when().delete("/Authors/{id}")
                .then().extract().response();
    }
}
