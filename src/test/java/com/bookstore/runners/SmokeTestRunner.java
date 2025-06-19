package com.bookstore.runners;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
       // glue = "com.bookstore.stepdefs",
         glue = {"com.bookstore.stepdefs", "com.bookstore.testHooks"},
        plugin = {
                "pretty",
                "html:target/smoke-report.html",
                "json:target/smoke-cucumber.json",


        },
        tags = "@smoke",
        monochrome = true
)
public class SmokeTestRunner {

}
