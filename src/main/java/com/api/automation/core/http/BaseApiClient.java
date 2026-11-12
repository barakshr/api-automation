package com.api.automation.core.http;

import com.api.automation.core.config.ConfigInfo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Shared HTTP client: base URI, timeouts, logging, and auth headers.
 * Auth (X-CHALLENGER / Bearer JWT) lives here for v1; split later if needed.
 */
public class BaseApiClient {

    private static final Logger log = LoggerFactory.getLogger(BaseApiClient.class);


    private final ConfigInfo config;
    private String challengerId;
    private String bearerToken;
    
    

    public BaseApiClient() {
        this.config = ConfigInfo.get();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public BaseApiClient withChallengerId(String challengerId) {
        this.challengerId = challengerId;
        return this;
    }

    public BaseApiClient withBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
        return this;
    }

    public String getChallengerId() {
        return challengerId;
    }

    /**
     * Creates an API Challenges session and stores X-CHALLENGER for later calls.
     */
    public String createChallengerSession() {
        Response response = givenBase()
                .when()
                .post("/api/challenger")
                .then()
                .extract()
                .response();

        String id = response.getHeader("X-CHALLENGER");
        if (id == null || id.isBlank()) {
            id = response.jsonPath().getString("challenger");
        }
        if (id == null || id.isBlank()) {
            throw new IllegalStateException("Could not obtain X-CHALLENGER from POST /api/challenger");
        }
        this.challengerId = id;
        log.info("Created challenger session: {}", challengerId);
        return challengerId;
    }

    public Response get(String path) {
        return givenBase().when().get(path).then().extract().response();
    }

    public Response post(String path, Object body) {
        RequestSpecification spec = givenBase();
        if (body != null) {
            spec.body(body);
        }
        return spec.when().post(path).then().extract().response();
    }

    public Response put(String path, Object body) {
        RequestSpecification spec = givenBase();
        if (body != null) {
            spec.body(body);
        }
        return spec.when().put(path).then().extract().response();
    }

    public Response delete(String path) {
        return givenBase().when().delete(path).then().extract().response();
    }

    protected RequestSpecification givenBase() {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(config.getBaseUri())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setRelaxedHTTPSValidation();

        applyAuth(builder);

        if (config.isRequestLoggingEnabled()) {
            builder.addFilter(new RequestLoggingFilter());
            builder.addFilter(new ResponseLoggingFilter());
        }

        return RestAssured.given().spec(builder.build());
    }

    private void applyAuth(RequestSpecBuilder builder) {
        String authType = config.getAuthType();
        if ("none".equalsIgnoreCase(authType)) {
            return;
        }
        if ("bearer".equalsIgnoreCase(authType)) {
            if (bearerToken != null && !bearerToken.isBlank()) {
                builder.addHeader("Authorization", "Bearer " + bearerToken);
            } else {
                String fromEnv = System.getenv("JWT");
                if (fromEnv == null || fromEnv.isBlank()) {
                    fromEnv = System.getenv("API_TOKEN");
                }
                if (fromEnv != null && !fromEnv.isBlank()) {
                    builder.addHeader("Authorization", "Bearer " + fromEnv);
                }
            }
            return;
        }
        // default: challenger (API Challenges)
        if (challengerId != null && !challengerId.isBlank()) {
            builder.addHeader("X-CHALLENGER", challengerId);
        }
    }
}
