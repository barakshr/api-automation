package com.api.automation.api.comp.demo_mock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import com.api.automation.api.comp.BaseApiComponentTest;

import io.restassured.module.jsv.JsonSchemaValidator;

public class DemoMockTests extends BaseApiComponentTest {

        private static final String SUT_BASE_URI = "http://localhost:19080";
        private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";

        @Test
        public void inbound_value() {
                getServer().stubFor(get(urlEqualTo("/api/activity"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{"
                                                                + "\"activity\":\"Take a bubble bath\","
                                                                + "\"key\":\"123\","
                                                                + "\"type\":\"relaxation\""
                                                                + "}")));

                String h = given()
                                .baseUri(SUT_BASE_URI)
                                .when()
                                .get("/v1/users/barak/activities/any")
                                .then()
                                .statusCode(200)
                                .extract().response().body().asPrettyString();
                System.out.print(h);

        }

        @Test
        public void inbound_contract() {
                getServer().stubFor(get(urlEqualTo("/api/activity"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{"
                                                                + "\"activity\":\"Take a bubble bath\","
                                                                + "\"key\":\"123\","
                                                                + "\"type\":\"relaxation\""
                                                                + "}")));

                given()
                                .baseUri(SUT_BASE_URI)
                                .when()
                                .get("/v1/users/barak/activities/any")
                                .then()
                                .statusCode(200)
                                .contentType("application/json")
                                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/activity-any.json"))
                  
        }

        @Ignore
        @Test
        public void outbound_value() {
                String correlationId = UUID.randomUUID().toString();

                getServer().stubFor(get(urlEqualTo("/api/activity"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{"
                                                                + "\"activity\":\"Take a bubble bath\","
                                                                + "\"key\":\"123\","
                                                                + "\"type\":\"relaxation\""
                                                                + "}")));

                given()
                                .baseUri(SUT_BASE_URI)
                                .header(CORRELATION_ID_HEADER, correlationId)
                                .when()
                                .get("/v1/users/barak/activities/any")
                                .then()
                                .statusCode(200);

                getServer().verify(1, getRequestedFor(urlEqualTo("/api/activity"))
                                .withHeader(CORRELATION_ID_HEADER, equalTo(correlationId)));
        }

}
