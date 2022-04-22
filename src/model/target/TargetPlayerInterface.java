package model.target;

import model.room.RoomInterface;

/**
 * An interface class for representing the target player in the world for Kill Doctor Lucky.
 *
 */
public interface TargetPlayerInterface {

  /**
   * Gets the health of the target player.
   *
   * @return the health value of the target player
   */
  int getTargetPlayerHealth();

  /**
   * Reduces the health of the target player.
   *
   * @param damageDone represents how much health must be reduced
   */
  void reduceTargetPlayerHealth(int damageDone);

  /**
   * Gets the current location/room of the target player.
   *
   * @return copy of current room object
   */
  RoomInterface getTargetPlayerRoom();

  /**
   * Updates the current location of the target player to the next indexed room.
   *
   * @param room represents the new location/room of the target player
   */
  void setTargetPlayerRoom(RoomInterface room);
}
