package controller.commands;

import model.world.WorldInterface;

public class AttackTarget extends AbstractCommands {

  private final String weaponName;

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
      commandResult.append(exception.getMessage());
    }
  }
}
