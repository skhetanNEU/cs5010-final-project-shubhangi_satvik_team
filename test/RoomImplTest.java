import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.pet.PetImpl;
import model.pet.PetInterface;
import model.players.ComputerPlayer;
import model.players.HumanPlayer;
import model.players.PlayerInterface;
import model.random.RandomClass;
import model.random.RandomGenerator;
import model.room.RoomImpl;
import model.room.RoomInterface;
import model.target.TargetPlayerImpl;
import model.target.TargetPlayerInterface;
import model.weapon.WeaponImpl;
import model.weapon.WeaponInterface;
import org.junit.Before;
import org.junit.Test;


/**
 * A JUnit test class for the RoomImpl class.
 */
public class RoomImplTest {

  private RoomInterface room0;
  private RoomInterface room1;
  private RoomInterface room2;
  private PlayerInterface humanPlayer1;
  private PlayerInterface humanPlayer2;
  private PlayerInterface computerPlayer1;
  private PlayerInterface computerPlayer2;
  private WeaponInterface weapon1;
  private WeaponInterface weapon3;

  /**
   * Method to initialize rooms to be used in the test cases before they are run.
   */
  @Before
  public void setUp() {
    RandomGenerator random = new RandomClass();
    room0 = new RoomImpl(0, "Armory", new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    room1 = new RoomImpl(1, "Billiard Room", new ArrayList<>(Arrays.asList(17, 14, 20, 20)));
    room2 = new RoomImpl(2, "Dining Hall", new ArrayList<>(Arrays.asList(7, 6, 15, 13)));
    TargetPlayerInterface targetPlayer = new TargetPlayerImpl("Dr. Lucky", 10, room0);
    PetInterface targetPet = new PetImpl("Fortune Cat", room0);
    humanPlayer1 = new HumanPlayer("humanPlayer1", 2, room0);
    humanPlayer2 = new HumanPlayer("humanPlayer2", -1, room1);
    computerPlayer1 = new ComputerPlayer("computerPlayer1", 3, room0, random);
    computerPlayer2 = new ComputerPlayer("computerPlayer2", -1, room2, random);
    weapon1 = new WeaponImpl(1, "Blade", 2);
    weapon3 = new WeaponImpl(3, "Rat Poison", 3);
  }

  @Test
  public void testRoomConstructor_Successful() {
    RoomInterface room = new RoomImpl(0, "Armory", new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    String expected = "Name: Armory\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No";
    assertEquals(expected, room.toString());
  }

  @Test
  public void testRoomConstructor_Unsuccessful_RoomIdNegative() {
    assertThrows(IllegalArgumentException.class, () -> new RoomImpl(-2, "Dining Hall",
            new ArrayList<>(Arrays.asList(7, 6, 15, 13))));
  }

  @Test
  public void testRoomConstructor_Unsuccessful_RoomNameEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new RoomImpl(2, "",
            new ArrayList<>(Arrays.asList(7, 6, 15, 13))));
  }

  @Test
  public void testRoomConstructor_Unsuccessful_RoomNameNull() {
    assertThrows(IllegalArgumentException.class, () -> new RoomImpl(2, null,
            new ArrayList<>(Arrays.asList(7, 6, 15, 13))));
  }

  @Test
  public void testRoomConstructor_Unsuccessful_RoomCoordinateRow1Negative() {
    assertThrows(IllegalArgumentException.class, () -> new RoomImpl(2, "Dining Hall",
            new ArrayList<>(Arrays.asList(-7, 6, 15, 13))));
  }

  @Test
  public void testRoomConstructor_Unsuccessful_RoomCoordinateCol1Negative() {
    assertThrows(IllegalArgumentException.class, () -> new RoomImpl(2, "Dining Hall",
            new ArrayList<>(Arrays.asList(7, -6, 15, 13))));
  }

