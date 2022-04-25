# CS 5010 Semester Project

This repo represents the coursework for CS 5010, the Fall 2022 Edition!

**Name:** Shubhangi Gupta, Satvik Khetan

**Email:** gupta.shubha@northeastern.edu, khetan.s@northeastern.edu

**Preferred Name:** Shubhangi, Satvik



### About/Overview

This is a game based on the board game "Kill Doctor Lucky" which consists of multiple, room, weapons along with players and a target. The aim of the game is to kill the target character and win the game.

###### Requirements to be implemented for Milestone 1:
1. Determine the neighbors of a space. Spaces that share a "wall" are neighbors.
2. Display information about a specified space in the world. In addition to the name of the space, this information should include what items are in the space and what spaces can be seen from the specified space.
3. Move the target character around the world. The target character starts in space 0 and moves from space to space in order using the ordered, 0-indexed list of spaces found in the world specification.
4. Create a graphical representation of the world in the form of a BufferedImage.

###### Requirements to be implemented for Milestone 2:
1. Add a controller to the game to take the inputs from the user.
2. There is a limit on the number of turns that can be played.
3. Allow players to be added to the game. Players must have unique names.
4. There should be 2 types of player - Human controlled and Computer controlled.
5. Allow players to take turns. They should get their turns in the order they were added.
6. Turn of a player includes:
   1. Look around the current space. Alongside displaying the information about the current space, display information about the neighbouring spaces as well.
   2. Move to a neighboring space of the current player's room.
   3. Pick a weapon available in the current player's room. This should remove the weapon from the room. Some players should have a limit on the number of weapons they can pick.
7. Target player moves after every turn of any player.
8. A human player can choose what turn it what turn it wants to play by taking user input.
9. A computer player should randomly choose a turn i.e. it should not take any input from the user that specifies the turn.
10. Display information about a specified space in the world. In addition to the name of the space, this information should include what items are in the space, which players are in the space and what spaces can be seen from the specified space.
11. Display information about a specified player in the world. In addition to the name of the player, this information should include what weapons the player has and the current room of the player.

###### Requirements to be implemented for Milestone 3:
1. User is displayed the details of the target player at the start of every turn (name, health and location)
2. User is displayed the details of the current player at the start of every turn (name, weapons, current room)
3. Look around command should display the details of the players current room and the details of the neighbours of the current room.
4. A target pet is added to the world.
   1. It enters the same space as the target player
   2. It makes the room in which it is present invisible to the players of the game.
   3. Players can choose to move the pet to any room in the world (not necessarily a neighbour)
   4. Extra Credit: Like the target player, the target pet moves after every turn in a DFS manner.
5. Players can choose to attack the target player.
   1. For a human player, it can choose to attack at any time in the game. The attack is unsuccessful if the target is not in the same room or if the attack was seen by some other players. It will however still be counted as a turn.
   2. For a computer player, if the target player is in the same room and the player cannot be seen by any other players in visible rooms, the player then prioritizes to attack. If not, a random turn is chosen for the player.
   3. A weapon used for attack irrespective of whether is successful or not, the weapon is removed as an evidence.
   4. If a player does not have any weapons it can choose to attack using poke which has damage value of 1.
   5. Computer player chooses the weapon that does most damage.

###### Requirements to be implemented for Milestone 4:
Requirements for Milestone 4 are mainly about creating the View using which we can play the game. Some of the requirements for that are -

1. Start game by showing a screen to welcome the user to the game along with credits to the creators of the assignment.
2. Have a JMenu that provide options to start a new game with a new world specification, starting a new game with the default world specification, and quitting the game.
3. Have a resizable JFrame with a reasonable layout in a minimum screen size of 300x300.
4. Majority of the screen should show the graphical representation of the world along with the target character and any players that have been added to the game.
5. If world is bigger than the area allocated to it on the screen, provide the ability to scroll the view.
6. Clearly indicate whose turn it is on the screen along with any information about where they are in the world.
7. Provide an option for getting the player's description by clicking on the player's graphical representation.
8. Provide an option for moving the player though the world using a mouse click. A player should not be able to make invalid moves.
9. Provide an option for the player to pick up an item in the world by pressing a key on the keyboard.
10. Provide an option for the player to look around the world by pressing a key on the keyboard.
11. Provide an option for the player to make an attempt on the target character's life by pressing a key on the keyboard.
12. Provide a clear indication of the results of each action a player takes.

