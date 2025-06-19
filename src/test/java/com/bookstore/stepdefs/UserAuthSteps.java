package com.bookstore.stepdefs;

import com.bookStoreAPI.model.UserRequest;
import com.bookStoreAPI.services.Login;
import com.bookStoreAPI.services.SignUp;
import com.bookStoreAPI.utils.ExtentReportUtil;
import io.cucumber.java.en.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

/*
 * UserAuthSteps implements Cucumber step definitions for user authentication scenarios.
 * Handles signup, login, and various negative/positive cases using the API services.
 * Generates unique credentials for each scenario and validates API responses.

 */

public class UserAuthSteps {

    private Response response;
    private int uniqueId;
    private String uniqueUsername;
    private String password;

    private String generateRandomUsername() {
        return "user_" + UUID.randomUUID().toString().replace("-", "") + "@mail.com";
    }

    private int generateRandomId() {
        return UUID.randomUUID().toString().hashCode();
    }

    private String generateRandomPassword() {
        return "Pwd@" + new Random().nextInt(99999);
    }

    @When("user tried to login with noSignUpUser credentials into book store system")
    public void loginWithoutSignup() {
        UserRequest userRequest = new UserRequest(generateRandomId(), generateRandomUsername(), generateRandomPassword());
        ExtentReportUtil.step("INFO", "Login attempt with non-existent userRequest: " + userRequest.getEmail());
        response = Login.login(userRequest);
    }

    @When("user tried to login with missingParam credentials into book store system")
    public void user_tried_to_login_with_missing_param_credentials_into_book_store_system() {
        UserRequest userRequest = new UserRequest(generateRandomId(), generateRandomUsername(), null);
        ExtentReportUtil.step("INFO", "Login attempt with missing password for: " + userRequest.getEmail());
        response = Login.login(userRequest);
    }

    @Then("verify the login response code is {int} and message contains {string}")
    public void validateLogin(int expectedCode, String expectedMsg) {
        int actualCode = response.getStatusCode();
        String body = response.getBody().asString();

        boolean is2xx = expectedCode / 100 ==2;
        boolean is4xx5xx = expectedCode /100 >= 4;

        try {
            if (is2xx && actualCode == expectedCode) {
                String token = JsonPath.from(body).getString("access_token");
                assertNotNull("Expected token but was null", token);
                ExtentReportUtil.step("PASS", "[PASS] Token found in response: " + token);
            } else if (is4xx5xx && actualCode == expectedCode) {
                String actualMsg = JsonPath.from(body).getString("detail");
                assertTrue("Message mismatch: Expected to contain '" + expectedMsg + "', but got: '" + actualMsg + "'", actualMsg.toLowerCase().contains(expectedMsg.toLowerCase()));
                ExtentReportUtil.step("PASS", "[PASS] Error message matched: " + actualMsg);
            } else {
                ExtentReportUtil.step("FAIL", "Unexpected status. Expected: " + expectedCode + ", Actual: " + actualCode);
                fail("Unexpected response. Expected: " + expectedCode + ", Actual: " + actualCode + ", Body: " + body);
            }
        } catch (Exception e) {
            ExtentReportUtil.step("FAIL", "Exception: " + e.getMessage() + "\nExpected: " + expectedCode + ", Actual: " + actualCode + ", Body: " + body);
            throw e;
        }
    }

    @When("user signs up with a unique email and password")
    public void userSignsUp() {
        uniqueId = generateRandomId();
        uniqueUsername = generateRandomUsername();
        password = generateRandomPassword();
        UserRequest userRequest = new UserRequest(uniqueId, uniqueUsername, password);
        ExtentReportUtil.step("INFO", "Sign up attempt: " + userRequest.getEmail());
        response = SignUp.signUp(userRequest);
    }

    @Then("verify user signup response code is {int} and message contains {string}")
    public void validateSignup(int expectedCode, String expectedMsg) {
        int actualCode = response.getStatusCode();
        String body = response.getBody().asString();

        try {
            assertEquals(expectedCode, actualCode);
            assertTrue(body.toLowerCase().contains(expectedMsg.toLowerCase()));
            ExtentReportUtil.step("PASS", "Signup response validated: " + body);
        } catch (Exception e) {
            ExtentReportUtil.step("FAIL", "Signup validation failed: " + e.getMessage());
            throw e;
        }
    }

    @Given("Sign up to the book store as the new user with email and password")
    public void sign_up_to_the_book_store_as_the_new_user_with_email_and_password() {
        uniqueId = generateRandomId();
        uniqueUsername = generateRandomUsername();
        password = generateRandomPassword();
        UserRequest userRequest = new UserRequest(uniqueId, uniqueUsername, password);
        response = SignUp.signUp(userRequest);
        ExtentReportUtil.step("INFO", "Sign up as new userRequest: " + userRequest.getEmail());
    }

