package com.bookStoreAPI.utils;

import com.bookStoreAPI.config.ApiConstants;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/*
 * ApiRequestUtil is a utility class for sending HTTP requests with standardized configuration.
 * It centralizes the setup for API requests (such as base URI and content type) to ensure consistency.
 * Currently, it supports POST requests for sending data to API endpoints.
 * This class is used by service classes to interact with API endpoints cleanly and reliably.
 */

public class ApiRequestUtil {

    public static Response postRequest(Object body, String endpoint) {
        return given()
                .baseUri(ApiConstants.BASE_URI)
                .contentType(ApiConstants.CONTENT_TYPE)
                .body(body)
                .when()
                .post(endpoint);
    }
}
