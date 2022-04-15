package controller.commands;

import model.world.WorldInterface;

public class MovePet extends AbstractCommands {

  private final String roomName;

  public MovePet(String roomName) {
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException("Invalid room name");
    }
    this.roomName = roomName;
  }

  @Override
  public void execute(WorldInterface model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is invalid");
    }
    try {
      commandResult.append(model.movePet(this.roomName));
      isCommandSuccessful = true;
    } catch (IllegalArgumentException exception) {
      commandResult.append(exception.getMessage());
    }
  }
}
