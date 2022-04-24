package model.world;

import java.util.List;

/**
 * An interface class for representing the World for Kill Doctor Lucky.
 *
 */
public interface WorldInterface extends ReadOnlyWorldInterface {

  /**
   * Get the list of rooms present in the world.
   * @return List of room names
   */
  List<String> getListOfRooms();

  /**
   * Get the weapons that the current player is carrying.
   * @return List of weapons with player.
   */
  List<String> getCurrentPlayerWeapons();

  /**
   * Get the weapons present in current player's room.
   * @return List of weapons present in the room.
   */
  List<String> getCurrentPlayerRoomWeapons();

  /**
   * Check if the player icon is clicked.
   * @param r Row coordinate where the click is made
   * @param c Column coordinate where the click is made.
   * @return Boolean representing whether the player icon is clicked or not.
   * @throws IllegalArgumentException when row or column coordinate is invalid.
   */
  boolean isPlayerIconClicked(int r, int c);

  /**
   * Update the world view after the recent changes.
   * @param isLookAround Boolean representing whether the update is because of the look around.
   * @throws IllegalArgumentException when the world view cannot be updated.
   */
  void updateWorldView(boolean isLookAround);

  /**
   * Adds a player to the world.
   *
   * @param playerName        represents the name of the player
   * @param weaponLimit       represents the limit on weapons
   * @param isComputerPlayer  represents if the player is computer controlled
   * @param startingRoomName  represents the name of the starting room
   * @throws IllegalArgumentException when the player name or weapon limit or startingRoomName is
   * invalid.
   */
  void addPlayerToGame(String playerName, int weaponLimit,
                       boolean isComputerPlayer, String startingRoomName);

  /**
   * Retrieves the details of the current room of the current player along with the details
   * of the neighboring rooms.
   *
   * @return the details of the current room and its neighbours
   */
  String lookAroundSpace();

  /**
   * Handle the click on the room.
   * @param x X coordinate of the click.
   * @param y Y coordinate of the click.
   * @return String representing whether the player was able to move.
   * @throws IllegalArgumentException when any of the coordintes is negative.
   */
  String handleRoomClick(int x, int y);

  /**
   * Get the information about a specific room.
   * @param roomName Name of the room.
   * @return String representing the information about the specified room.
   * @throws IllegalArgumentException when the room name is invalid.
   */
  String getRoomInformation(String roomName);

  /**
   * Retrieves the details of the player which includes the name, list of available weapons
   * and the current location of the player.
   *
   * @return details of the player
   */
  String getCurrentPlayerInformation();

  /**
   * Picks the weapon for the current player.
   *
   * @param weaponName represents the name of the weapon that the player has to pick.
   * @return String representing that player has picked the specified weapon.
   * @throws IllegalArgumentException when the weapon name is invalid.
   */
  String pickWeapon(String weaponName);

  /**
   * Moves the pet to the room.
   *
   * @param roomName represents the name of the room to which the pet has to be moved.
   * @return String representing that player has moved the pet.
   * @throws IllegalArgumentException when the room name is invalid.
   */
  String movePet(String roomName);

  /**
   * Attacks the target player with a weapon.
   *
   * @param weaponName represents the name of the weapon which is used to attack
   * @return String representing whether the attack was successful or not.
   * @throws IllegalArgumentException when the weapon name is invalid.
   */
  String attackTargetPlayer(String weaponName);

  /**
   * Takes a random turn for the computer player.
   * @return String representing outcome of operation done by the computer player.
   */
  String takeTurnForComputerPlayer();

  /**
   * Get the winner of the game.
   * @return Get the name of the player who is the winner.
   */
  String getWinner();

  /**
   * Checks if the current player is a computer player.
   *
   * @return if the current player is a computer player
   */
  boolean isCurrentPlayerComputer();

}