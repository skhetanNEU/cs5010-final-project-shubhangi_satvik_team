package controller;

import controller.commands.AddPlayer;
import controller.commands.AttackTarget;
import controller.commands.CommandsInterface;
import controller.commands.GetPlayerDescription;
import controller.commands.LookAround;
import controller.commands.MovePet;
import controller.commands.MovePlayer;
import controller.commands.PickWeapon;
import controller.commands.PlayTurnForComputerPlayer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import model.random.RandomClass;
import model.random.RandomGenerator;
import model.world.WorldImpl;
import model.world.WorldInterface;
import view.DefaultGameViewInterface;
import view.MainGameView;
import view.MainGameViewInterface;

public class Controller implements FeatureInterface {

  private WorldInterface model;
  private MainGameViewInterface gameView;
  private final DefaultGameViewInterface preGameView;
  private final String defaultConfigurationFilePath;
  private final int maxNumberOfTurns;

  public Controller(DefaultGameViewInterface preGameView,
                    String worldConfigurationPath, int maxNumberOfTurns) {
    if (preGameView == null) {
      throw new IllegalArgumentException("Pre Game View cannot be null");
    }
    if (worldConfigurationPath == null || "".equals(worldConfigurationPath)) {
      throw new IllegalArgumentException("Configuration file path is null/empty.");
    }
    if (maxNumberOfTurns <= 0) {
      throw new IllegalArgumentException("Number of turns cannot be non-positive.");
    }

    this.defaultConfigurationFilePath = worldConfigurationPath;
    this.maxNumberOfTurns = maxNumberOfTurns;
    this.preGameView = preGameView;
    this.preGameView.addFeatures(this);
    this.preGameView.makeVisible();
  }

  public Controller(DefaultGameViewInterface preGameView,
                    MainGameViewInterface gameView,
                    WorldInterface model,
                    String worldConfigurationPath, int maxNumberOfTurns) {
    if (preGameView == null) {
      throw new IllegalArgumentException("Pre Game View cannot be null.");
    } else if (gameView == null) {
      throw new IllegalArgumentException("Main Game View cannot be null.");
    } else if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    } else if (worldConfigurationPath == null || "".equals(worldConfigurationPath)) {
      throw new IllegalArgumentException("Configuration file path is null/empty.");
    } else if (maxNumberOfTurns <= 0) {
      throw new IllegalArgumentException("Number of turns cannot be non-positive.");
    }

    this.preGameView = preGameView;
    quitGame();

    this.defaultConfigurationFilePath = worldConfigurationPath;
    this.maxNumberOfTurns = maxNumberOfTurns;
    this.model = model;

