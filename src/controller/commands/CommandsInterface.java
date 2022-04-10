package controller.commands;

import model.world.WorldInterface;

/**
 * Interface for the commands required by the controller.
 *
 * @author Satvik Khetan
 */
public interface CommandsInterface {
  /**
   * Execute the required functionality by calling the required functions from the
   * model.
   *
   * @param model The model object of the game.
   * @throws IllegalArgumentException when the passed model is null.
   */
  public void execute(WorldInterface model);

  /**
   * Check if the execution of the command was successful.
   *
   * @return A boolean representing whether the command was successfully executed or not.
   */
  public boolean isCommandSuccessful();

  /**
   * Get the result of the execution of the command.
   *
   * @return A string representing the result of execution of the command.
   */
  public String getCommandResult();
}

