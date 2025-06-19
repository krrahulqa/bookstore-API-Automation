package com.bookstore.stepdefs;

import com.bookStoreAPI.model.BookRequest;
import com.bookStoreAPI.model.UserRequest;
import com.bookStoreAPI.services.BookService;
import com.bookStoreAPI.services.Login;
import com.bookStoreAPI.services.SignUp;
import com.bookStoreAPI.utils.ExtentReportUtil;
import io.cucumber.java.en.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.junit.Assert.*;

/*
 * BookManagementSteps implements Cucumber step definitions for the Book Management feature.
 * It covers all CRUD operations: Create, Read, Update, Delete for books,
 * as well as user registration and authentication required for API testing.
 * Each step validates API responses and logs results to the ExtentReport.
 */

public class BookManagementSteps {

    private Response response;
    private BookRequest book;
    private int createdBookId;
    private String accessToken;
    private String email;
    private String password;

    @Given("a user signs up and logs in successfully")
    public void signUpAndLogin() {
        email = "bookflow_user_" + System.currentTimeMillis() + "@mail.com";
        password = "BookRequest@123";
        UserRequest userRequest = new UserRequest((int)(System.currentTimeMillis() %1000), email, password);

        Response signUpResp = SignUp.signUp(userRequest);
        assertNotNull("SignUp response is null", signUpResp);
        assertEquals(200, signUpResp.getStatusCode());
        ExtentReportUtil.logValidation("UserRequest Signup",200, signUpResp.getStatusCode(), "UserRequest created successfully", signUpResp.getBody().asString(), true);

        Response loginResp = Login.login(userRequest);
        assertNotNull("Login response is null", loginResp);
        assertEquals(200, loginResp.getStatusCode());
        accessToken = JsonPath.from(loginResp.getBody().asString()).getString("access_token");
        assertNotNull("Access token should not be null after login", accessToken);
        ExtentReportUtil.logValidation("UserRequest Login",200, loginResp.getStatusCode(), "Login successful", loginResp.getBody().asString(), true);
    }

    @Given("a book payload with name {string}, author {string}, year {int}, and summary {string} is prepared")
    public void prepareBookPayload(String name, String author, int year, String summary) {
        book = new BookRequest(name, author, year, summary);
        ExtentReportUtil.step("INFO", "Prepared book payload: " + book);
    }

    @Given("a book payload missing name with id {int}, author {string}, year {int}, and summary {string} is prepared")
    public void prepareBookPayloadMissingName(int id, String author, int year, String summary) {
        book = new BookRequest(null, author, year, summary);
        ExtentReportUtil.step("INFO", "Prepared book payload with missing name: " + book);
    }

    @When("user sends a request to create a new book")
    public void createBookRequest() {
        assertNotNull("BookRequest payload must be prepared before create request", book);
        assertNotNull("Access token must be available before create request", accessToken);
        response = BookService.createBook(book, accessToken);
        String body = response != null ? response.getBody().asString() : "";
        int status = response != null ? response.getStatusCode() :-1;
        ExtentReportUtil.step("INFO", "BookRequest creation request response:\nStatus: " + status + "\nBody: " + body);

        if (status ==200 && body != null && !body.trim().isEmpty()) {
            try {
                createdBookId = JsonPath.from(body).getInt("id");
            } catch (Exception e) {
                createdBookId =-1;
            }
        }
    }

    @Then("validate book creation response code is200 and response contains book details")
    public void validate_book_creation_response_code_is200_and_response_contains_book_details() {
        validateCreateBookResponse(200);
    }

    @Then("validate book creation response code is {int} and response contains book details")
    public void validateCreateBookResponse(int expectedCode) {
        assertNotNull("No response from create book", response);
        int actualCode = response.getStatusCode();
        String body = response.getBody().asString();
        String name = null;
        try {
            name = JsonPath.from(body).getString("name");
        } catch (Exception e) {
            name = "";
        }
        boolean passed = actualCode == expectedCode && book != null && name.equals(book.getName());
        ExtentReportUtil.logValidation("BookRequest Creation", expectedCode, actualCode, book != null ? book.getName() : "", name, passed);

        assertEquals(expectedCode, actualCode);
        if (book != null) assertEquals(book.getName(), name);
    }

    @Then("validate book creation fails with code {int} and error message contains {string}")
    public void validateBookCreationFail(int expectedCode, String expectedError) {
        assertNotNull("No response for book creation failure", response);
        int actualCode = response.getStatusCode();
        String body = response.getBody().asString();
        boolean messageFound = body != null && body.toLowerCase().contains(expectedError.toLowerCase());
        ExtentReportUtil.logValidation("BookRequest Creation Fail", expectedCode, actualCode, expectedError, body, actualCode == expectedCode && messageFound);

        assertEquals(expectedCode, actualCode);
        assertTrue("Error message not found in response", messageFound);
    }

