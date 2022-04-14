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

public class Controller implements FeatureInterface {

  private WorldInterface model;
  private GameViewInterface gameView;
  private final PreGameViewInterface preGameView;

  public Controller(PreGameViewInterface preGameView) {
    if (preGameView == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    this.preGameView = preGameView;
    this.preGameView.addFeatures(this);
    this.preGameView.makeVisible();
  }

  public Controller(WorldInterface model, GameViewInterface gameView,
                    PreGameViewInterface preGameView) {
    if (model == null || gameView == null || preGameView == null) {
      throw new IllegalArgumentException("View/model cannot be null");
    }
    this.model = model;
    this.gameView = gameView;
    this.preGameView = preGameView;
  }

  @Override
  public void playGame(File file) {
    File chosen = file;
    if (file == null) {
      chosen = new File("res/FriendsWorld.txt");
    }
    try {
      Readable reader = new FileReader(chosen);
      RandomGenerator rand = new RandomClass(true);
      model = WorldImpl.getBuilder()
              .parseInputFile(reader)
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
  public void quitGame() {
    if (this.gameView != null) {
      this.gameView.close();
    } else {
      this.preGameView.close();
    }
  }

  @Override
  public String getPlayerDescription() {
    CommandsInterface getPlayerDescription = new GetPlayerDescription();
    getPlayerDescription.execute(model);
    return getPlayerDescription.getCommandResult();
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
    gameView.refresh(false);
    return addPlayer.getCommandResult();
  }

  @Override
  public void lookAround() {
    CommandsInterface lookAround = new LookAround();
    lookAround.execute(model);
    gameView.showCommandOutcome("Look Around Details", lookAround.getCommandResult(), true);
    gameView.refresh(true);
  }

  @Override
  public void movePlayer(int row, int col) {
    if (row < 0 || col < 0) {
      gameView.showCommandOutcome("Move Player Result", "Coordinates cannot be negative", false);
    } else {
      CommandsInterface movePlayer = new MovePlayer(row, col);
      movePlayer.execute(model);
      gameView.showCommandOutcome("Move Player Result", movePlayer.getCommandResult(), false);
    }
    gameView.refresh(true);
  }

  @Override
  public void pickWeapon() {
    String weaponName = gameView.showPickWeaponDialog();
    if (weaponName == null || "".equals(weaponName)) {
      gameView.showCommandOutcome("Pick Weapon Result", "Weapon name cannot be empty", false);
    } else {
      CommandsInterface pickWeapon = new PickWeapon(weaponName);
      pickWeapon.execute(model);
      gameView.showCommandOutcome("Pick Weapon Result", pickWeapon.getCommandResult(), false);
    }
    gameView.refresh(false);
  }

  @Override
  public void attackTarget() {
    String weaponName = gameView.showAttackTargetDialog();
    if (weaponName == null || "".equals(weaponName)) {
      gameView.showCommandOutcome("Attack Target Result", "Weapon name cannot be empty", false);
    } else {
      CommandsInterface attackTarget = new AttackTarget(weaponName);
      attackTarget.execute(model);
      gameView.showCommandOutcome("Attack Target Result", attackTarget.getCommandResult(), false);
    }
    gameView.refresh(false);
  }

  @Override
  public void movePet() {
    String roomName = gameView.showMovePetDialog();
    if (roomName == null || "".equals(roomName)) {
      gameView.showCommandOutcome("Move Pet Result", "Room name cannot be empty", false);
    } else {
      CommandsInterface movePet = new MovePet(roomName);
      movePet.execute(model);
      gameView.showCommandOutcome("Move Pet Result", movePet.getCommandResult(), false);
    }
    gameView.refresh(false);
  }
}
