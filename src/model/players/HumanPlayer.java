package model.players;

import java.util.List;
import model.room.RoomInterface;
import model.weapon.WeaponInterface;

/**
 * Class for a human player in the game.
 */

public class HumanPlayer extends AbstractPlayer {

  /**
   * Constructor for setting up a human player.
   * @param playerName Name of the player.
   * @param weaponLimit Maximum number of weapons that can be carried by the player. It is -1 if
   *                    there is no limit.
   * @param currentRoom Room where the player should be added.
   * @throws IllegalArgumentException when the player name or weapon limit is invalid or the
   *                                   current room is null.
   */
  public HumanPlayer(String playerName, int weaponLimit, RoomInterface currentRoom)
          throws IllegalArgumentException {
    super(playerName, weaponLimit, currentRoom);
  }

  @Override
  public List<String> getPlayerWeapons(boolean includeDamageValue) {
    List<String> weapons = getDefaultPlayerWeapons(includeDamageValue);
    weapons.add(includeDamageValue ? weaponPoke.toString() : weaponPoke.getWeaponName());
    return weapons;
  }

  @Override
  public String setPlayerRoom(String roomName) throws IllegalArgumentException {
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException("Room name cannot be null/empty.");
    }
    RoomInterface newRoom = currentRoom.getNeighboringRoom(roomName);
    currentRoom.removePlayerFromRoom(this);
    newRoom.addPlayerToRoom(this);
    currentRoom = newRoom;
    return roomName;
  }

  @Override
  public String pickWeapon(String weaponName) throws IllegalArgumentException {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name cannot be null/empty.");
    }
    WeaponInterface weapon = currentRoom.getWeaponByWeaponName(weaponName);
    addWeaponToPlayer(weapon);
    currentRoom.removeWeaponFromRoom(weapon);
    return weaponName;
  }

  @Override
  public int attackTarget(String weaponName) throws IllegalArgumentException {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name cannot be null/empty.");
    }
    return attemptToAttackTarget(weaponName);
  }
}
