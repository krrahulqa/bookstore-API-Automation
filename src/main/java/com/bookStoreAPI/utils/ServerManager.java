package com.bookStoreAPI.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bookStoreAPI.config.ConfigReader;

import io.restassured.RestAssured;

/*
 * ServerManager manages the lifecycle of the FastAPI backend server required for API testing.
 * It provides methods to start and stop the server process, including handling CI/CD environments.
 * The class ensures the server is running before tests execute by health-checking the backend.
 * Used to automate environment setup and teardown within the framework.
 */

public class ServerManager {
    private static Process serverProcess;

    public static void startServer() {
        if (System.getenv("CI") != null) {
            System.out.println("CI environment detected — skipping FastAPI server startup.");
            ExtentReportUtil.step("INFO", "CI mode: FastAPI server is managed by GitHub Actions.");
            return;
        }
        try {
            ExtentReportUtil.step("INFO", "----------Server startup---------");
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "uvicorn main:app --reload");
            String projectRoot = System.getProperty("user.dir");
            File backendDir = new File(projectRoot + File.separator + "bookstore-main" + File.separator + "bookstore");
            pb.directory(backendDir);
            serverProcess = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(serverProcess.getInputStream()));
            new Thread(() -> {
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        System.out.println("SERVER LOG: " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            boolean serverUp = false;
            int maxRetries = 10;
            int delayMillis = 1000;
            for (int i = 0; i < maxRetries; i++) {
                if (isServerRunning()) {
                    serverUp = true;
                    break;
                }
                Thread.sleep(delayMillis);
            }
            if (!serverUp) {
                ExtentReportUtil.step("Fail", "Server started but not responding on http://1270.0.1:8000");
                throw new RuntimeException("Server started but not responding on http://127.0.0.1:8000");
            }
            ExtentReportUtil.step("PASS", "FastAPI Server is up and ready!");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Server startup failed.", e);
        }
    }

    public static boolean isServerRunning() {
        try {
            RestAssured.baseURI = ConfigReader.getBaseUri();
            io.restassured.response.Response response = RestAssured.get("/health");
            System.out.println("Server check HTTP status: " + response.getStatusCode());
            System.out.println("Server check response body: " + response.getBody().asString());
            return response.getStatusCode() == 200;
        } catch (Exception e) {
            System.out.println("Server check exception: " + e.getMessage());
            return false;
        }
    }

    public static void stopServer() {
        if (serverProcess != null) {
            serverProcess.destroy();
            System.out.println("🛑 FastAPI Server stopped.");
        }
    }
}
