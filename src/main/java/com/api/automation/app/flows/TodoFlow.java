package com.api.automation.app.flows;

import com.api.automation.app.clients.TodosClient;
import com.api.automation.app.models.Todo;
import com.api.automation.core.assertions.BaseApiAssert;
import io.restassured.response.Response;

/**
 * Multi-step todo sequence for e2e reuse.
 */
public class TodoFlow {

    private final TodosClient todosClient;

    public TodoFlow(TodosClient todosClient) {
        this.todosClient = todosClient;
    }

    /**
     * Creates a todo, reads it back, updates title, deletes it, verifies gone.
     *
     * @return created todo id (before delete)
     */
    public int createGetUpdateDelete(String title, String updatedTitle) {
        Todo createBody = Todo.of(title, false);

        Response createResponse = todosClient.create(createBody);
        BaseApiAssert.assertStatus(createResponse, 201);
        int id = createResponse.jsonPath().getInt("id");
        BaseApiAssert.assertFieldEquals(createResponse, "title", title);

        Response getResponse = todosClient.getById(id);
        BaseApiAssert.assertStatus(getResponse, 200);
        BaseApiAssert.assertFieldEquals(getResponse, "todos[0].title", title);

        Todo updateBody = Todo.of(updatedTitle, true);
        Response updateResponse = todosClient.update(id, updateBody);
        BaseApiAssert.assertStatus(updateResponse, 200);
        BaseApiAssert.assertFieldEquals(updateResponse, "title", updatedTitle);

        Response deleteResponse = todosClient.delete(id);
        BaseApiAssert.assertStatus(deleteResponse, 204);

        Response afterDelete = todosClient.getById(id);
        BaseApiAssert.assertStatus(afterDelete, 404);

        return id;
    }
}
