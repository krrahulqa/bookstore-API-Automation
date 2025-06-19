package com.bookStoreAPI.services;

import com.bookStoreAPI.model.UserRequest;
import com.bookStoreAPI.config.ApiConstants;
import com.bookStoreAPI.utils.ApiRequestUtil;
import io.restassured.response.Response;

/*
 * Login provides a service method for user authentication via the API.
 * It exposes a static method to send login requests using user credentials and returns the API response.
 * This class is used to authenticate users and retrieve authentication tokens for subsequent API calls in automation tests.
 */

public class Login {

    public static Response login(UserRequest userRequest) {
        return ApiRequestUtil.postRequest(userRequest, ApiConstants.LOGIN_ENDPOINT);
    }
}
