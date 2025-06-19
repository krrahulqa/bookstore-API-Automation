package com.bookStoreAPI.services;

import com.bookStoreAPI.model.UserRequest;
import com.bookStoreAPI.config.ApiConstants;
import com.bookStoreAPI.utils.ApiRequestUtil;
import io.restassured.response.Response;

/*
 * SignUp provides the service method for registering new users through the API.
 * It exposes a static method to send sign-up requests with user data to the registration endpoint.
 * This class is used to automate and validate user registration functionality in the test framework.
 */

public class SignUp {

    public static Response signUp(UserRequest userRequest) {
        return ApiRequestUtil.postRequest(userRequest, ApiConstants.SIGNUP_ENDPOINT);
    }
}