    this.gameView = gameView;
    this.gameView.makeVisible();
    this.gameView.addFeatures(this);
  }

  private void showGameOverMessage() {
    if (model.isGameOver()) {
      String message = model.getWinner() != null
              ? String.format("Player %s has killed the target and won the game", model.getWinner())
              : "Target has escaped alive!";
      gameView.refresh();
      gameView.showCommandOutcome("Game Over!", message, false);
    }
  }

  @Override
  public void playGame(File file) {
    try {
      Readable chosen;
      if (file != null) {
        chosen = new FileReader(file);
      } else {
        chosen = new FileReader(defaultConfigurationFilePath);
      }
      RandomGenerator rand = new RandomClass();
      model = WorldImpl.getBuilder()
              .parseInputFile(chosen)
              .setRandomGenerator(rand)
              .setMaxTurns(maxNumberOfTurns)
              .build();
      gameView = new MainGameView(model, this, model.getListOfRooms());
      new Controller(this.preGameView, this.gameView, this.model,
              this.defaultConfigurationFilePath, this.maxNumberOfTurns);
    } catch (FileNotFoundException e) {
      preGameView.showCommandOutcome("ERROR", "File not found", false);
    } catch (IllegalArgumentException iae) {
      preGameView.showCommandOutcome("ERROR", iae.getMessage(), false);
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
    if (numWeapons < -1 || numWeapons == 0) {
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
    if (!model.isGameOver()) {
      CommandsInterface lookAround = new LookAround();
      model.updateWorldView(true);
      gameView.refresh();
      lookAround.execute(model);
      gameView.showCommandOutcome("Look Around Details", lookAround.getCommandResult(), true);
      model.updateWorldView(false);
      gameView.refresh();
      if (lookAround.isCommandSuccessful()) {
        checkAndPlayTurnForComputerPlayer();
        showGameOverMessage();
      }
    } else {
      gameView.showCommandOutcome("Game Over!", "No more turns allowed. Start a new game.", false);
    }
  }

  @Override
  public void handleRoomClick(int row, int col) {
    if (!model.isGameOver()) {
      CommandsInterface cellClick = null;
      if (row < 0 || col < 0) {
        gameView.showCommandOutcome("ERROR", "Coordinates cannot be negative", false);
      } else {
        if (model.isPlayerIconClicked(row, col)) {
          CommandsInterface playerInfo = new GetPlayerDescription();
          playerInfo.execute(model);
          gameView.showCommandOutcome("Current Player Details",
                  playerInfo.getCommandResult(), false);
        } else {
          cellClick = new MovePlayer(row, col);
          cellClick.execute(model);
          gameView.showCommandOutcome("Move Player Result", cellClick.getCommandResult(), false);
        }
      }
      model.updateWorldView(false);
      gameView.refresh();
      if (cellClick != null && cellClick.isCommandSuccessful()) {
        checkAndPlayTurnForComputerPlayer();
        showGameOverMessage();
      }
    } else {
      gameView.showCommandOutcome("Game Over!", "No more turns allowed. Start a new game.", false);
    }
  }

  @Override
  public void pickWeapon() {
    if (!model.isGameOver()) {
      CommandsInterface pickWeapon = null;
      String weaponName = gameView.showPickWeaponDialog(model.getCurrentPlayerRoomWeapons());
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
      if (pickWeapon != null && pickWeapon.isCommandSuccessful()) {
        checkAndPlayTurnForComputerPlayer();
        showGameOverMessage();
      }
    } else {
      gameView.showCommandOutcome("Game Over!", "No more turns allowed. Start a new game.", false);
    }
  }

  @Override
  public void attackTarget() {
    if (!model.isGameOver()) {
      CommandsInterface attackTarget = null;
      String weaponName = gameView.showAttackTargetDialog(model.getCurrentPlayerWeapons());
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
      if (attackTarget != null && attackTarget.isCommandSuccessful()) {
        checkAndPlayTurnForComputerPlayer();
        showGameOverMessage();
      }
    } else {
      gameView.showCommandOutcome("Game Over!", "No more turns allowed. Start a new game.", false);
    }
  }

  @Override
  public void movePet() {
    if (!model.isGameOver()) {
      CommandsInterface movePet = null;
      String roomName = gameView.showMovePetDialog(model.getListOfRooms());
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
      if (movePet != null && movePet.isCommandSuccessful()) {
        checkAndPlayTurnForComputerPlayer();
        showGameOverMessage();
      }
    } else {
      gameView.showCommandOutcome("Game Over!", "No more turns allowed. Start a new game.", false);
    }
  }

  @Override
  public void checkAndPlayTurnForComputerPlayer() {
    if (!model.isGameOver()) {
      if (model.isCurrentPlayerComputer()) {
        CommandsInterface playTurnForComputerPlayer = new PlayTurnForComputerPlayer();
        playTurnForComputerPlayer.execute(model);
        gameView.showCommandOutcome("Computer Player Turn Details",
                playTurnForComputerPlayer.getCommandResult(),
                false);
        if (playTurnForComputerPlayer.isCommandSuccessful()) {
          showGameOverMessage();
        }
        model.updateWorldView(false);
        gameView.refresh();
      }
      if (model.isCurrentPlayerComputer()) {
        checkAndPlayTurnForComputerPlayer();
      }
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

}
