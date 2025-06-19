package com.bookStoreAPI.config;

/*
 * ApiConstants holds all constant values related to API endpoints, headers, and base URI.
 * Centralizes commonly used API settings to avoid duplication and hardcoding throughout the framework.
 * Used across service, utility, and test classes for consistent endpoint and configuration reference.
 */

public final class ApiConstants {

    public static final String BASE_URI = "http://127.0.0.1:8000";
    public static final String CONTENT_TYPE = "application/json";
    public static final String SIGNUP_ENDPOINT = "/signup";
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String BOOKS = "/books/";

    private ApiConstants() {
        // Utility class: prevent instantiation
    }
}
