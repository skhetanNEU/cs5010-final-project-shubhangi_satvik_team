package mockview;

import controller.FeatureInterface;
import view.DefaultGameViewInterface;

public class MockPreGameView implements DefaultGameViewInterface {

  private StringBuilder out;

  public MockPreGameView(StringBuilder out) {
    this.out = out;
    this.out.append("PreGameView constructor called\n");
  }

  @Override
  public void addFeatures(FeatureInterface features) {
    this.out.append("PreGameView addFeatures() called\n");
  }

  @Override
  public void makeVisible() {
    this.out.append("PreGameView makeVisible() called\n");
  }

  @Override
  public void close() {
    this.out.append("PreGameView close() called\n");
  }

  @Override
  public void showCommandOutcome(String title, String outcome, boolean isLookAround) {
    this.out.append("PreGameView showAttackTargetDialog() called\n");
    this.out.append("Parameters: ")
            .append(title).append(", ")
            .append(outcome).append(", ")
            .append(isLookAround).append("\n");
  }
}
