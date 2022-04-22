package model.players;

import java.util.Collections;
import java.util.List;
import model.random.RandomClass;
import model.room.RoomInterface;
import model.weapon.WeaponInterface;

public class ComputerPlayer extends AbstractPlayer {

  public ComputerPlayer(String playerName, int weaponLimit, RoomInterface currentRoom) {
    super(playerName, weaponLimit, currentRoom);
  }

  /**
   * Gives the weapon that has maximum damage value.
   *
   * @return the name of the weapon with maximum damage value
   */
  private String getPlayerMaxDamageWeapon() {
    if (playerWeapons == null || playerWeapons.size() == 0) {
      return weaponPoke.getWeaponName();
    }
    Collections.sort(playerWeapons);
    int maxDamageValue = 0;
    String maxDamageWeapon = "";
    for (WeaponInterface w : playerWeapons) {
      if (w.getWeaponValue() > maxDamageValue) {
        maxDamageValue = w.getWeaponValue();
        maxDamageWeapon = w.getWeaponName();
      }
    }
    return maxDamageWeapon;
  }

  @Override
  public List<String> getPlayerWeapons(boolean includeDamageValue) {
    List<String> weapons = getDefaultPlayerWeapons(includeDamageValue);
    if (weapons.size() == 0) {
      weapons.add(includeDamageValue ? weaponPoke.toString() : weaponPoke.getWeaponName());
    }
    return weapons;
  }

  @Override
  public String setPlayerRoom(String roomName) {
    // Room can be null since we will randomly decide which room to move to
    List<String> neighborsOfCurrentRoom = currentRoom.getRoomNeighbours(false);
    if (neighborsOfCurrentRoom.size() == 0) {
      throw new IllegalArgumentException("Room doesn't have any neighbors");
    }

    String randomlySelectedNeighbor = neighborsOfCurrentRoom
            .get(new RandomClass().getRandomWithinBound(neighborsOfCurrentRoom.size()));
    currentRoom.removePlayerFromRoom(this);
    RoomInterface newRoom = currentRoom.getNeighboringRoom(randomlySelectedNeighbor);
    newRoom.addPlayerToRoom(this);
    currentRoom = newRoom;

    return randomlySelectedNeighbor;
  }

  @Override
  public String pickWeapon(String weaponName) {
    // weaponName can be null since we will randomly decide which weapon to pick
    List<String> weaponsInRoom = currentRoom.getAvailableWeapons(false);
    if (weaponsInRoom.size() == 0) {
      throw new IllegalArgumentException("Room does not have any weapons.");
    }
    String randomlySelectedWeapon =
            weaponsInRoom.get(new RandomClass().getRandomWithinBound(weaponsInRoom.size()));
    WeaponInterface weapon = currentRoom.getWeaponByWeaponName(randomlySelectedWeapon);
    addWeaponToPlayer(weapon);
    currentRoom.removeWeaponFromRoom(weapon);
    return randomlySelectedWeapon;
  }

  @Override
  public int attackTarget(String weaponName) {
    // weaponName can be null since we will randomly decide which weapon to pick
    String weaponNameForAttack = getPlayerMaxDamageWeapon();
    return attemptToAttackTarget(weaponNameForAttack);
  }
}
