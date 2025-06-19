package com.bookstore.runners; // Package declaration

import org.junit.runner.RunWith; // JUnit runner
import io.cucumber.junit.Cucumber; // Cucumber runner
import io.cucumber.junit.CucumberOptions; // Cucumber options


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",                // Feature file location
        glue = {"com.bookstore.stepdefs", "com.bookstore.testHooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports",
                "json:target/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,                                       // Clean console output
        tags = "@regression"
)
public class TestRunner {

}
