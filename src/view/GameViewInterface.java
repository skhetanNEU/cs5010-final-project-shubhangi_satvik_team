package view;

import controller.GUIControllerInterface;

public interface GameViewInterface {

  void addClickListener(GUIControllerInterface listener);

  void setKeyBoardListeners(GUIControllerInterface controller);

  void makeVisible();

  void close();

}
