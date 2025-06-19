package com.bookstore.runners;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
    //    glue = "com.bookstore.stepdefs",
        glue = {"com.bookstore.stepdefs", "com.bookstore.testHooks"},
        plugin = {
                "pretty",
                "html:target/extent-report.html",
                "json:target/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        tags = "@regression",
        monochrome = true
)
public class RegressionTestRunner {

}
