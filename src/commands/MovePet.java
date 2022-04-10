package commands;

import model.world.WorldInterface;

public class MovePet extends AbstractCommands {

    private String roomName;

    public MovePet(String roomName){
        if(roomName == null || "".equals(roomName)){
            throw new IllegalArgumentException("Invalid room name");
        }
        this.roomName = roomName;
    }

    @Override
    public void execute(WorldInterface model) {
        try{
            model.movePet(this.roomName);
            commandResult.append("Pet has moved to room ").append(this.roomName);
            isCommandSuccessful = true;
        } catch(IllegalArgumentException exception){
            commandResult.append(exception.getMessage());
        }
    }
}
