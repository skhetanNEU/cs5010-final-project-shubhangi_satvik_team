package model.players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.room.RoomInterface;
import model.weapon.WeaponImpl;
import model.weapon.WeaponInterface;

/**
 * Abstract class for a player that implements the PlayerInterface.
 */
public abstract class AbstractPlayer implements PlayerInterface {

  protected static final WeaponInterface weaponPoke = new WeaponImpl(99999, "Poke", 1);
  protected final String playerName;
  protected final int weaponLimit;
  protected RoomInterface currentRoom;
  protected final List<WeaponInterface> playerWeapons;

  /**
   * Constructor for player abstract class.
   *
   * @param playerName  Name of the player.
   * @param weaponLimit Maximum number of weapons.
   * @param currentRoom Room object where the player should be added.
   * @throws IllegalArgumentException when the player name or weapon limit is invalid or the
   *                                  current room is null.
   */
  public AbstractPlayer(String playerName, int weaponLimit, RoomInterface currentRoom)
          throws IllegalArgumentException {
    if (playerName == null || "".equals(playerName)) {
      throw new IllegalArgumentException("Player name cannot be null/empty.");
    } else if (weaponLimit <= 0 && weaponLimit != -1) {
      throw new IllegalArgumentException("Player weapon limit cannot be negative.");
    } else if (currentRoom == null) {
      throw new IllegalArgumentException("Player current room cannot be null.");
    }
    this.playerName = playerName;
    this.weaponLimit = weaponLimit;
    this.currentRoom = currentRoom;
    this.playerWeapons = new ArrayList<>();
  }

  @Override
  public String getPlayerName() {
    return playerName;
  }

  @Override
  public String getPlayerRoomName() {
    return currentRoom.getRoomName();
  }

  @Override
  public String toString() {
    return String.format("Name: %s\nCurrent Room: %s\nWeapons: %s",
            playerName,
            getPlayerRoomName(),
            playerWeapons.size() > 0 ? String.join(", ", getPlayerWeapons(true)) : "-"
    );
  }

  /**
   * Get the weapons that the player is carrying.
   *
   * @param includeDamageValue Include the damage values along with weapon names or not.
   * @return List of string representing the weapons that the player is carrying.
   */
  protected List<String> getDefaultPlayerWeapons(boolean includeDamageValue) {
    List<String> weapons = new ArrayList<>();

    Collections.sort(playerWeapons);
    for (WeaponInterface w : playerWeapons) {
      weapons.add(includeDamageValue ? w.toString() : w.getWeaponName());
    }
    return weapons;
  }

  /**
   * Making an attempt on the target.
   *
   * @param weaponName Name of the weapon that should be used for the attack.
   * @return Integer representing the damage of the attack. It is -1, if the attack was
   *          unsuccessful.
   * @throws IllegalArgumentException when the weapon name is invalid.
   */
  protected int attemptToAttackTarget(String weaponName) throws IllegalArgumentException {

    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name cannot be null/empty.");
    }

    boolean isAttackSuccessful = true;
    int damageOnTarget = -1;

    // Target not in same room || Room has more players || Neighbouring rooms have players
    if (!currentRoom.isTargetPlayerInRoom()
            || currentRoom.getNumberOfPlayersInRoom() - 1 > 0
            || (currentRoom.getNumberOfPlayersInNeighbouringRoom(true) > 0
            && !currentRoom.isPetInRoom())
    ) {
      isAttackSuccessful = false;
    }

    WeaponInterface weapon = removeWeaponFromPlayer(weaponName);
    if (isAttackSuccessful) {
      damageOnTarget = weapon.getWeaponValue();
    }

    return damageOnTarget;
  }

  /**
   * Adds a new weapon to the player.
   *
   * @param weapon represents the weapon object to be added
   * @throws IllegalArgumentException when the weapon is null or weapon cannot be added to the
   *                                  player due to its limit.
   */
  protected void addWeaponToPlayer(WeaponInterface weapon) throws IllegalArgumentException {
    if (weapon == null) {
      throw new IllegalArgumentException("Weapon cannot be null.");
    } else if (weaponLimit != -1 && playerWeapons != null && playerWeapons.size() == weaponLimit) {
      throw new IllegalArgumentException("Max weapon limit reached.");
    } else if (playerWeapons != null && !playerWeapons.contains(weapon)) {
      playerWeapons.add(weapon);
    }
  }

  /**
   * Removes the weapon from the player.
   *
   * @param weaponName represents the name of the weapon to be removed
   * @return weapon object that is removed
   * @throws IllegalArgumentException when the weapon name is invalid.
   */
  protected WeaponInterface removeWeaponFromPlayer(String weaponName)
          throws IllegalArgumentException {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name cannot be null/empty.");
    } else if (weaponPoke.getWeaponName().equalsIgnoreCase(weaponName)) {
      return new WeaponImpl(
              weaponPoke.getWeaponId(),
              weaponPoke.getWeaponName(),
              weaponPoke.getWeaponValue());
    } else if (playerWeapons != null && playerWeapons.size() != 0) {
      for (WeaponInterface w : playerWeapons) {
        if (weaponName.equalsIgnoreCase(w.getWeaponName())) {
          playerWeapons.remove(w);
          return new WeaponImpl(w.getWeaponId(), w.getWeaponName(), w.getWeaponValue());
        }
      }
    }
    throw new IllegalArgumentException("Player does not have the weapon.");
  }

}
