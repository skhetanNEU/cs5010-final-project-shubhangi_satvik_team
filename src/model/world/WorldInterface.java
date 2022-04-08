package model.world;

import java.util.List;

/**
 * An interface class for representing the World for Kill Doctor Lucky.
 *
 */
public interface WorldInterface {
  /**
   * Gives the list of all room names in the world.
   *
   * @return list of all room names
   */
  List<String> getListOfAllRooms();

  /**
   * Returns the name of the world.
   *
   * @return the name of the world
   */
  String getWorldName();

  /**
   * Checks if there is alteast one player to take a turn and play the game.
   */
  void checkIfPlayersExistToPlayGame();

  /**
   * Returns the name of the current player.
   *
   * @return the name of the current player
   */
  String getCurrentPlayerName();

  /**
   * Checks if the current player is a computer player.
   *
   * @return if the current player is a computer player
   */
  boolean isCurrentPlayerComputer();

  /**
   * Gives all weapons that player has.
   *
   * @return list of weapons with the player.
   */
  String getCurrentPlayerWeapons();

  /**
   * Gives the weapon that the player holds which has maximum damage value.
   *
   * @return name of the weapon with maximum damage value
   */
  String getCurrentPlayerWeaponWithMostDamage();

  /**
   * Gives the room that the current player is in.
   *
   * @return the name of the current player's room.
   */
  String getCurrentPlayerRoomName();

  /**
   * Returns the neighbours of the current player's room.
   *
   * @return list of neighbours that share a wall with the current player's room
   */
  String getCurrentPlayerRoomNeighbours();

  /**
   * Returns the weapons with damage value available in current player's room.
   *
   * @return list of weapons with damage value available in current player's room
   */
  String getCurrentPlayerRoomWeapons(boolean includeDamageValues);

  /**
   * Checks if the player can be seen from current room & neighbouring rooms.
   *
   * @return true if the player cannot be seen
   */
  boolean isCurrentPlayerSeenByPlayersInVisibleRooms();

  /**
   * Checks if target player is in the same room as current player.
   *
   * @return true if target is in the room
   */
  boolean isTargetInSameRoom();

  /**
   * Saves the graphical representation of the world.
   */
  void displayWorld();

  /**
   * Retrieves the details of the room which includes the room id, name,
   * list of available weapons and the neighbours of the room.
   *
   * @param roomName represents the name of the room whose details are needed
   * @return details of the room
   */
  String getRoomInformation(String roomName);

  /**
   * Retrieves the details of the player which includes the name, list of available weapons
   * and the current location of the player.
   *
   * @param playerName represents the name of the player whose details are needed
   * @return details of the player
   */
  String getPlayerInformation(String playerName);

  /**
   * Adds a player to the world.
   *
   * @param playerName        represents the name of the player
   * @param hasLimitOnWeapons represents if the player has a limit on number of weapons
   * @param weaponLimit       represents the limit on weapons
   * @param isComputerPlayer  represents if the player is computer controlled
   * @param startingRoomName  represents the name of the starting room
   */
  void addPlayerToGame(String playerName, boolean hasLimitOnWeapons, int weaponLimit,
                       boolean isComputerPlayer, String startingRoomName);

  /**
   * Retrieves the details of the current room of the current player along with the details
   * of the neighboring rooms.
   *
   * @return the details of the current room and its neighbours
   */
  String lookAroundSpace();

  /**
   * Moves the player to the room.
   *
   * @param roomName represents the name of the room to which the player has to be moved.
   */
  void movePlayer(String roomName);

  /**
   * Picks the weapon for the current player.
   *
   * @param weaponName represents the name of the weapon that the player has to pick.
   */
  void pickWeapon(String weaponName);

  /**
   * Moves the pet to the room.
   *
   * @param roomName represents the name of the room to which the pet has to be moved.
   */
  void movePet(String roomName);

  /**
   * Attacks the target player with a weapon.
   *
   * @param weaponName represents the name of the weapon which is used to attack
   * @return true if the attack was successful
   */
  boolean attackTargetPlayer(String weaponName);

  /**
   * Checks if the game is over.
   *
   * @return true if game is over
   */
  boolean isGameOver();

  /**
   * Gives the name of the winner of the game.
   *
   * @return the name of the winner
   */
  String getWinnerName();

  /**
   * Gets the details of the target player.
   *
   * @return details of the target player including health and current room
   */
  String getTargetPlayerDetails();
}