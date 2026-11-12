package com.api.automation.api.comp;

import com.api.automation.api.BaseApiTest;
import com.api.automation.app.models.Todo;
import com.api.automation.core.assertions.BaseApiAssert;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class TodosCompTest extends BaseApiTest {

    @Test(groups = {"comp", "smoke"})
    public void getTodos_returns200() {
        Response response = todosClient.getAll();
        BaseApiAssert.assertStatus(response, 200);
    }

    @Test(groups = {"comp"})
    public void createTodo_returns201_andEchoesTitle() {
        String title = "comp-todo-" + System.currentTimeMillis();
        Response response = todosClient.create(Todo.of(title, false));

        BaseApiAssert.assertStatus(response, 201);
        BaseApiAssert.assertFieldEquals(response, "title", title);
        BaseApiAssert.assertSchema(response, "schemas/todo.json");
    }
}