### List of Features

###### Milestone 1 Features:
- The model creates a world from a text file.
- The model can get details of a room given its room name.
- The model can get the neighbours of a room given its room name.
- The model supports the movement of the target player to the next room.
- The model can get the details of the target player including it current location.
- The model can view the world created in an output png file.

###### Milestone 2 Features:
- Creates a world from a text file.
- View the world created in an output png file.
- View details of a room given its room name.
- View details of a player given its name.
- Add players to the world.
- Pick weapon for human player.
- Move to a room for human player.
- Look around for human player.
- Randomized turns for computer players.

###### Milestone 3 Features:
- Creates a world from a text file.
- View the world created in an output png file.
- View details of the room in which the current player is present.
- View details of a player given its name.
- Add players to the world.
- Pick weapon for human player.
- Move to a room for human player.
- Look around for human player.
- Move Pet for human player.
- Attack target player for human player by choice of weapon.
- Randomized turns for computer players with priority given to attack if conditions feasible.

###### Milestone 4 Features:
- The game can be played using a GUI now.
- It starts off with showing a welcome screen.
- The Menu option to select a new configuration or the default configuration and then start the game.
- The Menu option also has a Help tab to help users know what key press should be used for the operations.
- Dialog to add players to any of the available rooms, add them as human player or a computer player and specify the limit of weapons for them.
- Visit the details of current player along with other details such as whose turn it is, details of target, etc on the right panel.
- Player can do look around by pressing key "L" to get a dialog representing the details of look around.
- Player can pick a weapon by pressing key "P" to get a dialog that shows the weapons available in the room to pick from.
- Player can try to attack target by pressing key "A" to get a dialog that shows the weapons that the player is carrying and the player can choose from that list and try to kill the target.
- Player can move the pet to any of the rooms in the world by pressing key "M"
- Player can try to move the player to any of the neighboring rooms by clicking on any of the neighboring rooms.
- Automatic play for the computer player
- Game can be restarted at any point using the default configuration file or a new configuration file.


### How to Run

java -jar ./res//Milestone-4.jar [text file path] [number of turns]



### How to Use the Program

Run the jar file (Refer "How to Run") with the command line arguments.

Consider the following example:

<pre>
java -jar ./res//Milestone-4.jar ./res/FriendsWorld.txt 20
</pre>

./res/FriendsWorld.txt is the name of the input text file used to create the world.
20 is the number of turns for the players in the game.



### Example Runs

#### Milestone 1
###### /ExampleRuns/CorrectFileNameArgument.txt
1. Prints the name of the world.
2. Prints the neighbours of Bowling Alley
3. Prints the neighbours of Living Room
4. Prints the neighbours of Gym
5. Tries to get the neighbours of testRoom1 but since the room does not exist in the world,
   it displays the appropriate message.
6. Prints the current details of the target player
7. Moves the target player 3 times from room 0 to room 3 in order
8. Prints the current details of the target player
###### /ExampleRuns/IncorrectFileNameArgument.txt
1. Prints an error message that file cannot be found
2. Takes input for user for providing the file path again, but displays error as file path is wrong.
3. Takes input for user for providing the file path again, this time the path is correct.
4. Prints the name of the world.
5. Prints the neighbours of Bowling Alley
6. Prints the neighbours of Living Room
7. Prints the neighbours of Gym
8. Tries to get the neighbours of testRoom1 but since the room does not exist in the world,
   it displays the appropriate message.
9. Prints the current details of the target player
10. Moves the target player 3 times from room 0 to room 3 in order
11. Prints the current details of the target player


