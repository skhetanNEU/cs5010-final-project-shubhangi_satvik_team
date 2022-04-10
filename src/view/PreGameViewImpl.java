package view;

import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class PreGameViewImpl extends JFrame implements PreGameViewInterface {

  public PreGameViewImpl() {
    // UI code
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {
    // buttonName.addActionListener(actionEvent);
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
