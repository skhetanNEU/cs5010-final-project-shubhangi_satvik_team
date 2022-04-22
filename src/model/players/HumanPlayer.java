package model.players;

import java.util.List;
import model.room.RoomInterface;
import model.weapon.WeaponInterface;

public class HumanPlayer extends AbstractPlayer {

  public HumanPlayer(String playerName, int weaponLimit, RoomInterface currentRoom) {
    super(playerName, weaponLimit, currentRoom);
  }

  @Override
  public List<String> getPlayerWeapons(boolean includeDamageValue) {
    List<String> weapons = getDefaultPlayerWeapons(includeDamageValue);
    weapons.add(includeDamageValue ? weaponPoke.toString() : weaponPoke.getWeaponName());
    return weapons;
  }

  @Override
  public String setPlayerRoom(String roomName) {
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
  public String pickWeapon(String weaponName) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name cannot be null/empty.");
    }
    WeaponInterface weapon = currentRoom.getWeaponByWeaponName(weaponName);
    addWeaponToPlayer(weapon);
    currentRoom.removeWeaponFromRoom(weapon);
    return weaponName;
  }

  @Override
  public int attackTarget(String weaponName) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name cannot be null/empty.");
    }
    return attemptToAttackTarget(weaponName);
  }
}
