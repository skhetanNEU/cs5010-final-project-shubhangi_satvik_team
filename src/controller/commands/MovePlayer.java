package controller.commands;

import model.world.WorldInterface;

/**
 * Command for moving the player.
 */
public class MovePlayer extends AbstractCommands {

  private final int row;
  private final int col;

  /**
   * Constructor for setting up the move player command.
   * @param row Row number where the player should be moved to according to player click.
   * @param col Column number where the player should be moved to according to player click.
   */
  public MovePlayer(int row, int col) {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Invalid coordinates");
    }
    this.row = row;
    this.col = col;
  }

  @Override
  public void execute(WorldInterface model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is invalid");
    }
    try {
      commandResult.append(model.handleRoomClick(row, col));
      isCommandSuccessful = true;
    } catch (IllegalArgumentException exception) {
      commandResult.append("Unable to move player. ").append(exception.getMessage());
    }
  }
}
