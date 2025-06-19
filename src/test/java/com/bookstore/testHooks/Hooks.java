package com.bookstore.testHooks;


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
