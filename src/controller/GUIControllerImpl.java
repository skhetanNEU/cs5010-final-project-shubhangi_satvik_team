package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commands.*;
import model.random.RandomClass;
import model.random.RandomGenerator;
import model.world.WorldImpl;
import model.world.WorldInterface;
import view.GameViewImpl;
import view.GameViewInterface;
import view.PreGameViewInterface;

public class GUIControllerImpl implements GUIControllerInterface, ActionListener {

  private WorldInterface model;
  private GameViewInterface gameView;
  private final PreGameViewInterface preGameView;

  public GUIControllerImpl(PreGameViewInterface preGameView) {
    if (preGameView == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    this.preGameView = preGameView;
    this.preGameView.setCommandButtonListener(this);
    this.preGameView.makeVisible();
  }

  public GUIControllerImpl(WorldInterface model, GameViewInterface gameView,
                           PreGameViewInterface preGameView) {
    if (model == null || gameView == null || preGameView == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    this.model = model;
    this.gameView = gameView;
    this.preGameView = preGameView;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    RandomGenerator rand = new RandomClass(true);
    model = WorldImpl.getBuilder()
            //        .parseInputFile(text)
            .setRandomGenerator(rand)
            .build();
    this.gameView = new GameViewImpl(model, this);
    playGame();
  }

  @Override
  public void playGame() {
    preGameView.close();
    gameView.makeVisible();
    gameView.addClickListener(this);
    gameView.setKeyBoardListeners(this);
  }

  @Override
  public void handleRoomClick(int row, int col) {
  }

  @Override
  public void restartGame() {

  }

  @Override
  public void newGame() {

  }

  @Override
  public String getPlayerDescription() {
    CommandsInterface getPlayerDescription = new GetPlayerDescription();
    getPlayerDescription.execute(model);
    return getPlayerDescription.getCommandResult();
  }

  @Override
  public String movePlayer(String roomName) {
    if(roomName == null || "".equals(roomName)){
      throw new IllegalArgumentException("Room name is invalid");
    }
    CommandsInterface movePlayer = new MovePlayer(roomName);
    movePlayer.execute(model);
    return movePlayer.getCommandResult();
  }

  @Override
  public String pickWeapon(String weaponName) {
    if(weaponName == null || "".equals(weaponName)){
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
    if(weaponName == null || "".equals(weaponName)){
      throw new IllegalArgumentException("Weapon name is invalid");
    }
    CommandsInterface attackTarget = new AttackTarget(weaponName);
    attackTarget.execute(model);
    return attackTarget.getCommandResult();
  }

  @Override
  public String movePet(String roomName) {
    if(roomName == null || "".equals(roomName)){
      throw new IllegalArgumentException("Room name is invalid");
    }
    CommandsInterface movePet = new MovePet(roomName);
    movePet.execute(model);
    return movePet.getCommandResult();
  }
}
