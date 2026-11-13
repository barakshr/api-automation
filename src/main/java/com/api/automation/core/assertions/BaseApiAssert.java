package com.api.automation.core.assertions;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

public class BaseApiAssert {

    private final Response response;

    public BaseApiAssert(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void assertStatus(int expectedStatus) {
        Assertions.assertThat(response.statusCode())
                .as("Unexpected HTTP status. Body: %s", response.asString())
                .isEqualTo(expectedStatus);
    }

    public void assertSchema(String schemaClasspath) {
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaClasspath));
    }

    public SoftAssertions soft() {
        return new SoftAssertions();
    }
}
