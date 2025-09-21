package com.example.bookstore.api.tests;

import com.example.bookstore.api.client.AuthorsClient;
import com.example.bookstore.api.models.Author;
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
@Feature("Authors")
public class AuthorsApiTests extends BaseApiTest {

    private AuthorsClient authors;
    private Faker faker;

    @BeforeClass
    public void init() {
        authors = new AuthorsClient();
        faker = new Faker();
    }

    @Story("GET all authors")
    @Test
    public void getAllAuthors_returns200AndNonEmptyList() {
        Response res = authors.getAll();
        res.then().statusCode(200);
        Author[] list = res.as(Author[].class);
        assertThat(list).isNotNull();
        assertThat(list.length).isGreaterThan(0);
    }

    @Story("GET author by id - existing")
    @Test
    public void getAuthorById_existing_returns200() {
        Response res = authors.getById(1);
        res.then().statusCode(200);
        Author a = res.as(Author.class);
        assertThat(a.getId()).isEqualTo(1);
    }

    @Story("GET author by id - not found")
    @Test
    public void getAuthorById_notFound_returns404() {
        Response res = authors.getById(Integer.MAX_VALUE);
        assertThat(res.statusCode()).isIn(404, 400);
    }

    @Story("GET authors by book id")
    @Test
    public void getAuthorsByBookId_returns200() {
        Response res = authors.getByBookId(1);
        res.then().statusCode(200);
        Author[] list = res.as(Author[].class);
        assertThat(list).isNotNull();
    }

    @Story("POST create author")
    @Test
    public void createAuthor_returns200AndEchoesPayload() {
        Author payload = new Author(IdGenerator.largeId(), 1, faker.name().firstName(), faker.name().lastName());
        Response res = authors.create(payload);
        res.then().statusCode(200);
        Author body = res.as(Author.class);
        assertThat(body.getId()).isEqualTo(payload.getId());
        assertThat(body.getIdBook()).isEqualTo(payload.getIdBook());
    }

    @Story("PUT update author")
    @Test
    public void updateAuthor_returns200() {
        int id = 1;
        Author payload = new Author(id, 1, "UpdatedFirst", "UpdatedLast");
        Response res = authors.update(id, payload);
        res.then().statusCode(200);
        Author body = res.as(Author.class);
        assertThat(body.getId()).isEqualTo(id);
        assertThat(body.getFirstName()).isEqualTo("UpdatedFirst");
    }

    @Story("DELETE author")
    @Test
    public void deleteAuthor_returns200() {
        Response res = authors.delete(1);
        res.then().statusCode(200);
    }
}
