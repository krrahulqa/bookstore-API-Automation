package com.bookStoreAPI.utils;



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
