package model.pet;

import model.room.RoomInterface;

/**
 * PetImpl represents the target pet in the world.
 * It consists of target pet name and current location.
 */
public class PetImpl implements PetInterface {

  private final String petName;
  private RoomInterface petRoom;

  /**
   * Construct a TargetPlayerImpl object that represents the target player.
   *
   * @param petName represents the name of the target pet
   * @param petRoom represents the current location of the target pet
   * @throws IllegalArgumentException if parameters are not valid
   */
  public PetImpl(String petName, RoomInterface petRoom)
          throws IllegalArgumentException {

    if (petName == null || ("").equals(petName)) {
      throw new IllegalArgumentException("Pet name cannot be null/empty.");
    }
    if (petRoom == null || petRoom.getRoomId() != 0) {
      throw new IllegalArgumentException("Pet cannot be in a room with id not 0.");
    }
    petRoom.updatePetPresence(true);
    this.petName = petName;
    this.petRoom = petRoom;
  }

  @Override
  public String getPetName() {
    return petName;
  }

  @Override
  public String getPetRoomName() {
    return this.petRoom.getRoomName();
  }

  @Override
  public void setPetRoom(RoomInterface newRoom) {
    if (newRoom == null) {
      throw new IllegalArgumentException("Room cannot be null.");
    }
    this.petRoom.updatePetPresence(false);
    newRoom.updatePetPresence(true);
    this.petRoom = newRoom;
  }

  @Override
  public String toString() {
    return String.format("Pet Name: %s\nPet Current Room: %s", petName, this.getPetRoomName());
  }

}
