package com.bookstore.testHooks;

import com.bookStoreAPI.utils.ExtentReportUtil;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

/*
 * Hooks class contains global Cucumber hooks for scenario setup and teardown.
 * Before each scenario, it initializes a new ExtentReport test node and logs scenario start.
 * After each scenario, it logs the scenario result (PASS/FAIL) and flushes the ExtentReport.
 * This ensures every scenario is tracked and reported in the test report.
 */

public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        ExtentReportUtil.createTest(scenario.getName());
        ExtentReportUtil.step("INFO", "Starting Scenario: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            ExtentReportUtil.step("FAIL", "Scenario failed: " + scenario.getName());
        } else {
            ExtentReportUtil.step("PASS", "Scenario passed: " + scenario.getName());
        }
        ExtentReportUtil.flushReport();
    }
}
