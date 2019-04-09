# Test Plan

**Author**: \<Team80\> 

## 1 Testing Strategy

### 1.1 Overall strategy
Unit test will be done during development as the code is ready, to make sure each method in units work as designed.

Tester will be in charge of system test and regression test with a new build, maybe automation stress test with Junit.

### 1.2 Test Selection


It will deploy blackbox test at system level which focus on input and output, and whitebox test at unit level which focus on logic behavior.


### 1.3 Adequacy Criterion

Unit test is to cover the function of each module and system test is to validate the system integration.

### 1.4 Bug Tracking

As the control flow in bugzilla or JIRA, we will deploy a shared google document to perform this function for this small project.

### 1.5 Technology

We will perform the manual test as development goes, may add automation test with JUnit to perform reliability and stress test.

## 2 Test Cases

### Unit Tests  
| Module | Current # of Tests | # Passed |
|--------|--------------------|----------|
| Main_App | 4 | 4 |  |  
| EWS_Facade | 5 | 5 |  |  
| Player | 6 | 6 |  |  
| Puzzle | 4 | 4 |  |  
| WordScramble | 5 | 5 |  |  
| Database_Handler | 5 | 5 |  |  

##  system test
| Test Case | Purpose | Steps | Expect | Result|
|-----------|---------|-------|--------|-------|
|1 |To verify that an existing user can log in with a correct user name | 1. Choose Login from initial screen <br> 2. Input correct, existing user name <br> 3. Tap Login button | User is logged in | Passed |
|2 |To verify that a user can not log in with an unrecognized user name | 1. Choose Login from initial screen <br> 2. Input non-existant user name <br> 3. Tap Login button | Error message is displayed | Passed |
|3 |To verify that adding a new user with an already existing user name returns a new user with a unique user name | 1. Choose Register from initial screen <br> 2. Input first name <br> 3. Input last name <br> 4. Input email address <br> 5. Input already existing user name <br> 6. Choose OK | Returned user name has had numbers appended to make it unique | Passed |
|4 |To verify a new user can log in after the user name has been created | 1. Create a new user <br> 2. Exit application <br> 3. Restart application <br> 4. Choose Login from initial screen <br> 5. Input newly created user name <br> 6. Choose Login | User is logged in | Passed |
|5 |To verify that a new scramble can be created successfully | 1. Log in <br> 2. Choose Create New WordScramble <br> 3. Input word or phase to be scrambled <br> 4. Input clue <br> 5. Choose Scramble <br> 6. Once scramble is acceptable, choose OK <br> 7. Choose Find Scrambles | New scramble should appear in the list of available scrambles | Passed |
|6 |To verify that a new scramble can be accessed by other users | 1. Create a new scramble <br> 2. Exit the application <br> 3. Restart the application <br> 4. Log in as a different user <br> 5. Choose Find Scrambles | New scramble should appear in the list of available scrambles | Passed |
|7 |To verify that scramble statistics are returned as expected | 1. Log in <br>  2. Choose Scramble Statistics | All scrambles should be listed with their unique ID, if they have been solved by this player, if they were created by this player, and the number of times they have been solved by any player | Passed (mock EWS is not supported fully.)|
|8 |To verify that player statistics are returned as expected | 1. Log in <br> 2. Choose Player Statistics | All players should be listed with their first and last names, number of scrambles they have sovled, number of scrambles they have created, and the average number of times their created scrambles have been solved. | Passed<br> (mock EWS is not supported fully.)|
|9 |To verify that in progress scrambles are saved | 1. Log in <br> 2. Choose Find Scrambles <br> 3. Choose a scramble <br> 4. Make an incorrect guess <br>  5. Log out user <br> 6. Log in as the same user <br> 7. Choose Find Scrambles <br> 8. Choose the previous scramble | Scramble should be loaded with last guess still present | Passed|

