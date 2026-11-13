package com.api.automation.core.http;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.api.automation.core.config.ApiConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;

public class Content {

    private Headers headers;
    private Cookies cookies;
    private String baseUrl;
    private String timeOut;
    private ContentType contentType = ContentType.JSON;

    public Content(Headers headers, Cookies cookies, String baseUrl, String timeOut, ContentType contentType) {
        this.headers = headers;
        this.cookies = cookies;
        this.baseUrl = baseUrl;
        this.timeOut = timeOut;
        this.contentType = contentType;
    }

    public Map<String, String> getHeadersAsMap() {
        return headers.asList().stream()
                .collect(Collectors.toMap(Header::getName, Header::getValue, (a, b) -> b));
    }

    public Headers getHeaders() {
        return headers;
    }

    public Cookies getCookies() {
        return cookies;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    static class Builder {
        Headers headers = null;
        Cookies cookies;
        String baseUrl = ApiConfig.getInstance().getBaseUri();
        String timeOut = ApiConfig.getInstance().getConnectTimeoutMs();
        ContentType type = ContentType.JSON;

        public Builder setHeaders(Headers headers) {
            this.headers = headers;
            return this;
        }

        public Builder setType(ContentType type) {
            this.type = type;
            return this;

        }

        public Builder setCookies(Cookies cookies) {
            this.cookies = cookies;
            return this;

        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;

        }

        public Builder setTimeOut(String timeOut) {
            this.timeOut = timeOut;
            return this;

        }

        Content build() {
            return new Content(headers, cookies, baseUrl, timeOut, type);
        }
    }
}
