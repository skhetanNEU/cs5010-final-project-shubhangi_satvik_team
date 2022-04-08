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
   * Gets the neighbours of the room.
   *
   * @param includeInvisibleRooms represents if neighbours should include rooms with pets
   * @return list of neighbours of the room
   */
  String getRoomNeighbours(boolean includeInvisibleRooms);

  /**
   * Sets the neighbours of the room.
   *
   * @param neighbours represents the neighbour of the room
   */
  void setRoomNeighbours(List<RoomInterface> neighbours);

  /**
   * Checks if a room is a neighbour of the current room.
   *
   * @param roomName represents the name of the other room
   * @param includeInvisibleRooms represents if valid neighbours should include rooms with pets
   */
  void checkIfRoomNeighbour(String roomName, boolean includeInvisibleRooms);

  /**
   * Gets the available weapons of the room.
   *
   * @return list of available weapons with their names and damage value
   */
  String getAvailableWeapons(boolean includeDamageValues);

  /**
   * Returns the weapon object.
   *
   * @param weaponName represents the name of the weapon
   * @return the weapon object
   */
  WeaponInterface getWeaponByWeaponName(String weaponName);

  /**
   * Adds a weapon to the room.
   *
   * @param weaponId          represents the id of the weapon
   * @param weaponDamageValue represents the damage value of the weapon
   * @param weaponName        represents the name of the weapon
   */
  void addWeaponToRoom(int weaponId, String weaponName, int weaponDamageValue);

  /**
   * Removes a weapon from the room.
   *
   * @param weapon represents the name of the weapon to be removed
   */
  void removeWeaponFromRoom(WeaponInterface weapon);

  /**
   * Gets the number of players in the room.
   *
   * @return the number of players in room
   */
  int getNumberOfPlayersInRoom();

  /**
   * Adds a player to the room.
   *
   * @param player represents the player object to be added
   */
  void addPlayerToRoom(PlayerInterface player);

  /**
   * Removes a player from the room.
   *
   * @param player represents the player object to be removed
   */
  void removePlayerFromRoom(PlayerInterface player);

}