package com.api.automation.api.comp.demo_mock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.api.automation.api.comp.BaseApiComponentTest;

public class DemoMockTests extends BaseApiComponentTest {

        @Test
        public void compTest() {
                getMockServer().stubFor(get(urlEqualTo("/hello"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{\"field\":\"hello\"}")));

                given()
                                .baseUri(mockBaseUrl())
                                .when()
                                .get("/hello")
                                .then()
                                .statusCode(200)
                                .body("field", equalTo("hello"));
        }
}
