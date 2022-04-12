package controller;

import java.io.File;

public interface FeatureInterface {

  void refreshGame(boolean isLookAround);

  void quitGame();

  void playGame(File file);

  String getPlayerDescription();

  String movePlayer(int row, int col);

  String pickWeapon(String weaponName);

  String lookAround();

  String attackTarget(String weaponName);

  String movePet(String roomName);

  String addPlayer(String playerName, String roomName, String maxNumberOfWeapons,
                 boolean isComputerPlayer);
}
