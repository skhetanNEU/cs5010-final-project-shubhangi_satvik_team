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
   * Takes a random turn for the computer player.
   *
   */
  void takeTurnForComputerPlayer();

}