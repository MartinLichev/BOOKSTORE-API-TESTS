package com.example.bookstore.api.tests;

import com.example.bookstore.api.BaseApiTest;
import com.example.bookstore.api.client.CoverPhotosClient;
import com.example.bookstore.api.models.CoverPhoto;
import com.example.bookstore.api.util.IdGenerator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Bookstore API")
@Feature("CoverPhotos")
public class CoverPhotosApiTests extends BaseApiTest {

    private CoverPhotosClient covers;

    @BeforeClass
    public void init() {
        covers = new CoverPhotosClient();
    }

    @Story("GET all cover photos")
    @Test
    public void getAllCoverPhotos_returns200AndNonEmptyList() {
        Response res = covers.getAll();
        res.then().statusCode(200);
        CoverPhoto[] list = res.as(CoverPhoto[].class);
        assertThat(list).isNotNull();
        assertThat(list.length).isGreaterThan(0);
    }

    @Story("GET cover photo by id - existing")
    @Test
    public void getCoverPhotoById_existing_returns200() {
        Response res = covers.getById(1);
        res.then().statusCode(200);
        CoverPhoto p = res.as(CoverPhoto.class);
        assertThat(p.getId()).isEqualTo(1);
    }

    @Story("GET cover photos by book id")
    @Test
    public void getCoverPhotosByBookId_returns200() {
        Response res = covers.getByBookId(1);
        res.then().statusCode(200);
        CoverPhoto[] list = res.as(CoverPhoto[].class);
        assertThat(list).isNotNull();
    }

    @Story("POST create cover photo")
    @Test
    public void createCoverPhoto_returns200AndEchoesPayload() {
        CoverPhoto payload = new CoverPhoto(IdGenerator.largeId(), 1, "https://example.com/cover.png");
        Response res = covers.create(payload);
        res.then().statusCode(200);
        CoverPhoto body = res.as(CoverPhoto.class);
        assertThat(body.getId()).isEqualTo(payload.getId());
        assertThat(body.getUrl()).isEqualTo(payload.getUrl());
    }

    @Story("PUT update cover photo")
    @Test
    public void updateCoverPhoto_returns200() {
        int id = 1;
        CoverPhoto payload = new CoverPhoto(id, 1, "https://example.com/cover-updated.png");
        Response res = covers.update(id, payload);
        res.then().statusCode(200);
        CoverPhoto body = res.as(CoverPhoto.class);
        assertThat(body.getId()).isEqualTo(id);
        assertThat(body.getUrl()).isEqualTo("https://example.com/cover-updated.png");
    }

    @Story("DELETE cover photo")
    @Test
    public void deleteCoverPhoto_returns200() {
        Response res = covers.delete(1);
        res.then().statusCode(200);
    }
}
