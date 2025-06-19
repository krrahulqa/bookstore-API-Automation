
@BookManagementFeature @regression
Feature: Book Management API Validations

  Background:
    Given a user signs up and logs in successfully

  @CreateBook @regression @smoke
  Scenario Outline: Create a book with valid details
    Given a book payload with name "<name>", author "<author>", year <year>, and summary "<summary>" is prepared
    When user sends a request to create a new book
    Then validate book creation response code is200 and response contains book details

    Examples:
      | name         | author   | year | summary           |
      | MyBook1 | Alice    |2024 | A story begins    |
      | AutomationQA | Bob      | 2025 | Test coverage     |
