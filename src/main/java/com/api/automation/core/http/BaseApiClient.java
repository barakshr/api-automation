package com.api.automation.core.http;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class BaseApiClient {

    private static final Logger log = LoggerFactory.getLogger(BaseApiClient.class);

    private final Content content;

    protected BaseApiClient() {
        this.content = Content.getBuilder().build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    protected BaseApiClient(Content content) {
        this.content = content;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    protected ApiResult get(String path) {
        Response response = givenBase().when().get(path).then().extract().response();
        return new ApiResult(response);
    }

    protected ApiResult get(String path, String query) {
        Response response = givenBase().when().get(withQuery(path, query)).then().extract().response();
        return new ApiResult(response);
    }

    protected ApiResult post(String path, Object body) {
        RequestSpecification spec = givenBase();
        if (body != null) {
            spec.body(body);
        }
        Response response = spec.when().post(path).then().extract().response();
        return new ApiResult(response);
    }

    protected Response put(String path, Object body, Content... optionalContent) {
        RequestSpecification spec = givenBase(optionalContent);
        if (body != null) {
            spec.body(body);
        }
        return spec.when().put(path).then().extract().response();
    }

    protected Response delete(String path) {
        return givenBase().when().delete(path).then().extract().response();
    }

    private RequestSpecification givenBase(Content... optionalContent) {

        Content tempContent;
        if (optionalContent.length != 0) {
            tempContent = optionalContent[0];
        } else {
            tempContent = content;
        }
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(tempContent.getBaseUrl())
                .setContentType(tempContent.getContentType())
                .setAuth(null)
                .addHeaders(content.getHeadersAsMap())
                .setAccept(ContentType.JSON);

        return RestAssured.given().spec(builder.build());
    }

    private static String withQuery(String path, String query) {
        String q = query.trim();
        return path + (q.startsWith("?") ? q : "?" + q);
    }

}
