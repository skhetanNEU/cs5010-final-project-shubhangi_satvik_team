package controller.commands;

import model.world.WorldInterface;

/**
 * Command class for playing a turn for the computer player.
 *
 */
public class PlayTurnForComputerPlayer extends AbstractCommands {

  @Override
  public void execute(WorldInterface model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is invalid");
    }
    try {
      commandResult.append(model.takeTurnForComputerPlayer());
      isCommandSuccessful = true;
    } catch (IllegalArgumentException exception) {
      commandResult.append(exception.getMessage());
    }
  }
}
