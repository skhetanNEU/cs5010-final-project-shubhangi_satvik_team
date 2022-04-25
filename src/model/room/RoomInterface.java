package model.room;

import java.util.List;
import model.players.PlayerInterface;
import model.weapon.WeaponInterface;

/**
 * An interface class for representing the room in the world for Kill Doctor Lucky.
 *
 */
public interface RoomInterface extends Comparable<RoomInterface> {
  /**
   * Gets the id of the room.
   *
   * @return id of the room
   */
  int getRoomId();

  /**
   * Gets the name of the room.
   *
   * @return name of the room
   */
  String getRoomName();

  /**
   * Gets the coordinates of the room.
   *
   * @return coordinates of the room
   */
  List<Integer> getRoomCoordinates();

  /**
   * Checks if the target player is in the room.
   *
   * @return true if target player is in room
   */
  boolean isTargetPlayerInRoom();

  /**
   * Checks if the target pet is in the room.
   *
   * @return true if target pet is in room
   */
  boolean isPetInRoom();

  /**
   * Updates the presence of the target player in the room.
   *
   * @param isPresent represents if the target player is in room
   */
  void updateTargetPlayerPresence(boolean isPresent);

  /**
   * Updates the presence of the target pet in the room.
   *
   * @param isPresent represents if the target pet is in room
   */
  void updatePetPresence(boolean isPresent);

  /**
   * Get the neighbors of the room.
   * @param includeInvisibleRooms Boolean to figure out if the invisible rooms should be added or
   *                             not.
   * @return List of string representing room names which are the neighbors.
   */
  List<String> getRoomNeighbours(boolean includeInvisibleRooms);

  /**
   * Sets the neighbours of the room.
   *
   * @param neighbours represents the neighbour of the room
   */
  void setRoomNeighbours(List<RoomInterface> neighbours);

  /**
   * Get the available weapons in the room.
   * @param includeDamageValues Boolean representing whether we should include the damage values
   *                            or not along with the weapon name.
   * @return List of weapons available in the room.
   */
  List<String> getAvailableWeapons(boolean includeDamageValues);

  /**
   * Returns the weapon object.
   *
   * @param weaponName represents the name of the weapon
   * @return the weapon object
   * @throws IllegalArgumentException when the weapon name is invalid.
   */
  WeaponInterface getWeaponByWeaponName(String weaponName);

  /**
   * Adds a weapon to the room.
   *
   * @param weaponId          represents the id of the weapon
   * @param weaponDamageValue represents the damage value of the weapon
   * @param weaponName        represents the name of the weapon
   * @throws IllegalArgumentException when the weapon id or damage value is negative or weapon
   *                                  name is invalid.
   */
  void addWeaponToRoom(int weaponId, String weaponName, int weaponDamageValue);

  /**
   * Removes a weapon from the room.
   *
   * @param weapon represents the name of the weapon to be removed
   * @throws IllegalArgumentException when the weapon is null.
   */
  void removeWeaponFromRoom(WeaponInterface weapon);

  /**
   * Gets the number of players in the room.
   *
   * @return the number of players in room
   */
  int getNumberOfPlayersInRoom();

  /**
   * Gets the number of players in the room.
   *
   * @return the number of players in room
   */
  int getNumberOfPlayersInNeighbouringRoom(boolean includeHiddenRooms);

  /**
   * Adds a player to the room.
   *
   * @param player represents the player object to be added
   * @throws IllegalArgumentException when the player is null.
   */
  void addPlayerToRoom(PlayerInterface player);

  /**
   * Removes a player from the room.
   *
   * @param player represents the player object to be removed
   * @throws IllegalArgumentException when the player is null.
   */
  void removePlayerFromRoom(PlayerInterface player);

  /**
   * Get the neighboring room if the specified room is a neighbor.
   * @param neighboringRoomName Name of the room whose object is required.
   * @return Room object if the specified room is neighbor of the current room.
   * @throws IllegalArgumentException when the neighboring room name is invalid.
   */
  RoomInterface getNeighboringRoom(String neighboringRoomName);

}