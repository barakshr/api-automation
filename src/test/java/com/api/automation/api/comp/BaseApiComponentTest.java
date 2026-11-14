package com.api.automation.api.comp;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class BaseApiComponentTest {

        protected static final int WIREMOCK_PORT = 3;

        private WireMockServer wireMockServer;

        @BeforeClass
        public void setup() {
                wireMockServer = new WireMockServer(wireMockConfig().port(WIREMOCK_PORT));
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

}
