package com.example.bookstore.api.tests;

import com.example.bookstore.api.BaseApiTest;
import com.example.bookstore.api.client.UsersClient;
import com.example.bookstore.api.models.User;
import com.example.bookstore.api.util.IdGenerator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Bookstore API")
@Feature("Users")
public class UsersApiTests extends BaseApiTest {

    private UsersClient users;
    private Faker faker;

    @BeforeClass
    public void init() {
        users = new UsersClient();
        faker = new Faker();
    }

    @Story("GET all users")
    @Test
    public void getAllUsers_returns200AndNonEmptyList() {
        Response res = users.getAll();
        res.then().statusCode(200);
        User[] list = res.as(User[].class);
        assertThat(list).isNotNull();
        assertThat(list.length).isGreaterThan(0);
    }

    @Story("GET user by id - existing")
    @Test
    public void getUserById_existing_returns200() {
        Response res = users.getById(1);
        res.then().statusCode(200);
        User u = res.as(User.class);
        assertThat(u.getId()).isEqualTo(1);
    }

    @Story("GET user by id - not found")
    @Test
    public void getUserById_notFound_returns404() {
        Response res = users.getById(Integer.MAX_VALUE);
        assertThat(res.statusCode()).isIn(404, 400);
    }

    @Story("POST create user")
    @Test
    public void createUser_returns200AndEchoesPayload() {
        User payload = new User(IdGenerator.largeId(), faker.name().username(), faker.internet().password());
        Response res = users.create(payload);
        res.then().statusCode(200);
        User body = res.as(User.class);
        assertThat(body.getId()).isEqualTo(payload.getId());
        assertThat(body.getUserName()).isEqualTo(payload.getUserName());
    }

    @Story("PUT update user")
    @Test
    public void updateUser_returns200() {
        int id = 1;
        User payload = new User(id, "updated_username", "updated_password");
        Response res = users.update(id, payload);
        res.then().statusCode(200);
        User body = res.as(User.class);
        assertThat(body.getId()).isEqualTo(id);
        assertThat(body.getUserName()).isEqualTo("updated_username");
    }

    @Story("DELETE user")
    @Test
    public void deleteUser_returns200() {
        Response res = users.delete(1);
        res.then().statusCode(200);
    }
}
