package com.api.automation.utils;

import static com.github.tomakehurst.wiremock.client.WireMock.recordSpec;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.recording.RecordingStatus;
import com.github.tomakehurst.wiremock.recording.SnapshotRecordResult;

public class WireMockTrafficRecorder implements AutoCloseable {

        private static final int PORT = 3;
        private static final Path RECORD_DIR = Paths.get("runtime/activities/wiremock/recordings");

        private final WireMockServer server;

        public WireMockTrafficRecorder(String targetBaseUrl) throws Exception {
                Files.createDirectories(RECORD_DIR.resolve("mappings"));
                Files.createDirectories(RECORD_DIR.resolve("__files"));

                server = new WireMockServer(wireMockConfig()
                                .port(PORT)
                                .usingFilesUnderDirectory(RECORD_DIR.toAbsolutePath().toString()));
                server.start();

                server.startRecording(recordSpec()
                                .forTarget(targetBaseUrl)
                                .makeStubsPersistent(true)
                                .ignoreRepeatRequests()
                                .matchRequestBodyWithEqualToJson(false, true));
        }

        public SnapshotRecordResult stopRecording() {
                if (server.getRecordingStatus().getStatus() == RecordingStatus.Recording) {
                        return server.stopRecording();
                }
                return null;
        }

        @Override
        public void close() {
                stopRecording();
                server.stop();
        }

        public static void main(String[] args) throws Exception {
                String targetBaseUrl = args.length > 0
                                ? args[0]
                                : "https://REPLACE_WITH_REAL_DEPENDENCY";

                try (WireMockTrafficRecorder recorder = new WireMockTrafficRecorder(targetBaseUrl)) {
                        System.out.println("Recording on http://localhost:" + PORT);
                        System.out.println("Proxy → " + targetBaseUrl);
                        System.out.println("Press Enter to stop...");
                        System.in.read();
                }
                System.out.println("Mappings: " + RECORD_DIR.resolve("mappings"));
        }
}
