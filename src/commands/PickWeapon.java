package commands;

import model.world.WorldInterface;

public class PickWeapon extends AbstractCommands {

    private String weaponName;
    public PickWeapon(String weaponName){
        if(weaponName == null || "".equals(weaponName)){
            throw new IllegalArgumentException("Weapon name is invalid");
        }
        this.weaponName = weaponName;
    }

    @Override
    public void execute(WorldInterface model) {
        try{
            model.pickWeapon(this.weaponName);
            commandResult.append("Player has picked the weapon");
            isCommandSuccessful = true;
        } catch(IllegalArgumentException exception){
            commandResult.append(exception.getMessage());
        }
        //TODO : Check in all commands if there should be any catch for any other exception
    }
}
