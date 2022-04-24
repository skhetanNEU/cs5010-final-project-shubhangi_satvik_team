package controller.commands;

import model.world.WorldInterface;

/**
 * Command for getting the description of the player.
 */
public class GetPlayerDescription extends AbstractCommands {

  @Override
  public void execute(WorldInterface model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model is invalid");
    }
    try {
      commandResult.append(model.getCurrentPlayerInformation());
      isCommandSuccessful = true;
    } catch (IllegalArgumentException exception) {
      commandResult.append(exception.getMessage());
    }
  }
}
