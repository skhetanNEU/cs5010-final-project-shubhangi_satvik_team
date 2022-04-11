package view;

import controller.FeatureInterface;

public interface GameViewInterface {

  void addFeatures(FeatureInterface features);

  void refresh(boolean isLookAround);

  void makeVisible();

  void close();

}
