# 📦 Release Notes

## 🚀 Version 1.0.0 - Initial Release

### 🔥 Highlights

- 🎯 Fully functional API automation framework designed for a FastAPI-based Bookstore backend.
- 🧪 BDD-style test cases leveraging **Cucumber + RestAssured** for clear, scenario-driven validation.
- ⚙️ **Automated backend launch** via Java’s ProcessBuilder ensures seamless test startup.
- 📊 Comprehensive HTML report generation using **ExtentReports** for quick and easy insights.
- 🛠️ **Reusable utilities** for service logic, configuration, and reporting simplify code maintenance.
- ☁️ Ready for CI/CD integration with GitHub Actions.
- 🧱 Highly modular and scalable folder structure to support future growth.

---

### ✅ Core Features

- End-to-end authentication workflow coverage (registration, login, and edge cases)
- CRUD operations for Book Management with extensive validation
- Centralized configuration and request specification handling
- Automatic FastAPI backend startup before test execution
- Clean separation of code concerns: service logic, hooks, step definitions, and utilities

---

### 📁 Project Directory Overview
bookstore-api-test/
├── bookstore-main/                 # FastAPI backend code
│   ├── main.py                     # Entry point for the FastAPI app
│   └── requirements.txt            # Python library dependencies
├── src/
│   └── main/java/com/bookStoreAPI/
│       ├── config/                 # Configuration helpers (ApiConstants, ConfigReader)
│       ├── model/                  # Java models for request payloads (BookRequest, UserRequest)
│       ├── services/               # API service Classes (BookService, Login, SignUp)
│       ├── utils/                  # Utilities: API requests, config loader, reporting, REST, server tools
│           ├── ApiRequestUtil
│           ├── ConfigLoader
│           ├── ExtentReportUtil
│           ├── RestUtil
│           └── ServerManager
│   └── test/java/com/bookstore/
│       ├── runners/                # Test runners (RegressionTestRunner)
│       ├── stepdefs hooks/         # Test setup/teardown hooks
│       ├── stepdefs/               # Step definitions for scenarios
├── features/                       # Cucumber feature files
├── pom.xml                         # Maven configuration
└── README.md                       # Project documentation


bookstore-main/: Python FastAPI backend code and dependencies.
src/main/java/com/bookStoreAPI/: Main Java source code.
config/: Static constants and config reading utility.
model/: POJOs for API request/response payloads.
services/: Service layer for API logic.
utils/: Common utilities (API request, config, reporting, REST, server management).
src/test/java/com/bookstore/: All Java test code.
runners/: Cucumber runner classes.
stepdefs hooks/: Hooks for before/after scenario logic.
stepdefs/: Step definitions matching feature steps.
features/: Cucumber .feature files.
pom.xml: Maven project configuration.
README.md: Documentation.

### 🛠️ Getting Started

The utils package under com.bookStoreAPI contains utility classes supporting the test framework:

ApiRequestUtil: Handles reusable logic for making API requests.
ConfigLoader: Loads configuration properties and environment variables.
ExtentReportUtil: Manages creation and update of HTML test reports using ExtentReports.
RestUtil: Provides helper methods for REST actions and validation.
ServerManager: Handles backend server startup and shutdown logic.
The runners package under com.bookstore in the test directory contains classes like RegressionTestRunner to configure and trigger Cucumber test execution. This typically includes tags, glue paths, and reporting setup.

The project is organized to ensure:

Centralized configuration and reporting,
Reusable service and utility logic,
Clear separation between main code and test code,
Easy extension for new API services, models, and test features.


### ⚙️ Setup Notes

- Java 17 or higher recommended
- Maven is used for build and test execution
- Python dependencies are listed in `bookstore-main/requirements.txt`

### 🧪 Test Execution & Reporting

#### Run Tests

```bash
mvn clean test
```



#### View Extent Report

```bash
target/cucumber-reports/ExtentReport.html
```

---

### 🛠️ CI/CD Pipeline (GitHub Actions Example)

```yaml
name: CI Pipeline

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v3

    - name: Set up Java
      uses: actions/setup-java@v3
      with:
        java-version: '17'

    - name: Install Python dependencies
      run: |
        cd bookstore-main
        pip install -r requirements.txt

    - name: Start FastAPI server
      run: |
        nohup uvicorn main:app --port 8000 &
      working-directory: bookstore-main

    - name: Run API Tests
      run: mvn clean test
```

---


## 🧪 Jenkins Integration

This project can be easily integrated into Jenkins for continuous integration and reporting.

### 🔧 Freestyle Job Setup

1. **Create a new Freestyle job** in Jenkins.
2. Under **Source Code Management**, choose Git and enter your GitHub repository URL.
3. Under **Build**, add a build step: `Invoke top-level Maven targets` and use:
   ```
   clean test
   ```
4. Under **Post-build Actions**, add **Allure Report** with results directory:
   ```
   target/allure-results
   ```

### 🧰 Requirements on Jenkins Server

- **Java 17+**
- **Maven**
- **Python 3 & pip**
- **Allure CLI** (installed and added to system path)
- Required Jenkins plugins:
    - Git Plugin
    - Maven Integration
    - Allure Jenkins Plugin




### 📝 Jenkins Pipeline  ######

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven 3'
        jdk 'Java 17'
    }

    environment {
        ALLURE_RESULTS_DIR = "target/allure-results"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/your-username/bookstore-api-test.git'
            }
        }

        stage('Install Backend Dependencies') {
            steps {
                sh 'pip install -r bookstore-main/requirements.txt'
            }
        }

        stage('Start FastAPI Server') {
            steps {
                sh '''
                    cd bookstore-main
                    nohup uvicorn main:app --port 8000 &
                '''
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Allure Report') {
            steps {
                allure includeProperties: false, results: [[path: 'target/allure-results']]
            }
        }
    }
}

### 🔮 Future Enhancements

- Test parallelization using JUnit 5 or TestNG
- Dynamic environment and credential management
- Integration with Swagger/OpenAPI spec validation
- Docker support for backend and test containers
- Scheduled nightly runs and Allure history dashboard

