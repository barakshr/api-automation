package com.api.automation.api.e2e;

import com.api.automation.api.BaseApiTest;
import com.api.automation.app.flows.TodoFlow;
import org.testng.annotations.Test;

public class TodoE2ETest extends BaseApiTest {

    @Test(groups = {"e2e", "smoke"})
    public void todoLifecycle_createGetUpdateDelete() {
        TodoFlow flow = new TodoFlow(todosClient);
        String title = "e2e-todo-" + System.currentTimeMillis();
        String updated = title + "-updated";

        flow.createGetUpdateDelete(title, updated);
    }
}
