package com.api.automation.api;

import com.api.automation.app.clients.TodosClient;
import com.api.automation.core.http.BaseApiClient;
import org.testng.annotations.BeforeClass;

/**
 * Shared TestNG setup for API tests (challenger session + clients).
 */
public abstract class BaseApiTest {

    protected BaseApiClient apiClient;
    protected TodosClient todosClient;

    @BeforeClass(alwaysRun = true)
    public void setUpApi() {
        apiClient = new BaseApiClient();
        apiClient.createChallengerSession();
        todosClient = new TodosClient(apiClient);
    }
}
