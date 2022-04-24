package model.players;

import java.util.List;

/**
 * An interface class for representing normal players in the world for Kill Doctor Lucky.
 *
 */
public interface PlayerInterface {

  /**
   * Gets the name of the player.
   *
   * @return the name of the player
   */
  String getPlayerName();

  /**
   * Gets the current location of the player.
   *
   * @return name of the current location of player
   */
  String getPlayerRoomName();

  /**
   * Get the weapons that the player is carrying.
   * @param includeDamageValue Boolean representing whether we should include the damage values
   *                           or not.
   * @return List of weapons that the player is carrying.
   */
  List<String> getPlayerWeapons(boolean includeDamageValue);

  /**
   * Updates the current location of the player.
   *
   * @param roomName represents the new location/room of the player
   * @throws IllegalArgumentException when the room name is invalid and player is not computer.
   */
  String setPlayerRoom(String roomName);

  /**
   * Pick up the specified weapon.
   * @param weaponName Name of the weapon that the player wants to pick.
   * @return String representing whether the pick up was successful or not.
   * @throws IllegalArgumentException when the weapon name is invalid and player is not computer.
   */
  String pickWeapon(String weaponName);

  /**
   * Attack the target with the specified weapon.
   * @param weaponName Name of the weapon that should be used for the attack.
   * @return A number representing the damage of the attack. It is -1 if the attack was not
   *      successful.
   * @throws IllegalArgumentException when the weapon name is invalid and player is not computer.
   */
  int attackTarget(String weaponName);
}
