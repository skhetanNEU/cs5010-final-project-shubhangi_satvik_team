package controller;

import java.io.File;

public interface FeatureInterface{

  void playGame(File file);

  void startPlaying();

  void quitGame();

  String addPlayer(String playerName, String roomName, String maxNumberOfWeapons,
                   boolean isComputerPlayer);

  void lookAround();

  void handleRoomClick(int row, int col);

  void pickWeapon();

  void attackTarget();

  void movePet();
}