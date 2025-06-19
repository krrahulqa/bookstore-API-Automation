package com.bookstore.testHooks;

import com.bookStoreAPI.utils.ExtentReportUtil;
import com.bookStoreAPI.utils.ServerManager;
import io.cucumber.java.BeforeAll;

/*
 * TestSetup contains a global Cucumber setup hook that runs once before all scenarios.
 * It initializes the ExtentReport for reporting and starts the FastAPI backend server using ServerManager.
 * Ensures the test environment is ready before the test suite begins execution.
 */

public class TestSetup {

    @BeforeAll
    public static void globalSetup() {
        ExtentReportUtil.initReport();
        ExtentReportUtil.createTest("FastAPI Server Setup");
        ServerManager.startServer();
    }
}
