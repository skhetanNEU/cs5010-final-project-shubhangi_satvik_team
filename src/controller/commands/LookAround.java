package controller.commands;

import model.world.WorldInterface;

public class LookAround extends AbstractCommands {

  @Override
  public void execute(WorldInterface model) {
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
