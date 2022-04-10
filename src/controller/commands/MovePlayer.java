package controller.commands;

import model.world.WorldInterface;

public class MovePlayer extends AbstractCommands {

  private String roomName;

  public MovePlayer(String roomName) {
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException("Room name is invalid");
    }
    this.roomName = roomName;
  }

  @Override
  public void execute(WorldInterface model) {
    if(model == null){
      throw new IllegalArgumentException("Model is invalid");
    }
    try {
      model.movePlayer(this.roomName);
      commandResult.append("Player has moved to room ").append(this.roomName);
      isCommandSuccessful = true;
    } catch (IllegalArgumentException exception) {
      commandResult.append(exception.getMessage());
    }
  }
}
