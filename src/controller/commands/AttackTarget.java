package controller.commands;

import model.world.WorldInterface;

public class AttackTarget extends AbstractCommands {

  String weaponName;

  public AttackTarget(String weaponName) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Invalid weapon name");
    }
    this.weaponName = weaponName;
  }

  @Override
  public void execute(WorldInterface model) {
    if(model == null){
      throw new IllegalArgumentException("Model is invalid");
    }
    try {
      model.attackTargetPlayer(this.weaponName);
      commandResult.append("Attack on target successful");
      isCommandSuccessful = true;
    } catch (IllegalArgumentException exception) {
      commandResult.append(exception.getMessage());
    }
  }
}
