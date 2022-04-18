import controller.Controller;
import view.PreGameViewImpl;
import view.PreGameViewInterface;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Driver {
  public static void main(String[] args) {

    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;

    try {
      String filePath = args[0];
      String numberOfTurns = args[1];
      int maxNumberOfTurns = Integer.parseInt(numberOfTurns);
      FileReader worldConfiguration = new FileReader(filePath);

      PreGameViewInterface preGameView = new PreGameViewImpl();
      new Controller(preGameView, worldConfiguration, maxNumberOfTurns);
    } catch (IllegalStateException illegalStateException) {
      System.out.println("Cannot create the world due to incorrect file");
    } catch (FileNotFoundException fileNotFoundException) {
      System.out.println("Specified world configuration file not found");
    } catch (NumberFormatException numberFormatException){
      System.out.println("Invalid number of turns");
    }

  }
}
