package controller.commands;

import model.world.WorldInterface;

/**
 * Command for picking a weapon for the player.
 */
public class PickWeapon extends AbstractCommands {

  private String weaponName;

  /**
   * Constructor for setting up the pick weapon command.
   * @param weaponName Name of the weapon that should be picked.
   * @throws IllegalArgumentException when the weapon name is invalid.
   */
  public PickWeapon(String weaponName) throws IllegalArgumentException {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name is invalid");
    }
    this.weaponName = weaponName;
  }

  @Override
  public void execute(WorldInterface model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model is invalid");
    }
    try {
      commandResult.append(model.pickWeapon(this.weaponName));
      isCommandSuccessful = true;
    } catch (IllegalArgumentException exception) {
      commandResult.append("Unable to pick weapon. ").append(exception.getMessage());
    }
  }
}
