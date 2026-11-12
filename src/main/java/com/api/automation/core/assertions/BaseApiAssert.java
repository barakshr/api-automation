package com.api.automation.core.assertions;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

/**
 * Reusable API assertions (status, fields, schema).
 * Package is {@code assertions} because {@code assert} is a Java keyword.
 */
public final class BaseApiAssert {

    private BaseApiAssert() {
    }

    public static void assertStatus(Response response, int expectedStatus) {
        Assertions.assertThat(response.statusCode())
                .as("Unexpected HTTP status. Body: %s", response.asString())
                .isEqualTo(expectedStatus);
    }

    public static void assertFieldEquals(Response response, String jsonPath, Object expected) {
        Object actual = response.jsonPath().get(jsonPath);
        Assertions.assertThat(actual)
                .as("Field '%s'", jsonPath)
                .isEqualTo(expected);
    }

    public static void assertSchema(Response response, String schemaClasspath) {
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaClasspath));
    }

    public static SoftAssertions soft() {
        return new SoftAssertions();
    }
}
