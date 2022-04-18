package controller;

import controller.commands.AddPlayer;
import controller.commands.AttackTarget;
import controller.commands.CommandsInterface;
import controller.commands.GetPlayerDescription;
import controller.commands.LookAround;
import controller.commands.MovePet;
import controller.commands.MovePlayer;
import controller.commands.PickWeapon;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import model.random.RandomClass;
import model.random.RandomGenerator;
import model.world.WorldImpl;
import model.world.WorldInterface;
import view.GameViewImpl;
import view.GameViewInterface;
import view.PreGameViewInterface;

public class Controller implements FeatureInterface{

  private WorldInterface model;
  private GameViewInterface gameView;
  private final PreGameViewInterface preGameView;
  private final int maxNumberOfTurns;
  private Readable worldConfiguration;

  public Controller(PreGameViewInterface preGameView,
                    FileReader worldConfiguration, int maxNumberOfTurns) {
    if (preGameView == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    if(worldConfiguration == null){
      throw new IllegalArgumentException("Invalid configuration file");
    }
    if(maxNumberOfTurns <= 0){
      throw new IllegalArgumentException("Number of turns cannot be zero");
    }

    this.worldConfiguration = worldConfiguration;
    this.preGameView = preGameView;
    this.preGameView.addFeatures(this);
    this.preGameView.makeVisible();
    this.maxNumberOfTurns = maxNumberOfTurns;

  }

//  public Controller(WorldInterface model, GameViewInterface gameView,
//                    PreGameViewInterface preGameView, int maxNumberOfTurns) {
//    if (model == null || gameView == null || preGameView == null) {
//      throw new IllegalArgumentException("View/model cannot be null");
//    }
//    this.model = model;
//    this.gameView = gameView;
//    this.preGameView = preGameView;
//    this.maxNumberOfTurns = maxNumberOfTurns;
//  }

  @Override
  public void playGame(File file) {
    //File chosen = file;

    try {
      if (file != null) {
        this.worldConfiguration = new FileReader(file);
        //chosen = new File("res/FriendsWorld.txt");
      }
      RandomGenerator rand = new RandomClass(true);
      model = WorldImpl.getBuilder()
              .parseInputFile(this.worldConfiguration)
              .setRandomGenerator(rand)
              .build();
      quitGame();
      this.gameView = new GameViewImpl(model, this);
      gameView.makeVisible();
      gameView.addFeatures(this);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found.");
    }
  }

  @Override
  public void startPlaying(){
    while(model.GetTurnNumber()<maxNumberOfTurns){
      if(model.isCurrentPlayerComputer()){
        String result = model.takeTurnForComputerPlayer();
        gameView.showCommandOutcome("Computer Player Turn Details", result,
                false);
        model.updateWorldView(false);
        gameView.refresh();
      }
    }
  }

  @Override
  public void quitGame() {
    if (this.gameView != null) {
      this.gameView.close();
    } else {
      this.preGameView.close();
    }
  }

  @Override
  public String addPlayer(String playerName, String roomName, String maxNumberOfWeapons,
                          boolean isComputerPlayer) {
    int numWeapons;

    if (playerName == null || "".equals(playerName)) {
      return "Player name cannot be empty";
    }
    if (roomName == null || "".equals(roomName)) {
      return "Room name cannot be empty";
    }
    try {
      numWeapons = Integer.parseInt(maxNumberOfWeapons);
    } catch (NumberFormatException nfe) {
      return "Maximum number of weapons must be a number";
    }
    if (numWeapons < -1) {
      return "Maximum number of weapons should be -1 or positive";
    }
    CommandsInterface addPlayer = new AddPlayer(playerName, roomName, numWeapons,
            isComputerPlayer);
    addPlayer.execute(model);
    model.updateWorldView(false);
    gameView.refresh();
    return addPlayer.getCommandResult();
  }

  @Override
  public void lookAround() {
    CommandsInterface lookAround = new LookAround();
    model.updateWorldView(true);
    gameView.refresh();
    lookAround.execute(model);
    gameView.showCommandOutcome("Look Around Details", lookAround.getCommandResult(), true);
    model.updateWorldView(false);
    gameView.refresh();

  }

  @Override
  public void handleRoomClick(int row, int col) {
    if (row < 0 || col < 0) {
      gameView.showCommandOutcome("ERROR", "Coordinates cannot be negative", false);
    } else {
      if (model.isPlayerIconClicked(row, col)) {
        CommandsInterface playerInfo = new GetPlayerDescription();
        playerInfo.execute(model);
        gameView.showCommandOutcome("Current Player Details", playerInfo.getCommandResult(), false);
      } else {
        CommandsInterface cellClick = new MovePlayer(row, col);
        cellClick.execute(model);
        gameView.showCommandOutcome("Move Player Result", cellClick.getCommandResult(), false);
      }
    }
    model.updateWorldView(false);
    gameView.refresh();
  }

  @Override
  public void pickWeapon() {
    String weaponName = gameView.showPickWeaponDialog();
    if (weaponName == null || "".equals(weaponName)) {
      gameView.showCommandOutcome("ERROR", "Weapon name cannot be empty", false);
    } else {
      CommandsInterface pickWeapon = new PickWeapon(weaponName);
      pickWeapon.execute(model);
      gameView.showCommandOutcome("Pick Weapon Result", pickWeapon.getCommandResult(), false);
    }
    model.updateWorldView(false);
    gameView.refresh();
  }

  @Override
  public void attackTarget() {
    String weaponName = gameView.showAttackTargetDialog();
    if (weaponName == null || "".equals(weaponName)) {
      gameView.showCommandOutcome("ERROR", "Weapon name cannot be empty", false);
    } else {
      CommandsInterface attackTarget = new AttackTarget(weaponName);
      attackTarget.execute(model);
      gameView.showCommandOutcome("Attack Target Result", attackTarget.getCommandResult(), false);
    }
    model.updateWorldView(false);
    gameView.refresh();
  }

  @Override
  public void movePet() {
    String roomName = gameView.showMovePetDialog();
    if (roomName == null || "".equals(roomName)) {
      gameView.showCommandOutcome("ERROR", "Room name cannot be empty", false);
    } else {
      CommandsInterface movePet = new MovePet(roomName);
      movePet.execute(model);
      gameView.showCommandOutcome("Move Pet Result", movePet.getCommandResult(), false);
    }
    model.updateWorldView(false);
    gameView.refresh();
  }
}