    @When("do the sign up with valid credentials")
    public void do_the_sign_up_with_valid_credentials() {
        UserRequest userRequest = new UserRequest(uniqueId, uniqueUsername, password);
        response = SignUp.signUp(userRequest);
        ExtentReportUtil.step("INFO", "Sign up with valid credentials: " + userRequest.getEmail());
    }

    @When("user tried to login with valid credentials into book store system")
    public void user_tried_to_login_with_valid_credentials_into_book_store_system() {
        UserRequest userRequest = new UserRequest(uniqueId, uniqueUsername, password);
        response = Login.login(userRequest);
        ExtentReportUtil.step("INFO", "Login with valid credentials: " + userRequest.getEmail());
    }

    @Given("I prepare a unique user with email prefix {string} and password {string}")
    public void i_prepare_a_unique_user_with_email_prefix_and_password(String prefix, String pwd) {
        uniqueId = generateRandomId();
        uniqueUsername = prefix + "_" + UUID.randomUUID().toString().replace("-", "") + "@test.com";
        password = pwd;
        UserRequest userRequest = new UserRequest(uniqueId, uniqueUsername, password);
        response = SignUp.signUp(userRequest);
        ExtentReportUtil.step("INFO", "Prepared userRequest: " + userRequest.getEmail());
    }

    @When("user tries to login using the same email prefix {string} and password {string}")
    public void user_tries_to_login_using_the_same_email_prefix_and_password(String prefix, String pwd) {
        UserRequest userRequest = new UserRequest(uniqueId, uniqueUsername, pwd);
        response = Login.login(userRequest);
        ExtentReportUtil.step("INFO", "Login with: " + userRequest.getEmail());
    }

    @Then("validate signup response code is {int} and message contains {string}")
    public void validate_signup_response_code_is_and_message_contains(Integer expectedCode, String expectedMsg) {
        int actualCode = response.getStatusCode();
        String body = response.getBody().asString();
        assertEquals(expectedCode.intValue(), actualCode);
        assertTrue(body.toLowerCase().contains(expectedMsg.toLowerCase()));
        ExtentReportUtil.step("PASS", "Signup response validated: " + body);
    }

    @When("do the sign up with old credentials")
    public void do_the_sign_up_with_old_credentials() {
        UserRequest userRequest = new UserRequest(uniqueId, uniqueUsername, password);
        response = SignUp.signUp(userRequest);
        ExtentReportUtil.step("INFO", "Sign up with old credentials: " + userRequest.getEmail());
    }

    @When("do the sign up with newPasswordOnly credentials")
    public void do_the_sign_up_with_newPasswordOnly_credentials() {
        String newPassword = generateRandomPassword();
        UserRequest userRequest = new UserRequest(uniqueId, uniqueUsername, newPassword);
        response = SignUp.signUp(userRequest);
        ExtentReportUtil.step("INFO", "Sign up with new password only: " + userRequest.getEmail());
    }

    @When("I sign up with the same email and a new ID using prefix {string} and password {string}")
    public void i_sign_up_with_the_same_email_and_a_new_id_using_prefix_and_password(String prefix, String pwd) {
        int newId = generateRandomId();
        UserRequest userRequest = new UserRequest(newId, uniqueUsername, pwd);
        response = SignUp.signUp(userRequest);
        ExtentReportUtil.step("INFO", "Sign up with same email, new ID: " + userRequest.getEmail());
    }

    @When("I sign up with the same ID and a new email using prefix {string} and password {string}")
    public void i_sign_up_with_the_same_id_and_a_new_email_using_prefix_and_password(String prefix, String pwd) {
        String newEmail = prefix + "_" + UUID.randomUUID().toString().replace("-", "") + "@test.com";
        UserRequest userRequest = new UserRequest(uniqueId, newEmail, pwd);
        response = SignUp.signUp(userRequest);
        ExtentReportUtil.step("INFO", "Sign up with same ID, new email: " + userRequest.getEmail());
    }

    // Add these **NEW** step definitions to your UserAuthSteps class to fix all missing/undefined Cucumber step errors
// -- Make sure these are pasted at the bottom of your UserAuthSteps.java file (inside the class)

    // For: @Then("verify the login response code is400 and message contains {string}")
    @Then("verify the login response code is400 and message contains {string}")
    public void verify_the_login_response_code_is400_and_message_contains(String expectedMsg) {
        // This will check for HTTP 400 and error message in detail
        int actualCode = response.getStatusCode();
        String body = response.getBody().asString();
        assertEquals(400, actualCode);
        String actualMsg = "";
        try {
            actualMsg = JsonPath.from(body).getString("detail");
        } catch (Exception e) {
            actualMsg = body;
        }
        assertTrue("Message mismatch: Expected to contain '" + expectedMsg + "', but got: '" + actualMsg + "'", actualMsg.toLowerCase().contains(expectedMsg.toLowerCase()));
        ExtentReportUtil.step("PASS", "[PASS] Error message matched: " + actualMsg);
    }





}
