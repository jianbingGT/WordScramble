# Team Deliverable 1 for Team 80:

Chen Sun  
Jianbing Zhang  
Xiao Qin  
Christina Carmack  

## Design 1:
Author: Christina Carmack  

![Design 1](/GroupProject/Design-Team/images/design_1.png)

### *Pros:*
1. Included getters and setters  
1. Used public / private markings
1. Included the return types for methods

### *Cons:*
1. Missing link to EWS for Puzzle class's puzzle_solved() method  
1. Some methods in Main_App could be spun off to simplify it, such as making a statistics class  
1. There should be a connection between WordScramble and the EWS because the EWS contains all the WordScrambles  
1. The database could be added to the design to increase clarity  
1. The associations that EWS has with both Main_App and Player should be dependencies instead  

## Design 2:
Author: Jianbing Zhang  

![Design 2](/GroupProject/Design-Team/images/design_2.png)

### *Pros:*
1. Used class inheritance.
1. Login UI.
1. Use the private and public marking.
1. Including the returning data types for operations

### *Cons:*
1. Seems too many classes.
1. The arrow for ExternalWebService is wrong.
1. The loginCreateNewUser is not related to EWS as a utility class to store all the players and words.
1. Do not include main_app to interact with Player and WordScramble classes. 
1. Not method to store the user information (Username to indentify the user).
1. Some private / public markings are wrong.
1. Not including the returning data types for every operation.

## Design 3:
Author: Chen Sun  

![Design 3](/GroupProject/Design-Team/images/design_3.png)

### *Pros:*
1. Included getters and setters  
1. Used public / private markings  
1. Indicate the relationship which stored at local database

### *Cons:*
1. Scramble Algorithm class not necessary on UML.
1. Need a method to update solved to EWS instead of local database
1. get_playlist():hash not necessary, can be added to scramble attribute in a player object
1. Added exit() method to Player class.

## Design 4:
Author: Xiao Qin  

![Design 4](/GroupProject/Design-Team/images/design_4.png)

### *Pros:*
1. Main App checks for new users

### *Cons:*
1. Not completed, needs more classes and relationships between them
1. Dupicated information between classes
1. Missing method to create player with unique username from ExternalWebService

## Team Design:

![Team Design](/GroupProject/Design-Team/images/design-team.png)

### *Commonalities with individual designs:*
1. Included Main_App
1. Included separate classes for Players and Scrambles
1. Included methods to calculate statistics from information provided by the ExternalWebService
1. Included method to persist in-progress Scrambles to local database
1. Included getter and setter methods
1. Included return types on methods
1. Included inheritance (Jianbing)
1. Included exit method

### *Differences from individual designs:*
1. ExternalWebService only takes and returns basic types

### *Explaination of team design:*
1. Since ExternalWebService has a method for reporting solved scrambles, we assumed it used that method to record which Scrambles had been solved by a given player.
1. All statistics are calculated in the Main App by retrieving the Players and Scrambles from the ExternalWebService and using that data.
1. In progress scrambles have their ID, the current player, and the current guess persisted to the local database.

## Summary:
### *Lessons Learned:*
1. Different time zones make finding good meeting times difficult.
1. We all have different levels of experience.
1. Learn how to ask the right question to understand each design.
1. LucidChart can be used collaboratively.
1. How to use WebEx.
1. Learn how to systematically use the operations in each class to set the relationship and dependency to other classes. 
1. Learned how to use Maps and String arrays instead of getting information only from the database.
