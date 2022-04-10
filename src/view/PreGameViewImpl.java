package view;

import controller.FeatureInterface;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class PreGameViewImpl extends JFrame implements PreGameViewInterface {

  public PreGameViewImpl() {
    // UI code
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void addFeatures(FeatureInterface features) {
    // buttonName.addActionListener(l-> features.methodName());
  }

  @Override
  public void makeVisible() {
    // this.setVisible(true);
  }

  @Override
  public void close() {
    // this.setVisible(false);
  }
}