    @When("user fetches all books")
    public void user_fetches_all_books() {
        assertNotNull("Access token must be available before fetch all", accessToken);
        response = BookService.getAllBooks(accessToken);
        ExtentReportUtil.step("INFO", "Fetch all books Response: " + (response != null ? response.getBody().asString() : ""));
    }

    @Then("verify response code is {int} and list contains the book name {string}")
    public void verify_response_code_is_and_list_contains_the_book_name(int expectedCode, String expectedBookName) {
        assertNotNull("No response for fetch all books", response);
        int actualCode = response.getStatusCode();
        String body = response.getBody().asString();
        boolean containsBook = body != null && body.contains(expectedBookName);
        ExtentReportUtil.logValidation("Fetch All Books", expectedCode, actualCode, expectedBookName, body, actualCode == expectedCode && containsBook);

        assertEquals(expectedCode, actualCode);
        assertTrue("BookRequest name not found in list", containsBook);
    }

    @When("user fetches book by invalid ID")
    public void user_fetches_book_by_invalid_id() {
        assertNotNull("Access token must be available before fetch by invalid ID", accessToken);
        response = BookService.getBookById(-1, accessToken);
        ExtentReportUtil.step("INFO", "Fetching book by invalid ID:-1 Response: " + (response != null ? response.getBody().asString() : ""));
    }

    @Then("validate not found response with code {int} and message contains {string}")
    public void validate_not_found_response_with_code_and_message_contains(int expectedCode, String expectedMsg) {
        assertNotNull("No response for fetch by invalid ID", response);
        int actualCode = response.getStatusCode();
        String body = response.getBody().asString();
        boolean containsMsg = body != null && body.toLowerCase().contains(expectedMsg.toLowerCase());
        ExtentReportUtil.logValidation("Fetch BookRequest by Invalid ID", expectedCode, actualCode, expectedMsg, body, actualCode == expectedCode && containsMsg);

        assertEquals(expectedCode, actualCode);
        assertTrue("Expected message not found", containsMsg);
    }

    @When("user fetches book by valid ID")
    public void fetchBookByValidId() {
        assertTrue("BookRequest must be created before fetching by valid ID", createdBookId > 0);
        assertNotNull("Access token must be available before fetch by valid ID", accessToken);
        response = BookService.getBookById(createdBookId, accessToken);
        ExtentReportUtil.step("INFO", "Fetching book by valid ID: " + createdBookId + " Response Body: " + (response != null ? response.getBody().asString() : ""));
    }

    @Then("verify single book fetch response code is {int} and book name is {string}")
    public void validateSingleBookResponse(int expectedCode, String expectedName) {
        assertNotNull("No response for fetch by valid ID", response);
        int actualCode = response.getStatusCode();
        String name = "";
        try {
            name = JsonPath.from(response.getBody().asString()).getString("name");
        } catch (Exception e) {
            name = "";
        }
        boolean passed = actualCode == expectedCode && expectedName.equals(name);
        ExtentReportUtil.logValidation("Fetch BookRequest by ID", expectedCode, actualCode, expectedName, name, passed);

        assertEquals(expectedCode, actualCode);
        assertEquals(expectedName, name);
    }

    // ----------------------------- UPDATE operation steps ---------------------------

    @When("user updates the book name to {string} and summary to {string}")
    public void user_updates_the_book(String newName, String newSummary) {
        assertTrue("Book must be created before update", createdBookId > 0);
        assertNotNull("Access token required", accessToken);
        book = new BookRequest(createdBookId, newName, book.getAuthor(), book.getPublishedYear(), newSummary);
        response = BookService.updateBook(createdBookId, book, accessToken);
        ExtentReportUtil.step("INFO", "Updated book: " + book);
    }

    @Then("validate book update response code is {int} and book name is {string}")
    public void validateUpdateBookResponse(int expectedCode, String expectedName) {
        assertNotNull("No response for update book", response);
        int actualCode = response.getStatusCode();
        String name = "";
        try {
            name = JsonPath.from(response.getBody().asString()).getString("name");
        } catch (Exception e) {
            name = "";
        }
        assertEquals(expectedCode, actualCode);
        assertEquals(expectedName, name);
        ExtentReportUtil.logValidation("Update Book", expectedCode, actualCode, expectedName, name, actualCode == expectedCode && expectedName.equals(name));
    }

    // ----------------------------- DELETE operation steps ---------------------------

    @When("user deletes the book by valid ID")
    public void user_deletes_the_book_by_valid_id() {
        assertTrue("Book must be created before delete", createdBookId > 0);
        assertNotNull("Access token required", accessToken);
        response = BookService.deleteBook(createdBookId, accessToken);
        ExtentReportUtil.step("INFO", "Deleted book with ID: " + createdBookId);
    }

    @Then("validate book deletion response code is {int}")
    public void validateDeleteBookResponse(int expectedCode) {
        assertNotNull("No response for delete book", response);
        int actualCode = response.getStatusCode();
        assertEquals(expectedCode, actualCode);
        ExtentReportUtil.logValidation("Delete Book", expectedCode, actualCode, String.valueOf(createdBookId), "", actualCode == expectedCode);
    }
}
