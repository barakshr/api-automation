package com.api.automation.app.clients;

import com.api.automation.core.http.ApiResult;
import com.api.automation.core.http.BaseApiClient;

public class FrontEndApiClient extends BaseApiClient {

        // private final static ApiConfig apiConfig = ApiConfig.getInstance();

        // public FrontEndApiClient() {
        // super(apiConfig);
        // }

        public ApiResult getChallenge() {
                return get("/api/challenges");
        }

        public ApiResult getTodo() {
                return get("/api/todo");
        }

        public ApiResult getId(String id) {
                return get("/api/todos/" + id);
        }

        public ApiResult filter(String path, String query) {
                return get(path, query);
        }

        public ApiResult post(String pathParam, Object body) {
                return super.post(pathParam, body);
        }

}
