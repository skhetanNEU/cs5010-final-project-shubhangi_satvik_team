import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import model.players.PlayerImpl;
import model.players.PlayerInterface;
import model.room.RoomImpl;
import model.room.RoomInterface;
import model.weapon.WeaponImpl;
import model.weapon.WeaponInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test class for the PlayerImpl class.
 *
 */
public class PlayerImplTest {

  private RoomInterface room0;
  private RoomInterface room1;
  private RoomInterface room2;

  /**
   * Method to initialize the variables for Rooms used in the test cases before they are run.
   */
  @Before
  public void setUp() {
    room0 = new RoomImpl(0, "Armory", new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    room1 = new RoomImpl(1, "Billiard Room", new ArrayList<>(Arrays.asList(17, 14, 20, 20)));
    room2 = new RoomImpl(2, "Dining Hall", new ArrayList<>(Arrays.asList(7, 6, 15, 13)));
  }

  @Test
  public void testPlayerConstructor_Successful_Computer() {
    PlayerInterface t1 = new PlayerImpl(1, "Test Computer 1", true, 2, true, room0);
    String expectedString1 = "Name: Test Computer 1\nPlayer Type: Computer\nCurrent Room: "
            + "Armory\nWeapons: No weapons";
    assertEquals(expectedString1, t1.toString());

    PlayerInterface t2 = new PlayerImpl(2, "Test Computer 2", false, 0, true, room1);
    String expectedString2 = "Name: Test Computer 2\nPlayer Type: Computer\nCurrent Room: "
            + "Billiard Room\nWeapons: No weapons";
    assertEquals(expectedString2, t2.toString());
  }

  @Test
  public void testPlayerConstructor_Successful_Human() {
    PlayerInterface t1 = new PlayerImpl(1, "Test Human 1", true, 2, false, room0);
    String expectedString1 = "Name: Test Human 1\nPlayer Type: Human\nCurrent Room: Armory"
            + "\nWeapons: No weapons";
    assertEquals(expectedString1, t1.toString());

    PlayerInterface t2 = new PlayerImpl(2, "Test Human 2", false, 0, false, room2);
    String expectedString2 = "Name: Test Human 2\nPlayer Type: Human\nCurrent Room: Dining Hall"
            + "\nWeapons: No weapons";
    assertEquals(expectedString2, t2.toString());
  }

  @Test
  public void testPlayerConstructor_Unsuccessful_IdNegative() {
    assertThrows(IllegalArgumentException.class, () ->
            new PlayerImpl(-1, "Test Human", false, 0, false, room2));
    assertThrows(IllegalArgumentException.class, () ->
            new PlayerImpl(-10, "Test Computer", true, 2, true, room0));
  }

  @Test
  public void testPlayerConstructor_Unsuccessful_NameEmpty() {
    assertThrows(IllegalArgumentException.class, () ->
            new PlayerImpl(1, "", false, 0, false, room2));
    assertThrows(IllegalArgumentException.class, () ->
            new PlayerImpl(10, "", true, 2, true, room0));
  }

  @Test
  public void testPlayerConstructor_Unsuccessful_NameNull() {
    assertThrows(IllegalArgumentException.class, () ->
            new PlayerImpl(1, null, false, 0, false, room2));
    assertThrows(IllegalArgumentException.class, () ->
            new PlayerImpl(10, null, true, 2, true, room0));
  }

  @Test
  public void testPlayerConstructor_Unsuccessful_WeaponLimitNegative() {
    assertThrows(IllegalArgumentException.class, () ->
            new PlayerImpl(1, "Test Human", false, -10, false, room2));
    assertThrows(IllegalArgumentException.class, () ->
            new PlayerImpl(10, "Test Computer", false, -2, true, room0));
  }

  @Test
  public void testPlayerConstructor_Unsuccessful_RoomNull() {
    assertThrows(IllegalArgumentException.class, () ->
            new PlayerImpl(1, "Test Human", false, 0, false, null));
    assertThrows(IllegalArgumentException.class, () ->
            new PlayerImpl(10, "Test Computer", false, 0, true, null));
  }

  @Test
  public void testGetPlayerName_Human() {
    PlayerInterface t1 = new PlayerImpl(1, "Test Human", false, 0, false, room1);
    assertEquals("Test Human", t1.getPlayerName());
  }

  @Test
  public void testGetPlayerName_Computer() {
    PlayerInterface t1 = new PlayerImpl(1, "Test Computer", true, 2, true, room2);
    assertEquals("Test Computer", t1.getPlayerName());
  }

  @Test
  public void testIsComputerPlayer_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", true, 1, false, room1);
    assertFalse(p.isComputerPlayer());
  }

