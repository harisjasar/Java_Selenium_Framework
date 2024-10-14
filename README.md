# Automation Framework

This is a Selenium-based automation framework that uses Java, TestNG, and the Page Object Model (POM) design pattern.

## Prerequisites

- Java Development Kit (JDK) 8 or above
- Maven for dependency management
- IntelliJ IDEA or any other Java IDE (optional)
- A modern web browser (Chrome)

## Install Dependencies

To install the required dependencies, navigate to the root folder of the project and run:

- cmd
```pwsh
mvn clean install -Dmaven.test.skip.exec
```

- powershell
```pwsh
mvn clean install "-Dmaven.test.skip.exec"
```


## Running Tests with Specific Browsers
To specify a browser for the tests (e.g., Chrome), pass the browser parameter through Maven:

```pwsh
mvn test -Dbrowser=chrome
```

## Running Tests via IntelliJ IDEA
1. Open the project in IntelliJ.
2. Right-click on the testng.xml file in the root directory.
3. Select Run 'testng.xml'.

> [!IMPORTANT]  
> You must update the `testData.json` file with valid `username`, `password`, and `authToken` values before running the tests.  
> The placeholders look like this:
> 
> ```json
> {
>   "userName": "<username>",
>   "password": "<password>",
>   "authToken": "<authToken>"
> }
> ```
> Ensure you replace the `<username>`, `<password>`, and `<authToken>` with actual credentials.





https://github.com/user-attachments/assets/4ff2a0b4-76c7-483b-8da4-2c30a121dc9c





