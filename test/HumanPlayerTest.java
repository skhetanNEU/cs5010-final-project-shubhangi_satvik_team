import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import model.players.HumanPlayer;
import model.players.PlayerInterface;
import model.room.RoomImpl;
import model.room.RoomInterface;
import org.junit.Before;
import org.junit.Test;

public class HumanPlayerTest {

  private RoomInterface room0;
  private RoomInterface room1;
  private RoomInterface room2;

  /**
   * Method to initialize the variables for Rooms used in the test cases before they are run.
   */
  @Before
  public void setUp() throws FileNotFoundException {
    room0 = new RoomImpl(0, "Armory", new ArrayList<>(Arrays.asList(22, 19, 23, 26)));
    room1 = new RoomImpl(1, "Billiard Room", new ArrayList<>(Arrays.asList(16, 21, 21, 28)));
    room2 = new RoomImpl(2, "Dining Hall", new ArrayList<>(Arrays.asList(12, 11, 21, 20)));
    room2.addWeaponToRoom(1, "Ball", 4);

  }

  @Test
  public void testPlayerConstructor_Successful_Human() {
    PlayerInterface t1 = new HumanPlayer("Test Human 1", 2, room0);
    String expectedString1 = "Name: Test Human 1\nCurrent Room: Armory"
            + "\nWeapons: -";
    assertEquals(expectedString1, t1.toString());

    PlayerInterface t2 = new HumanPlayer("Test Human 2", 2, room2);
    String expectedString2 = "Name: Test Human 2\nCurrent Room: Dining Hall"
            + "\nWeapons: -";
    assertEquals(expectedString2, t2.toString());
  }

  @Test
  public void testPlayerConstructor_Unsuccessful_NameEmpty() {
    assertThrows(IllegalArgumentException.class, () ->
            new HumanPlayer("", 2, room0));
  }

  @Test
  public void testPlayerConstructor_Unsuccessful_NameNull() {
    assertThrows(IllegalArgumentException.class, () ->
            new HumanPlayer(null, 2, room0));
    ;
  }

  @Test
  public void testPlayerConstructor_Unsuccessful_WeaponLimitNegative() {
    assertThrows(IllegalArgumentException.class, () ->
            new HumanPlayer("Test Human 1", -2, room0));
  }

  @Test
  public void testPlayerConstructor_Unsuccessful_WeaponLimitZero() {
    assertThrows(IllegalArgumentException.class, () ->
            new HumanPlayer("Test Human 1", 0, room0));
  }

  @Test
  public void testPlayerConstructor_Unsuccessful_RoomNull() {
    assertThrows(IllegalArgumentException.class, () ->
            new HumanPlayer("Test Human 1", 2, null));
  }

  @Test
  public void testGetPlayerName_Human() {
    PlayerInterface t1 = new HumanPlayer("Test Human 1", 2, room0);
    assertEquals("Test Human 1", t1.getPlayerName());
  }

  @Test
  public void testGetPlayerRoomName_Human() {
    PlayerInterface p = new HumanPlayer("Test Human 1", 2, room1);
    assertEquals("Billiard Room", p.getPlayerRoomName());
  }

  @Test
  public void testToString_Human() {
    PlayerInterface p = new HumanPlayer("Test Human", 1, room0);
    String expected = "Name: Test Human\n"
            + "Current Room: Armory\n"
            + "Weapons: -";
    assertEquals(expected, p.toString());
  }


  @Test
  public void testToString_ZeroWeapon() {
    PlayerInterface p = new HumanPlayer("Test Human", 1, room0);
    String expected = "Name: Test Human\n"
            + "Current Room: Armory\n"
            + "Weapons: -";
    assertEquals(expected, p.toString());
  }

  @Test
  public void testToString_OneWeapon() {
    PlayerInterface p = new HumanPlayer("Test Human", 1, room0);
    room0.addWeaponToRoom(2, "Stick", 4);
    p.pickWeapon("Stick");
    String expected = "Name: Test Human\n"
            + "Current Room: Armory\n"
            + "Weapons: Stick (Damage: 4), Poke (Damage: 1)";
    assertEquals(expected, p.toString());
  }

  @Test
  public void testToString_MoreThanOneWeapon() {
    PlayerInterface p = new HumanPlayer("Test Human", 2, room0);
    room0.addWeaponToRoom(2, "Stick", 4);
    room0.addWeaponToRoom(3, "RAM", 2);
    p.pickWeapon("Stick");
    p.pickWeapon("RAM");
    String expected = "Name: Test Human\n"
            + "Current Room: Armory\n"
            + "Weapons: Stick (Damage: 4), RAM (Damage: 2), Poke (Damage: 1)";
    assertEquals(expected, p.toString());
  }
}