#### Milestone 2
###### /res/Milestone2/Milestone 2 - ExampleRun.txt
1. Displays message that welcomes user to the world.
2. Shows the number of turns left and asks the user to add players to take turns in the game.
3. Menu is displayed to the user to select an action.
4. User selects 4 to add a human player and option chosen is displayed.
5. User then enters the name, whether player has limit, the limit on weapons and the name of start room.
6. Message is displayed showing that human player was successfully added.
7. Shows the number of turns left and shows that next turn of taken will be for human player.
8. Menu is displayed to the user to select an action.
9. User selects 5 to add a computer player and option chosen is displayed.
10. User then enters the name, whether player has limit and the name of start room.
11. Message is displayed showing that computer player was successfully added.
12. Shows the number of turns left and shows that next turn of taken will be for human player.
13. Menu is displayed to the user to select an action.
14. User selects 6 to take a turn and look around and option chosen is displayed.
15. Details of the current room and the neighbouring rooms are displayed.
16. Shows the number of turns left (now 5) and shows that next turn of taken will be for computer player.
17. Menu is displayed to the user to select an action.
18. User selects 9 to take a random turn for the computer player
19. Random turn chosen is displayed as Move player and the player is moved to gym.
20. Shows the number of turns left(now 4) and shows that next turn of taken will be for human player.
21. Menu is displayed to the user to select an action.
22. User selects 7 to take a turn and move the player and option chosen is displayed.
23. User then enters the name of the destination room.
24. Message is displayed showing that human player was successfully moved to gym.
25. Shows the number of turns left(now 3) and shows that next turn of taken will be for computer player.
26. Menu is displayed to the user to select an action.
27. User selects 9 to take a random turn for the computer player.
28. Random turn chosen is displayed as Move player and the player is moved to wine cellar.
29. Message is displayed showing that computer player was successfully moved.
30. Shows the number of turns left(now 2) and shows that next turn of taken will be for human player.
31. Menu is displayed to the user to select an action.
32. User selects 8 to take a turn and pick weapon and the option chosen is displayed.
33. User then enters the name of the weapon to pick as saw.
34. Message is displayed showing that player successfully picked the weapon saw.
35. Shows the number of turns left(now 1) and shows that next turn of taken will be for computer player.
36. Menu is displayed to the user to select an action.
37. User now selects 3 to view details of a player. Option chosen is displayed.
38. User enters the name of the player as "human player".
39. Details of the human player are displayed.
40. Shows the number of turns left(still 1) and shows that next turn of taken will be for computer player.
41. Menu is displayed to the user to select an action.
42. User now selects 2 to view details of the room. Option chosen is displayed.
43. User enters the name of the room as wine cellar.
44. Details of wine cellar room and its neighbours are displayed to the user.
45. Shows the number of turns left(still 1) and shows that next turn of taken will be for computer player.
46. Menu is displayed to the user to select an action.
47. User now selects 1 to view the world. Option chosen is displayed.
48. Message is displayed telling that view of the world is saved as a png file.
49. Shows the number of turns left(still 1) and shows that next turn of taken will be for computer player.
50. Menu is displayed to the user to select an action.
51. User now selects 9 to take random turn for computer player.
52. Random option chosen is displayed as Move player.
53. Message is shown telling that computer player has moved to gym.
54. Shows the number of turns left(now 0).
55. Since turns is 0, game has ended and the appropriate message is shown.

#### Milestone 3
Used sample text file (/res/ExampleRunMansion.txt) for example runs to easily recreate certain scenarios.

###### /res/Milestone3/ExampleRun1 - HumanPlayerAttack-MovePet-TargetEscaped.txt
1. Displays the welcome message
2. Game menu displayed - Turns left 3 - No players in game - User chooses option 4 and adds human player 1 to kitchen
3. Game menu displayed - Turns left 3 - Current turn for 'human player 1' - User chooses option 4 and adds human player 2 to bathroom
4. Game menu displayed - Turns left 3 - Current turn for 'human player 1' - User chooses option 8 and picks a weapon pan from kitchen
5. Game menu displayed - Turns left 2 - Current turn for 'human player 2' - User chooses option 2 and views its room details (target and pet present)
6. Game menu displayed - Turns left 2 - Current turn for 'human player 2' - User chooses option 10 and attacks target with poke (damage value 1) - attack successful and player health decreased by 1
7. Game menu displayed - Turns left 1 - Current turn for 'human player 1' - User chooses option 9 and moves pet to kitchen
8. No more turns left - Target player escaped - Game ended

