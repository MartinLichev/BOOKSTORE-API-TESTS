package com.example.bookstore.api.client;

import com.example.bookstore.api.http.RequestSpecFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CoverPhotosClient {

    public Response getAll() {
        return given().spec(RequestSpecFactory.spec())
                .when().get("/CoverPhotos")
                .then().extract().response();
    }

    public Response getById(int id) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .when().get("/CoverPhotos/{id}")
                .then().extract().response();
    }

    public Response getByBookId(int bookId) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("idBook", bookId)
                .when().get("/CoverPhotos/books/covers/{idBook}")
                .then().extract().response();
    }

    public Response create(Object coverPhoto) {
        return given().spec(RequestSpecFactory.spec())
                .body(coverPhoto)
                .when().post("/CoverPhotos")
                .then().extract().response();
    }

    public Response update(int id, Object coverPhoto) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .body(coverPhoto)
                .when().put("/CoverPhotos/{id}")
                .then().extract().response();
    }

    public Response delete(int id) {
        return given().spec(RequestSpecFactory.spec())
                .pathParam("id", id)
                .when().delete("/CoverPhotos/{id}")
                .then().extract().response();
    }
}
