package view;

import controller.FeatureInterface;

public interface PreGameViewInterface {

  void addFeatures(FeatureInterface features);

  void makeVisible();

  void close();
}
