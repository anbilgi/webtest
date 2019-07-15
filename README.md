# Introduction
webtest is a selenium based automation suite to test https://revolut.com.

### Tech/framework used
 * Java
 * Maven
 * Selenium
 * TestNG 

### Installation
This project needs firefox version 57, which can be downloaded from here :
 https://ftp.mozilla.org/pub/firefox/releases/59.0/mac/en-GB/
 
And Geckodriver version v0.20.1 which can be downloaded from here :
 https://github.com/mozilla/geckodriver/releases/tag/v0.20.1

Once the gecko driver is installed on your local machine, You need to provide the **absolute path to geckodriver
in the** **config.properties** file.

### Project Structure
config.properties file has the path to geckodriver and URL to revolut homepage.
And the testNG.xml is the suiteXmlFile to execute the test suite.

### Steps to run the test
Run the following command to execute the test suite from the root directory of the project.

  **``` mvn clean install test -Dsurefire.suiteXmlFiles=src/main/resources/testNG.xml ```**

### Surefire reports
Test reports can be found in path : target/surefire-reports/emailable-report.html.

Here's a sample emailable-report.html from the test suite run from my local machine.
![Screenshot] (src/main/resources/emailable-report.png)