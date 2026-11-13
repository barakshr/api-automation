package com.api.automation.api.e2e;

import com.api.automation.api.BaseApiTest;
import com.api.automation.app.assertions.AssertChallenges;
import com.api.automation.core.http.ApiResult;
import org.testng.annotations.Test;

public class E2ETest extends BaseApiTest {

    @Test
    public void checkChallenges() {
        frontEndAPiClient
                .getChallenge()
                .assertResult(AssertChallenges.class)
                .checkListNotEmpty();
    }

    @Test
    public void check() {
        ApiResult p = frontEndAPiClient.post("/api/todos/8", s);
        String d = p.getResponse().body().prettyPrint();
    }

    String s = "{\"title\": \"solution widget todo\",\"doneStatus\": true,\"description\": \"created from the solution page\"}";

}
