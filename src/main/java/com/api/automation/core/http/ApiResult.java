package com.api.automation.core.http;

import com.api.automation.core.assertions.BaseApiAssert;
import io.restassured.response.Response;

public class ApiResult {

    private final Response response;

    public ApiResult(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public   <T extends BaseApiAssert> T assertResult(Class<T> assertClass) {
        try {
            return assertClass.getConstructor(Response.class).newInstance(response);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(
                    "Assert class must extend BaseApiAssert and declare a public constructor(Response): "
                            + assertClass.getName(),
                    e);
        }
    }
}
