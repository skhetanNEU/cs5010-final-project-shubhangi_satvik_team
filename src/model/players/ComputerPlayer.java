package model.players;

import java.util.Collections;
import java.util.List;
import model.random.RandomClass;
import model.random.RandomGenerator;
import model.room.RoomInterface;
import model.weapon.WeaponInterface;

/**
 * Class for a computer player in the game.
 */

public class ComputerPlayer extends AbstractPlayer {

  private RandomGenerator random;

  /**
   * Constructor for setting up a computer player.
   * @param playerName Name of the player.
   * @param weaponLimit Maximum number of weapons that can be carried by the player. It is -1 if
   *                    there is no limit.
   * @param currentRoom Room where the player should be added.
   * @param random Random generator option to randomly generate options for computer player.
   * @throws IllegalArgumentException when the player name or weapon limit is invalid or the
   * current room or the random is null.
   */
  public ComputerPlayer(String playerName, int weaponLimit, RoomInterface currentRoom,
                        RandomGenerator random) throws IllegalArgumentException {
    super(playerName, weaponLimit, currentRoom);
    if (random == null) {
      throw new IllegalArgumentException("Random Generator is null");
    }
    this.random = random;
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
            .get(random.getRandomWithinBound(neighborsOfCurrentRoom.size()));
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
            weaponsInRoom.get(random.getRandomWithinBound(weaponsInRoom.size()));
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
