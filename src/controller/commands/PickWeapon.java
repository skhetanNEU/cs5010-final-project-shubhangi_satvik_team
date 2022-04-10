package controller.commands;

import model.world.WorldInterface;

public class PickWeapon extends AbstractCommands {

  private String weaponName;

  public PickWeapon(String weaponName) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name is invalid");
    }
    this.weaponName = weaponName;
  }

  @Override
  public void execute(WorldInterface model) {
    if(model == null){
      throw new IllegalArgumentException("Model is invalid");
    }
    try {
      model.pickWeapon(this.weaponName);
      commandResult.append("Player has picked the weapon");
      isCommandSuccessful = true;
    } catch (IllegalArgumentException exception) {
      commandResult.append(exception.getMessage());
    }
  }
}
