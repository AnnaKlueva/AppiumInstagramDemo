## Android app with Appium

#### Requires:
Appium Java client  >= 6.1.0
Running instance of Appium server >= 1.7

#### Test Subject
Test application instagram.apk (added to /resources folder)

#### Functionality covered
1. Instagram - main screen and profile page actions

#### How To Run
mvn clean test -DrunType=local or sauceLabs  (by default for local runs -DrunType=local)

default driver is UiAutomator2

#### Allure report
1. To run tests and generate Allure report:

```
mvn clean test
```
2. To see a report:

```
allure serve path_to_allure_results_folder/allure-results
```
