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
   * Checks if the current player is a computer player.
   *
   * @return if the current player is a computer player
   */
  boolean isCurrentPlayerComputer();

  /**
   * Returns the name of the current player.
   *
   * @return the name of the current player
   */
  String getCurrentPlayerName();

  /**
   * Gives all weapons that player has.
   *
   * @return list of weapons with the player.
   */
  String getCurrentPlayerWeapons();

  /**
   * Gives the room that the current player is in.
   *
   * @return the name of the current player's room.
   */
  String getCurrentPlayerRoomName();

  /**
   * Returns the weapons with damage value available in current player's room.
   *
   * @return list of weapons with damage value available in current player's room
   */
  String getCurrentPlayerRoomWeapons(boolean includeDamageValues);

  /**
   * Gets the details of the target player.
   *
   * @return details of the target player including health and current room
   */
  String getTargetPlayerDetails();

  /**
   * Retrieves the details of the player which includes the name, list of available weapons
   * and the current location of the player.
   *
   * @return details of the player
   */
  String getCurrentPlayerInformation();

  /**
   * Retrieves the details of the current player's room which includes the room id, name,
   * list of available weapons and the neighbours of the room.
   *
   * @return details of the room
   */
  String getCurrentPlayerRoomInformation();

  /**
   * Checks if the game is over.
   *
   * @return true if game is over
   */
  boolean isGameOver();

  BufferedImage getWorldView();

  boolean isPlayerIconClicked(int r, int c);

}
