package controller.commands;

import model.world.WorldInterface;

public class MovePlayer extends AbstractCommands {

  private final int row;
  private final int col;

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
      commandResult.append(exception.getMessage());
    }
  }
}
