package com.api.automation.api;

import com.api.automation.app.clients.FrontEndApiClient;
import org.testng.annotations.BeforeClass;

public abstract class BaseApiTest {

    protected FrontEndApiClient frontEndAPiClient;

    @BeforeClass(alwaysRun = true)
    public void setUpApi() {
        frontEndAPiClient = new FrontEndApiClient();
    }
}
