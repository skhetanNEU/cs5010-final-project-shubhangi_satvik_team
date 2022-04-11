package controller.commands;

import model.world.WorldInterface;

public class GetTargetDetails extends AbstractCommands{

    @Override
    public void execute(WorldInterface model) {
        if (model == null) {
            throw new IllegalArgumentException("Model is invalid");
        }
        try{
            commandResult.append(model.getTargetPlayerDetails());
            isCommandSuccessful = true;
        } catch (IllegalArgumentException exception){
            commandResult.append(exception.getMessage());
        }
    }
}