  @Test
  public void testIsComputerPlayer_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", true, 1, true, room0);
    assertTrue(p.isComputerPlayer());
  }

  @Test
  public void testGetPlayerRoomName_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", true, 1, false, room1);
    assertEquals("Billiard Room", p.getPlayerRoomName());
  }

  @Test
  public void testGetPlayerRoomName_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", true, 1, true, room0);
    assertEquals("Armory", p.getPlayerRoomName());
  }

  @Test
  public void testSetPlayerRoom_RoomNull_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", true, 1, false, room0);
    assertThrows(IllegalArgumentException.class, () -> p.setPlayerRoom(null));
  }

  @Test
  public void testSetPlayerRoom_RoomNull_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 0, true, room2);
    assertThrows(IllegalArgumentException.class, () -> p.setPlayerRoom(null));
  }

  @Test
  public void testSetPlayerRoom_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", true, 1, true, room0);
    p.setPlayerRoom(room2);
    String expected = "Name: Test Computer\n"
            + "Player Type: Computer\n"
            + "Current Room: Dining Hall\n"
            + "Weapons: No weapons";
    assertEquals(expected, p.toString());
  }

  @Test
  public void testSetPlayerRoom_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 0, false, room1);
    p.setPlayerRoom(room0);
    String expected = "Name: Test Human\n"
            + "Player Type: Human\n"
            + "Current Room: Armory\n"
            + "Weapons: No weapons";
    assertEquals(expected, p.toString());
  }

  @Test
  public void testAddWeaponToPlayer_Unsuccessful_WeaponNull_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", true, 1, false, room0);
    assertThrows(IllegalArgumentException.class, () -> p.addWeaponToPlayer(null));
  }

  @Test
  public void testAddWeaponToPlayer_Unsuccessful_WeaponNull_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", true, 1, true, room2);
    assertThrows(IllegalArgumentException.class, () -> p.addWeaponToPlayer(null));
  }

  @Test
  public void testAddWeaponToPlayer_Unsuccessful_WeaponLimitReached_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", true, 1, false, room0);
    WeaponInterface w1 = new WeaponImpl(2, "Stick", 4);
    WeaponInterface w2 = new WeaponImpl(3, "Ball", 2);
    assertThrows(IllegalArgumentException.class, () -> {
      p.addWeaponToPlayer(w1);
      p.addWeaponToPlayer(w2);
    });
  }

  @Test
  public void testAddWeaponToPlayer_Unsuccessful_WeaponLimitReached_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", true, 0, true, room0);
    WeaponInterface w = new WeaponImpl(2, "Stick", 4);
    assertThrows(IllegalArgumentException.class, () -> p.addWeaponToPlayer(w));
  }

  @Test
  public void testAddWeaponToPlayer_Successful_WithLimit_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", true, 2, false, room0);
    WeaponInterface w = new WeaponImpl(2, "Stick", 4);
    p.addWeaponToPlayer(w);
    String expected = "Name: Test Human\n"
            + "Player Type: Human\n"
            + "Current Room: Armory\n"
            + "Weapons: Stick with damage value 4";
    assertEquals(expected, p.toString());
  }

  @Test
  public void testAddWeaponToPlayer_Successful_WithLimit_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", true, 2, true, room0);
    WeaponInterface w = new WeaponImpl(2, "Stick", 4);
    p.addWeaponToPlayer(w);
    String expected = "Name: Test Computer\n"
            + "Player Type: Computer\n"
            + "Current Room: Armory\n"
            + "Weapons: Stick with damage value 4";
    assertEquals(expected, p.toString());
  }

  @Test
  public void testAddWeaponToPlayer_Successful_WithoutLimit_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 2, false, room0);
    WeaponInterface w = new WeaponImpl(2, "Stick", 4);
    p.addWeaponToPlayer(w);
    String expected = "Name: Test Human\n"
            + "Player Type: Human\n"
            + "Current Room: Armory\n"
            + "Weapons: Stick with damage value 4";
    assertEquals(expected, p.toString());
  }

  @Test
  public void testAddWeaponToPlayer_Successful_WithoutLimit_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room0);
    WeaponInterface w = new WeaponImpl(2, "Stick", 4);
    p.addWeaponToPlayer(w);
    String expected = "Name: Test Computer\n"
            + "Player Type: Computer\n"
            + "Current Room: Armory\n"
            + "Weapons: Stick with damage value 4";
    assertEquals(expected, p.toString());
  }

  @Test
  public void testRemoveWeaponFromPlayer_WeaponNameNull_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 2, false, room0);
    WeaponInterface w = new WeaponImpl(2, "Stick", 4);
    p.addWeaponToPlayer(w);
    assertThrows(IllegalArgumentException.class, () -> p.removeWeaponFromPlayer(null));
  }

  @Test
  public void testRemoveWeaponFromPlayer_WeaponNameNull_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room1);
    WeaponInterface w = new WeaponImpl(2, "Stick", 4);
    p.addWeaponToPlayer(w);
    assertThrows(IllegalArgumentException.class, () -> p.removeWeaponFromPlayer(null));
  }

  @Test
  public void testRemoveWeaponFromPlayer_WeaponNameEmpty_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 2, false, room0);
    WeaponInterface w = new WeaponImpl(2, "Stick", 4);
    p.addWeaponToPlayer(w);
    assertThrows(IllegalArgumentException.class, () -> p.removeWeaponFromPlayer(""));
  }

  @Test
  public void testRemoveWeaponFromPlayer_WeaponNameEmpty_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room1);
    WeaponInterface w = new WeaponImpl(2, "Stick", 4);
    p.addWeaponToPlayer(w);
    assertThrows(IllegalArgumentException.class, () -> p.removeWeaponFromPlayer(""));
  }

  @Test
  public void testRemoveWeaponFromPlayer_WeaponWithPlayer_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 2, false, room0);
    WeaponInterface w = new WeaponImpl(2, "Stick", 4);
    p.addWeaponToPlayer(w);
    assertEquals("Stick with damage value 4", p.removeWeaponFromPlayer("stick").toString());
  }

  @Test
  public void testRemoveWeaponFromPlayer_WeaponWithPlayer_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room1);
    WeaponInterface w = new WeaponImpl(2, "Ball", 4);
    p.addWeaponToPlayer(w);
    assertEquals("Ball with damage value 4", p.removeWeaponFromPlayer("ball").toString());
  }

  @Test
  public void testRemoveWeaponFromPlayer_WeaponIsPoke_HasWeapons_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 2, false, room0);
    WeaponInterface w = new WeaponImpl(2, "Stick", 4);
    p.addWeaponToPlayer(w);
    assertEquals("Poke with damage value 1", p.removeWeaponFromPlayer("poke").toString());
  }

  @Test
  public void testRemoveWeaponFromPlayer_WeaponIsPoke_NoWeapons_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 2, false, room0);
    assertEquals("Poke with damage value 1", p.removeWeaponFromPlayer("poke").toString());
  }

  @Test
  public void testRemoveWeaponFromPlayer_WeaponIsPoke_HasWeapons_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room0);
    WeaponInterface w = new WeaponImpl(2, "Stick", 4);
    p.addWeaponToPlayer(w);
    assertThrows(IllegalArgumentException.class, () -> p.removeWeaponFromPlayer("poke"));
  }

  @Test
  public void testRemoveWeaponFromPlayer_WeaponIsPoke_NoWeapons_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 2, true, room0);
    assertEquals("Poke with damage value 1", p.removeWeaponFromPlayer("poke").toString());
  }

  @Test
  public void testRemoveWeaponFromPlayer_NoWeaponsWithPlayer_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 2, false, room0);
    assertThrows(IllegalArgumentException.class, () -> p.removeWeaponFromPlayer("ball"));
  }

  @Test
  public void testRemoveWeaponFromPlayer_NoWeaponsWithPlayer_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room1);
    assertThrows(IllegalArgumentException.class, () -> p.removeWeaponFromPlayer("stick"));
  }

  @Test
  public void testRemoveWeaponFromPlayer_WeaponNotWithPlayer_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 2, false, room0);
    WeaponInterface w = new WeaponImpl(2, "Stick", 4);
    p.addWeaponToPlayer(w);
    assertThrows(IllegalArgumentException.class, () -> p.removeWeaponFromPlayer("ball"));
  }

  @Test
  public void testRemoveWeaponFromPlayer_WeaponNotWithPlayer_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room1);
    WeaponInterface w = new WeaponImpl(2, "Ball", 4);
    p.addWeaponToPlayer(w);
    assertThrows(IllegalArgumentException.class, () -> p.removeWeaponFromPlayer("stick"));
  }

  @Test
  public void testGetPlayerWeapons_WithPoke_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 2, false, room0);
    WeaponInterface w = new WeaponImpl(2, "Ball", 4);
    p.addWeaponToPlayer(w);
    assertEquals("Ball with damage value 4, Poke with damage value 1", p.getPlayerWeapons(true));
  }

  @Test
  public void testGetPlayerWeapons_WithPoke_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room1);
    WeaponInterface w = new WeaponImpl(2, "Ball", 4);
    p.addWeaponToPlayer(w);
    assertEquals("Ball with damage value 4", p.getPlayerWeapons(true));
  }

  @Test
  public void testGetPlayerWeapons_WithoutPoke_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 2, false, room0);
    WeaponInterface w = new WeaponImpl(2, "Ball", 4);
    p.addWeaponToPlayer(w);
    assertEquals("Ball with damage value 4", p.getPlayerWeapons(false));
  }

  @Test
  public void testGetPlayerWeapons_WithoutPoke_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room1);
    WeaponInterface w = new WeaponImpl(2, "Ball", 4);
    p.addWeaponToPlayer(w);
    assertEquals("Ball with damage value 4", p.getPlayerWeapons(false));
  }


  @Test
  public void testGetPlayerWeapons_ZeroWeapons() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room1);
    assertEquals("No weapons", p.getPlayerWeapons(false));
  }

  @Test
  public void testGetPlayerWeapons_OneWeapons() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 2, false, room0);
    WeaponInterface w = new WeaponImpl(2, "Ball", 4);
    p.addWeaponToPlayer(w);
    assertEquals("Ball with damage value 4", p.getPlayerWeapons(false));
  }

  @Test
  public void testGetPlayerWeapons_MoreThanOneWeapons() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room1);
    WeaponInterface w1 = new WeaponImpl(2, "Ball", 4);
    WeaponInterface w2 = new WeaponImpl(2, "Stick", 2);
    p.addWeaponToPlayer(w1);
    p.addWeaponToPlayer(w2);
    assertEquals("Ball with damage value 4, Stick with damage value 2", p.getPlayerWeapons(false));
  }

  @Test
  public void testGetPlayerMaxDamageWeapon_NoWeapons_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 2, false, room0);
    assertEquals("Poke", p.getPlayerMaxDamageWeapon());
  }

  @Test
  public void testGetPlayerMaxDamageWeapon_NoWeapons_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room1);
    assertEquals("Poke", p.getPlayerMaxDamageWeapon());
  }

  @Test
  public void testGetPlayerMaxDamageWeapon_HasWeapons_Human() {
    PlayerInterface p = new PlayerImpl(1, "Test Human", false, 2, false, room0);
    WeaponInterface w1 = new WeaponImpl(2, "Ball", 2);
    WeaponInterface w2 = new WeaponImpl(3, "Stick", 4);
    p.addWeaponToPlayer(w1);
    p.addWeaponToPlayer(w2);
    assertEquals("Stick", p.getPlayerMaxDamageWeapon());
  }

  @Test
  public void testGetPlayerMaxDamageWeapon_HasWeapons_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room1);
    WeaponInterface w1 = new WeaponImpl(1, "Ball", 4);
    WeaponInterface w2 = new WeaponImpl(2, "Stick", 4);
    p.addWeaponToPlayer(w1);
    p.addWeaponToPlayer(w2);
    assertEquals("Ball", p.getPlayerMaxDamageWeapon());
  }

  @Test
  public void testToString_Human() {
    PlayerInterface p = new PlayerImpl(2, "Test Human", false, 1, false, room0);
    String expected = "Name: Test Human\n"
            + "Player Type: Human\n"
            + "Current Room: Armory\n"
            + "Weapons: No weapons";
    assertEquals(expected, p.toString());
  }

  @Test
  public void testToString_Computer() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", true, 1, true, room2);
    String expected = "Name: Test Computer\n"
            + "Player Type: Computer\n"
            + "Current Room: Dining Hall\n"
            + "Weapons: No weapons";
    assertEquals(expected, p.toString());
  }

  @Test
  public void testToString_ZeroWeapon() {
    PlayerInterface p = new PlayerImpl(2, "Test Human", false, 1, false, room0);
    String expected = "Name: Test Human\n"
            + "Player Type: Human\n"
            + "Current Room: Armory\n"
            + "Weapons: No weapons";
    assertEquals(expected, p.toString());
  }

  @Test
  public void testToString_OneWeapon() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room1);
    WeaponInterface w = new WeaponImpl(2, "Stick", 4);
    p.addWeaponToPlayer(w);
    String expected = "Name: Test Computer\n"
            + "Player Type: Computer\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: Stick with damage value 4";
    assertEquals(expected, p.toString());
  }

  @Test
  public void testToString_MoreThanOneWeapon() {
    PlayerInterface p = new PlayerImpl(1, "Test Computer", false, 2, true, room2);
    WeaponInterface w1 = new WeaponImpl(2, "Stick", 4);
    WeaponInterface w2 = new WeaponImpl(3, "RAM", 2);
    p.addWeaponToPlayer(w1);
    p.addWeaponToPlayer(w2);
    String expected = "Name: Test Computer\n"
            + "Player Type: Computer\n"
            + "Current Room: Dining Hall\n"
            + "Weapons: Stick with damage value 4, RAM with damage value 2";
    assertEquals(expected, p.toString());
  }


}