
# Selenium Java Sample Framework Design

This is a Selenium framework that provides a set of utility functions and supports various features like data provider, parallel testing, extent report, allure report, and keywords for each WebDriver action.

## Setup

1. Clone the repository:

run `git clone git@github.com:nageshkarup/SeleniumJavaFramework.git`

2. Install the desired IDE. eg. Eclipse or IntelliJ

3. Configure test environment:
- Update the `config.properties` file with the appropriate browser, URL, and other configurations.
- Provide necessary credentials or test data in the `test_data.xlsx` file.

## Utility Functions

The framework includes utility functions for common tasks such as:
- Handling WebDriver initialization and management.
- Taking screenshots on test failure.
- Reading test data from Excel files.
- Logging and reporting.
- Custom listener implementation
- Assertion utils
  
## Data Provider

The framework supports data-driven testing using TestNG's `@DataProvider` annotation. You can define test data in Excel files and easily retrieve it using the `ExcelUtils` utility class.

## Parallel Testing

Parallel execution of tests is supported using TestNG's parallel execution features. You can configure the number of parallel threads in the `testng.xml` file.

## Reporting

The framework provides two reporting options: Extent Report and Allure Report.

- Extent Report: A detailed HTML report is generated after test execution, showing test case status, logs, screenshots, and other relevant information. The report is available in the `extent-report` directory.
![Selection_963](https://github.com/nageshkarup/SeleniumJavaFramework/assets/88143302/9a1cae56-a2de-46aa-b1d6-2316d7e5c822)
![Selection_964](https://github.com/nageshkarup/SeleniumJavaFramework/assets/88143302/af6afff9-be8a-434e-a2e8-f8d707e7931e)


- Allure Report: Allure report generates interactive and comprehensive HTML reports with detailed information about test execution, test steps, logs, attachments, and more. The report is available in the `allure-report` directory.
![Selection_961](https://github.com/nageshkarup/SeleniumJavaFramework/assets/88143302/3f433138-7abe-480c-b16f-63e6a636a3a0)
![Selection_962](https://github.com/nageshkarup/SeleniumJavaFramework/assets/88143302/7555d321-29b6-46df-8a6e-fd32e2e81f31)

  
## WebDriver Actions

The framework includes a set of keywords for common WebDriver actions, such as:
- Interacting with input fields, buttons, dropdowns, checkboxes, and radio buttons.
- Handling alerts, frames, and windows.
- Performing mouse actions (click, hover, drag and drop, etc.).
- Executing JavaScript code.
- Explicit wait
- Handling dynamic loaded elements
- Locating elements by various strategies (XPath, CSS selector, etc.).

Refer to the documentation or the code itself for more information on the available keywords and their usage.


