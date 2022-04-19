import controller.Controller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import view.PreGameViewImpl;
import view.PreGameViewInterface;

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
      FileReader worldConfiguration = new FileReader(filePath);

      PreGameViewInterface preGameView = new PreGameViewImpl();
      new Controller(preGameView, worldConfiguration, maxNumberOfTurns);

    } catch (IllegalStateException ise) {
      System.out.println("ERROR: Cannot create the world due to incorrect file.");
    } catch (FileNotFoundException fnf) {
      System.out.println("ERROR: Specified world configuration file not found.");
    } catch (NumberFormatException nfe) {
      System.out.println("ERROR: Number of turns must be a number.");
    } catch (IllegalArgumentException iae) {
      System.out.println(iae.getMessage());
    }

  }
}
