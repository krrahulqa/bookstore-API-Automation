@UserSignUpFlowFeature @regression
Feature: User Sign In and Sign Up API Validations

  @LoginBeforeSignUp @regression @smoke
  Scenario: Login attempt before signup should return error
    When user tried to login with noSignUpUser credentials into book store system
    Then verify the login response code is400 and message contains "Incorrect email or password"

  @LoginAPIValidationWithMissingParam @regression
  Scenario: Login attempt with missing parameters should return error
    When user tried to login with missingParam credentials into book store system
    Then verify the login response code is400 and message contains "Incorrect email or password"

  @SignUpAndLogin @regression @smoke
  Scenario: Sign up as a new user and login with the same credentials
    Given Sign up to the book store as the new user with email and password
    When do the sign up with valid credentials
    Then validate signup response code is 200 and message contains "User created successfully"
    When user tried to login with valid credentials into book store system
    Then verify the login response code is 200 and message contains "access_token"
