package view;

import java.util.List;

public interface GameViewInterface extends PreGameViewInterface {

  void refresh();

  int showCommandOutcome(String title, String outcome, boolean isLookAround);

  String showPickWeaponDialog();

  String showMovePetDialog();

  String showAttackTargetDialog();
}
