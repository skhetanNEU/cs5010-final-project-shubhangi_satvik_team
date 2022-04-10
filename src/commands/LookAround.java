package commands;

import model.world.WorldInterface;

public class LookAround extends AbstractCommands {

    public LookAround(){
    }

    @Override
    public void execute(WorldInterface model) {
        try{
            commandResult.append(model.lookAroundSpace());
            isCommandSuccessful = true;
        } catch (IllegalArgumentException exception){
            commandResult.append(exception.getMessage());
        }
    }
}
