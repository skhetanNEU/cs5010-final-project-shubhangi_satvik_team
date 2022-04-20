package view;

public interface MainGameViewInterface extends DefaultGameViewInterface {

  void refresh();

  int showCommandOutcome(String title, String outcome, boolean isLookAround);

  String showPickWeaponDialog();

  String showMovePetDialog();

  String showAttackTargetDialog();
}
