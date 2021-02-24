package fr.maximedavid.todos;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(DatabaseResource.class)
public class TodoResourceTest {

    @Test
    public void createTest() {
        Todo todo = new Todo();
        todo.setTitle("testTitle");
        todo.setDescription("testDescription");
        todo.setCompleted(true);

        Todo[] response = given()
                .header("Content-Type", "application/json")
                .body(todo)
                .post("/todos")
                .then()
                .statusCode(200)
                .extract()
                .as(Todo[].class);

        Assertions.assertEquals(1, response.length);
        Assertions.assertEquals("testTitle", response[0].getTitle());
        Assertions.assertEquals("testDescription", response[0].getDescription());
        Assertions.assertEquals(true, response[0].isCompleted());
    }

}
