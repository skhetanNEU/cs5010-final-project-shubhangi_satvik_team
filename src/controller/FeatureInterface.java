package controller;

import java.io.File;

public interface FeatureInterface {
  void quitGame();

  void playGame(File file);

  void handleRoomClick(int row, int col);

  String getPlayerDescription();

  String movePlayer(String roomName);

  String pickWeapon(String weaponName);

  String lookAround();

  String attackTarget(String weaponName);

  String movePet(String roomName);
}
