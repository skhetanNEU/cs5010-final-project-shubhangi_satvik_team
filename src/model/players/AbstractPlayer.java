package model.players;

import model.room.RoomInterface;
import model.weapon.WeaponImpl;
import model.weapon.WeaponInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractPlayer implements PlayerInterface{

    protected static final WeaponInterface weaponPoke = new WeaponImpl(99999, "Poke", 1);

    protected final String playerName;
    protected final int weaponLimit;
    protected RoomInterface currentRoom;
    protected final List<WeaponInterface> playerWeapons;

    public AbstractPlayer(String playerName, int weaponLimit, RoomInterface currentRoom){
        if (playerName == null || "".equals(playerName)) {
            throw new IllegalArgumentException("Player name cannot be null/empty.");
        } else if (weaponLimit < 0 && weaponLimit != -1) {
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
    public void addWeaponToPlayer(WeaponInterface weapon) {
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
     */
    protected WeaponInterface removeWeaponFromPlayer(String weaponName) {
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

    protected List<String> getDefaultPlayerWeapons(boolean includeDamageValue) {
        List<String> weapons = new ArrayList<>();

        Collections.sort(playerWeapons);
        for (WeaponInterface w : playerWeapons) {
            weapons.add(includeDamageValue ? w.toString() : w.getWeaponName());
        }
        return weapons;
    }

      @Override
      public String toString() {
        return String.format("Name: %s\nCurrent Room: %s\nWeapons: %s",
                playerName,
                getPlayerRoomName(),
                playerWeapons.size() > 0 ? String.join(", ", getPlayerWeapons(true)) : "-"
        );
      }
}
