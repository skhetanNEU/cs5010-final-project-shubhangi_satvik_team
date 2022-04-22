//package model.players;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import model.room.RoomInterface;
//import model.weapon.WeaponImpl;
//import model.weapon.WeaponInterface;
//
///**
// * PlayerImpl represents the normal player in the world of Kill Doctor Lucky.
// * It consists of the player id, name, player type, limit on weapon,
// * list of weapons and current location data.
// */
//public class PlayerImpl implements PlayerInterface {
//
//  private static final WeaponInterface weaponPoke = new WeaponImpl(99999, "Poke", 1);
//
//  private final String playerName;
//  private final int weaponLimit;
//  private final PlayerType playerType;
//  private RoomInterface currentRoom;
//  private final List<WeaponInterface> playerWeapons;
//
//
//  /**
//   * Constructs a Player object that represents the normal player.
//   *
//   * @param playerName  represents the name of the player
//   * @param weaponLimit represents the max limit on number of weapons a player can pick
//   * @param isComputer  represents if the player is a computer player or not
//   * @param currentRoom represents the current room in which the player is
//   * @throws IllegalArgumentException if parameters are not valid
//   */
//  public PlayerImpl(String playerName, int weaponLimit,
//                    boolean isComputer, RoomInterface currentRoom) {
//    if (playerName == null || "".equals(playerName)) {
//      throw new IllegalArgumentException("Player name cannot be null/empty.");
//    } else if (weaponLimit < 0 && weaponLimit != -1) {
//      throw new IllegalArgumentException("Player weapon limit cannot be negative.");
//    } else if (currentRoom == null) {
//      throw new IllegalArgumentException("Player current room cannot be null.");
//    }
//    this.playerName = playerName;
//    this.weaponLimit = weaponLimit;
//    this.playerType = isComputer ? PlayerType.COMPUTER : PlayerType.HUMAN;
//    this.currentRoom = currentRoom;
//    this.playerWeapons = new ArrayList<>();
//  }
//
//  @Override
//  public String getPlayerName() {
//    return playerName;
//  }
//
//  @Override
//  public boolean isComputerPlayer() {
//    return playerType == PlayerType.COMPUTER;
//  }
//
//  @Override
//  public String getPlayerRoomName() {
//    return currentRoom.getRoomName();
//  }
//
//  @Override
//  public void setPlayerRoom(RoomInterface room) {
//    if (room == null) {
//      throw new IllegalArgumentException("Room cannot be null.");
//    }
//    currentRoom.checkIfRoomNeighbour(room.getRoomName(), false);
//    currentRoom.removePlayerFromRoom(this);
//    room.addPlayerToRoom(this);
//    currentRoom = room;
//  }
//
//  @Override
//  public void addWeaponToPlayer(WeaponInterface weapon) {
//    if (weapon == null) {
//      throw new IllegalArgumentException("Weapon cannot be null.");
//    } else if (weaponLimit != -1 && playerWeapons != null && playerWeapons.size() == weaponLimit) {
//      throw new IllegalArgumentException("Max weapon limit reached.");
//    } else if (playerWeapons != null && !playerWeapons.contains(weapon)) {
//      playerWeapons.add(weapon);
//    }
//  }
//
//  @Override
//  public WeaponInterface removeWeaponFromPlayer(String weaponName) {
//    if (weaponName == null || "".equals(weaponName)) {
//      throw new IllegalArgumentException("Weapon name cannot be null/empty.");
//    } else if (
//            ((isComputerPlayer() && (playerWeapons == null || playerWeapons.size() == 0))
//                    || (!isComputerPlayer())
//            ) && weaponPoke.getWeaponName().equalsIgnoreCase(weaponName)) {
//      return new WeaponImpl(
//              weaponPoke.getWeaponId(),
//              weaponPoke.getWeaponName(),
//              weaponPoke.getWeaponValue());
//    } else if (playerWeapons != null && playerWeapons.size() != 0) {
//      for (WeaponInterface w : playerWeapons) {
//        if (weaponName.equalsIgnoreCase(w.getWeaponName())) {
//          playerWeapons.remove(w);
//          return new WeaponImpl(w.getWeaponId(), w.getWeaponName(), w.getWeaponValue());
//        }
//      }
//    }
//    throw new IllegalArgumentException("Player does not have the weapon.");
//  }
//
//  @Override
//  public List<String> getPlayerWeapons(boolean includePoke, boolean includeDamageValue) {
//    List<String> weapons = new ArrayList<>();
//    if (!includePoke && (playerWeapons == null || playerWeapons.size() == 0)) {
//      return weapons;
//    }
//    Collections.sort(playerWeapons);
//    for (WeaponInterface w : playerWeapons) {
//      weapons.add(includeDamageValue ? w.toString() : w.getWeaponName());
//    }
//    if (includePoke) {
//      if ((isComputerPlayer() && weapons.size() == 0) || !isComputerPlayer()) {
//        weapons.add(includeDamageValue ? weaponPoke.toString() : weaponPoke.getWeaponName());
//      }
//    }
//    return weapons;
//  }
//
//  @Override
//  public String getPlayerMaxDamageWeapon() {
//    if (playerWeapons == null || playerWeapons.size() == 0) {
//      return weaponPoke.getWeaponName();
//    }
//    Collections.sort(playerWeapons);
//    int maxDamageValue = 0;
//    String maxDamageWeapon = "";
//    for (WeaponInterface w : playerWeapons) {
//      if (w.getWeaponValue() > maxDamageValue) {
//        maxDamageValue = w.getWeaponValue();
//        maxDamageWeapon = w.getWeaponName();
//      }
//    }
//    return maxDamageWeapon;
//  }
//
//  @Override
//  public String toString() {
//    return String.format("Name: %s\nPlayer Type: %s\nCurrent Room: %s\nWeapons: %s",
//            playerName,
//            playerType,
//            getPlayerRoomName(),
//            playerWeapons.size() > 0 ? String.join(", ", getPlayerWeapons(false, true)) : "-"
//    );
//  }
//}
