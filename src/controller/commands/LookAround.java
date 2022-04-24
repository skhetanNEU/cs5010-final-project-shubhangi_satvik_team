package controller.commands;

import model.world.WorldInterface;

/**
 * Command for look around option for the player.
 */
public class LookAround extends AbstractCommands {

  @Override
  public void execute(WorldInterface model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model is invalid");
    }
    try {
      commandResult.append(model.lookAroundSpace());
      isCommandSuccessful = true;
    } catch (IllegalArgumentException exception) {
      commandResult.append("Unable to look around. ").append(exception.getMessage());
    }
  }
}
