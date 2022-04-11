package view;

import controller.FeatureInterface;

public interface GameViewInterface {

  void addFeatures(FeatureInterface features);

  void refresh();

  void makeVisible();

  void close();

}
