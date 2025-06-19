package com.bookStoreAPI.model;

import java.util.Objects;

/*
 * BookRequest models the structure for book data used in API requests and responses.
 * It encapsulates core book attributes (id, name, author, published_year, summary),
 * and provides constructors, getters, setters, and standard overrides for use in test automation.
 * Used throughout the framework for creating, updating, and validating book information.
 */

public class BookRequest {
    private int id;
    private String name;
    private String author;
    private int published_year;
    private String book_summary;

    public BookRequest(String name, String author, int published_year, String book_summary) {
        this.id = (int) (System.currentTimeMillis() % 1000);
        this.name = name;
        this.author = author;
        this.published_year = published_year;
        this.book_summary = book_summary;
    }

    public BookRequest(int id, String name, String author, int published_year, String book_summary) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.published_year = published_year;
        this.book_summary = book_summary;
    }

    public void setId(int id) { this.id = id; }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getAuthor() { return author; }

    public int getPublishedYear() { return published_year; }

    public String getBookSummary() { return book_summary; }

    @Override
    public String toString() {
        return "BookRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", published_year=" + published_year +
                ", book_summary='" + book_summary + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookRequest)) return false;
        BookRequest book = (BookRequest) o;
        return id == book.id &&
                published_year == book.published_year &&
                Objects.equals(name, book.name) &&
                Objects.equals(author, book.author) &&
                Objects.equals(book_summary, book.book_summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, published_year, book_summary);
    }
}
