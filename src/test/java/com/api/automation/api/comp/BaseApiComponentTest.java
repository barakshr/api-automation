package com.api.automation.api.comp;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class BaseApiComponentTest {

        private static final int DEFAULT_WIREMOCK_PORT = 3;

        private WireMockServer wireMockServer;

        @BeforeClass
        public void setup() {
                wireMockServer = new WireMockServer(wireMockConfig().port(resolveWireMockPort()));
                wireMockServer.start();
        }

        @AfterClass
        public void tearDown() {
                if (wireMockServer != null) {
                        wireMockServer.stop();
                }
        }

        protected WireMockServer getServer() {
                return wireMockServer;
        }

        private int resolveWireMockPort() {
                String fromEnv = System.getenv("WIREMOCK_PORT");
                if (fromEnv != null && !fromEnv.isBlank()) {
                        return Integer.parseInt(fromEnv);
                }
                return DEFAULT_WIREMOCK_PORT;
        }

}
