package com.api.automation.app.clients;

import com.api.automation.app.models.Todo;
import com.api.automation.core.http.BaseApiClient;
import io.restassured.response.Response;

public class TodosClient {

    private final BaseApiClient client;

    public TodosClient(BaseApiClient client) {
        this.client = client;
    }

    public Response getAll() {
        return client.get("/api/todos");
    }

    public Response getById(int id) {
        return client.get("/api/todos/" + id);
    }

    public Response create(Todo todo) {
        return client.post("/api/todos", todo);
    }

    public Response update(int id, Todo todo) {
        return client.post("/api/todos/" + id, todo);
    }

    public Response delete(int id) {
        return client.delete("/api/todos/" + id);
    }
}
