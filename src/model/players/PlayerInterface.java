package model.players;

import java.util.List;
import model.room.RoomInterface;
import model.weapon.WeaponInterface;

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
   * Returns if player is computer player.
   *
   * @return if player is computer player
   */
  boolean isComputerPlayer();

  /**
   * Gets the current location of the player.
   *
   * @return name of the current location of player
   */
  String getPlayerRoomName();

  /**
   * Updates the current location of the player.
   *
   * @param room represents the new location/room of the player
   */
  void setPlayerRoom(RoomInterface room);

  /**
   * Adds a new weapon to the player.
   *
   * @param weapon represents the weapon object to be added
   */
  void addWeaponToPlayer(WeaponInterface weapon);

  /**
   * Removes the weapon from the player.
   *
   * @param weaponName represents the name of the weapon to be removed
   * @return weapon object that is removed
   */
  WeaponInterface removeWeaponFromPlayer(String weaponName);

  List<String> getPlayerWeapons(boolean includePoke, boolean includeDamageValue);

  /**
   * Gives the weapon that has maximum damage value.
   *
   * @return the name of the weapon with maximum damage value
   */
  String getPlayerMaxDamageWeapon();
}
