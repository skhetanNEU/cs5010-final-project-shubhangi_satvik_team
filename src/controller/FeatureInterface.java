package controller;

import java.io.File;

public interface FeatureInterface {
  void quitGame();

  void playGame(File file);

  void getPlayerDescription();

  void movePlayer(int row, int col);

  void pickWeapon(String weaponName);

  void lookAround();

  void attackTarget(String weaponName);

  void movePet(String roomName);

  void addPlayer(String playerName, String roomName, String maxNumberOfWeapons,
                 boolean isComputerPlayer);
}
