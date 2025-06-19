package com.bookstore.testHooks;

import com.bookStoreAPI.utils.ServerManager;
import com.bookStoreAPI.utils.ExtentReportUtil;
import io.cucumber.java.AfterAll;


public class TestTearDown {

    @AfterAll
    public static void globalTearDown() {
        ExtentReportUtil.step("INFO", "Tearing down: Stopping FastAPI Server.");
        ServerManager.stopServer();
        ExtentReportUtil.step("PASS", "FastAPI Server stopped successfully.");
    }
}
