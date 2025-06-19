package com.bookstore.stepdefs;


import com.bookstore.utils.RestUtil;

public class BookManagementSteps

    private RestUtil restUtil;

    public BookManagementSteps() {
        this.restUtil = new RestUtil();
    }

    public void addBook(String bookDetails) {
        // Logic to add a book using restUtil
        restUtil.post("/books", bookDetails);
    }

    public void updateBook(String bookId, String updatedDetails) {
        // Logic to update a book using restUtil
        restUtil.put("/books/" + bookId, updatedDetails);
    }

    public void deleteBook(String bookId) {
        // Logic to delete a book using restUtil
        restUtil.delete("/books/" + bookId);
    }

    public String getBook(String bookId) {
        // Logic to get a book using restUtil
        return restUtil.get("/books/" + bookId);
    }
