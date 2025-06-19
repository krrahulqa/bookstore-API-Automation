package com.bookStoreAPI.utils;

import com.bookStoreAPI.config.ApiConstants;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/*
 * RestUtil provides static utility methods for sending authenticated REST API requests.
 * Supports POST, GET, PUT, and DELETE methods with bearer token authentication.
 * Used by service classes to abstract low-level HTTP operations and ensure consistent API interactions.
 * Optionally integrates with reporting (if enabled) for logging request and response details.
 */

public class RestUtil {

    public static Response post(String endpoint, Object body, String token) {
        ExtentReportUtil.step("INFO", "POST " + endpoint + " | Body: " + body);

        Response response = given()
                .baseUri(ApiConstants.BASE_URI)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(body)
                .when()
                .post(endpoint);

        ExtentReportUtil.step("INFO", "POST Response: " + response.getBody().asString());

        return response;
    }

    public static Response get(String endpoint, String token) {
        ExtentReportUtil.step("INFO", "GET " + endpoint);

        Response response = given()
                .baseUri(ApiConstants.BASE_URI)
                .auth().oauth2(token)
                .when()
                .get(endpoint);

        ExtentReportUtil.step("INFO", "GET Response: " + response.getBody().asString());

        return response;
    }

    public static Response put(String endpoint, Object body, String token) {
        ExtentReportUtil.step("INFO", "PUT " + endpoint + " | Body: " + body);

        Response response = given()
                .baseUri(ApiConstants.BASE_URI)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(body)
                .when()
                .put(endpoint);

        ExtentReportUtil.step("INFO", "PUT Response: " + response.getBody().asString());

        return response;
    }

    public static Response delete(String endpoint, String token) {
        ExtentReportUtil.step("INFO", "DELETE " + endpoint);

        Response response = given()
                .baseUri(ApiConstants.BASE_URI)
                .auth().oauth2(token)
                .when()
                .delete(endpoint);

        ExtentReportUtil.step("INFO", "DELETE Response: " + response.getBody().asString());

        return response;
    }
}
