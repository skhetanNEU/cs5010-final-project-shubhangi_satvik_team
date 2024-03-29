package controller.commands;

import model.world.WorldInterface;

/**
 * Command for moving the pet.
 */
public class MovePet extends AbstractCommands {

  private final String roomName;

  /**
   * Constructor for setting up the move pet command.
   * @param roomName Name of the room where the pet should be moved to.
   * @throws IllegalArgumentException when the room name is invalid.
   */
  public MovePet(String roomName) throws IllegalArgumentException {
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException("Invalid room name");
    }
    this.roomName = roomName;
  }

  @Override
  public void execute(WorldInterface model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model is invalid");
    }
    try {
      commandResult.append(model.movePet(this.roomName));
      isCommandSuccessful = true;
    } catch (IllegalArgumentException exception) {
      commandResult.append("Unable to move pet. ").append(exception.getMessage());
    }
  }
}
