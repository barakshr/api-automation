package com.api.automation.api.comp;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class BaseApiComponentTest {

        private WireMockServer wireMockServer;

        @BeforeClass
        public void setup() {
                wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
                wireMockServer.start();
        }

        @AfterClass
        public void tearDown() {
                if (wireMockServer != null) {
                        wireMockServer.stop();
                }
        }

        protected WireMockServer getMockServer() {
                return wireMockServer;
        }

        protected String mockBaseUrl() {
                return wireMockServer.baseUrl();
        }
}
