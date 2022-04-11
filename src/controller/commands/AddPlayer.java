package controller.commands;

import model.world.WorldInterface;

public class AddPlayer extends AbstractCommands {
    private String playerName;
    private String roomName;
    private int maxNumberOfWeapons;
    private boolean isComputerPlayer;

    public AddPlayer(String playerName, String roomName, int maxNumberOfWeapons,
                     boolean isComputerPlayer){
        if(playerName == null || "".equals(playerName)){
            throw new IllegalArgumentException("Invalid player name");
        }
        if(roomName == null || "".equals(roomName)){
            throw new IllegalArgumentException("Invalid room name");
        }
        if(maxNumberOfWeapons < 0 || maxNumberOfWeapons != -1){
            throw new IllegalArgumentException("Invalid maximum number of weapons that a player "
                    + "can carry");
        }

        this.playerName = playerName;
        this.roomName = roomName;
        this.maxNumberOfWeapons = maxNumberOfWeapons;
        this.isComputerPlayer = isComputerPlayer;
    }


    @Override
    public void execute(WorldInterface model) {
        if (model == null) {
            throw new IllegalArgumentException("Model is invalid");
        }
        try{
            model.addPlayerToGame(this.playerName,this.maxNumberOfWeapons,
                    this.isComputerPlayer, this.roomName);
            commandResult.append("Player ").append(this.playerName).append(" is added to room ").append(this.roomName);
            isCommandSuccessful = true;
        } catch (IllegalArgumentException exception){
            commandResult.append(exception.getMessage());
        }
    }
}
