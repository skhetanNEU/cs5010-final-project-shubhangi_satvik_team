import controller.GUIControllerImpl;
import view.PreGameViewImpl;
import view.PreGameViewInterface;

public class Driver {
  public static void main(String[] args) {
    PreGameViewInterface preGameView = new PreGameViewImpl();
    new GUIControllerImpl(preGameView);
  }
}
