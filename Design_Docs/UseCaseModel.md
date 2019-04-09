# Use Case Model

**Author**:   
            Chen Sun  
            Christina Carmack  
            Jianbing Zhang  
            Xiao Qin  

## 1. Use Case Diagram
![Use Case Diagram](/GroupProject/Docs/images/UseCaseDiagram.jpeg)

## 2. Use Case Descriptions

Use Case 1: User Logs In  
Requirements:  
The user inputs their username to log into the application in order to create and solve puzzles or look at statistics.  
Pre-conditions:  
Application has been started  
Post-conditions:  
User is logged in  
Scenarios:  
Scenario 1:  
1. User chooses Log-in option from initial screen
2. User enters username into input field
3. User taps Log-in button
4. System authenticates user
5. User proceeds to main application screen
Scenario 2:  
1. User chooses Log-in option from initial screen
2. User enters invalid username into input field
3. User taps Log-in button
4. System is unable to authenticate user
5. User is shown invalid log-in error
6. User clicks "ok" button
7. User is returned to initial screen
8. User chooses Log-in option from initial screen
9. User enters correct username into input field
10. User taps Log-in button
11. System authenticates user
12. User proceeds to main application screen

Use Case 2: Register New User  
Requirements:  
The user inputs their information and desired username into the application to create a new user in the system that they can use to log in  
Pre-conditions:  
Application has been started  
Post-conditions:  
User is logged in with the new user name  
Scenarios:  
Scenario 1:  
1. User chooses Register from initial screen
2. User enters their first name
3. User enters their last name
4. User enters their email address
5. User enters their desired user name
6. User taps OK button
7. System checks if user name is in use
8. User name is not found among current users
9. New user is created with unchanged user name
10. User is logged in as this new user
Scenario 2:  
1. User chooses Register from initial screen
2. User enters their first name
3. User enters their last name
4. User enters their email address
5. User enters their desired user name
6. User taps OK button
7. System checks if user name is in use
8. User name is found among current users
9. User name has a 0 added to the end
10. System checks if user name is in use
11. User name is not found among current users
12. New user is created with unchanged user name
13. User is logged in as this new user

Use Case 3: Check Scramble Statistics  
Requirements:  
A logged in user selects to look at the Scramble Statistics  
Pre-conditions:  
Application has been started, User has logged in  
Post-conditions:  
None  
Scenarios:  
Scenario 1:  
1. User selects Statistics from initial screen
2. User selects Scramble statistics
3. System displays Scramble statistics

Use Case 4: Check Player Statistics  
Requirements:  
A logged in user selects to look at the Player Statistics  
Pre-conditions:  
Application has been started, User has logged in  
Post-conditions:  
None  
Scenarios:  
Scenario 1:  
1. User selects Players from initial screen
2. User selects Player statistics
3. System displays Player statistics

Use Case 5: Create a Qualified Scramble  
Requirements:  
A logged in user selects to create a new scramble  
Pre-conditions:  
Application has been started, User has logged in  
Post-conditions:  
The new scramble created by user should be qualified to the requirements of scrambles in list  
Scenarios:  
Scenario 1:  
1. User selects to create a new scramble
2. User inputs a phrase
3. User inputs a clue
4. User tap the Scramble button until the user finds the scramble acceptable
5. User selects Add Scramble
6. System checks and confirms this new scramble is not created yet 
7. System checks and confirms this new scramble is qualified to the requirements of system
8. System confirm that creating a new scramble sucessfully and restore it in Scramble Statistics
9. User proceeds to main application screen

Use Case 6: Create an Unqualified Scramble  
Requirements:  
A logged in user selects to create a new scramble  
Pre-conditions:  
Application has been started, User has logged in  
Post-conditions:  
The new scramble created by user should be unqualified to the requirements of scrambles in list  
Scenarios:  
Scenario 1:  
1. User selects to create a new scramble
2. User inputs a phrase
3. User inputs a clue
4. User tap the Scramble button until the user finds the scramble acceptable
5. User selects Add Scramble
6. System checks and confirms this new scramble is created yet or unqualified to the requirements of system
7. System will send out an unqualified error message to user
8. User proceeds to input another scramble

Use Case 7: Start a new Scramble  
Requirements:  
A logged in user selects to start a new scramble  
Pre-conditions:  
Application has been started, User has logged in  
Post-conditions:  
Scramble will be started and in progress  
Scenarios:  
Scenario 1:  
1. User selects to start a new scramble
2. System will display list of available scrambles
3. User will select desired scramble to work on
4. System will display that scramble

Use Case 8: Solved Scramble  
Requirements:  
A logged in user selects to solves a scramble  
Pre-conditions:  
Application has been started, User has logged in, a Scramble has been started  
Post-conditions:  
Scramble will be solved  
Scenarios:  
Scenario 1:  
1. User inputs a guess for the scramble
2. User selects submit
3. System checks that user's guess is correct
4. System records Scramble as being solved by user
5. System displays congratulations message to user
6. User selects ok
7. User is returned to the main screen

Use Case 9: Unsolved Scramble  
Requirements:  
A logged in user selects to solves a scramble  
Pre-conditions:  
Application has been started, User has logged in, a Scramble has been started  
Post-conditions:  
The guess is wrong. Scramble will still be unsolved. System can let user guess again or exit to main screen, saving progress  
Scenarios:  
Scenario 1:  
1. User inputs a guess for the scramble
2. User selects submit
3. System checks that user's guess is wrong
4. System will let user to choose guess again or exit
5. If user choose to guess again, system will let user return to input a guess screen. If user choose to exit, system will let user to return to main screen.
6. System will save user's progress.

Use Case 10: Restart In-Progress Scramble  
Requirements:  
A logged in user selects to solves a scramble  
Pre-conditions:  
Application has been started, User has logged in, user must have a scramble that they have guessed incorrectly before  
Post-conditions:  
Scramble will be solved or still be unsolved and in-progress  
Scenarios:  
Scenario 1:  
1. User views the scramble list and choose a scramble in-progress
2. System display this word scramble user chose
3. User inputs a guess for the scramble
4. User selects submit
5. System checks that user's guess is correct or wrong
6. If user's guess is correct, System will display congratulations message and records Scramble as being solved by user. User will select Ok. System will let user to return to main screen.
7. If user's guess is wrong, System will let user to choose guess again or exit. User can choose guess again to return to input or choose exit to return to main screen. System will mark this scramble as unsolved and save the progess.



















