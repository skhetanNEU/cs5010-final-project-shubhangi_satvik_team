package controller.commands;

/**
 * Abstract class for reusing the code that will be used for all the game
 * commands.
 *
 */
public abstract class AbstractCommands implements CommandsInterface {

  protected StringBuilder commandResult;
  protected boolean isCommandSuccessful;

  /**
   * Constructor for the abstract game commands. It sets the required string
   * builder and isCommandSuccessful accordingly.
   */
  public AbstractCommands() {
    this.commandResult = new StringBuilder();
    this.isCommandSuccessful = false;
  }

  @Override
  public boolean isCommandSuccessful() {
    return this.isCommandSuccessful;
  }

  @Override
  public String getCommandResult() {
    return this.commandResult.toString();
  }
}
