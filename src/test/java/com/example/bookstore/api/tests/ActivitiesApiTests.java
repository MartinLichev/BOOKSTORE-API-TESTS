package com.example.bookstore.api.tests;

import com.example.bookstore.api.BaseApiTest;
import com.example.bookstore.api.client.ActivitiesClient;
import com.example.bookstore.api.models.Activity;
import com.example.bookstore.api.util.IdGenerator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Bookstore API")
@Feature("Activities")
public class ActivitiesApiTests extends BaseApiTest {

    private ActivitiesClient activities;
    private Faker faker;

    @BeforeClass
    public void init() {
        activities = new ActivitiesClient();
        faker = new Faker();
    }

    @Story("GET all activities")
    @Test
    public void getAllActivities_returns200AndNonEmptyList() {
        Response res = activities.getAll();
        res.then().statusCode(200);
        Activity[] list = res.as(Activity[].class);
        assertThat(list).isNotNull();
        assertThat(list.length).isGreaterThan(0);
    }

    @Story("GET activity by id - existing")
    @Test
    public void getActivityById_existing_returns200() {
        Response res = activities.getById(1);
        res.then().statusCode(200);
        Activity a = res.as(Activity.class);
        assertThat(a.getId()).isEqualTo(1);
    }

    @Story("GET activity by id - not found")
    @Test
    public void getActivityById_notFound_returns404() {
        Response res = activities.getById(Integer.MAX_VALUE);
        assertThat(res.statusCode()).isIn(404, 400);
    }

    @Story("POST create activity")
    @Test
    public void createActivity_returns200AndEchoesPayload() {
        Activity payload = new Activity(
                IdGenerator.largeId(),
                "Task " + faker.lorem().word(),
                Instant.now().toString(),
                true);
        Response res = activities.create(payload);
        res.then().statusCode(200);
        Activity body = res.as(Activity.class);
        assertThat(body.getId()).isEqualTo(payload.getId());
        assertThat(body.getTitle()).isEqualTo(payload.getTitle());
    }

    @Story("PUT update activity")
    @Test
    public void updateActivity_returns200() {
        int id = 1;
        Activity payload = new Activity(id, "Updated", Instant.now().toString(), false);
        Response res = activities.update(id, payload);
        res.then().statusCode(200);
        Activity body = res.as(Activity.class);
        assertThat(body.getId()).isEqualTo(id);
        assertThat(body.getTitle()).isEqualTo("Updated");
    }

    @Story("DELETE activity")
    @Test
    public void deleteActivity_returns200() {
        Response res = activities.delete(1);
        res.then().statusCode(200);
    }
}
