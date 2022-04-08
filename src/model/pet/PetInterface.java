package model.pet;

import model.room.RoomInterface;

/**
 * An interface class for representing the target pet in the world for Kill Doctor Lucky.
 */
public interface PetInterface {

  /**
   * Gets the name of the target pet.
   *
   * @return the name of the target pet
   */
  String getPetName();

  /**
   * Gets the name of the current location/room of the target pet.
   *
   * @return name of current room
   */
  String getPetRoomName();

  /**
   * Updates the current location of the target pet.
   *
   * @param room represents the new location/room of the target pet
   */
  void setPetRoom(RoomInterface room);
}
