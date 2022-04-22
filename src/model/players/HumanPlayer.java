package model.players;

import model.room.RoomInterface;
import model.weapon.WeaponInterface;

import java.util.List;

public class HumanPlayer extends AbstractPlayer{
    public HumanPlayer(String playerName, int weaponLimit, RoomInterface currentRoom) {
        super(playerName, weaponLimit, currentRoom);
    }

    @Override
    public List<String> getPlayerWeapons(boolean includeDamageValue){
        List<String> weapons = getDefaultPlayerWeapons(includeDamageValue);
        weapons.add(includeDamageValue ? weaponPoke.toString() : weaponPoke.getWeaponName());
        return weapons;
    }

    @Override
    public String setPlayerRoom(String roomName) {
        if (roomName == null || "".equals(roomName)) {
            throw new IllegalArgumentException("Room name is invalid");
        }

        RoomInterface newRoom = currentRoom.getNeighboringRoom(roomName);
        if(newRoom == null){
            throw new IllegalArgumentException("Invalid neighbor selected");
        }
        currentRoom.removePlayerFromRoom(this);
        newRoom.addPlayerToRoom(this);
        currentRoom = newRoom;
        return roomName;
    }

    @Override
    public String pickWeapon(String weaponName){
        if(weaponName == null || "".equals(weaponName)){
            throw new IllegalArgumentException("Invalid weapon name");
        }
        WeaponInterface weapon = currentRoom.getWeaponByWeaponName(weaponName);
        addWeaponToPlayer(weapon);
        currentRoom.removeWeaponFromRoom(weapon);
        return weaponName;
    }

    @Override
    public int attackTarget(String weaponName){
        if(weaponName == null || "".equals(weaponName)){
            throw new IllegalArgumentException("Weapon is invalid");
        }

        boolean isAttackSuccessful = true;
        int damageOnTarget = -1;

        // Target not in same room || Room has more players
        if (!currentRoom.isTargetPlayerInRoom()
                || currentRoom.getNumberOfPlayersInRoom() - 1 > 0) {
            isAttackSuccessful = false;
        } else {
            // Neighbouring rooms have players
            List<String> neighbours = currentRoom.getRoomNeighbours(true);
            if (neighbours.size() > 0) {
                int numPlayersInNeighbours = 0;
                for (String n : neighbours) {
                    RoomInterface neighboringRoom = currentRoom.getNeighboringRoom(n);
                    if(neighboringRoom!=null){
                        numPlayersInNeighbours += neighboringRoom.getNumberOfPlayersInRoom();
                    }
                }
                if (numPlayersInNeighbours > 0 && !currentRoom.isPetInRoom()) {
                    isAttackSuccessful = false;
                }
            }
        }

        WeaponInterface weapon = removeWeaponFromPlayer(weaponName);
        if(isAttackSuccessful){
            damageOnTarget = weapon.getWeaponValue();
        }
        return damageOnTarget;
    }
}
