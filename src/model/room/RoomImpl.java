package model.room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import model.players.PlayerInterface;
import model.weapon.WeaponImpl;
import model.weapon.WeaponInterface;

/**
 * RoomImpl represents a room of the world in Kill Doctor Lucky.
 * It consists of room id, room name, room coordinates, list of neighbours
 * and list of available weapons.
 */
public class RoomImpl implements RoomInterface {

  private final int roomId;
  private final String roomName;
  private final List<Integer> roomCoordinates;
  private List<RoomInterface> roomNeighbours;
  private final List<WeaponInterface> weaponsInSpace;
  private final List<PlayerInterface> playersInSpace;
  private boolean isTargetPlayerInRoom;
  private boolean isPetInRoom;

  /**
   * Construct a RoomImpl object that represents the world.
   *
   * @param roomId          represents the id of the room
   * @param roomName        represents the name of the room
   * @param roomCoordinates represents the coordinates of the room
   */
  public RoomImpl(int roomId, String roomName, List<Integer> roomCoordinates)
          throws IllegalArgumentException {
    if (roomId < 0) {
      throw new IllegalArgumentException("Room id cannot be negative");
    }
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException("Room name cannot be null/empty");
    }
    validateRoomCoordinates(roomCoordinates);
    this.roomId = roomId;
    this.roomName = roomName;
    this.roomCoordinates = new ArrayList<>();
    this.roomCoordinates.addAll(roomCoordinates);
    this.roomNeighbours = new ArrayList<>();
    this.weaponsInSpace = new ArrayList<>();
    this.playersInSpace = new ArrayList<>();
    this.isTargetPlayerInRoom = false;
    this.isPetInRoom = false;
  }

  /**
   * Helper method that validates if the room coordinates are in proper order and do not have
   * invalid values.
   *
   * @param roomCoordinates represents the list of coordinates
   * @throws IllegalArgumentException if the values of coordinates are invalid
   */
  private void validateRoomCoordinates(List<Integer> roomCoordinates)
          throws IllegalArgumentException {
    for (int c : roomCoordinates) {
      if (c < 0) {
        throw new IllegalArgumentException("Room coordinates cannot be negative.");
      }
    }
    if (roomCoordinates.get(2) <= roomCoordinates.get(0)
            || roomCoordinates.get(3) <= roomCoordinates.get(1)) {
      throw new IllegalArgumentException("Room coordinates are out of order.");
    }
  }

  /**
   * Gets the players currently present in the room.
   *
   * @return list of players in the room.
   */
  private String getPlayersInRoom() {
    List<String> players = new ArrayList<>();
    if (playersInSpace != null && playersInSpace.size() != 0) {
      for (PlayerInterface p : playersInSpace) {
        players.add(p.getPlayerName());
      }
    }
    return String.join(", ", players);
  }

  @Override
  public int getRoomId() {
    return this.roomId;
  }

  @Override
  public String getRoomName() {
    return this.roomName;
  }

  @Override
  public List<Integer> getRoomCoordinates() {
    return new ArrayList<>(this.roomCoordinates);
  }

  @Override
  public boolean isTargetPlayerInRoom() {
    return this.isTargetPlayerInRoom;
  }

  @Override
  public boolean isPetInRoom() {
    return this.isPetInRoom;
  }

  @Override
  public void updateTargetPlayerPresence(boolean isPresent) {
    this.isTargetPlayerInRoom = isPresent;
  }

  @Override
  public void updatePetPresence(boolean isPresent) {
    this.isPetInRoom = isPresent;
  }

  @Override
  public List<String> getRoomNeighbours(boolean includeInvisibleRooms) {
    List<String> neighbours = new ArrayList<>();
    if (roomNeighbours != null && roomNeighbours.size() != 0) {
      Collections.sort(roomNeighbours);
      for (RoomInterface nei : roomNeighbours) {
        if ((includeInvisibleRooms) || (!includeInvisibleRooms && !nei.isPetInRoom())) {
          neighbours.add(nei.getRoomName());
        }
      }
    }
    return neighbours;
  }

  @Override
  public void setRoomNeighbours(List<RoomInterface> neighbours) {
    if (neighbours != null && neighbours.size() > 0) {
      this.roomNeighbours = neighbours;
    }
  }

  @Override
  public void checkIfRoomNeighbour(String roomName, boolean includeInvisibleRooms) {
    boolean flag = false;
    for (RoomInterface r : roomNeighbours) {
      if (r.getRoomName().equalsIgnoreCase(roomName)) {
        if ((includeInvisibleRooms) || (!includeInvisibleRooms && !r.isPetInRoom())) {
          return;
        }
        break;
      }
    }
    if (!flag) {
      throw new IllegalArgumentException("Desired room is not a neighbour of current room.");
    }
  }

  @Override
  public List<String> getAvailableWeapons(boolean includeDamageValues) {
    List<String> weapons = new ArrayList<>();
    if (weaponsInSpace == null || weaponsInSpace.size() == 0) {
      return weapons;
    }
    Collections.sort(weaponsInSpace);
    for (WeaponInterface w : weaponsInSpace) {
      weapons.add(includeDamageValues ? w.toString() : w.getWeaponName());
    }
    return weapons;
  }

  @Override
  public WeaponInterface getWeaponByWeaponName(String weaponName) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name cannot be empty.");
    }
    WeaponInterface weapon = null;
    for (WeaponInterface w : weaponsInSpace) {
      if (w.getWeaponName().equalsIgnoreCase(weaponName)) {
        weapon = w;
        break;
      }
    }
    if (weapon == null) {
      throw new IllegalArgumentException("Weapon does not exist in room.");
    }

    return new WeaponImpl(weapon.getWeaponId(), weapon.getWeaponName(), weapon.getWeaponValue());
  }

  @Override
  public void addWeaponToRoom(int weaponId, String weaponName, int weaponDamageValue) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name cannot be empty.");
    } else if (weaponId < 0) {
      throw new IllegalArgumentException("Weapon id cannot be negative.");
    } else if (weaponDamageValue <= 0) {
      throw new IllegalArgumentException("Weapon damage value cannot be non-positive.");
    }
    WeaponInterface newWeapon = new WeaponImpl(weaponId, weaponName, weaponDamageValue);
    if (!weaponsInSpace.contains((newWeapon))) {
      weaponsInSpace.add(newWeapon);
    }
  }

  @Override
  public void removeWeaponFromRoom(WeaponInterface weapon) {
    if (weapon == null) {
      throw new IllegalArgumentException("Weapon cannot be null.");
    }
    if (!weaponsInSpace.contains(weapon)) {
      throw new IllegalArgumentException("Weapon does not exist in room.");
    } else {
      weaponsInSpace.remove(weapon);
    }
  }

  @Override
  public int getNumberOfPlayersInRoom() {
    if (this.playersInSpace == null || this.playersInSpace.size() == 0) {
      return 0;
    }
    return this.playersInSpace.size();
  }

  @Override
  public void addPlayerToRoom(PlayerInterface player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (!playersInSpace.contains((player))) {
      playersInSpace.add(player);
    }
  }

  @Override
  public void removePlayerFromRoom(PlayerInterface player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (playersInSpace.contains((player))) {
      playersInSpace.remove(player);
    } else {
      throw new IllegalArgumentException("Player does not exist in room.");
    }
  }

  @Override
  public RoomInterface getNeighboringRoom(String neighboringRoomName){
    if(neighboringRoomName == null || "".equals(neighboringRoomName)){
      throw new IllegalArgumentException("Invalid room name");
    }
    for(RoomInterface neighboringRoom : roomNeighbours){
      if(neighboringRoom.getRoomName().equals(neighboringRoomName)){
        return neighboringRoom;
      }
    }

    return null;
  }

  @Override
  public String toString() {
    return String.format("Name: %s\nNeighbours: %s\nWeapons: %s"
                    + "\nPlayers: %s\nIs Target Present: %s\nIs Pet Present: %s",
            roomName,
            getRoomNeighbours(false).size() > 0
                    ? String.join(", ", getRoomNeighbours(false))
                    : '-',
            weaponsInSpace.size() > 0
                    ? String.join(", ", getAvailableWeapons(true))
                    : '-',
            getPlayersInRoom(),
            isTargetPlayerInRoom ? "Yes" : "No",
            isPetInRoom ? "Yes" : "No"
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoomImpl room = (RoomImpl) o;
    return roomId == room.roomId
            && roomName.equalsIgnoreCase(room.roomName)
            && roomCoordinates.equals(room.roomCoordinates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomId, roomName, roomCoordinates);
  }

  @Override
  public int compareTo(RoomInterface o) {
    return Integer.compare(this.roomId, o.getRoomId());
  }
}
