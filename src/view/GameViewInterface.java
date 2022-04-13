package view;

import java.util.List;

public interface GameViewInterface extends PreGameViewInterface {
  void refresh(boolean isLookAround);

  int showCommandOutcome(String title, String outcome);

  String showPickWeaponDialog();

  String showMovePetDialog();

  String showAttackTargetDialog();
}