###### /res/Milestone3/ExampleRun2 - HumanPlayerWins-LookAroundWithPetInNeighbour.txt
1. Displays the welcome message
2. Game menu displayed - Turns left 7 - No players in game - User chooses option 4 and adds human player to kitchen
3. Game menu displayed - Turns left 7 - Current turn for 'human player' - User chooses option 5 and adds computer player to bedroom
4. Game menu displayed - Turns left 7 - Current turn for 'human player' - User chooses option 8 and picks a weapon pan from kitchen
5. Game menu displayed - Turns left 6 - Current turn for 'computer player' - User chooses option 11 and looks around (pet in bathroom hence details of bathroom do not show up)
6. Game menu displayed - Turns left 5 - Current turn for 'human player' - User chooses option 10 and attacks target with pan (damage value 2) - attack successful and player health decreased by 2
7. Turns left 4 - Target player dead - Game over - Game winner is 'human player'

###### /res/Milestone3/ExampleRun3 - ComputerPlayerAttackAndWin.txt
1. Displays the welcome message
2. Game menu displayed - Turns left 20 - No players in game - User chooses option 5 and adds computer player 1 to kitchen
3. Game menu displayed - Turns left 20 - Current turn for 'computer player 1' - User chooses option 11 and moves player to bathroom
4. Game menu displayed - Turns left 19 - Current turn for 'computer player 1' - User chooses option 11 and attacks target player with poke - attack successful - target player health reduced by 1
5. Game menu displayed - Turns left 18 - Current turn for 'computer player 1' - User chooses option 11 and moves pet to kitchen
6. Game menu displayed - Turns left 17 - Current turn for 'computer player 1' - User chooses option 11 and picks rod (damage value 2)
7. Game menu displayed - Turns left 16 - Current turn for 'computer player 1' - User chooses option 11 and attacks target player with rod - attack successful - target player health decreased by 2
8. Turns left 15 - Target player dead - Game over - Game winner is 'computer player 1'

###### /res/Milestone3/ExampleRun4 - PetDFS.txt
**DFS order to be followed**: Bedroom - Bathroom - Kitchen - Bathroom - Bedroom - Bathroom - Kitchen - Bathroom - Bedroom
1. Displays the welcome message
2. Game menu displayed - Turns left 20 - No players in game - User chooses option 4 and adds human player 1 to bedroom
3. Game menu displayed - Turns left 20 - Current turn for 'human player 1' - User chooses option 4 and adds human player to bedroom
4. Game menu displayed - Turns left 20 - Current turn for 'human player 1' - User chooses option 2 (current room details) - Pet is present in Bedroom
5. Game menu displayed - Turns left 20 - Current turn for 'human player 1' - User chooses option 7 (move player) and moves to Bathroom
6. Game menu displayed - Turns left 19 - Current turn for 'human player 1' - User chooses option 2 (current room details) - Pet is present in Bathroom
7. Game menu displayed - Turns left 19 - Current turn for 'human player 1' - User chooses option 7 (move player) and moves to Kitchen
8. Game menu displayed - Turns left 18 - Current turn for 'human player 1' - User chooses option 2 (current room details) - Pet is present in Kitchen
9. Game menu displayed - Turns left 18 - Current turn for 'human player 1' - User chooses option 7 (move player) and moves to Bathroom
10. Game menu displayed - Turns left 17 - Current turn for 'human player 1' - User chooses option 2 (current room details) - Pet is present in Bathroom
11. Game menu displayed - Turns left 17 - Current turn for 'human player 1' - User chooses option 7 (move player) and moves to Bedroom
12. Game menu displayed - Turns left 16 - Current turn for 'human player 1' - User chooses option 2 (current room details) - Pet is present in Bedroom
13. Game menu displayed - Turns left 16 - Current turn for 'human player 1' - User chooses option 7 (move player) and moves to Bathroom
14. Game menu displayed - Turns left 15 - Current turn for 'human player 1' - User chooses option 2 (current room details) - Pet is present in Bathroom
15. Game menu displayed - Turns left 15 - Current turn for 'human player 1' - User chooses option 7 (move player) and moves to Kitchen
16. Game menu displayed - Turns left 14 - Current turn for 'human player 1' - User chooses option 2 (current room details) - Pet is present in Kitchen
17. Game menu displayed - Turns left 14 - Current turn for 'human player 1' - User chooses option 7 (move player) and moves to Bathroom
18. Game menu displayed - Turns left 13 - Current turn for 'human player 1' - User chooses option 2 (current room details) - Pet is present in Bathroom
19. Game menu displayed - Turns left 13 - Current turn for 'human player 1' - User chooses option 7 (move player) and moves to Bedroom
20. Game menu displayed - Turns left 12 - Current turn for 'human player 1' - User chooses option 2 (current room details) - Pet is present in Bedroom
21. Game menu displayed - Turns left 12 - Current turn for 'human player 1' - User enters q
22. Game quit

