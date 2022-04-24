package controller.commands;

import model.world.WorldInterface;

/**
 * Command for making an attack on the target.
 */
public class AttackTarget extends AbstractCommands {

  private final String weaponName;

  /**
   * Constructor for setting up the attack target.
   * @param weaponName Name of the weapon that should be used for attacking the target.
   */
  public AttackTarget(String weaponName) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Invalid weapon name");
    }
    this.weaponName = weaponName;
  }

  @Override
  public void execute(WorldInterface model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is invalid");
    }
    try {
      commandResult.append(model.attackTargetPlayer(this.weaponName));
      isCommandSuccessful = true;
    } catch (IllegalArgumentException exception) {
      commandResult.append("Unable to attack target. ").append(exception.getMessage());
    }
  }
}
