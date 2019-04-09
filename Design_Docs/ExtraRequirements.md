# Supplimentary Requirements

**Author**: Chen Sun  
            Christina Carmack  
            Jianbing Zhang  
            Xiao Qin  
			
1. The application shall maintain an underlying database to save persistent information across runs (e.g., word scrambles, player information, statistics).
1. Word scrambles and statistics will be shared with other instances of the application.  An external web service utility will be used by the application to communicate with a central server to:
   1.	Add a player and ensure that their username is unique.
   1.	Send new word scrambles and receive a unique identifier for them.
   1.	Retrieve the list of scrambles, together with information on which player created each of them. 
   1.	Report a solved scramble.
   1.	Retrieve the list of players and the scrambles each have solved.
1. All other characters and spacing will remain as they originally are.
1. The User Interface (UI) shall be intuitive and responsive.
