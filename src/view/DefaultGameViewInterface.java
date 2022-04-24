package view;

import controller.FeatureInterface;

/**
 * Interface for the game view functionalities.
 */
public interface DefaultGameViewInterface {

  /**
   * Add features and set the event listeners for the functionalities offered by the game.
   * @param features Features available for the game
   */
  void addFeatures(FeatureInterface features);

  /**
   * Make the view visible.
   */
  void makeVisible();

  /**
   * Close the view.
   */
  void close();

  /**
   * Showing the outcome of an operation in the view.
   * @param title Title of the dialog.
   * @param outcome Result of the operation to be displayed in the dialog.
   * @param isLookAround Specifies whether the operation was a lookaround operation.
   */
  void showCommandOutcome(String title, String outcome, boolean isLookAround);
}
