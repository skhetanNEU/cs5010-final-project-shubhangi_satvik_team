package model.world;

/**
 * An interface class for representing the World for Kill Doctor Lucky.
 *
 */
public interface WorldInterface extends ReadOnlyWorldInterface {

  /**
   * Adds a player to the world.
   *
   * @param playerName        represents the name of the player
   * @param weaponLimit       represents the limit on weapons
   * @param isComputerPlayer  represents if the player is computer controlled
   * @param startingRoomName  represents the name of the starting room
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
   * Moves the player to the room.
   *
   * @param roomName represents the name of the room to which the player has to be moved.
   * @return String representing that player has moved to the specified room.
   */
  String movePlayer(String roomName);

  String movePlayer(int x, int y);

  /**
   * Picks the weapon for the current player.
   *
   * @param weaponName represents the name of the weapon that the player has to pick.
   * @return String representing that player has picked the specified weapon.
   */
  String pickWeapon(String weaponName);

  /**
   * Moves the pet to the room.
   *
   * @param roomName represents the name of the room to which the pet has to be moved.
   * @return String representing that player has moved the pet.
   */
  String movePet(String roomName);

  /**
   * Attacks the target player with a weapon.
   *
   * @param weaponName represents the name of the weapon which is used to attack
   * @return String representing whether the attack was successful or not.
   */
  String attackTargetPlayer(String weaponName);

  /**
   * Takes a random turn for the computer player.
   * @return String representing outcome of operation done by the computer player.
   */
  String takeTurnForComputerPlayer();

}