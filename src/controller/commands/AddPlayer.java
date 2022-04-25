package controller.commands;

import model.world.WorldInterface;

/**
 * Command class for adding a player to the world.
 */
public class AddPlayer extends AbstractCommands {

  private final String playerName;
  private final String roomName;
  private final int maxNumberOfWeapons;
  private final boolean isComputerPlayer;

  /**
   * Constructor for adding a player to the game.
   * @param playerName Name of the player
   * @param roomName Name of the room where the player should be added
   * @param maxNumberOfWeapons Maximum number of weapons that can be carried by the player. If it
   *                          is -1, then there is no limit.
   * @param isComputerPlayer If the player is a computer player or not.
   * @throws IllegalArgumentException When any of the player name, room name, maximum number of
   *                                  weapons and isComputerPlayer is invalid.
   */
  public AddPlayer(String playerName, String roomName, int maxNumberOfWeapons,
                   boolean isComputerPlayer) throws IllegalArgumentException {
    if (playerName == null || "".equals(playerName)) {
      throw new IllegalArgumentException("Invalid player name");
    }
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException("Invalid room name");
    }
    if (maxNumberOfWeapons < -1) {
      throw new IllegalArgumentException("Invalid maximum number of weapons that a player "
              + "can carry");
    }

    this.playerName = playerName;
    this.roomName = roomName;
    this.maxNumberOfWeapons = maxNumberOfWeapons;
    this.isComputerPlayer = isComputerPlayer;
  }

  @Override
  public void execute(WorldInterface model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model is invalid");
    }
    try {
      model.addPlayerToGame(this.playerName, this.maxNumberOfWeapons,
              this.isComputerPlayer, this.roomName);
      isCommandSuccessful = true;
    } catch (IllegalArgumentException exception) {
      commandResult.append(exception.getMessage());
    }
  }
}
