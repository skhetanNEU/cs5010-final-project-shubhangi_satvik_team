import controller.Controller;

import java.io.InputStreamReader;
import view.PreGameView;
import view.DefaultGameViewInterface;

public class Driver {
  public static void main(String[] args) {

    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;

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
