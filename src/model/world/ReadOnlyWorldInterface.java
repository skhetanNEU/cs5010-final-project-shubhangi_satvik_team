package model.world;

import java.awt.image.BufferedImage;

/**
 * An interface class for representing the World for Kill Doctor Lucky.
 *
 */
public interface ReadOnlyWorldInterface {

  /**
   * Returns the name of the world.
   *
   * @return the name of the world
   */
  String getWorldName();

  /**
   * Returns the name of the current player.
   *
   * @return the name of the current player
   */
  String getCurrentPlayerName();

  /**
   * Gets the details of the target player.
   *
   * @return details of the target player including health and current room
   */
  String getTargetPlayerDetails();

  /**
   * Retrieves the details of the current player's room which includes the room id, name,
   * list of available weapons and the neighbours of the room.
   *
   * @return details of the room
   */
  String getCurrentPlayerRoomInformation();

  /**
   * Get the view of the world map.
   * @return Buffered image of the map of the world.
   */
  BufferedImage getWorldView();

  /**
   * Checks if the game is over.
   *
   * @return true if game is over
   */
  boolean isGameOver();

}
