package com.bookstore.runners; // Package declaration

import org.junit.runner.RunWith; // JUnit runner
import io.cucumber.junit.Cucumber; // Cucumber runner
import io.cucumber.junit.CucumberOptions; // Cucumber options


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.bookstore.stepdefs", "com.bookstore.testHooks"},
        plugin = {
                "pretty",                                            // Console output
                "html:target/cucumber-reports",                      // HTML report
                "json:target/cucumber.json"                          // JSON report
        },
        monochrome = true,
        tags = "@regression"
)
public class TestRunner {

}