#### Milestone 4
The video submission showcasing the demo of the game will be added soon.



### Design/Model Changes

#### Milestone 1
In the modified design,
1. Created a builder class, WorldBuilder, to parse the input file.
2. Created helper method in WorldImpl to check for overlapping rooms.
3. Created helper method in WorldImpl to check for invalid rooms coordinates in accordance
   with world coordinates.
4. Created helper method in WorldImpl to get room object by room name instead of room id.
5. Removed currently not required getter methods in PlayerImpl and WeaponImpl
6. Created helper method in RoomImpl to check for invalid rooms coordinates.

#### Milestone 2
###### In the modified design for controller,
1. playGame() method of world controller takes the number of turns as a parameter along with the world model.
2. Constructor of world console controller takes as input a random number generator class along with the appendable and the readable.
3. World command interface has an additional method that is used to determine of the command execution was successful or not.
4. Each command class that implements the command interface stores 3 variables - appendable to output, readable to take inputs and boolean to save the status of the command.
5. RandomTurnForComputer command class has an additional variable used to generate the random number for a real game.
6. RandomGenerator (Interface) and its implementing class RandomClass have been added. The World controller uses the RandomClass.

###### In the modified design for model,
1. Created helper method in WorldImpl to get the next turn in the game.
2. Removed helper method of getPlayersByRoom and instead added helper method getPlayerByPlayerName in worldImpl.
3. Created helper method to check if player with a name already exist in the world.
4. Added a public method checkIfPlayersExistToPlayGame to check if there are any player in the world to start the turns.
5. Added a public method to get the name if the current player
6. Added a public method to check if the current player is computer controlled or human controlled.
7. Added a public method to get the neighbour of a room given its name.
8. Added public method to get the available weapons in the room given room name.
9. Separated the TargetPlayer interface and class from the regular player interface and class.
10. Added methods in RoomImpl to add and remove players from room, get list of players in room, get details of a weapon and check if room is neighbour of current room.
11. Added getter methods in weapon class.
12. PlayerImpl uses enum for the player type instead of boolean.

#### Milestone 3
###### In the modified design for controller,
1. RandomTurnForComputer command class does not have any additional method to get the weapon with most damage. It relies on the model to provide that information.
2. RandomClass implementing RandomGenerator has an additional method to randomly generate a number between 0 and the provided upper bound as the method parameter.