  @Test
  public void testRoomConstructor_Unsuccessful_RoomCoordinateRow2Negative() {
    assertThrows(IllegalArgumentException.class, () -> new RoomImpl(2, "Dining Hall",
            new ArrayList<>(Arrays.asList(7, 6, -15, 13))));
  }

  @Test
  public void testRoomConstructor_Unsuccessful_RoomCoordinateCol2Negative() {
    assertThrows(IllegalArgumentException.class, () -> new RoomImpl(2, "Dining Hall",
            new ArrayList<>(Arrays.asList(7, 6, 15, -13))));
  }

  @Test
  public void testRoomConstructor_Unsuccessful_RoomCoordinateRow1GreaterThanRow2() {
    assertThrows(IllegalArgumentException.class, () -> new RoomImpl(2, "Dining Hall",
            new ArrayList<>(Arrays.asList(7, 6, 6, 13))));
  }

  @Test
  public void testRoomConstructor_Unsuccessful_RoomCoordinateCol1GreaterThanCol2() {
    assertThrows(IllegalArgumentException.class, () -> new RoomImpl(2, "Dining Hall",
            new ArrayList<>(Arrays.asList(7, 6, 15, 2))));
  }

  @Test
  public void testRoomConstructor_Unsuccessful_RoomCoordinateRow1EqualsRow2() {
    assertThrows(IllegalArgumentException.class, () -> new RoomImpl(2, "Dining Hall",
            new ArrayList<>(Arrays.asList(7, 6, 7, 10))));
  }

  @Test
  public void testRoomConstructor_Unsuccessful_RoomCoordinateCol1EqualsCol2() {
    assertThrows(IllegalArgumentException.class, () -> new RoomImpl(2, "Dining Hall",
            new ArrayList<>(Arrays.asList(7, 6, 15, 6))));
  }

  @Test
  public void testGetRoomId_Tc1() {
    assertEquals(0, room0.getRoomId());
  }

  @Test
  public void testGetRoomId_Tc2() {
    assertEquals(1, room1.getRoomId());
  }

  @Test
  public void testGetRoomId_Tc3() {
    assertEquals(2, room2.getRoomId());
  }

  @Test
  public void testGetRoomName_Tc1() {
    assertEquals("Armory", room0.getRoomName());
  }

  @Test
  public void testGetRoomName_Tc2() {
    assertEquals("Billiard Room", room1.getRoomName());
  }

  @Test
  public void testGetRoomName_Tc3() {
    assertEquals("Dining Hall", room2.getRoomName());
  }

  @Test
  public void testGetRoomCoordinates_Tc1() {
    assertEquals(new ArrayList<>(Arrays.asList(14, 14, 16, 20)), room0.getRoomCoordinates());
  }

  @Test
  public void testGetRoomCoordinates_Tc12() {
    assertEquals(new ArrayList<>(Arrays.asList(17, 14, 20, 20)), room1.getRoomCoordinates());
  }

  @Test
  public void testGetRoomCoordinates_Tc13() {
    assertEquals(new ArrayList<>(Arrays.asList(7, 6, 15, 13)), room2.getRoomCoordinates());
  }

  @Test
  public void testIsTargetPlayerInRoom_Yes() {
    assertTrue(room0.isTargetPlayerInRoom());
  }

  @Test
  public void testIsTargetPlayerInRoom_No() {
    assertFalse(room1.isTargetPlayerInRoom());
  }

  @Test
  public void testIsPetInRoom_Yes() {
    assertTrue(room0.isPetInRoom());
  }

  @Test
  public void testIsPetInRoom_No() {
    assertFalse(room1.isPetInRoom());
  }

  @Test
  public void testGetRoomNeighbours_AllNeighbours_ZeroNeighbours() {
    assertEquals(List.of(), room0.getRoomNeighbours(true));
  }

  @Test
  public void testGetRoomNeighbours_AllNeighbours_OneNeighbours() {
    room1.setRoomNeighbours(new ArrayList<>(List.of(room0)));
    assertEquals(List.of("Armory"), room1.getRoomNeighbours(true));
  }

