package view;

import controller.FeatureInterface;

public interface GameViewInterface {

  void addFeatures(FeatureInterface features);

  void makeVisible();

  void close();

}
