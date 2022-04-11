package controller;

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
      this.gameView = new GameViewImpl(model, this);
      preGameView.close();
      gameView.makeVisible();
      gameView.addFeatures(this);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found.");
    }

  }

  @Override
  public void handleRoomClick(int row, int col) {
  }

  @Override
  public String getPlayerDescription() {
    CommandsInterface getPlayerDescription = new GetPlayerDescription();
    getPlayerDescription.execute(model);
    return getPlayerDescription.getCommandResult();
  }

  @Override
  public String movePlayer(String roomName) {
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException("Room name is invalid");
    }
    CommandsInterface movePlayer = new MovePlayer(roomName);
    movePlayer.execute(model);
    return movePlayer.getCommandResult();
  }

  @Override
  public String pickWeapon(String weaponName) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name is invalid");
    }
    CommandsInterface pickWeapon = new PickWeapon(weaponName);
    pickWeapon.execute(model);
    return pickWeapon.getCommandResult();
  }

  @Override
  public String lookAround() {
    CommandsInterface lookAround = new LookAround();
    lookAround.execute(model);
    return lookAround.getCommandResult();
  }

  @Override
  public String attackTarget(String weaponName) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name is invalid");
    }
    CommandsInterface attackTarget = new AttackTarget(weaponName);
    attackTarget.execute(model);
    return attackTarget.getCommandResult();
  }

  @Override
  public String movePet(String roomName) {
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException("Room name is invalid");
    }
    CommandsInterface movePet = new MovePet(roomName);
    movePet.execute(model);
    return movePet.getCommandResult();
  }
}
