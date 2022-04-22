import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.world.WorldImpl;
import model.world.WorldInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test class for the WorldImpl class.
 *
 */
public class WorldImplTest {

  private WorldInterface testWorld;
  private String worldName;
  private List<Integer> worldCoordinates;
  private int targetPlayerHealth;
  private String targetPlayerName;
  private int numRooms;
  private List<List<Integer>> roomCoordinates;
  private List<String> roomNames;
  private int numWeapons;
  private List<Integer> weaponRoomIds;
  private List<Integer> weaponDamageValues;
  private List<String> weaponNames;
  private String targetPetName;

  /**
   * Method to initialize the variables for world used in the test cases before they are run.
   */
  @Before
  public void setUp() {
    worldCoordinates = new ArrayList<>(Arrays.asList(36, 30));
    worldName = "Doctor Lucky's Mansion";
    targetPlayerHealth = 6;
    targetPlayerName = "Doctor Lucky";

    numRooms = 5;
    roomCoordinates = new ArrayList<>();
    roomCoordinates.add(Arrays.asList(16, 21, 21, 28));
    roomCoordinates.add(Arrays.asList(12, 11, 21, 20));
    roomCoordinates.add(Arrays.asList(16, 3, 21, 10));
    roomCoordinates.add(Arrays.asList(0, 23, 3, 28));
    roomCoordinates.add(Arrays.asList(10, 5, 15, 10));
    roomNames = new ArrayList<>();
    roomNames.add("Billiard Room");
    roomNames.add("Dining Hall");
    roomNames.add("Kitchen");
    roomNames.add("Nursery");
    roomNames.add("Parlor");

    numWeapons = 5;
    weaponRoomIds = new ArrayList<>(Arrays.asList(3, 2, 4, 2));
    weaponDamageValues = new ArrayList<>(Arrays.asList(2, 3, 1, 2));
    weaponNames = new ArrayList<>(Arrays.asList("Loud Noise", "Pan", "Rod", "Rope"));

    targetPetName = "Fortune Cat";

    Readable validFile = new StringReader("36 30 Doctor Lucky's Mansion\n"
            + "2 Doctor Lucky\n"
            + "Fortune Cat\n"
            + "5\n"
            + "16 21 21 28 Billiard Room\n"
            + "12 11 21 20 Dining Hall\n"
            + "16  3 21 10 Kitchen\n"
            + " 0 23  3 28 Nursery\n"
            + "10  5 15 10 Parlor\n"
            + "4\n"
            + "0 2 Loud Noise\n"
            + "2 3 Pan\n"
            + "1 1 Rod\n"
            + "2 2 Rope");
    testWorld = WorldImpl.getBuilder()
            .parseInputFile(validFile)
            .build();
  }

