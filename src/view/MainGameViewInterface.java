package view;

import java.util.List;

public interface MainGameViewInterface extends DefaultGameViewInterface {

  void refresh();

  int showCommandOutcome(String title, String outcome, boolean isLookAround);

  String showPickWeaponDialog(List<String> roomWeapons);

  String showMovePetDialog(List<String> roomList);

  String showAttackTargetDialog(List<String> playerWeapons);
}
