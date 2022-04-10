package view;

import java.awt.event.ActionListener;

public interface PreGameViewInterface {

  void setCommandButtonListener(ActionListener actionEvent);

  void makeVisible();

  void close();
}