###### In the modified design for model,
1. Method added in WorldImpl to get details of the target player.
2. Method added in WorldImpl to determine if game is over.
3. Method added in WorldImpl to get the name of the winner if the game is over.
4. Method getNumberOfPlayersInRoom() is removed from WorldImpl.
5. Method is added to RoomImpl to get the number of players in the room.
6. Method is added to get the health of the target player in TargetImpl.
7. 2 getter methods added to RoomImpl to determine if the target player is in room or if the pet is in room.
8. Methods getAvailableWeaponsString() and getAvailableWeaponsNames() in RoomImpl have been combined into 1 method that now takes in a parameter to determine if damage values should be part of the information.
9. Method getRoomNeighboursString() is updated to consider a boolean parameter which determines if the invisible rooms should be part of the list of neighbours.


#### Milestone 4
###### In the modified design for view,
1. Popup classes for pick weapon, attack target and move pet  have been removed. Instead normal methods have been added to the MainGameView to show the popup using JOptionPane.
2. MainGameView interface now extends the DefaultGameViewInterface.
3. An additional method has been added to show the outcome of the user's move.

###### In the modified design for controller,
1. GetTargetInformation command class has been removed
2. Method has been added to check and play turn for computer player and to show game over message.

###### In the modified design for model,
1. Interface added for WorldBuilder class.
2. Simplified ReadOnlyInterface to include only those methods that view calls to get information about target, current player, current player room and the buffered image of the world.
3. Polymorphism added for players. An abstract class is created for player and 2 new classes - one for computer and one for human player have been added
4. Number of turns is now handled in the model and corresponding changes have been made in the constructor of the world and in the worldBuilder class.
5. Removed unused methods from all classes.
6. Refactored the code to reuse existing methods thereby minimizing code duplication.
7. Random turn for computer is now handled in the model. Random class has been added to the model and removed from controller.


### Assumptions

1. World cannot have 2 or rooms with same name
2. World cannot have 2 or more players with same name.
3. Retrieving player details can be done only via clicking the player icon and can be done only for the current player.
4. Whether a player has a limit on number of weapons or not is dependent on the user.
5. If an attempt on a turn is unsuccessful, number of turns left does not change.
6. A room that has a pet is invisible to the player. Therefore the player cannot move to a neighboring room that has the pet since the room is invisible. It is not counted as a turn.
7. All the players should be added in the beginning of the game.
8. Player can choose to start off a new game with a new configuration or the default one at any point of time during the game.
9. Attack on target player by computer player can fail if there are any players in the invisible neighbours of the current room.
10. View of the game shows the current player with a blue player icon. All other players that are added to the world are still visible but are grouped using a multi-player icon.


### Limitations

The project covers all the requirements given in the project description. Although, there can be
a few limitations
1. Room details displays the name of the neighbours and not details of the neighbouring rooms.
2. Room details does not show the invisible neighbouring rooms.
3. Player details can be obtained only for the current player.
4. If an input from a series of inputs is invalid, the user will have to select the turn and enter all values all over again.
5. When the pet moves automatically using DFS,
   a. If it starts from a room that does not have any neighbours, the DFS is stuck on that room only and the pet does not move to any other room.
   b. If a room has 0 neighbours and the DFS does not start from that room, then the pet will never reach that room unless the player specifically chooses to move the pet to the room.
6. To view the number of other players in current/neighbouring rooms, user will have to use the lookAround command as currently a multi-player icon is used to show existence and not the count of players.


### Citations

1. Effective Java
   - https://learning.oreilly.com/library/view/effective-java/9780134686097/
2. Head First Design Patterns
   - https://learning.oreilly.com/library/view/head-first-design/9781492077992/
3. Canvas Module 03 - Models & the MVC Pattern
   - https://northeastern.instructure.com/courses/108354/pages/module-3-models-and-the-mvc-pattern?module_item_id=6907177
4. Canvas Module 04 - MVC Controllers
   - https://northeastern.instructure.com/courses/108354/pages/module-4-mvc-controllers?module_item_id=6907187
5. Canvas Module 10 - MVC Views & GUIs
   - https://northeastern.instructure.com/courses/108354/pages/module-10-mvc-views-and-guis?module_item_id=6907248
6. Visual Guide to Swing Components
   - https://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html
7. Visual Guide to Layout Managers in Java
   - https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html