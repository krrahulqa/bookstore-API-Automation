package com.bookStoreAPI.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/*
 * ExtentReportUtil manages the lifecycle and operations of ExtentReports for test reporting.
 * It provides static methods to initialize the report, create test nodes, log steps with statuses,
 * log validation/assertion results, and flush the report to disk after test execution.
 * This utility centralizes reporting logic and is used in hooks, step definitions, and utility classes
 * to produce detailed HTML test reports for the framework.
 */

public class ExtentReportUtil {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initReport() {
        if (extent == null) {
            ExtentSparkReporter reporter = new ExtentSparkReporter("target/extent-report.html");
            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
    }

    public static void createTest(String testName) {
        test = extent.createTest(testName);
    }

    public static void step(String status, String details) {
        if (test != null) {
            switch (status.toUpperCase()) {
                case "INFO":
                    test.info(details);
                    break;
                case "PASS":
                    test.pass(details);
                    break;
                case "FAIL":
                    test.fail(details);
                    break;
                default:
                    test.info(details);
            }
        }
    }

    public static void step(Status status, String details) {
        step(status.toString(), details);
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static void logValidation(String title, Object expected, Object actual, String expectedDesc, String actualDesc, boolean pass) {
        if (test != null) {
            if (pass) {
                test.pass("✅ " + title + "\nExpected: " + expected + " - " + expectedDesc + "\nActual: " + actual + " - " + actualDesc);
            } else {
                test.fail("❌ " + title + "\nExpected: " + expected + " - " + expectedDesc + "\nActual: " + actual + " - " + actualDesc);
            }
        }
    }
}
