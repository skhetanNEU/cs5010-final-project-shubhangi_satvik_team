package model.target;

import model.room.RoomImpl;
import model.room.RoomInterface;

/**
 * TargetPlayerImpl represents the target player in the world of Kill Doctor Lucky.
 * It consists of target player name, health and current location data.
 *
 */
public class TargetPlayerImpl implements TargetPlayerInterface {

  private final String playerName;
  private int playerHealth;
  private RoomInterface playerLocation;

  /**
   * Construct a TargetPlayerImpl object that represents the target player.
   *
   * @param playerName     represents the name of the target player
   * @param playerHealth   represents the health of the target player
   * @param playerLocation represents the current location of the target player
   * @throws IllegalArgumentException if parameters are not valid
   */
  public TargetPlayerImpl(String playerName, int playerHealth, RoomInterface playerLocation)
          throws IllegalArgumentException {
    if (playerName == null || ("").equals(playerName)) {
      throw new IllegalArgumentException("Target player name cannot be null/empty.");
    }
    if (playerHealth <= 0) {
      throw new IllegalArgumentException("Target player cannot have non positive health.");
    }
    if (playerLocation == null || playerLocation.getRoomId() != 0) {
      throw new IllegalArgumentException("Target player cannot be in a room with id not 0.");
    }
    playerLocation.updateTargetPlayerPresence(true);
    this.playerName = playerName;
    this.playerHealth = playerHealth;
    this.playerLocation = playerLocation;
  }

  @Override
  public int getTargetPlayerHealth() {
    return this.playerHealth;
  }

  @Override
  public void reduceTargetPlayerHealth(int damageDone) {
    if (damageDone < 0) {
      throw new IllegalArgumentException("Target player health damage cannot be non-positive.");
    }
    this.playerHealth -= (Math.min(damageDone, this.playerHealth));
  }

  @Override
  public RoomInterface getTargetPlayerRoom() {
    return new RoomImpl(
            playerLocation.getRoomId(),
            playerLocation.getRoomName(),
            playerLocation.getRoomCoordinates());
  }

  @Override
  public void setTargetPlayerRoom(RoomInterface newRoom) {
    if (newRoom == null) {
      throw new IllegalArgumentException(
              "Unable to update location of target player. Room cannot be null.");
    }
    this.playerLocation.updateTargetPlayerPresence(false);
    newRoom.updateTargetPlayerPresence(true);
    this.playerLocation = newRoom;
  }

  @Override
  public String toString() {
    return String.format("Name: %s\nHealth: %s\nCurrent Room: %s",
            playerName,
            playerHealth,
            playerLocation.getRoomName());
  }
}
