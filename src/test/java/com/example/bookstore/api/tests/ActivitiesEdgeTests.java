package com.example.bookstore.api.tests;

import com.example.bookstore.api.BaseApiTest;
import com.example.bookstore.api.http.RequestSpecFactory;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Bookstore API")
@Feature("Activities - Edge Cases")
public class ActivitiesEdgeTests extends BaseApiTest {

    @Story("Dates are ISO-8601")
    @Test(groups = {"edge","activities"})
    public void getAllActivities_dueDate_isIso8601_onSample() {
        List<String> dates =
            given()
                .spec(RequestSpecFactory.spec())
            .when()
                .get("/Activities")
            .then()
                .statusCode(200)
                .extract()
                .jsonPath().getList("dueDate");

        if (dates != null && !dates.isEmpty()) {
            int max = Math.min(3, dates.size());
            for (int i = 0; i < max; i++) {
                OffsetDateTime.parse(dates.get(i));
            }
        }
    }

    @Story("PUT toggles completed flag (best-effort on demo API)")
    @Test(groups = {"edge","activities"})
    public void putActivity_toggleCompleted_bestEffort() {
        Map<String, Object> activity =
            given().spec(RequestSpecFactory.spec()).get("/Activities/1").then().statusCode(200).extract().as(Map.class);

        boolean current = (boolean) activity.get("completed");
        activity.put("completed", !current);

        given()
            .spec(RequestSpecFactory.spec())
            .pathParam("id", activity.get("id"))
            .body(activity)
        .when()
            .put("/Activities/{id}")
        .then()
            .statusCode(anyOf(is(200), is(204)));
    }
}
