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
   * Gets the current location of the player.
   *
   * @return name of the current location of player
   */
  String getPlayerRoomName();

  /**
   * Updates the current location of the player.
   *
   * @param roomName represents the new location/room of the player
   */
  String setPlayerRoom(String roomName);

  /**
   * Adds a new weapon to the player.
   *
   * @param weapon represents the weapon object to be added
   */
  void addWeaponToPlayer(WeaponInterface weapon);

  List<String> getPlayerWeapons(boolean includeDamageValue);

  String pickWeapon(String weaponName);

  int attackTarget(String weaponName);
}
