package view;

import controller.FeatureInterface;

public interface DefaultGameViewInterface {

  void addFeatures(FeatureInterface features);

  void makeVisible();

  void close();
}
