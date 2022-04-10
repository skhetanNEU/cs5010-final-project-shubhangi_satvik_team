package commands;
import model.world.WorldInterface;

public class GetPlayerDescription extends AbstractCommands {

    public GetPlayerDescription(){
    }

    @Override
    public void execute(WorldInterface model) {
        try{
            //TODO: Make it work for current player
            commandResult.append(model.getPlayerInformation(""));
            isCommandSuccessful = true;
        } catch(IllegalArgumentException exception){
            commandResult.append(exception.getMessage());
        }
    }
}
