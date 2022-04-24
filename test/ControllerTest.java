import static org.junit.Assert.assertEquals;

import controller.Controller;
import controller.FeatureInterface;
import java.io.File;
import mockmodel.MockWorldComputer;
import mockmodel.MockWorldComputerCommandThrowsException;
import mockmodel.MockWorldComputerGameOver;
import mockmodel.MockWorldHuman;
import mockmodel.MockWorldHumanAttackSuccessful;
import mockmodel.MockWorldHumanAttackUnsucessful;
import mockmodel.MockWorldHumanGameOver;
import mockmodel.MockWorldHumanInvalidRoomClicked;
import mockmodel.MockWorldHumanPlayerClicked;
import mockview.MockMainGameView;
import mockview.MockMainGameViewCommandCancelled;
import mockview.MockMainGameViewCommandReturnEmpty;
import mockview.MockPreGameView;
import model.world.WorldInterface;
import org.junit.Test;
import view.DefaultGameViewInterface;
import view.MainGameViewInterface;

public class ControllerTest {

  @Test(expected = IllegalArgumentException.class)
  public void testUnsuccessfulConstructor1_InvalidPreGameView() {
    int maxNumberOfTurns = 23;
    String filePath = "res/FriendsWorld.txt";
    new Controller(null, filePath, maxNumberOfTurns);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUnsuccessfulConstructor1_InvalidFilePath_Null() {
    StringBuilder logView = new StringBuilder();
    int maxNumberOfTurns = 23;
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    new Controller(preGameView, null, maxNumberOfTurns);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUnsuccessfulConstructor1_InvalidFilePath_Empty() {
    StringBuilder logView = new StringBuilder();
    int maxNumberOfTurns = 23;
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    new Controller(preGameView, "", maxNumberOfTurns);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUnsuccessfulConstructor1_InvalidTurns_Negative() {
    StringBuilder logView = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    new Controller(preGameView, filePath, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUnsuccessfulConstructor1_InvalidTurns_Zero() {
    StringBuilder logView = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    new Controller(preGameView, filePath, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUnsuccessfulConstructor2_InvalidPreGameView() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    new Controller(null, gameView, model, filePath);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUnsuccessfulConstructor2_InvalidGameView() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    new Controller(preGameView, null, model, filePath);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUnsuccessfulConstructor2_InvalidModel() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    new Controller(preGameView, gameView, null, filePath);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUnsuccessfulConstructor2_InvalidFilePath_Null() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    new Controller(preGameView, gameView, model, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUnsuccessfulConstructor2_InvalidFilePath_Empty() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    new Controller(preGameView, gameView, model, "");
  }

  @Test
  public void testValidPreGameView() {
    StringBuilder logView = new StringBuilder();
    int maxNumberOfTurns = 5;
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    FeatureInterface c = new Controller(preGameView, filePath, maxNumberOfTurns);

    assertEquals("PreGameView constructor called\n"
            + "PreGameView addFeatures() called\n"
            + "PreGameView makeVisible() called\n", logView.toString());

  }

  @Test
  public void testPreGameView_StartWithDefaultConfiguration() {
    StringBuilder logView = new StringBuilder();
    int maxNumberOfTurns = 5;
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    FeatureInterface c = new Controller(preGameView, filePath, maxNumberOfTurns);

    c.playGame(null);

    assertEquals("PreGameView constructor called\n"
            + "PreGameView addFeatures() called\n"
            + "PreGameView makeVisible() called\n"
            + "PreGameView close() called\n", logView.toString());

  }

  @Test
  public void testPreGameView_StartWithNewConfiguration() {
    StringBuilder logView = new StringBuilder();
    int maxNumberOfTurns = 5;
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    FeatureInterface c = new Controller(preGameView, filePath, maxNumberOfTurns);

    c.playGame(new File(filePath));

    assertEquals("PreGameView constructor called\n"
            + "PreGameView addFeatures() called\n"
            + "PreGameView makeVisible() called\n"
            + "PreGameView close() called\n", logView.toString());

  }

  @Test
  public void testPreGameView_StartWithInvalidFile() {
    StringBuilder logView = new StringBuilder();
    int maxNumberOfTurns = 5;
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    FeatureInterface c = new Controller(preGameView, filePath, maxNumberOfTurns);

    c.playGame(new File("res/test.txt"));

    assertEquals("PreGameView constructor called\n"
            + "PreGameView addFeatures() called\n"
            + "PreGameView makeVisible() called\n"
            + "PreGameView showAttackTargetDialog() called\n"
            + "Parameters: ERROR, File not found, false\n", logView.toString());

  }

  @Test
  public void testPreGameView_QuitGame() {
    StringBuilder logView = new StringBuilder();
    int maxNumberOfTurns = 5;
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    FeatureInterface c = new Controller(preGameView, filePath, maxNumberOfTurns);

    c.quitGame();

    assertEquals("PreGameView constructor called\n"
            + "PreGameView addFeatures() called\n"
            + "PreGameView makeVisible() called\n"
            + "PreGameView close() called\n", logView.toString());

  }

  @Test
  public void testValidGameView() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    new Controller(preGameView, gameView, model, filePath);
  }

  @Test
  public void testGameView_StartWithDefaultConfiguration() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.playGame(null);

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "PreGameView showAttackTargetDialog() called\n"
            + "Parameters: ERROR, Number of turns cannot be non-positive., false\n",
            logView.toString());

  }

  @Test
  public void testGameView_StartWithNewConfiguration() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.playGame(new File(filePath));

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "PreGameView showAttackTargetDialog() called\n"
            + "Parameters: ERROR, Number of turns cannot be non-positive., false\n",
            logView.toString());

  }

  @Test
  public void testGameView_StartWithInvalidFile() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.playGame(new File("res/test.txt"));

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "PreGameView showAttackTargetDialog() called\n"
            + "Parameters: ERROR, File not found, false\n", logView.toString());

  }

