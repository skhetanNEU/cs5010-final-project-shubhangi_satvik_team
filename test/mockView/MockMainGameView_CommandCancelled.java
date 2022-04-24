package mockView;

import java.util.List;

import controller.FeatureInterface;
import view.MainGameViewInterface;

public class MockMainGameView_CommandCancelled implements MainGameViewInterface {

  private StringBuilder out;

  public MockMainGameView_CommandCancelled(StringBuilder out) {
    this.out = out;
    this.out.append("MainGameView constructor called\n");
  }

  @Override
  public void addFeatures(FeatureInterface features) {
    this.out.append("MainGameView addFeatures() called\n");
  }

  @Override
  public void makeVisible() {
    this.out.append("MainGameView makeVisible() called\n");
  }

  @Override
  public void close() {
    this.out.append("MainGameView close() called\n");
  }

  @Override
  public void refresh() {
    this.out.append("MainGameView refresh() called\n");
  }

  @Override
  public void showCommandOutcome(String title, String outcome, boolean isLookAround) {
    this.out.append("MainGameView showCommandOutcome() called\n");
    this.out.append("Parameters: ")
            .append(title).append(", ")
            .append(outcome).append(", ")
            .append(isLookAround).append("\n");
  }

  @Override
  public String showPickWeaponDialog(List<String> roomWeapons) {
    this.out.append("MainGameView showPickWeaponDialog() called\n");
    this.out.append("Parameters: ").append(roomWeapons).append("\n");
    return null;
  }

  @Override
  public String showMovePetDialog(List<String> roomList) {
    this.out.append("MainGameView showMovePetDialog() called\n");
    this.out.append("Parameters: ").append(roomList).append("\n");
    return null;
  }

  @Override
  public String showAttackTargetDialog(List<String> playerWeapons) {
    this.out.append("MainGameView showAttackTargetDialog() called\n");
    this.out.append("Parameters: ").append(playerWeapons).append("\n");
    return null;
  }
}