  @Test
  public void testWorldConstructor_Successful() {

    new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName);

  }

  @Test
  public void testWorldConstructor_Unsuccessful_WorldNameEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, "",
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_WorldNameNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, null,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_WorldCoordinatesNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(null, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_WorldCoordinatesEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(new ArrayList<>(), worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_TargetPlayerHealthZero() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            0, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_TargetPlayerHealthNegative() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            -10, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_TargetPlayerNameNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, null,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_TargetPlayerNameEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, "",
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_NumRoomsZero() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            0, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_NumRoomsNegative() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            -10, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_RoomCoordinatesNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, null, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_RoomNamesNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, null,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_NumWeaponsZero() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            0, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_NumWeaponsNegative() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            -20, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_WeaponRoomIdsNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, null, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_WeaponDamageValuesNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, null, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_WeaponNamesNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, null, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_PetNameEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, ""));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_PetNameNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, null));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_SameNameRooms() {
    List<String> exactRoomsRoomNames = new ArrayList<>();
    exactRoomsRoomNames.add("Billiard Room");
    exactRoomsRoomNames.add("Billiard Room");
    exactRoomsRoomNames.add("Kitchen");
    exactRoomsRoomNames.add("Nursery");
    exactRoomsRoomNames.add("Parlor");
    exactRoomsRoomNames.add("Trophy Room");
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, exactRoomsRoomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_SameNameWeapons() {
    List<String> sameWeaponNames = new ArrayList<>(Arrays.asList("Pan", "Pan", "Rod", "Rope"));
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, sameWeaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_IncorrectRoomIds() {
    List<Integer> incorrectWeaponRoomIds = new ArrayList<>(Arrays.asList(-3, 2, 4, 2));
    List<Integer> incorrectWeaponRoomIds2 = new ArrayList<>(Arrays.asList(3, 2, 10, 2));

    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, incorrectWeaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, incorrectWeaponRoomIds2, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_RoomBiggerThanWorld() {
    List<List<Integer>> roomCoordinatesIncorrect = new ArrayList<>();
    roomCoordinatesIncorrect.add(Arrays.asList(200, 21, 21, 28));
    roomCoordinatesIncorrect.add(Arrays.asList(12, 300, 21, 20));
    roomCoordinatesIncorrect.add(Arrays.asList(16, 3, 21, 10));
    roomCoordinatesIncorrect.add(Arrays.asList(0, 400, 3, 28));
    roomCoordinatesIncorrect.add(Arrays.asList(10, 5, 15, 10));
    roomCoordinatesIncorrect.add(Arrays.asList(10, 21, 15, 26));
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinatesIncorrect, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_OverlappingRooms() {
    List<List<Integer>> overlappingRoomCoordinates = new ArrayList<>();
    overlappingRoomCoordinates.add(Arrays.asList(16, 3, 21, 10));
    overlappingRoomCoordinates.add(Arrays.asList(12, 2, 21, 20));
    overlappingRoomCoordinates.add(Arrays.asList(16, 3, 21, 10));
    overlappingRoomCoordinates.add(Arrays.asList(0, 4, 3, 28));
    overlappingRoomCoordinates.add(Arrays.asList(10, 5, 15, 10));
    overlappingRoomCoordinates.add(Arrays.asList(10, 21, 15, 26));
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, overlappingRoomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName));
  }

  @Test
  public void testGetListOfAllRooms() {
    List<String> expected = new ArrayList<>(Arrays.asList(
            "Billiard Room", "Dining Hall", "Kitchen", "Nursery", "Parlor"));
    assertEquals(expected, testWorld.getListOfAllRooms());
  }

  @Test
  public void testGetWorldName() {
    assertEquals("Doctor Lucky's Mansion", testWorld.getWorldName());
  }

  @Test
  public void testCheckIfPlayersExistToPlayGame_NoPlayers() {
    assertThrows(IllegalArgumentException.class, () -> testWorld.checkIfPlayersExistToPlayGame());
  }

  @Test
  public void testCheckIfPlayersExistToPlayGame_OnePlayerExist() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    testWorld.checkIfPlayersExistToPlayGame();
  }

  @Test
  public void testCheckIfPlayersExistToPlayGame_ManyPlayerExist() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Kitchen");
    testWorld.addPlayerToGame("Computer", true, 2, true, "Kitchen");
    testWorld.checkIfPlayersExistToPlayGame();
  }

  @Test
  public void testGetCurrentPlayerName_NoPlayers() {
    assertThrows(IllegalArgumentException.class, () -> testWorld.getCurrentPlayerName());
  }

  @Test
  public void testGetCurrentPlayerName_OnePlayerExist() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertEquals("Test", testWorld.getCurrentPlayerName());
  }

  @Test
  public void testGetCurrentPlayerName_ManyPlayerExist() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Kitchen");
    testWorld.addPlayerToGame("Computer", true, 2, true, "Kitchen");
    assertEquals("Human", testWorld.getCurrentPlayerName());
  }

  @Test
  public void testIsCurrentPlayerComputer_CurrentPlayerNull() {
    assertThrows(IllegalArgumentException.class, () -> testWorld.isCurrentPlayerComputer());
  }

  @Test
  public void testIsCurrentPlayerComputer_CurrentPlayerComputer() {
    testWorld.addPlayerToGame("Test", true, 2, true, "Kitchen");
    assertTrue(testWorld.isCurrentPlayerComputer());
  }

  @Test
  public void testIsCurrentPlayerComputer_CurrentPlayerHuman() {
    testWorld.addPlayerToGame("Test", false, 0, false, "Kitchen");
    assertFalse(testWorld.isCurrentPlayerComputer());
  }

  @Test
  public void testGetCurrentPlayerWeapons_ZeroWeapons_Computer() {
    testWorld.addPlayerToGame("Computer", true, 2, true, "Nursery");
    assertEquals("Poke with damage value 1", testWorld.getCurrentPlayerWeapons());
  }

  @Test
  public void testGetCurrentPlayerWeapons_ZeroWeapons_Human() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Kitchen");
    assertEquals("Poke with damage value 1", testWorld.getCurrentPlayerWeapons());
  }

  @Test
  public void testGetCurrentPlayerWeapons_OneWeapon_Computer() {
    testWorld.addPlayerToGame("Computer", true, 2, true, "Billiard Room");
    testWorld.pickWeapon("loud noise");
    assertEquals("Loud Noise with damage value 2", testWorld.getCurrentPlayerWeapons());
  }

  @Test
  public void testGetCurrentPlayerWeapons_OneWeapon_Human() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Kitchen");
    testWorld.pickWeapon("pan");
    assertEquals("Pan with damage value 3, Poke with damage value 1",
            testWorld.getCurrentPlayerWeapons());
  }

  @Test
  public void testGetCurrentPlayerWeapons_ManyWeapons_Computer() {
    testWorld.addPlayerToGame("Computer", true, 2, true, "Kitchen");
    testWorld.pickWeapon("pan");
    testWorld.pickWeapon("rope");
    assertEquals("Pan with damage value 3, Rope with damage value 2",
            testWorld.getCurrentPlayerWeapons());
  }

  @Test
  public void testGetCurrentPlayerWeapons_ManyWeapons_Human() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Kitchen");
    testWorld.pickWeapon("pan");
    testWorld.pickWeapon("rope");
    assertEquals("Pan with damage value 3, Rope with damage value 2, Poke with damage value 1",
            testWorld.getCurrentPlayerWeapons());
  }

  @Test
  public void testGetCurrentPlayerWeaponWithMostDamage_ZeroWeapons_Computer() {
    testWorld.addPlayerToGame("Computer", true, 2, true, "Nursery");
    assertEquals("Poke", testWorld.getCurrentPlayerWeaponWithMostDamage());
  }

  @Test
  public void testGetCurrentPlayerWeaponWithMostDamage_ZeroWeapons_Human() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Kitchen");
    assertEquals("Poke", testWorld.getCurrentPlayerWeaponWithMostDamage());
  }

  @Test
  public void testGetCurrentPlayerWeaponWithMostDamage_OneWeapon_Computer() {
    testWorld.addPlayerToGame("Computer", true, 2, true, "Billiard Room");
    testWorld.pickWeapon("loud noise");
    assertEquals("Loud Noise", testWorld.getCurrentPlayerWeaponWithMostDamage());
  }

  @Test
  public void testGetCurrentPlayerWeaponWithMostDamage_OneWeapon_Human() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Kitchen");
    testWorld.pickWeapon("pan");
    assertEquals("Pan", testWorld.getCurrentPlayerWeaponWithMostDamage());
  }

  @Test
  public void testGetCurrentPlayerWeaponWithMostDamage_ManyWeapons_Computer() {
    testWorld.addPlayerToGame("Computer", true, 2, true, "Kitchen");
    testWorld.pickWeapon("pan");
    testWorld.pickWeapon("rope");
    assertEquals("Pan", testWorld.getCurrentPlayerWeaponWithMostDamage());
  }

  @Test
  public void testGetCurrentPlayerWeaponWithMostDamage_ManyWeapons_Human() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Kitchen");
    testWorld.pickWeapon("pan");
    testWorld.pickWeapon("rope");
    assertEquals("Pan", testWorld.getCurrentPlayerWeaponWithMostDamage());
  }

  @Test
  public void testGetCurrentPlayerRoomName_Human() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Kitchen");
    assertEquals("Kitchen", testWorld.getCurrentPlayerRoomName());
  }

  @Test
  public void testGetCurrentPlayerRoomName_Computer() {
    testWorld.addPlayerToGame("Computer", true, 2, true, "Nursery");
    assertEquals("Nursery", testWorld.getCurrentPlayerRoomName());
  }

  @Test
  public void testGetCurrentPlayerRoomNeighbours_ZeroNeighbours() {
    testWorld.addPlayerToGame("test human 1", true, 1, false, "Nursery");
    assertEquals("No neighbours", testWorld.getCurrentPlayerRoomNeighbours());
  }

  @Test
  public void testGetCurrentPlayerRoomNeighbours_OneNeighbours() {
    testWorld.addPlayerToGame("test human 1", true, 1, false, "Billiard Room");
    assertEquals("Dining Hall", testWorld.getCurrentPlayerRoomNeighbours());
  }

  @Test
  public void testGetCurrentPlayerRoomNeighbours_ManyNeighbours() {
    testWorld.addPlayerToGame("test human 1", true, 1, false, "Kitchen");
    assertEquals("Dining Hall, Parlor", testWorld.getCurrentPlayerRoomNeighbours());
  }

  @Test
  public void testGetCurrentPlayerRoomWeapons_WithoutDamage_ZeroWeapons() {
    testWorld.addPlayerToGame("test human 1", true, 1, false, "Nursery");
    assertEquals("No weapons", testWorld.getCurrentPlayerRoomWeapons(false));
  }

  @Test
  public void testGetCurrentPlayerRoomWeapons_WithoutDamage_ManyWeapons() {
    testWorld.addPlayerToGame("test human 1", true, 1, false, "Kitchen");
    assertEquals("Pan, Rope", testWorld.getCurrentPlayerRoomWeapons(false));
  }

  @Test
  public void testGetCurrentPlayerRoomWeapons_WithDamage_ZeroWeapons() {
    testWorld.addPlayerToGame("test human 1", true, 1, false, "Nursery");
    assertEquals("No weapons", testWorld.getCurrentPlayerRoomWeapons(true));
  }

  @Test
  public void testGetCurrentPlayerRoomWeapons_WithDamage_ManyWeapons() {
    testWorld.addPlayerToGame("test human 1", true, 1, false, "Kitchen");
    assertEquals("Pan with damage value 3, Rope with damage value 2",
            testWorld.getCurrentPlayerRoomWeapons(true));
  }

  @Test
  public void testIsCurrentPlayerSeen_PetAndOtherPlayerInSame() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Billiard Room");
    testWorld.addPlayerToGame("Computer", true, 2, true, "Billiard Room");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: Human, Computer\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    assertTrue(testWorld.isCurrentPlayerSeenByPlayersInVisibleRooms());
  }

  @Test
  public void testIsCurrentPlayerSeen_OtherPlayerInSame() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Nursery");
    testWorld.addPlayerToGame("Computer", true, 2, true, "Nursery");
    assertEquals("Name: Nursery\n"
            + "Neighbours: No neighbours\n"
            + "Weapons: No weapons\n"
            + "Players: Human, Computer\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Nursery"));
    assertTrue(testWorld.isCurrentPlayerSeenByPlayersInVisibleRooms());
  }

  @Test
  public void testIsCurrentPlayerSeen_OtherPlayerInSame_PetInOther() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Dining Hall");
    testWorld.addPlayerToGame("Computer", true, 2, true, "Dining Hall");
    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: Human, Computer\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    assertTrue(testWorld.isCurrentPlayerSeenByPlayersInVisibleRooms());
  }

  @Test
  public void testIsCurrentPlayerSeen_PetInSame_OtherPlayerInOther() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Billiard Room");
    testWorld.addPlayerToGame("Computer", true, 2, true, "Dining Hall");
    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: Computer\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: Human\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    assertFalse(testWorld.isCurrentPlayerSeenByPlayersInVisibleRooms());
  }

  @Test
  public void testIsCurrentPlayerSeen_OtherPlayerInOther() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Parlor");
    testWorld.addPlayerToGame("Computer", true, 2, true, "Kitchen");
    assertEquals("Name: Parlor\n"
            + "Neighbours: Dining Hall, Kitchen\n"
            + "Weapons: No weapons\n"
            + "Players: Human\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Parlor"));
    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
            + "Players: Computer\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Kitchen"));
    assertTrue(testWorld.isCurrentPlayerSeenByPlayersInVisibleRooms());
  }

  @Test
  public void testIsCurrentPlayerSeen_PetAndOtherPlayerInOther() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Dining Hall");
    testWorld.addPlayerToGame("Computer", true, 2, true, "Billiard Room");
    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: Human\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: Computer\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    assertFalse(testWorld.isCurrentPlayerSeenByPlayersInVisibleRooms());
  }

  @Test
  public void testIsCurrentPlayerSeen_WithoutPetAndOtherPlayer() {
    testWorld.addPlayerToGame("Human", false, 0, false, "Kitchen");
    testWorld.addPlayerToGame("Computer", true, 2, true, "Billiard Room");
    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
            + "Players: Human\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Kitchen"));
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: Computer\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    assertFalse(testWorld.isCurrentPlayerSeenByPlayersInVisibleRooms());
  }

  @Test
  public void testIsCurrentPlayerSeen_PetInOther() {
    testWorld.addPlayerToGame("Computer", true, 2, true, "Billiard Room");
    testWorld.addPlayerToGame("Human", false, 0, false, "Kitchen");
    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
            + "Players: Human\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Kitchen"));
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: Computer\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    assertFalse(testWorld.isCurrentPlayerSeenByPlayersInVisibleRooms());
  }

  @Test
  public void testIsTargetInSameRoom_Yes() {
    testWorld.addPlayerToGame("Computer", true, 2, true, "Billiard Room");
    assertTrue(testWorld.isTargetInSameRoom());

  }

  @Test
  public void testIsTargetInSameRoom_No() {
    testWorld.addPlayerToGame("Computer", true, 2, true, "Kitchen");
    assertFalse(testWorld.isTargetInSameRoom());
  }

  @Test
  public void testGetRoomInformation_Unsuccessful_EmptyRoomName() {
    assertThrows(IllegalArgumentException.class, () -> testWorld.getRoomInformation(""));
  }

  @Test
  public void testGetRoomInformation_Unsuccessful_NullRoomName() {
    assertThrows(IllegalArgumentException.class, () -> testWorld.getRoomInformation(null));
  }

  @Test
  public void testGetRoomInformation_Unsuccessful_RoomDoesNotExist() {
    assertThrows(IllegalArgumentException.class, () -> testWorld.getRoomInformation("testRoom"));
  }

  @Test
  public void testGetRoomInformation_Successful_ZeroNeighboursWeaponsPlayers() {
    assertEquals("Name: Nursery\n"
                    + "Neighbours: No neighbours\n"
                    + "Weapons: No weapons\n"
                    + "Players: No players\n"
                    + "Is Target Present: No\n"
                    + "Is Pet Present: No",
            testWorld.getRoomInformation("Nursery"));
  }

  @Test
  public void testGetRoomInformation_Successful_OneNeighboursWeaponsPlayers() {
    testWorld.addPlayerToGame("test human 1", true, 1, false, "Billiard Room");
    assertEquals("Name: Billiard Room\n"
                    + "Neighbours: Dining Hall\n"
                    + "Weapons: Loud Noise with damage value 2\n"
                    + "Players: test human 1\n"
                    + "Is Target Present: Yes\n"
                    + "Is Pet Present: Yes",
            testWorld.getRoomInformation("Billiard Room"));
  }

  @Test
  public void testGetRoomInformation_Successful_MoreThanOneNeighboursWeaponsPlayers() {
    testWorld.addPlayerToGame("test human 1", true, 1, false, "Kitchen");
    testWorld.addPlayerToGame("test comp 1", false, 0, true, "Kitchen");

    assertEquals("Name: Kitchen\n"
                    + "Neighbours: Dining Hall, Parlor\n"
                    + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
                    + "Players: test human 1, test comp 1\n"
                    + "Is Target Present: No\n"
                    + "Is Pet Present: No",
            testWorld.getRoomInformation("Kitchen"));
  }

  @Test
  public void testGetPlayerInformation_EmptyPlayerName() {
    testWorld.addPlayerToGame("test human 1", true, 1, false, "Kitchen");
    testWorld.addPlayerToGame("test comp 1", false, 0, true, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.getPlayerInformation(""));
  }

  @Test
  public void testGetPlayerInformation_NullPlayerName() {
    testWorld.addPlayerToGame("test human 1", true, 1, false, "Kitchen");
    testWorld.addPlayerToGame("test comp 1", false, 0, true, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.getPlayerInformation(null));
  }

  @Test
  public void testGetPlayerInformation_PlayerDoesNotExist() {
    testWorld.addPlayerToGame("test human 1", true, 1, false, "Kitchen");
    testWorld.addPlayerToGame("test comp 1", false, 0, true, "Kitchen");
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.getPlayerInformation("test player"));
  }

  @Test
  public void testGetPlayerInformation_PlayerExists_HumanPlayerHasLimit() {
    testWorld.addPlayerToGame("test human 1", true, 1, false, "Kitchen");
    testWorld.pickWeapon("pan");
    assertEquals("Name: test human 1\n"
                    + "Player Type: Human\n"
                    + "Current Room: Kitchen\n"
                    + "Weapons: Pan with damage value 3",
            testWorld.getPlayerInformation("test human 1"));
  }

  @Test
  public void testGetPlayerInformation_PlayerExists_HumanPlayerNoLimit() {
    testWorld.addPlayerToGame("test human 1", false, 0, false, "Kitchen");
    assertEquals("Name: test human 1\n"
                    + "Player Type: Human\n"
                    + "Current Room: Kitchen\n"
                    + "Weapons: No weapons",
            testWorld.getPlayerInformation("test human 1"));
  }

  @Test
  public void testGetPlayerInformation_PlayerExists_ComputerPlayerHasLimit() {
    testWorld.addPlayerToGame("test comp 1", true, 2, true, "Kitchen");
    assertEquals("Name: test comp 1\n"
                    + "Player Type: Computer\n"
                    + "Current Room: Kitchen\n"
                    + "Weapons: No weapons",
            testWorld.getPlayerInformation("test comp 1"));
  }

  @Test
  public void testGetPlayerInformation_PlayerExists_ComputerPlayerNoLimit() {
    testWorld.addPlayerToGame("test comp 1", false, 0, true, "Kitchen");
    testWorld.pickWeapon("pan");
    assertEquals("Name: test comp 1\n"
                    + "Player Type: Computer\n"
                    + "Current Room: Kitchen\n"
                    + "Weapons: Pan with damage value 3",
            testWorld.getPlayerInformation("test comp 1"));
  }

  @Test
  public void testGetPlayerInformation_Unsuccessful_TargetPlayer() {
    assertThrows(IllegalArgumentException.class,
            () -> testWorld.getPlayerInformation("Doctor Lucky"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_NameEmpty_Human() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("", true, 2, false, "Kitchen"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_NameEmpty_Computer() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("", true, 2, true, "Kitchen"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_NameNull_Human() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame(null, true, 2, false, "Kitchen"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_NameNull_Computer() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame(null, true, 2, true, "Kitchen"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_LimitNegative_Human() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", true, -2, false, "Kitchen"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_LimitNegative_Computer() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", true, -2, true, "Kitchen"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_RoomNull_Human() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", true, -2, false, null));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_RoomNull_Computer() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", true, 2, true, null));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_RoomEmpty_Human() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", true, 2, false, ""));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_RoomEmpty_Computer() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", true, 2, true, ""));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_SameName() {
    testWorld.addPlayerToGame("Test", true, 2, true, "Kitchen");
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", true, 2, false, "Library"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_RoomNotExist() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", true, 2, false, "TestRoom"));
  }

  @Test
  public void testAddPlayerToGame_ValidHuman() {
    testWorld.addPlayerToGame("Test", false, 2, false, "Kitchen");
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Kitchen\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
  }

  @Test
  public void testAddPlayerToGame_ValidComputer() {
    testWorld.addPlayerToGame("Test2", true, 2, true, "Kitchen");
    assertEquals("Name: Test2\n"
            + "Player Type: Computer\n"
            + "Current Room: Kitchen\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test2"));
  }

  @Test
  public void testLookAround_NoNeighboursNoWeapons() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Nursery");
    assertEquals("------Current Room Details------\n"
            + "Name: Nursery\n"
            + "Neighbours: No neighbours\n"
            + "Weapons: No weapons\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n\n", testWorld.lookAroundSpace());
  }

  @Test
  public void testLookAround_HasOneNeighboursOneWeapon() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Billiard Room");
    assertEquals("------Current Room Details------\n"
            + "Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes\n"
            + "\n"
            + "------Neighboring Room Details------\n"
            + "\n"
            + "Name: Dining Hall\n"
            + "Neighbours: Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n", testWorld.lookAroundSpace());
  }

  @Test
  public void testLookAround_VariableNeighboursManyWeapons() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Dining Hall");
    testWorld.addPlayerToGame("Test2", true, 2, false, "Billiard Room");
    testWorld.addPlayerToGame("Test3", true, 2, false, "Kitchen");
    testWorld.addPlayerToGame("Test4", true, 2, false, "Kitchen");


    assertEquals("------Current Room Details------\n"
            + "Name: Dining Hall\n"
            + "Neighbours: Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n"
            + "\n"
            + "------Neighboring Room Details------\n"
            + "\n"
            + "Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
            + "Players: Test3, Test4\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n"
            + "\n"
            + "Name: Parlor\n"
            + "Neighbours: Dining Hall, Kitchen\n"
            + "Weapons: No weapons\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n", testWorld.lookAroundSpace());
  }

  @Test
  public void testLookAround_NeighbourWithPetDoesNotShow() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Dining Hall");
    assertEquals("------Current Room Details------\n"
            + "Name: Dining Hall\n"
            + "Neighbours: Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n"
            + "\n"
            + "------Neighboring Room Details------\n"
            + "\n"
            + "Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n"
            + "\n"
            + "Name: Parlor\n"
            + "Neighbours: Dining Hall, Kitchen\n"
            + "Weapons: No weapons\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n", testWorld.lookAroundSpace());

    // Pet moved to other room using DFS - previously hidden room now shows up in neighbouring rooms

    assertEquals("------Current Room Details------\n"
            + "Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes\n"
            + "\n"
            + "------Neighboring Room Details------\n"
            + "\n"
            + "Name: Billiard Room\n"
            + "Neighbours: No neighbours\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n"
            + "\n"
            + "Name: Kitchen\n"
            + "Neighbours: Parlor\n"
            + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n"
            + "\n"
            + "Name: Parlor\n"
            + "Neighbours: Kitchen\n"
            + "Weapons: No weapons\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n", testWorld.lookAroundSpace());
  }

  @Test
  public void testMovePlayer_RoomEmpty() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.movePlayer(""));
  }

  @Test
  public void testMovePlayer_RoomNull() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.movePlayer(null));

  }

  @Test
  public void testMovePlayer_RoomSameAsCurrentRoom() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.movePlayer("Kitchen"));

  }

  @Test
  public void testMovePlayer_RoomDoesNotExist() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.movePlayer("TestRoom"));
  }

  @Test
  public void testMovePlayer_RoomNotNeighbour() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.movePlayer("Billiard Room"));
  }

  @Test
  public void testMovePlayer_VisibleNeighbour() {
    testWorld.addPlayerToGame("Test2", true, 2, false, "Dining Hall");
    testWorld.movePlayer("Parlor");
    assertEquals("Name: Parlor\n"
            + "Neighbours: Kitchen\n"
            + "Weapons: No weapons\n"
            + "Players: Test2\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Parlor"));
  }

  @Test
  public void testMovePlayer_InvisibleNeighbour() {
    testWorld.addPlayerToGame("Test2", true, 2, false, "Dining Hall");
    assertThrows(IllegalArgumentException.class, () -> testWorld.movePlayer("Billiard Room"));
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
  }

  @Test
  public void testPickWeapon_WeaponNameEmpty() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.pickWeapon(""));
  }

  @Test
  public void testPickWeapon_WeaponNameNull() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.pickWeapon(null));
  }

  @Test
  public void testPickWeapon_NoWeaponsInRoom() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Nursery");
    assertThrows(IllegalArgumentException.class, () -> testWorld.pickWeapon("Shovel"));
  }

  @Test
  public void testPickWeapon_WeaponNotInCurrentRoom() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.pickWeapon("Rod"));
  }

  @Test
  public void testPickWeapon_WeaponLimitReached() {
    testWorld.addPlayerToGame("Test", true, 1, false, "Kitchen");
    testWorld.pickWeapon("Pan");
    assertThrows(IllegalArgumentException.class, () -> testWorld.pickWeapon("Rope"));
  }

  @Test
  public void testPickWeapon_WeaponInCurrentRoom() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    testWorld.pickWeapon("Pan");
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Kitchen\n"
            + "Weapons: Pan with damage value 3", testWorld.getPlayerInformation("Test"));
    assertEquals("Name: Kitchen\n"
            + "Neighbours: Parlor\n"
            + "Weapons: Rope with damage value 2\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Kitchen"));
  }

  @Test
  public void testMovePet_Unsuccessful_RoomEmpty() {
    assertThrows(IllegalArgumentException.class, () -> testWorld.movePet(""));
  }

  @Test
  public void testMovePet_Unsuccessful_RoomNull() {
    assertThrows(IllegalArgumentException.class, () -> testWorld.movePet(null));
  }

  @Test
  public void testMovePet_Successful_SameRoom() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    testWorld.movePet("Billiard Room");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: No neighbours\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));
    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));
  }

  @Test
  public void testMovePet_Successful_NotNeighbourOfPetsCurrentRoom() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    testWorld.movePet("Nursery");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));
    assertEquals("Name: Nursery\n"
            + "Neighbours: No neighbours\n"
            + "Weapons: No weapons\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Nursery"));
  }

  @Test
  public void testMovePet_Successful_NeighbourOfPetsCurrentRoom() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    testWorld.movePet("Dining Hall");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));
  }

  @Test
  public void testMovePet_Successful_ToIsolatedRoom() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    testWorld.movePet("Nursery");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));
    assertEquals("Name: Nursery\n"
            + "Neighbours: No neighbours\n"
            + "Weapons: No weapons\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Nursery"));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_WeaponEmpty_Human() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.attackTargetPlayer(""));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_WeaponEmpty_Computer() {
    testWorld.addPlayerToGame("Test", true, 2, true, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.attackTargetPlayer(""));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_WeaponNull_Human() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.attackTargetPlayer(null));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_WeaponNull_Computer() {
    testWorld.addPlayerToGame("Test", true, 2, true, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.attackTargetPlayer(null));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_WeaponNotWithPlayer_Human() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Dining Hall");
    testWorld.pickWeapon("Rod");
    assertThrows(IllegalArgumentException.class, () -> testWorld.attackTargetPlayer("rope"));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_WeaponNotWithPlayer_Computer() {
    testWorld.addPlayerToGame("Test", true, 2, true, "Dining Hall");
    testWorld.pickWeapon("Rod");
    assertThrows(IllegalArgumentException.class, () -> testWorld.attackTargetPlayer("rope"));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_TargetNotInSameRoom_Human() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    testWorld.pickWeapon("pan");
    assertEquals("Name: Kitchen\n"
            + "Neighbours: Parlor\n"
            + "Weapons: Rope with damage value 2\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Kitchen"));
    testWorld.attackTargetPlayer("pan");
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Kitchen\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_TargetNotInSameRoom_Computer() {
    testWorld.addPlayerToGame("Test", true, 2, true, "Kitchen");
    testWorld.pickWeapon("pan");
    assertEquals("Name: Kitchen\n"
            + "Neighbours: Parlor\n"
            + "Weapons: Rope with damage value 2\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Kitchen"));
    testWorld.attackTargetPlayer("pan");
    assertEquals("Name: Test\n"
            + "Player Type: Computer\n"
            + "Current Room: Kitchen\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testAttackTargetPlayer_Successful_KillsTarget_Human() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    testWorld.lookAroundSpace();
    testWorld.pickWeapon("Rope");
    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan with damage value 3\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));
    testWorld.attackTargetPlayer("Rope");
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Kitchen\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Nursery", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testAttackTargetPlayer_Successful_KillsTarget_Computer() {
    testWorld.addPlayerToGame("Test", true, 2, true, "Kitchen");
    testWorld.pickWeapon("pan");
    testWorld.lookAroundSpace();
    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Rope with damage value 2\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));
    testWorld.attackTargetPlayer("pan");
    assertEquals("Name: Test\n"
            + "Player Type: Computer\n"
            + "Current Room: Kitchen\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Nursery", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomPetAndPlayers_NeighbourNoPetNoPlayers() {

    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    testWorld.addPlayerToGame("Test2", true, 2, false, "Kitchen");

    testWorld.pickWeapon("Pan");
    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Rope with damage value 2\n"
            + "Players: Test, Test2\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    assertFalse(testWorld.attackTargetPlayer("pan"));

    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Nursery", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Kitchen\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomPetAndPlayers_NeighbourOnlyPlayers() {
    // Neighbours: players, no pet

    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    testWorld.addPlayerToGame("Test2", true, 2, false, "Parlor");
    testWorld.addPlayerToGame("Test3", true, 2, false, "Dining Hall");

    testWorld.pickWeapon("Pan");
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    testWorld.movePlayer("Parlor");
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();


    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: Test3\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Parlor\n"
            + "Neighbours: Dining Hall, Kitchen\n"
            + "Weapons: No weapons\n"
            + "Players: Test2, Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Parlor"));

    assertFalse(testWorld.attackTargetPlayer("pan"));

    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Parlor\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomPlayersOnly_NeighbourNoPetNoPlayers() {

    testWorld.addPlayerToGame("Test", true, 2, false, "Billiard Room");
    testWorld.addPlayerToGame("Test2", true, 2, false, "Billiard Room");

    testWorld.pickWeapon("Loud Noise");
    testWorld.lookAroundSpace();

    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: No weapons\n"
            + "Players: Test, Test2\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    assertFalse(testWorld.attackTargetPlayer("loud noise"));

    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomPlayersOnly_NeighbourPetOnly() {

    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    testWorld.addPlayerToGame("Test2", true, 2, false, "Dining Hall");

    testWorld.lookAroundSpace();
    testWorld.pickWeapon("rod");
    testWorld.lookAroundSpace();
    testWorld.movePlayer("Kitchen");
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: No weapons\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Parlor\n"
            + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
            + "Players: Test, Test2\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Kitchen"));

    assertFalse(testWorld.attackTargetPlayer("rod"));

    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Nursery", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test2\n"
            + "Player Type: Human\n"
            + "Current Room: Kitchen\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test2"));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomPlayersOnly_NeighbourPlayersOnly() {

    testWorld.addPlayerToGame("Test", true, 2, false, "Billiard Room");
    testWorld.addPlayerToGame("Test2", true, 2, false, "Dining Hall");

    testWorld.pickWeapon("loud noise");
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: Test2\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: No weapons\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    assertFalse(testWorld.attackTargetPlayer("loud noise"));

    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomPlayersOnly_NeighbourPetAndPlayers() {

    testWorld.addPlayerToGame("Test2", true, 2, false, "Billiard Room");
    testWorld.addPlayerToGame("Test3", true, 2, false, "Dining Hall");
    testWorld.addPlayerToGame("Test", true, 2, false, "Billiard Room");

    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.pickWeapon("loud noise");
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: Test3\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: No neighbours\n"
            + "Weapons: No weapons\n"
            + "Players: Test2, Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    assertFalse(testWorld.attackTargetPlayer("loud noise"));

    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
  }

  @Test
  public void testAttackTargetPlayer_Successful_SameRoomPetOnly_NeighbourNoPetNoPlayers() {

    testWorld.addPlayerToGame("Test", true, 2, false, "Dining Hall");

    testWorld.pickWeapon("rod");

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: No weapons\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: No neighbours\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    assertTrue(testWorld.attackTargetPlayer("rod"));

    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 1\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Dining Hall\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
  }

  @Test
  public void testAttackTargetPlayer_Successful_SameRoomPetOnly_NeighbourPlayersOnly() {

    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    testWorld.addPlayerToGame("Test2", true, 2, false, "Dining Hall");

    testWorld.pickWeapon("pan");
    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: Test2\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Rope with damage value 2\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    assertTrue(testWorld.attackTargetPlayer("pan"));

    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Nursery", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Kitchen\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomNoPetNoPlayers_NeighbourPlayersOnly() {

    testWorld.addPlayerToGame("Test", true, 2, false, "Billiard Room");
    testWorld.addPlayerToGame("Test2", true, 2, false, "Dining Hall");

    testWorld.pickWeapon("loud noise");
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: Test2\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: No weapons\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    assertFalse(testWorld.attackTargetPlayer("loud noise"));

    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
  }

  @Test
  public void testAttackTargetPlayer_Successful_SameRoomNoPetNoPlayers_NeighbourPetOnly() {

    testWorld.addPlayerToGame("Test", true, 2, false, "Dining Hall");

    testWorld.pickWeapon("rod");
    testWorld.movePlayer("Parlor");
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    assertEquals("Name: Parlor\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: No weapons\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Parlor"));

    assertTrue(testWorld.attackTargetPlayer("rod"));
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 1\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Parlor\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomNoPetNoPlayers_NeighbourPetAndPlayers() {

    testWorld.addPlayerToGame("Test", true, 2, false, "Dining Hall");
    testWorld.addPlayerToGame("Test2", true, 2, false, "Billiard Room");

    testWorld.lookAroundSpace();
    testWorld.pickWeapon("loud noise");
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: No neighbours\n"
            + "Weapons: No weapons\n"
            + "Players: Test2\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    assertFalse(testWorld.attackTargetPlayer("loud noise"));

    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test2\n"
            + "Player Type: Human\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test2"));
  }

  @Test
  public void testAttackTargetPlayer_Successful_SameRoomNoPetNoPlayers_NeighbourNoPetNoPlayers() {

    testWorld.addPlayerToGame("Test", true, 2, false, "Billiard Room");

    testWorld.pickWeapon("loud noise");
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: No weapons\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    assertTrue(testWorld.attackTargetPlayer("loud noise"));

    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
  }

  @Test
  public void testAttackTargetPlayer_Successful_AttackWithPoke_Computer() {

    testWorld.addPlayerToGame("Test", true, 2, true, "Billiard Room");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    assertTrue(testWorld.attackTargetPlayer("poke"));

    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 1\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Player Type: Computer\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
  }

  @Test
  public void testAttackTargetPlayer_Successful_AttackWithPoke_Human() {

    testWorld.addPlayerToGame("Test", true, 2, false, "Billiard Room");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    assertTrue(testWorld.attackTargetPlayer("poke"));

    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 1\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Player Type: Human\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: No weapons", testWorld.getPlayerInformation("Test"));
  }

  @Test
  public void testAttackTargetPlayer_Computer_MultipleWeapons() {
    testWorld.addPlayerToGame("Test", true, 2, true, "Kitchen");
    testWorld.pickWeapon("rope"); // Damage value 2
    testWorld.pickWeapon("pan"); // Damage value 3
    assertEquals("Name: Test\n"
                    + "Player Type: Computer\n"
                    + "Current Room: Kitchen\n"
                    + "Weapons: Pan with damage value 3, Rope with damage value 2",
            testWorld.getPlayerInformation("Test"));
    testWorld.attackTargetPlayer(testWorld.getCurrentPlayerWeaponWithMostDamage());
    assertEquals("Name: Test\n"
            + "Player Type: Computer\n"
            + "Current Room: Kitchen\n"
            + "Weapons: Rope with damage value 2", testWorld.getPlayerInformation("Test"));
  }

  @Test
  public void testIsGameOver_No() {
    assertFalse(testWorld.isGameOver());
  }

  @Test
  public void testIsGameOver_Yes_TargetKilled_Human() {
    testWorld.addPlayerToGame("Human", true, 2, false, "Kitchen");
    testWorld.addPlayerToGame("Computer", true, 2, true, "Billiard Room");
    testWorld.pickWeapon("pan");
    testWorld.lookAroundSpace();
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    testWorld.attackTargetPlayer("pan");
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Nursery", testWorld.getTargetPlayerDetails());
    assertTrue(testWorld.isGameOver());
    assertEquals("Human", testWorld.getWinnerName());
  }

  @Test
  public void testIsGameOver_Yes_TargetKilled_Computer() {
    testWorld.addPlayerToGame("Computer", true, 2, true, "Kitchen");
    testWorld.addPlayerToGame("Human", true, 2, false, "Billiard Room");
    testWorld.pickWeapon("pan");
    testWorld.lookAroundSpace();
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    testWorld.attackTargetPlayer("pan");
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Nursery", testWorld.getTargetPlayerDetails());
    assertTrue(testWorld.isGameOver());
    assertEquals("Computer", testWorld.getWinnerName());
  }

  @Test
  public void testGetWinnerName_NoWinner() {
    assertEquals("Game not over. No winner yet!", testWorld.getWinnerName());
  }

  @Test
  public void testGetWinnerName_Human() {
    testWorld.addPlayerToGame("Human", true, 2, false, "Kitchen");
    testWorld.addPlayerToGame("Computer", true, 2, true, "Billiard Room");
    testWorld.pickWeapon("pan");
    testWorld.lookAroundSpace();
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    testWorld.attackTargetPlayer("pan");
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Nursery", testWorld.getTargetPlayerDetails());
    assertEquals("Human", testWorld.getWinnerName());
  }

  @Test
  public void testGetWinnerName_Computer() {
    testWorld.addPlayerToGame("Computer", true, 2, true, "Kitchen");
    testWorld.addPlayerToGame("Human", true, 2, false, "Billiard Room");
    testWorld.pickWeapon("pan");
    testWorld.lookAroundSpace();
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    testWorld.attackTargetPlayer("pan");
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Nursery", testWorld.getTargetPlayerDetails());
    assertEquals("Computer", testWorld.getWinnerName());
  }

  @Test
  public void testGetTargetPlayerDetails() {
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testPetMovesInDfs_IfNotMovedInBetween() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Parlor\n"
            + "Neighbours: Dining Hall, Kitchen\n"
            + "Weapons: No weapons\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Parlor"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));
  }

  @Test
  public void testPetMovesInDfs_RestartsAfterPetMovedByPlayer() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Parlor\n"
            + "Neighbours: Dining Hall, Kitchen\n"
            + "Weapons: No weapons\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Parlor"));

    testWorld.movePet("Dining Hall");

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: No neighbours\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Kitchen"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Parlor\n"
            + "Neighbours: Kitchen\n"
            + "Weapons: No weapons\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Parlor"));
  }

  @Test
  public void testPetMovesInDfs_PetMovedToIsolatedRoom() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));


    assertEquals("Name: Nursery\n"
            + "Neighbours: No neighbours\n"
            + "Weapons: No weapons\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Nursery"));

    testWorld.movePet("Nursery");

    assertEquals("Name: Nursery\n"
            + "Neighbours: No neighbours\n"
            + "Weapons: No weapons\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Nursery"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Nursery\n"
            + "Neighbours: No neighbours\n"
            + "Weapons: No weapons\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Nursery"));
  }

  @Test
  public void testPetMovesAfterLookAround() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));
  }

  @Test
  public void testPetMovesAfterMovePlayer() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.movePlayer("Parlor");

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));
  }

  @Test
  public void testPetMovesAfterPickWeapon() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.pickWeapon("pan");

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

  }

  @Test
  public void testPetMovesAfterAttackTarget() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");

    testWorld.pickWeapon("pan");
    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Rope with damage value 2\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    testWorld.attackTargetPlayer("pan");

    assertEquals("Name: Parlor\n"
            + "Neighbours: Dining Hall, Kitchen\n"
            + "Weapons: No weapons\n"
            + "Players: No players\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Parlor"));

  }

  @Test
  public void testPetMovesAfterMovePet() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise with damage value 2\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.movePet("Kitchen");

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Parlor\n"
            + "Weapons: Pan with damage value 3, Rope with damage value 2\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Kitchen"));

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod with damage value 1\n"
            + "Players: No players\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));


  }

  @Test
  public void testTargetPlayerMovesAfterPlayerMoves() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Billiard Room");
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    testWorld.movePlayer("Dining Hall");
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testTargetPlayerMovesAfterPlayerPicksWeapon() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Billiard Room");
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    testWorld.pickWeapon("loud noise");
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testTargetPlayerMovesAfterLookAround() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Billiard Room");
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    testWorld.lookAroundSpace();
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testTargetPlayerMovesAfterPetMoves() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Billiard Room");
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    testWorld.movePet("Nursery");
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testTargetPlayerMovesAfterAttackTarget() {
    testWorld.addPlayerToGame("Test", true, 2, false, "Kitchen");
    testWorld.pickWeapon("pan");
    testWorld.lookAroundSpace();
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    testWorld.attackTargetPlayer("pan");
    assertEquals("---------- Target Player Details ----------\n"
            + "Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Nursery", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testTurnAlternateBetweenPlayersInOrderAndEachPlayerGetsOneTurn() {
    testWorld.addPlayerToGame("P1", true, 2, false, "Kitchen");
    testWorld.addPlayerToGame("P2", false, 0, true, "Billiard room");
    testWorld.addPlayerToGame("P3", true, 2, false, "Dining Hall");

    assertEquals("P1", testWorld.getCurrentPlayerName());
    testWorld.lookAroundSpace();
    assertEquals("P2", testWorld.getCurrentPlayerName());
    testWorld.lookAroundSpace();
    assertEquals("P3", testWorld.getCurrentPlayerName());
    testWorld.lookAroundSpace();

    assertEquals("P1", testWorld.getCurrentPlayerName());
    testWorld.movePlayer("Dining Hall");
    assertEquals("P2", testWorld.getCurrentPlayerName());
    testWorld.movePlayer("Dining Hall");
    assertEquals("P3", testWorld.getCurrentPlayerName());
    testWorld.movePlayer("Billiard Room");

    assertEquals("P1", testWorld.getCurrentPlayerName());
    testWorld.lookAroundSpace();
    assertEquals("P2", testWorld.getCurrentPlayerName());
    testWorld.pickWeapon("rod");
    assertEquals("P3", testWorld.getCurrentPlayerName());
    testWorld.pickWeapon("loud noise");

    assertEquals("P1", testWorld.getCurrentPlayerName());
    testWorld.movePet("Dining Hall");
    assertEquals("P2", testWorld.getCurrentPlayerName());
    testWorld.movePet("Kitchen");
    assertEquals("P3", testWorld.getCurrentPlayerName());
    testWorld.movePet("Billiard Room");

    assertEquals("P1", testWorld.getCurrentPlayerName());
    testWorld.lookAroundSpace();
    assertEquals("P2", testWorld.getCurrentPlayerName());
    testWorld.attackTargetPlayer("rod");
    assertEquals("P3", testWorld.getCurrentPlayerName());
    testWorld.attackTargetPlayer("loud noise");

    assertEquals("P1", testWorld.getCurrentPlayerName());
  }

}