#Assignment

#Dependencies:
selenium-api 3.14.0, selenium-remote-driver 3.14.0, testng 6.8, selenium-firefox-driver 2.53.0, selenium-chrome-driver 3.141.0

#TestFolder:
There is a class named as "TestClass" which has 04 test methods
location: src/test/java/TestClass

# Library folder
Library :
1. src/main/java/Common/ReadProperty.class
2. src/main/java/Pages/RouteCalculation.class

# Output
=> routes.txt placed under src/test/java

# Testng file
This file is placed under src directory with name: testng.xml

# How to run:
1. install maven on your machine
2. Set apache maven bin path
3. use below command to run the project from command line:
=> mvn clean test -Dsuite=testng.xml

# Output - surefire reports
=> There is a folder "target" which gets created once you run above mvn command
All 04 tests are passing
Results can be seen in index.html file under target folder

# Test Cases:
1. Verify file1 is not blank
2. Verify file2 is not blank
3. Verify response of urls present in file1 is not empty
4. Verify response of urls present in file2 is not empty
5. Compare output of urls present in both files

# Drawback of current approach
=> comparing json strings gets failed sometimes because of the data positioning in json response which gets changed

# Things yet to be done:
Need to convert json response into map & validate the response using each key in nested maps


