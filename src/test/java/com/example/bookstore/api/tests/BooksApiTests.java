package com.example.bookstore.api.tests;

import com.example.bookstore.api.BaseApiTest;
import com.example.bookstore.api.client.BooksClient;
import com.example.bookstore.api.models.Book;
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
@Feature("Books")
public class BooksApiTests extends BaseApiTest {

    private BooksClient books;
    private Faker faker;

    @BeforeClass
    public void init() {
        books = new BooksClient();
        faker = new Faker();
    }

    @Story("GET all books")
    @Test
    public void getAllBooks_returns200AndNonEmptyList() {
        Response res = books.getAll();
        res.then().statusCode(200);
        Book[] all = res.as(Book[].class);
        assertThat(all).isNotNull();
        assertThat(all.length).isGreaterThan(0);
    }

    @Story("GET by id - existing")
    @Test
    public void getBookById_existing_returns200() {
        Response res = books.getById(1);
        res.then().statusCode(200);
        Book book = res.as(Book.class);
        assertThat(book.getId()).isEqualTo(1);
        assertThat(book.getTitle()).isNotBlank();
    }

    @Story("GET by id - not found")
    @Test
    public void getBookById_notFound_returns404() {
        Response res = books.getById(Integer.MAX_VALUE);
        assertThat(res.statusCode()).isIn(404, 400);
    }

    @Story("POST create")
    @Test
    public void createBook_returns200AndEchoesPayload() {
        Book payload = new Book(
                IdGenerator.largeId(),
                faker.book().title(),
                faker.lorem().sentence(8),
                faker.number().numberBetween(50, 1000),
                faker.lorem().sentence(6),
                Instant.now().toString());

        Response res = books.create(payload);
        res.then().statusCode(200);
        Book body = res.as(Book.class);
        assertThat(body.getId()).isEqualTo(payload.getId());
        assertThat(body.getTitle()).isEqualTo(payload.getTitle());
        assertThat(body.getPageCount()).isEqualTo(payload.getPageCount());
    }

    @Story("PUT update")
    @Test
    public void updateBook_returns200AndBodyMatchesPayload() {
        int targetId = 1;
        Book payload = new Book(
                targetId,
                "Updated " + faker.book().title(),
                "Updated " + faker.lorem().sentence(8),
                faker.number().numberBetween(100, 600),
                "Updated excerpt",
                Instant.now().toString());

        Response res = books.update(targetId, payload);
        res.then().statusCode(200);
        Book body = res.as(Book.class);
        assertThat(body.getId()).isEqualTo(targetId);
        assertThat(body.getTitle()).isEqualTo(payload.getTitle());
        assertThat(body.getPageCount()).isEqualTo(payload.getPageCount());
    }

    @Story("DELETE by id")
    @Test
    public void deleteBook_returns200() {
        Response res = books.delete(1);
        res.then().statusCode(200);
    }
}
