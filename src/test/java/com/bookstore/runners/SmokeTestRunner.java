package com.bookstore.runners; // Package declaration

import org.junit.runner.RunWith; // JUnit runner
import io.cucumber.junit.Cucumber; // Cucumber runner
import io.cucumber.junit.CucumberOptions; // Cucumber options

/**
 * SmokeTestRunner executes Cucumber feature files tagged as @smoke
 * and generates HTML, JSON,
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",           // Feature file location
       // glue = "com.bookstore.stepdefs",
         glue = {"com.bookstore.stepdefs", "com.bookstore.testHooks"},
        plugin = {
                "pretty",                                       // Console output
                "html:target/smoke-report.html",                // Extent/HTML report
                "json:target/smoke-cucumber.json",              // JSON repor


        },
        tags = "@smoke",                                    // Only run scenarios tagged @smoke
        monochrome = true                                   // Clean console output
)
public class SmokeTestRunner {
    // No code needed; configuration via annotations
}
