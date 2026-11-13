package com.api.automation.api.e2e;

import com.api.automation.api.BaseApiTest;
import com.api.automation.app.assertions.AssertChallenges;
import org.testng.annotations.Test;

public class TodoE2ETest extends BaseApiTest {

    @Test(groups = { "e2e", "smoke" })
    public void checkChallenges() {
        frontEndAPiClient.getChallenge()
                .assertResult(AssertChallenges.class)
                .checkListNotEmpty();
    }

    
}