  @Test
  public void testGetRoomNeighbours_AllNeighbours_MoreThan1Neighbours() {
    room2.setRoomNeighbours(new ArrayList<>(Arrays.asList(room0, room1)));
    assertEquals(Arrays.asList("Armory", "Billiard Room"),
            room2.getRoomNeighbours(true));
  }

  @Test
  public void testGetRoomNeighbours_VisibleNeighbours() {
    room2.setRoomNeighbours(new ArrayList<>(Arrays.asList(room0, room1)));
    assertEquals(List.of("Billiard Room"), room2.getRoomNeighbours(false));
  }

  @Test
  public void testGetNeighboringRoom_RoomNameEmpty() {
    room2.setRoomNeighbours(new ArrayList<>(Arrays.asList(room0, room1)));
    assertThrows(IllegalArgumentException.class, () -> room2.getNeighboringRoom(""));
  }

  @Test
  public void testGetNeighboringRoom_RoomNameNull() {
    room2.setRoomNeighbours(new ArrayList<>(Arrays.asList(room0, room1)));
    assertThrows(IllegalArgumentException.class, () -> room2.getNeighboringRoom(null));
  }

  @Test
  public void testGetNeighboringRoom_NotNeighbour() {
    room2.setRoomNeighbours(new ArrayList<>(Arrays.asList(room0, room1)));
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> room2.getNeighboringRoom("TestRoom"));
    assertEquals("Desired room is not a neighbour of current room.", exception.getMessage());
  }

  @Test
  public void testGetNeighboringRoom_ValidNeighbour() {
    room2.setRoomNeighbours(new ArrayList<>(Arrays.asList(room0, room1)));
    assertEquals("Armory", room2.getNeighboringRoom("Armory").getRoomName());
  }

  @Test
  public void testGetAvailableWeapons_WithDamageValues_ZeroWeapons() {
    assertEquals(List.of(), room0.getAvailableWeapons(true));
  }

  @Test
  public void testGetAvailableWeapons_WithDamageValues_OneWeapon() {
    room2.addWeaponToRoom(1, "Plate", 3);
    assertEquals(List.of("Plate (Damage: 3)"),
            room2.getAvailableWeapons(true));
  }

  @Test
  public void testGetAvailableWeapons_WithDamageValues_MoreThanOneWeapon() {
    room0.addWeaponToRoom(9, "Billiard Cue", 1);
    room0.addWeaponToRoom(6, "Revolver", 4);
    assertEquals(Arrays.asList("Revolver (Damage: 4)", "Billiard Cue (Damage: 1)"),
            room0.getAvailableWeapons(true));
  }

  @Test
  public void testGetAvailableWeapons_WithoutDamageValues_ZeroWeapons() {
    assertEquals(List.of(), room0.getAvailableWeapons(false));
  }

  @Test
  public void testGetAvailableWeapons_WithoutDamageValues_OneWeapon() {
    room2.addWeaponToRoom(1, "Plate", 3);
    assertEquals(List.of("Plate"), room2.getAvailableWeapons(false));
  }

  @Test
  public void testGetAvailableWeapons_WithoutDamageValues_MoreThanOneWeapon() {
    room0.addWeaponToRoom(9, "Billiard Cue", 1);
    room0.addWeaponToRoom(6, "Revolver", 4);
    assertEquals(Arrays.asList("Revolver", "Billiard Cue"), room0.getAvailableWeapons(false));
  }

  @Test
  public void testGetWeaponByWeaponName_WeaponNameEmpty() {
    room0.addWeaponToRoom(1, "Blade", 2);
    assertThrows(IllegalArgumentException.class, () -> room0.getWeaponByWeaponName(""));
  }

  @Test
  public void testGetWeaponByWeaponName_WeaponNameNull() {
    room0.addWeaponToRoom(1, "Blade", 2);
    assertThrows(IllegalArgumentException.class, () -> room0.getWeaponByWeaponName(null));
  }

  @Test
  public void testGetWeaponByWeaponName_WeaponNotInSpace() {
    room0.addWeaponToRoom(1, "Blade", 2);
    assertThrows(IllegalArgumentException.class, () -> room0.getWeaponByWeaponName("Ball"));
  }

  @Test
  public void testGetWeaponByWeaponName_WeaponInSpace() {
    room0.addWeaponToRoom(1, "Blade", 2);
    assertEquals(weapon1, room0.getWeaponByWeaponName("Blade"));
  }

  @Test
  public void testGetNumberOfPlayersInRoom_ZeroPlayers() {
    assertEquals(0, room0.getNumberOfPlayersInRoom());
  }

  @Test
  public void testGetNumberOfPlayersInRoom_OnePlayer() {
    room1.addPlayerToRoom(humanPlayer1);
    assertEquals(1, room1.getNumberOfPlayersInRoom());
  }

  @Test
  public void testGetNumberOfPlayersInRoom_MoreThan1Players() {
    room2.addPlayerToRoom(humanPlayer2);
    room2.addPlayerToRoom(computerPlayer2);
    assertEquals(2, room2.getNumberOfPlayersInRoom());
  }

  @Test
  public void testGetNumberOfPlayersInNeighbouringRoom_NoNeighbours() {
    assertEquals(0, room0.getNumberOfPlayersInNeighbouringRoom(false));
    assertEquals(0, room0.getNumberOfPlayersInNeighbouringRoom(true));
  }

  @Test
  public void testGetNumberOfPlayersInNeighbouringRoom_RoomsHidden_Zero() {
    room0.addPlayerToRoom(humanPlayer1);
    room1.setRoomNeighbours(Arrays.asList(room0, room2));
    assertEquals(0, room1.getNumberOfPlayersInNeighbouringRoom(false));
  }

  @Test
  public void testGetNumberOfPlayersInNeighbouringRoom_RoomsHidden_MoreThanZero() {
    room2.addPlayerToRoom(humanPlayer1);
    room2.addPlayerToRoom(humanPlayer2);
    room1.setRoomNeighbours(Arrays.asList(room0, room2));
    assertEquals(2, room1.getNumberOfPlayersInNeighbouringRoom(false));
  }

  @Test
  public void testGetNumberOfPlayersInNeighbouringRoom_RoomsVisible_Zero() {
    room1.setRoomNeighbours(Arrays.asList(room0, room2));
    assertEquals(0, room1.getNumberOfPlayersInNeighbouringRoom(true));
  }

  @Test
  public void testGetNumberOfPlayersInNeighbouringRoom_RoomsVisible_MoreThanZero() {
    room2.addPlayerToRoom(humanPlayer1);
    room2.addPlayerToRoom(computerPlayer1);
    room0.addPlayerToRoom(humanPlayer2);
    room1.setRoomNeighbours(Arrays.asList(room0, room2));
    assertEquals(3, room1.getNumberOfPlayersInNeighbouringRoom(true));
  }

  @Test
  public void testUpdateTargetPlayerPresence_SetToTrue() {
    assertFalse(room1.isTargetPlayerInRoom());
    room1.updateTargetPlayerPresence(true);
    assertTrue(room1.isTargetPlayerInRoom());
  }

  @Test
  public void testUpdateTargetPlayerPresence_SetToFalse() {
    assertTrue(room0.isTargetPlayerInRoom());
    room0.updateTargetPlayerPresence(false);
    assertFalse(room0.isTargetPlayerInRoom());
  }

  @Test
  public void testUpdatePetPresence_SetToTrue() {
    assertFalse(room1.isPetInRoom());
    room1.updatePetPresence(true);
    assertTrue(room1.isPetInRoom());
  }

  @Test
  public void testUpdatePetPresence_SetToFalse() {
    assertTrue(room0.isPetInRoom());
    room0.updatePetPresence(false);
    assertFalse(room0.isPetInRoom());
  }

  @Test
  public void testSetRoomNeighbours_NeighboursNull() {
    RoomInterface room0 = new RoomImpl(0, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    room0.setRoomNeighbours(null);
    assertEquals(List.of(), room0.getRoomNeighbours(true));
  }

  @Test
  public void testSetRoomNeighbours_NeighboursEmpty() {
    room0.setRoomNeighbours(new ArrayList<>(0));
    assertEquals(List.of(), room0.getRoomNeighbours(true));
  }

  @Test
  public void testSetRoomNeighbours_AddOneNeighbour() {
    room1.setRoomNeighbours(new ArrayList<>(List.of(room0)));
    assertEquals(List.of("Armory"), room1.getRoomNeighbours(true));
  }

  @Test
  public void testSetRoomNeighbours_AddMoreThanOneNeighbour() {
    room2.setRoomNeighbours(new ArrayList<>(List.of(room0, room1)));
    assertEquals(Arrays.asList("Armory", "Billiard Room"),
            room2.getRoomNeighbours(true));
  }

  @Test
  public void testAddRoomWeapon_Unsuccessful_WeaponNameEmpty() {
    assertThrows(IllegalArgumentException.class, () -> room0.addWeaponToRoom(9, "", 1));
  }

  @Test
  public void testAddRoomWeapon_Unsuccessful_WeaponNameNull() {
    assertThrows(IllegalArgumentException.class, () -> room0.addWeaponToRoom(9, null, 1));
  }

  @Test
  public void testAddRoomWeapon_Unsuccessful_WeaponIdNegative() {
    assertThrows(IllegalArgumentException.class, () -> room0.addWeaponToRoom(-9, "Cue", 1));
  }

  @Test
  public void testAddRoomWeapon_Unsuccessful_WeaponDamageValueNegative() {
    assertThrows(IllegalArgumentException.class, () -> room0.addWeaponToRoom(9, "Cue", -1));
  }

  @Test
  public void testAddRoomWeapon_Unsuccessful_WeaponDamageValueZero() {
    assertThrows(IllegalArgumentException.class, () -> room0.addWeaponToRoom(9, "Cue", 0));
  }

  @Test
  public void testAddRoomWeapon_Successful_WeaponExists() {
    room2.addWeaponToRoom(1, "Plate", 3);
    room2.addWeaponToRoom(1, "Plate", 3);
    assertEquals("[Plate (Damage: 3)]", room2.getAvailableWeapons(true).toString());
  }

  @Test
  public void testAddRoomWeapon_Successful_OneWeapon() {
    room2.addWeaponToRoom(1, "Plate", 3);
    assertEquals(List.of("Plate (Damage: 3)"), room2.getAvailableWeapons(true));
  }

  @Test
  public void testAddRoomWeapon_Successful_MoreThan1Weapon() {
    room0.addWeaponToRoom(9, "Billiard Cue", 1);
    room0.addWeaponToRoom(6, "Revolver", 4);
    assertEquals(Arrays.asList("Revolver (Damage: 4)", "Billiard Cue (Damage: 1)"),
            room0.getAvailableWeapons(true));
  }

  @Test
  public void testRemoveWeaponFromRoom_Unsuccessful_WeaponNull() {
    room0.addWeaponToRoom(9, "Cue", 10);
    assertThrows(IllegalArgumentException.class, () -> room0.removeWeaponFromRoom(null));
  }

  @Test
  public void testRemoveWeaponFromRoom_Unsuccessful_WeaponDoesNotExist() {
    room0.addWeaponToRoom(9, "Cue", 10);
    assertThrows(IllegalArgumentException.class, () -> room0.removeWeaponFromRoom(weapon1));
  }

  @Test
  public void testRemoveWeaponFromRoom_Successful_WeaponExist() {
    room0.addWeaponToRoom(3, "Rat Poison", 3);
    room0.addWeaponToRoom(10, "Ball", 8);
    room0.removeWeaponFromRoom(weapon3);
    assertEquals(List.of("Ball"), room0.getAvailableWeapons(false));
  }

  @Test
  public void testAddPlayerToRoom_Unsuccessful_PlayerNull() {
    assertThrows(IllegalArgumentException.class, () -> room0.addPlayerToRoom(null));
  }

  @Test
  public void testAddPlayerToRoom_Successful_PlayerAlreadyExists() {
    room1.addPlayerToRoom(computerPlayer2);
    room1.addPlayerToRoom(computerPlayer2);
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: computerPlayer2\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", room1.toString());
  }

  @Test
  public void testAddPlayerToRoom_Successful_PlayerDoesNotAlreadyExist() {
    room2.addPlayerToRoom(humanPlayer1);
    room2.addPlayerToRoom(computerPlayer1);
    assertEquals("Name: Dining Hall\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: humanPlayer1, computerPlayer1\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", room2.toString());
  }

  @Test
  public void testRemovePlayerFromRoom_Unsuccessful_PlayerNull() {
    assertThrows(IllegalArgumentException.class, () -> room0.removePlayerFromRoom(null));
  }

  @Test
  public void testRemovePlayerFromRoom_Unsuccessful_PlayerDoesNotExist() {
    room1.addPlayerToRoom(humanPlayer2);
    assertThrows(IllegalArgumentException.class, () -> room1.removePlayerFromRoom(humanPlayer1));
  }

  @Test
  public void testRemovePlayerFromRoom_Successful_PlayerExist() {
    room2.addPlayerToRoom(humanPlayer1);
    room2.addPlayerToRoom(computerPlayer1);
    room2.addPlayerToRoom(computerPlayer2);
    room2.removePlayerFromRoom(computerPlayer1);
    assertEquals("Name: Dining Hall\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: humanPlayer1, computerPlayer2\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", room2.toString());
  }

  @Test
  public void testToString_ZeroWeaponsNeighboursPlayers() {
    String expected = "Name: Armory\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes";
    assertEquals(expected, room0.toString());
  }

  @Test
  public void testToString_OneWeaponsNeighboursPlayers() {
    room0.setRoomNeighbours(new ArrayList<>(List.of(room1)));
    room0.addWeaponToRoom(4, "Knife", 5);
    room0.addPlayerToRoom(humanPlayer1);
    String expected = "Name: Armory\n"
            + "Neighbours: Billiard Room\n"
            + "Weapons: Knife (Damage: 5)\n"
            + "Players: humanPlayer1\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes";
    assertEquals(expected, room0.toString());
  }

  @Test
  public void testToString_MoreThanOneWeaponsNeighboursPlayers() {
    room1.setRoomNeighbours(new ArrayList<>(Arrays.asList(room2, room0)));
    room1.addWeaponToRoom(1, "Blade", 2);
    room1.addWeaponToRoom(2, "Rope", 1);
    room1.addPlayerToRoom(humanPlayer2);
    room1.addPlayerToRoom(computerPlayer2);
    String expected = "Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Blade (Damage: 2), Rope (Damage: 1)\n"
            + "Players: humanPlayer2, computerPlayer2\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No";
    assertEquals(expected, room1.toString());
  }

  @Test
  public void testToString_OnlyTargetNoPet() {
    room1.setRoomNeighbours(new ArrayList<>(Arrays.asList(room2, room0)));
    room1.addWeaponToRoom(1, "Blade", 2);
    room1.addWeaponToRoom(2, "Rope", 1);
    room1.addPlayerToRoom(humanPlayer2);
    room1.addPlayerToRoom(computerPlayer2);
    room1.updateTargetPlayerPresence(true);
    String expected = "Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Blade (Damage: 2), Rope (Damage: 1)\n"
            + "Players: humanPlayer2, computerPlayer2\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No";
    assertEquals(expected, room1.toString());
  }

  @Test
  public void testToString_OnlyPetNoTarget() {
    room1.setRoomNeighbours(new ArrayList<>(Arrays.asList(room2, room0)));
    room1.addWeaponToRoom(1, "Blade", 2);
    room1.addWeaponToRoom(2, "Rope", 1);
    room1.addPlayerToRoom(humanPlayer2);
    room1.addPlayerToRoom(computerPlayer2);
    room1.updatePetPresence(true);
    String expected = "Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Blade (Damage: 2), Rope (Damage: 1)\n"
            + "Players: humanPlayer2, computerPlayer2\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes";
    assertEquals(expected, room1.toString());
  }

  @Test
  public void testEquals_WithNull() {
    RoomInterface room = new RoomImpl(0, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    assertNotEquals(null, room);
  }

  @Test
  public void testEquals_SameObjects() {
    assertEquals(room1, room1);
  }

  @Test
  public void testEquals_SameValuesDiffObjects() {
    RoomInterface room = new RoomImpl(0, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    RoomInterface room0 = new RoomImpl(0, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    assertEquals(room, room0);
  }

  @Test
  public void testEquals_IdDifferent() {
    RoomInterface room = new RoomImpl(0, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    RoomInterface room1 = new RoomImpl(1, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    assertNotEquals(room, room1);
  }

  @Test
  public void testEquals_RoomNameDifferent() {
    RoomInterface room = new RoomImpl(0, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    RoomInterface room2 = new RoomImpl(0, "Dining Hall",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    assertNotEquals(room, room2);
  }

  @Test
  public void testEquals_RoomCoordinatesDifferent() {
    RoomInterface room = new RoomImpl(0, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    RoomInterface room3 = new RoomImpl(0, "Armory",
            new ArrayList<>(Arrays.asList(7, 6, 15, 13)));
    assertNotEquals(room, room3);
  }

  @Test
  public void testHashCode_SameValuesDifferentObjects() {
    RoomInterface room = new RoomImpl(0, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    RoomInterface room0 = new RoomImpl(0, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    assertEquals(room.hashCode(), room0.hashCode());

  }

  @Test
  public void testHashCode_RoomIdDifferent() {
    RoomInterface room = new RoomImpl(0, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    RoomInterface room1 = new RoomImpl(1, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    assertNotEquals(room.hashCode(), room1.hashCode());
  }

  @Test
  public void testHashCode_RoomNameDifferent() {
    RoomInterface room = new RoomImpl(0, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    RoomInterface room2 = new RoomImpl(0, "Dining Hall",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    assertNotEquals(room.hashCode(), room2.hashCode());
  }

  @Test
  public void testHashCode_RoomCoordinatesDifferent() {
    RoomInterface room = new RoomImpl(0, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    RoomInterface room3 = new RoomImpl(0, "Armory",
            new ArrayList<>(Arrays.asList(7, 6, 15, 13)));
    assertNotEquals(room.hashCode(), room3.hashCode());
  }

  @Test
  public void compareTo_RoomIdEqual() {
    RoomInterface room = new RoomImpl(1, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    RoomInterface room0 = new RoomImpl(1, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    assertEquals(0, room.compareTo(room0));
  }

  @Test
  public void compareTo_RoomIdSmaller() {
    RoomInterface room = new RoomImpl(1, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    RoomInterface room1 = new RoomImpl(2, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    assertNotEquals(1, room.compareTo(room1));
  }

  @Test
  public void compareTo_RoomIdGreater() {
    RoomInterface room = new RoomImpl(1, "Armory",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    RoomInterface room2 = new RoomImpl(0, "Dining Hall",
            new ArrayList<>(Arrays.asList(14, 14, 16, 20)));
    assertNotEquals(-1, room.compareTo(room2));
  }

}