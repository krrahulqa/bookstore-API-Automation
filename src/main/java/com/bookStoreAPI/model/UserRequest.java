package com.bookStoreAPI.model;

import java.util.Objects;

/*
 * UserRequest models the user entity for API requests and responses in the bookstore framework.
 * It encapsulates user attributes (id, email, password) and provides constructors, getters, setters,
 * and overrides for equals, hashCode, and toString. This class is used for user signup, login,
 * and validation operations throughout the test automation suite.
 */

public class UserRequest {
    private int id;
    private String email;
    private String password;

    public UserRequest() {}

    public UserRequest(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "UserRequest{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + "[PROTECTED]" + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRequest)) return false;
        UserRequest userRequest = (UserRequest) o;
        return id == userRequest.id &&
                Objects.equals(email, userRequest.email) &&
                Objects.equals(password, userRequest.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }
}
