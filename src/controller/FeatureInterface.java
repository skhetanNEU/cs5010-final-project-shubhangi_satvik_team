package controller;

import java.io.File;

/**
 * Interface for the features presented in the game that can be used by the players.
 */
public interface FeatureInterface {

  /**
   * Start playing the game.
   * @param file Configuration file for the game.
   */
  void playGame(File file);

  /**
   * Quit the game.
   */
  void quitGame();

  /**
   * Add players to the game.
   * @param playerName Name of the player.
   * @param roomName Name of the room.
   * @param maxNumberOfWeapons Maximum number of weapons that can be carried by the player.
   * @param isComputerPlayer Boolean specifying whether the player is computer or human.
   * @return String representing whether the add was successful or not.
   */
  String addPlayer(String playerName, String roomName, String maxNumberOfWeapons,
                   boolean isComputerPlayer);

  /**
   * Look around for the player.
   */
  void lookAround();

  /**
   * Handle room click for moving a player.
   * @param row Row coordinate of the click.
   * @param col Column coordinate of the click.
   */
  void handleRoomClick(int row, int col);

  /**
   * Pick weapon for the player.
   */
  void pickWeapon();

  /**
   * Attack target for the player.
   */
  void attackTarget();

  /**
   * Move the pet.
   */
  void movePet();

  /**
   * Play a turn automatically if it's computer player's turn.
   */
  void checkAndPlayTurnForComputerPlayer();

}