package mockview;

import controller.FeatureInterface;
import java.util.List;
import view.MainGameViewInterface;

/**
 * Mock class for the main game view.
 */
public class MockMainGameView implements MainGameViewInterface {

  private StringBuilder out;

  /**
   * Constructor for setting up the mock main game view.
   * @param out StringBuilder for appending the actions.
   */
  public MockMainGameView(StringBuilder out) {
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
    return "1234";
  }

  @Override
  public String showMovePetDialog(List<String> roomList) {
    this.out.append("MainGameView showMovePetDialog() called\n");
    this.out.append("Parameters: ").append(roomList).append("\n");
    return "1234";
  }

  @Override
  public String showAttackTargetDialog(List<String> playerWeapons) {
    this.out.append("MainGameView showAttackTargetDialog() called\n");
    this.out.append("Parameters: ").append(playerWeapons).append("\n");
    return "1234";
  }
}
