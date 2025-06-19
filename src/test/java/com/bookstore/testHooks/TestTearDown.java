package com.bookstore.testHooks;

import com.bookStoreAPI.utils.ServerManager;
import com.bookStoreAPI.utils.ExtentReportUtil;
import io.cucumber.java.AfterAll;

/*
 * TestTearDown contains a global Cucumber teardown hook that runs once after all scenarios.
 * It stops the FastAPI backend server and logs shutdown steps to the ExtentReport.
 * Ensures proper cleanup of test environment after the suite execution completes.
 */

public class TestTearDown {

    @AfterAll
    public static void globalTearDown() {
        ExtentReportUtil.step("INFO", "Tearing down: Stopping FastAPI Server.");
        ServerManager.stopServer();
        ExtentReportUtil.step("PASS", "FastAPI Server stopped successfully.");
    }
}
