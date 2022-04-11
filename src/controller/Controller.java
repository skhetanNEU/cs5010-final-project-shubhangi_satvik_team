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
      throw new IllegalArgumentException("Cannot be null");
    }
    this.preGameView = preGameView;
    this.preGameView.addFeatures(this);
    this.preGameView.makeVisible();
  }

  public Controller(WorldInterface model, GameViewInterface gameView,
                    PreGameViewInterface preGameView) {
    if (model == null || gameView == null || preGameView == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    this.model = model;
    this.gameView = gameView;
    this.preGameView = preGameView;
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

  // TODO: Update movePlayer method and remove this
  @Override
  public void handleRoomClick(int row, int col) {
  }

  // TODO: Set result in view
  @Override
  public void getPlayerDescription() {
    CommandsInterface getPlayerDescription = new GetPlayerDescription();
    getPlayerDescription.execute(model);
    String result = getPlayerDescription.getCommandResult();
    gameView.refresh(false);
  }

  // TODO: Set result in view
  // TODO: Input parameters should take in row and column value - model determines the room
  @Override
  public void movePlayer(String roomName) {
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException("Room name is invalid");
    }
    CommandsInterface movePlayer = new MovePlayer(roomName);
    movePlayer.execute(model);
    String result = movePlayer.getCommandResult();
    gameView.refresh(false);
  }

  // TODO: Set result in view
  @Override
  public void pickWeapon(String weaponName) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name is invalid");
    }
    CommandsInterface pickWeapon = new PickWeapon(weaponName);
    pickWeapon.execute(model);
    String result = pickWeapon.getCommandResult();
    gameView.refresh(false);
  }

  // TODO: Set result in view
  @Override
  public void lookAround() {
    CommandsInterface lookAround = new LookAround();
    lookAround.execute(model);
    String result = lookAround.getCommandResult();
    gameView.refresh(true);
  }

  // TODO: Set result in view
  @Override
  public void attackTarget(String weaponName) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name is invalid");
    }
    CommandsInterface attackTarget = new AttackTarget(weaponName);
    attackTarget.execute(model);
    String result = attackTarget.getCommandResult();
    gameView.refresh(false);
  }

  // TODO: Set result in view
  @Override
  public void movePet(String roomName) {
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException("Room name is invalid");
    }
    CommandsInterface movePet = new MovePet(roomName);
    movePet.execute(model);
    String result = movePet.getCommandResult();
    gameView.refresh(false);
  }

  @Override
  public void addPlayer(String playerName, String roomName, String maxNumberOfWeapons,
                        boolean isComputerPlayer) {
    int numWeapons;

    if (playerName == null || "".equals(playerName)) {
      throw new IllegalArgumentException("Invalid player name");
    }
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException("Invalid room name");
    }
    try {
      numWeapons = Integer.parseInt(maxNumberOfWeapons);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Maximum number of weapons should be a number");
    }
    if (numWeapons < -1) {
      throw new IllegalArgumentException("Invalid maximum number of weapons that a player "
              + "can carry");
    }
    CommandsInterface addPlayer = new AddPlayer(playerName, roomName, numWeapons,
            isComputerPlayer);
    addPlayer.execute(model);
    String result = addPlayer.getCommandResult();
    gameView.refresh(false);
  }
}
