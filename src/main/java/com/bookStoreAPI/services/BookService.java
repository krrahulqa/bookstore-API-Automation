package com.bookStoreAPI.services;

import com.bookStoreAPI.model.BookRequest;
import com.bookStoreAPI.config.ApiConstants;
import com.bookStoreAPI.utils.RestUtil;
import io.restassured.response.Response;

/*
 * BookService provides static methods to perform CRUD operations on book entities via API endpoints.
 * It acts as a service layer between the test logic and the HTTP request utility.
 * All methods require a user authentication token and interact with the API using RestUtil.
 * Used throughout the automation framework to create, retrieve, update, and delete books for test scenarios.
 */

public class BookService {

    public static Response createBook(BookRequest book, String token) {
        return RestUtil.post(ApiConstants.BOOKS, book, token);
    }

    public static Response getAllBooks(String token) {
        return RestUtil.get(ApiConstants.BOOKS, token);
    }

    public static Response getBookById(int id, String token) {
        return RestUtil.get(ApiConstants.BOOKS + id, token);
    }

    public static Response updateBook(int id, BookRequest book, String token) {
        return RestUtil.put(ApiConstants.BOOKS + id, book, token);
    }

    public static Response deleteBook(int id, String token) {
        return RestUtil.delete(ApiConstants.BOOKS + id, token);
    }
}
