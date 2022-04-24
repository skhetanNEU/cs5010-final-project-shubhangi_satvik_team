import controller.Controller;
import view.DefaultGameViewInterface;
import view.PreGameView;

/**
 * This is a driver class that takes a specification file name as an argument
 * and maximum number of turns. It sets up the welcome screen and sets up the controller.
 */
public class Driver {

  /**
   * Driver class to setup the world and try out functionalities offered by the
   * game.
   *
   * @param args One argument that represents the world specification file name and other
   *             representing the maximum number of turns in the game.
   */
  public static void main(String[] args) {

    try {

      if (args.length != 2) {
        throw new IllegalArgumentException("ERROR: Invalid number of arguments passed.");
      }

      String filePath = args[0];
      String numberOfTurns = args[1];

      int maxNumberOfTurns = Integer.parseInt(numberOfTurns);

      DefaultGameViewInterface preGameView = new PreGameView();
      new Controller(preGameView, filePath, maxNumberOfTurns);

    } catch (IllegalStateException ise) {
      System.out.println("ERROR: Cannot create the world due to incorrect file.");
    } catch (NumberFormatException nfe) {
      System.out.println("ERROR: Number of turns must be a number.");
    } catch (IllegalArgumentException iae) {
      System.out.println(iae.getMessage());
    }

  }
}