  @Test
  public void testGameView_QuitGame() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.quitGame();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView close() called\n", logView.toString());

  }

  @Test
  public void testGameView_AddPlayer_NameNull() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    assertEquals("Player name cannot be empty", c.addPlayer(null, "testRoom", "2", false));

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n", logView.toString());
  }

  @Test
  public void testGameView_AddPlayer_NameEmpty() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    assertEquals("Player name cannot be empty", c.addPlayer("", "testRoom", "2", true));

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n", logView.toString());
  }

  @Test
  public void testGameView_AddPlayer_RoomNameNull() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    assertEquals("Room name cannot be empty", c.addPlayer("testPlayer", null, "2", false));

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n", logView.toString());
  }

  @Test
  public void testGameView_AddPlayer_RoomNameEmpty() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    assertEquals("Room name cannot be empty", c.addPlayer("testPlayer", "", "2", true));

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n", logView.toString());
  }

  @Test
  public void testGameView_AddPlayer_MaxNumWeapons_Negative() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    assertEquals("Maximum number of weapons should be -1 or positive",
            c.addPlayer("testPlayer", "testRoom", "-2", false));

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n", logView.toString());
  }

  @Test
  public void testGameView_AddPlayer_MaxNumWeapons_Zero() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    assertEquals("Maximum number of weapons should be -1 or positive",
            c.addPlayer("testPlayer", "testRoom", "0", true));

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n", logView.toString());
  }

  @Test
  public void testGameView_AddPlayer_MaxNumWeapons_NotInt() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    assertEquals("Maximum number of weapons must be a number",
            c.addPlayer("testPlayer", "testRoom", "s", true));

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n", logView.toString());
  }

  @Test
  public void testGameView_AddPlayer_SuccessfulHumanPlayer() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.addPlayer("testPlayer", "Ross's Museum", "2", false);

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model addPlayerToGame() called\n"
            + "Parameters: testPlayer, 2, false, Ross's Museum\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_AddPlayer_SuccessfulComputerPlayer() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.addPlayer("testPlayer", "Ross's Museum", "10", true);

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model addPlayerToGame() called\n"
            + "Parameters: testPlayer, 10, true, Ross's Museum\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_LookAround_BeforeGameOver() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.lookAround();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView refresh() called\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Look Around Details, 1234, true\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: true\n"
            + "Model lookAroundSpace() called\n"
            + "Model isGameOver() called\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isGameOver() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_LookAround_AfterGameOver() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHumanGameOver(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.lookAround();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Game Over!, No more turns allowed. Start a new game., false\n",
            logView.toString());

    assertEquals("Model isGameOver() called\n", logModel.toString());
  }

  @Test
  public void testGameView_HandleRoomClick_BeforeGameOver_NegativeRow() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.handleRoomClick(-2, 3);

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: ERROR, Coordinates cannot be negative, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_HandleRoomClick_BeforeGameOver_NegativeCol() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.handleRoomClick(2, -3);

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: ERROR, Coordinates cannot be negative, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_HandleRoomClick_BeforeGameOver_IsPlayer() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHumanPlayerClicked(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.handleRoomClick(2, 3);

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Current Player Details, 1234, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model isPlayerIconClicked() called\n"
            + "Parameters: 2, 3\n"
            + "Model getCurrentPlayerInformation() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_HandleRoomClick_BeforeGameOver_IsValidRoom() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.handleRoomClick(2, 3);

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Move Player Result, 1234, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model isPlayerIconClicked() called\n"
            + "Parameters: 2, 3\n"
            + "Model handleRoomClick() called\n"
            + "Parameters: 2, 3\n"
            + "Model isGameOver() called\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isGameOver() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_HandleRoomClick_BeforeGameOver_IsInvalidRoom() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHumanInvalidRoomClicked(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.handleRoomClick(2, 3);

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Move Player Result, Unable to move player. Invalid room selected., "
            + "false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model isPlayerIconClicked() called\n"
            + "Parameters: 2, 3\n"
            + "Model handleRoomClick() called\n"
            + "Parameters: 2, 3\n"
            + "Exception thrown - Invalid room selectedModel updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_HandleRoomClick_AfterGameOver() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHumanGameOver(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.handleRoomClick(2, 4);

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Game Over!, No more turns allowed. Start a new game., false\n",
            logView.toString());

    assertEquals("Model isGameOver() called\n", logModel.toString());
  }

  @Test
  public void testGameView_PickWeapon_BeforeGameOver_Cancelled() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameViewCommandCancelled(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.pickWeapon();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showPickWeaponDialog() called\n"
            + "Parameters: [1234]\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: CANCELLED, Pick weapon cancelled, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model getCurrentPlayerRoomWeapons() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_PickWeapon_BeforeGameOver_WeaponNameEmpty() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameViewCommandReturnEmpty(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.pickWeapon();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showPickWeaponDialog() called\n"
            + "Parameters: [1234]\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: ERROR, Weapon name cannot be empty, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model getCurrentPlayerRoomWeapons() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_PickWeapon_BeforeGameOver_Successful() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.pickWeapon();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showPickWeaponDialog() called\n"
            + "Parameters: [1234]\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Pick Weapon Result, 1234, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model getCurrentPlayerRoomWeapons() called\n"
            + "Model pickWeapon() called\n"
            + "Parameters: 1234\n"
            + "Model isGameOver() called\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isGameOver() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_PickWeapon_AfterGameOver() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHumanGameOver(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.pickWeapon();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Game Over!, No more turns allowed. Start a new game., false\n",
            logView.toString());

    assertEquals("Model isGameOver() called\n", logModel.toString());
  }

  @Test
  public void testGameView_AttackTarget_BeforeGameOver_Cancelled() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameViewCommandCancelled(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.attackTarget();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showAttackTargetDialog() called\n"
            + "Parameters: [1234]\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: CANCELLED, Attack target cancelled, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model getCurrentPlayerWeapons() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_AttackTarget_BeforeGameOver_WeaponNameEmpty() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameViewCommandReturnEmpty(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.attackTarget();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showAttackTargetDialog() called\n"
            + "Parameters: [1234]\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: ERROR, Weapon name cannot be empty, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model getCurrentPlayerWeapons() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_AttackTarget_BeforeGameOver_Successful() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHumanAttackSuccessful(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.attackTarget();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showAttackTargetDialog() called\n"
            + "Parameters: [1234]\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Attack Target Result, 1234, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model getCurrentPlayerWeapons() called\n"
            + "Model attackTargetPlayer() called\n"
            + "Parameters: 1234\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isGameOver() called\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isGameOver() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_AttackTarget_BeforeGameOver_Unsuccessful() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHumanAttackUnsucessful(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.attackTarget();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showAttackTargetDialog() called\n"
            + "Parameters: [1234]\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Attack Target Result, Unable to attack target. "
            + "Weapon name is null/empty., false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model getCurrentPlayerWeapons() called\n"
            + "Model attackTargetPlayer() called\n"
            + "Parameters: 1234\n"
            + "Exception thrown - Weapon name is null/empty\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_AttackTarget_AfterGameOver() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHumanGameOver(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.attackTarget();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Game Over!, No more turns allowed. Start a new game., false\n",
            logView.toString());

    assertEquals("Model isGameOver() called\n", logModel.toString());
  }

  @Test
  public void testGameView_MovePet_BeforeGameOver_Cancelled() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameViewCommandCancelled(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.movePet();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showMovePetDialog() called\n"
            + "Parameters: [1234]\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: CANCELLED, Move pet cancelled, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model getListOfRooms() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_MovePet_BeforeGameOver_RoomNameEmpty() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameViewCommandReturnEmpty(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.movePet();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showMovePetDialog() called\n"
            + "Parameters: [1234]\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: ERROR, Room name cannot be empty, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model getListOfRooms() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_MovePet_BeforeGameOver_Successful() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHuman(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.movePet();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showMovePetDialog() called\n"
            + "Parameters: [1234]\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Move Pet Result, 1234, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model getListOfRooms() called\n"
            + "Model movePet() called\n"
            + "Parameters: 1234\n"
            + "Model isGameOver() called\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isGameOver() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_MovePet_AfterGameOver() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldHumanGameOver(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.movePet();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Game Over!, No more turns allowed. Start a new game., false\n",
            logView.toString());

    assertEquals("Model isGameOver() called\n", logModel.toString());
  }

  @Test
  public void testGameView_ComputerRandomTurn_BeforeGameOver_Successful() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldComputer(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.checkAndPlayTurnForComputerPlayer();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showMovePetDialog() called\n"
            + "Parameters: [1234]\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Move Pet Result, 1234, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model getListOfRooms() called\n"
            + "Model movePet() called\n"
            + "Parameters: 1234\n"
            + "Model isGameOver() called\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isGameOver() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_ComputerRandomTurn_BeforeGameOver_UnSuccessful() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldComputerCommandThrowsException(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.checkAndPlayTurnForComputerPlayer();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n"
            + "MainGameView showMovePetDialog() called\n"
            + "Parameters: [1234]\n"
            + "MainGameView showCommandOutcome() called\n"
            + "Parameters: Move Pet Result, 1234, false\n"
            + "MainGameView refresh() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n"
            + "Model getListOfRooms() called\n"
            + "Model movePet() called\n"
            + "Parameters: 1234\n"
            + "Model isGameOver() called\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isCurrentPlayerComputer() called\n"
            + "Model isGameOver() called\n"
            + "Model updateWorldView() called\n"
            + "Parameters: false\n", logModel.toString());
  }

  @Test
  public void testGameView_ComputerRandomTurn_AfterGameOver() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    String filePath = "res/FriendsWorld.txt";
    DefaultGameViewInterface preGameView = new MockPreGameView(logView);
    MainGameViewInterface gameView = new MockMainGameView(logView);
    WorldInterface model = new MockWorldComputerGameOver(logModel, "1234");
    FeatureInterface c = new Controller(preGameView, gameView, model, filePath);

    c.checkAndPlayTurnForComputerPlayer();

    assertEquals("PreGameView constructor called\n"
            + "MainGameView constructor called\n"
            + "PreGameView close() called\n"
            + "MainGameView makeVisible() called\n"
            + "MainGameView addFeatures() called\n", logView.toString());

    assertEquals("Model isGameOver() called\n", logModel.toString());
  }


}
