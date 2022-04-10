package controller;

public interface GUIControllerInterface {

  void playGame();

  void handleRoomClick(int row, int col);

  void restartGame();

  void newGame();

  String getPlayerDescription();

  String movePlayer(String roomName);

  String pickWeapon(String weaponName);

  String lookAround();

  String attackTarget(String weaponName);

  String movePet(String roomName);
}
