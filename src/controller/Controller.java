package controller;

import controller.commands.*;

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

public class Controller implements FeatureInterface {

  private WorldInterface model;
  private GameViewInterface gameView;
  private final PreGameViewInterface preGameView;
  private final int maxNumberOfTurns;
  private Readable defaultConfiguration;
  private int currentTurnNumber;

  public Controller(PreGameViewInterface preGameView,
                    FileReader worldConfiguration, int maxNumberOfTurns) {
    if (preGameView == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    if (worldConfiguration == null) {
      throw new IllegalArgumentException("Invalid configuration file");
    }
    if (maxNumberOfTurns <= 0) {
      throw new IllegalArgumentException("Number of turns cannot be non-positive");
    }

    this.defaultConfiguration = worldConfiguration;
    this.maxNumberOfTurns = maxNumberOfTurns;
    this.preGameView = preGameView;
    this.preGameView.addFeatures(this);
    this.preGameView.makeVisible();
    this.currentTurnNumber = 0;
  }

  @Override
  public void playGame(File file) {
    Readable chosen = defaultConfiguration;
    try {
      if (file != null) {
        chosen = new FileReader(file);
      }
      RandomGenerator rand = new RandomClass(true);
      model = WorldImpl.getBuilder()
              .parseInputFile(chosen)
              .setRandomGenerator(rand)
              .build();
      quitGame();
      gameView = new GameViewImpl(model, this);
      gameView.makeVisible();
      gameView.addFeatures(this);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("ERROR: File not found.");
    }
  }

  @Override
  public void quitGame() {
    if (gameView != null) {
      gameView.close();
    } else {
      preGameView.close();
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
    if(lookAround.isCommandSuccessful()){
      this.currentTurnNumber++;
      checkAndPlayTurnForComputerPlayer();
      checkIfGameIsOver();
    }
  }

  @Override
  public void handleRoomClick(int row, int col) {
    CommandsInterface cellClick = null;
    if (row < 0 || col < 0) {
      gameView.showCommandOutcome("ERROR", "Coordinates cannot be negative", false);
    } else {
      if (model.isPlayerIconClicked(row, col)) {
        CommandsInterface playerInfo = new GetPlayerDescription();
        playerInfo.execute(model);
        gameView.showCommandOutcome("Current Player Details", playerInfo.getCommandResult(), false);
      } else {
        cellClick = new MovePlayer(row, col);
        cellClick.execute(model);
        gameView.showCommandOutcome("Move Player Result", cellClick.getCommandResult(), false);
      }
    }
    model.updateWorldView(false);
    gameView.refresh();
    if(cellClick!=null && cellClick.isCommandSuccessful()){
      this.currentTurnNumber++;
      checkAndPlayTurnForComputerPlayer();
      checkIfGameIsOver();
    }
  }

  @Override
  public void pickWeapon() {
    CommandsInterface pickWeapon = null;
    String weaponName = gameView.showPickWeaponDialog();
    if (weaponName == null) {
      gameView.showCommandOutcome("CANCELLED", "Pick weapon cancelled", false);
    } else if ("".equals(weaponName)) {
      gameView.showCommandOutcome("ERROR", "Weapon name cannot be empty", false);
    } else {
      pickWeapon = new PickWeapon(weaponName);
      pickWeapon.execute(model);
      gameView.showCommandOutcome("Pick Weapon Result", pickWeapon.getCommandResult(), false);
    }
    model.updateWorldView(false);
    gameView.refresh();
    if(pickWeapon!=null && pickWeapon.isCommandSuccessful()){
      this.currentTurnNumber++;
      checkAndPlayTurnForComputerPlayer();
      checkIfGameIsOver();
    }
  }

  @Override
  public void attackTarget() {
    CommandsInterface attackTarget = null;
    String weaponName = gameView.showAttackTargetDialog();
    if (weaponName == null) {
      gameView.showCommandOutcome("CANCELLED", "Attack target cancelled", false);
    } else if ("".equals(weaponName)) {
      gameView.showCommandOutcome("ERROR", "Weapon name cannot be empty", false);
    } else {
      attackTarget = new AttackTarget(weaponName);
      attackTarget.execute(model);
      gameView.showCommandOutcome("Attack Target Result", attackTarget.getCommandResult(), false);
    }
    model.updateWorldView(false);
    gameView.refresh();
    if(attackTarget!=null && attackTarget.isCommandSuccessful()){
      this.currentTurnNumber++;
      checkAndPlayTurnForComputerPlayer();
      checkIfGameIsOver();
    }
  }

  @Override
  public void movePet() {
    CommandsInterface movePet = null;
    String roomName = gameView.showMovePetDialog();
    if (roomName == null) {
      gameView.showCommandOutcome("CANCELLED", "Move pet cancelled", false);
    } else if ("".equals(roomName)) {
      gameView.showCommandOutcome("ERROR", "Room name cannot be empty", false);
    } else {
      movePet = new MovePet(roomName);
      movePet.execute(model);
      gameView.showCommandOutcome("Move Pet Result", movePet.getCommandResult(), false);
    }
    model.updateWorldView(false);
    gameView.refresh();
    if(movePet!=null && movePet.isCommandSuccessful()){
      this.currentTurnNumber++;
      checkAndPlayTurnForComputerPlayer();
      checkIfGameIsOver();
    }
  }

  @Override
  public void checkAndPlayTurnForComputerPlayer(){
    if (model.isCurrentPlayerComputer()) {
      CommandsInterface playTurnForComputerPlayer = new PlayTurnForComputerPlayer();
      playTurnForComputerPlayer.execute(model);
      gameView.showCommandOutcome("Computer Player Turn Details", playTurnForComputerPlayer.getCommandResult(),
              false);
      model.updateWorldView(false);
      gameView.refresh();
      if(playTurnForComputerPlayer.isCommandSuccessful()){
        this.currentTurnNumber++;
        checkIfGameIsOver();
      }
    }
    if(model.isCurrentPlayerComputer()){
      checkAndPlayTurnForComputerPlayer();
    }
  }

  @Override
  public int getTurnNumber(){
    return this.currentTurnNumber;
  }

  private void checkIfGameIsOver(){
    if(model.isGameOver()){
      gameView.showCommandOutcome("Game Over!",model.getWinner(), false);
    } else if(this.currentTurnNumber >= this.maxNumberOfTurns){
      gameView.showCommandOutcome("Game Over!", "Target has escaped alive!", false);
    }
    gameView.refresh();
  }
}
