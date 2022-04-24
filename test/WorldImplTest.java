import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.random.RandomClass;
import model.random.RandomGenerator;
import model.world.WorldImpl;
import model.world.WorldInterface;
import org.junit.Before;
import org.junit.Test;


/**
 * A JUnit test class for the WorldImpl class.
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
  private int maxNumberOfTurns;
  private RandomGenerator random;

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
    maxNumberOfTurns = 20;
    random = new RandomClass();
    testWorld = WorldImpl.getBuilder()
            .parseInputFile(validFile).setMaxTurns(maxNumberOfTurns).build();

  }

  @Test
  public void testWorldConstructor_Successful() {

    new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns);

  }

  @Test
  public void testWorldConstructor_Unsuccessful_WorldNameEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, "",
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName, random,
            maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_WorldNameNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, null,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames,
            targetPetName, random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_WorldCoordinatesNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(null, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_WorldCoordinatesEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(new ArrayList<>(), worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName, random,
            maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_TargetPlayerHealthZero() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            0, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_TargetPlayerHealthNegative() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            -10, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_TargetPlayerNameNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, null,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_TargetPlayerNameEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, "",
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_NumRoomsZero() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            0, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_NumRoomsNegative() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            -10, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_RoomCoordinatesNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, null, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_RoomNamesNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, null,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_NumWeaponsZero() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            0, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_NumWeaponsNegative() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            -20, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_WeaponRoomIdsNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, null, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_WeaponDamageValuesNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, null, weaponNames, targetPetName, random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_WeaponNamesNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, null, targetPetName,
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_PetNameEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, "",
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_PetNameNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, null,
            random, maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_RandomNull() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName, null,
            maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_InvalidMaxNumberOfTurns() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName, random,
            -1));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_ZeroMaxNumberOfTurns() {
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName, random,
            0));
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
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName, random,
            maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_SameNameWeapons() {
    List<String> sameWeaponNames = new ArrayList<>(Arrays.asList("Pan", "Pan", "Rod", "Rope"));
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, sameWeaponNames, targetPetName, random,
            maxNumberOfTurns));
  }

  @Test
  public void testWorldConstructor_Unsuccessful_IncorrectRoomIds() {
    List<Integer> incorrectWeaponRoomIds = new ArrayList<>(Arrays.asList(-3, 2, 4, 2));
    List<Integer> incorrectWeaponRoomIds2 = new ArrayList<>(Arrays.asList(3, 2, 10, 2));

    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, incorrectWeaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns));
    assertThrows(IllegalArgumentException.class, () -> new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, incorrectWeaponRoomIds2, weaponDamageValues, weaponNames, targetPetName,
            random, maxNumberOfTurns));
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
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName, random,
            maxNumberOfTurns));
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
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName, random,
            maxNumberOfTurns));
  }

  @Test
  public void testGetListOfAllRooms() {
    List<String> expected = new ArrayList<>(Arrays.asList(
            "Billiard Room", "Dining Hall", "Kitchen", "Nursery", "Parlor"));
    assertEquals(expected, testWorld.getListOfRooms());
  }

  @Test
  public void testGetWorldName() {
    assertEquals("Doctor Lucky's Mansion", testWorld.getWorldName());
  }

  @Test
  public void testGetCurrentPlayerName_NoPlayers() {
    assertEquals("-", testWorld.getCurrentPlayerName());
  }

  @Test
  public void testGetCurrentPlayerName_OnePlayerExist() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertEquals("Test", testWorld.getCurrentPlayerName());
  }

  @Test
  public void testGetCurrentPlayerName_ManyPlayerExist() {
    testWorld.addPlayerToGame("Human", 2, false, "Kitchen");
    testWorld.addPlayerToGame("Computer", 2, true, "Kitchen");
    assertEquals("Human", testWorld.getCurrentPlayerName());
  }

  @Test
  public void testIsCurrentPlayerComputer_CurrentPlayerNull() {
    assertThrows(IllegalArgumentException.class, () -> testWorld.isCurrentPlayerComputer());
  }

  @Test
  public void testIsCurrentPlayerComputer_CurrentPlayerComputer() {
    testWorld.addPlayerToGame("Test", 2, true, "Kitchen");
    assertTrue(testWorld.isCurrentPlayerComputer());
  }

  @Test
  public void testIsCurrentPlayerComputer_CurrentPlayerHuman() {
    testWorld.addPlayerToGame("Test", 1, false, "Kitchen");
    assertFalse(testWorld.isCurrentPlayerComputer());
  }

  @Test
  public void testGetCurrentPlayerWeapons_ZeroWeapons_Computer() {
    testWorld.addPlayerToGame("Computer", 2, true, "Nursery");
    assertEquals("Poke (Damage: 1)", String.join("", testWorld.getCurrentPlayerWeapons()));
  }

  @Test
  public void testGetCurrentPlayerWeapons_ZeroWeapons_Human() {
    testWorld.addPlayerToGame("Human", 2, false, "Kitchen");
    assertEquals("Poke (Damage: 1)", String.join("", testWorld.getCurrentPlayerWeapons()));
  }

  @Test
  public void testGetCurrentPlayerWeapons_OneWeapon_Computer() {
    testWorld.addPlayerToGame("Computer", 2, true, "Billiard Room");
    testWorld.pickWeapon(null);
    assertEquals("Loud Noise (Damage: 2)", String.join(",", testWorld.getCurrentPlayerWeapons()));
  }

  @Test
  public void testGetCurrentPlayerWeapons_OneWeapon_Human() {
    testWorld.addPlayerToGame("Human", 1, false, "Kitchen");
    testWorld.pickWeapon("pan");
    assertEquals("Pan (Damage: 3),Poke (Damage: 1)",
            String.join(",", testWorld.getCurrentPlayerWeapons()));
  }

  @Test
  public void testGetCurrentPlayerWeapons_ManyWeapons_Computer() {
    RandomClass predictableRandom = new RandomClass(0);
    testWorld = new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            predictableRandom, maxNumberOfTurns);
    testWorld.addPlayerToGame("Computer", 2, true, "Kitchen");
    testWorld.pickWeapon(null);
    testWorld.pickWeapon(null);
    assertEquals("Pan (Damage: 3)Rope (Damage: 2)",
            String.join("", testWorld.getCurrentPlayerWeapons()));
  }

  @Test
  public void testGetCurrentPlayerWeapons_ManyWeapons_Human() {
    testWorld.addPlayerToGame("Human", 2, false, "Kitchen");
    testWorld.pickWeapon("pan");
    testWorld.pickWeapon("rope");
    assertEquals("Pan (Damage: 3),Rope (Damage: 2),Poke (Damage: 1)",
            String.join(",", testWorld.getCurrentPlayerWeapons()));
  }

  @Test
  public void testGetCurrentPlayerRoomName_Human() {
    testWorld.addPlayerToGame("Human", 2, false, "Kitchen");
    String expected = "Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: Human\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No";
    assertEquals(expected, testWorld.getCurrentPlayerRoomInformation());
  }

  @Test
  public void testGetCurrentPlayerRoomName_Computer() {
    testWorld.addPlayerToGame("Computer", 2, true, "Nursery");
    String expected = "Name: Nursery\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: Computer\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No";
    assertEquals(expected, testWorld.getCurrentPlayerRoomInformation());
  }

  @Test
  public void testGetCurrentPlayerRoomWeapons_WithoutDamage_ZeroWeapons() {
    testWorld.addPlayerToGame("test human 1", 1, true, "Nursery");
    assertEquals("", String.join("", testWorld.getCurrentPlayerRoomWeapons()));
  }

  @Test
  public void testGetCurrentPlayerRoomWeapons_WithoutDamage_ManyWeapons() {
    testWorld.addPlayerToGame("test human 1", 1, true, "Kitchen");
    assertEquals("Pan (Damage: 3),Rope (Damage: 2)",
            String.join(",", testWorld.getCurrentPlayerRoomWeapons()));
  }

  @Test
  public void testGetCurrentPlayerRoomWeapons_WithDamage_ZeroWeapons() {
    testWorld.addPlayerToGame("test human 1", 1, true, "Nursery");
    assertEquals("", String.join("", testWorld.getCurrentPlayerRoomWeapons()));
  }

  @Test
  public void testGetCurrentPlayerRoomWeapons_WithDamage_ManyWeapons() {
    testWorld.addPlayerToGame("test human 1", 1, true, "Kitchen");
    assertEquals("Pan (Damage: 3)Rope (Damage: 2)",
            String.join("", testWorld.getCurrentPlayerRoomWeapons()));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_NameEmpty_Human() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("", 2, false, "Kitchen"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_NameEmpty_Computer() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("", 2, true, "Kitchen"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_NameNull_Human() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame(null, 2, false, "Kitchen"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_NameNull_Computer() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame(null, 2, true, "Kitchen"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_LimitNegative_Human() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", -2, false, "Kitchen"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_LimitNegative_Computer() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", -2, true, "Kitchen"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_RoomNull_Human() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", 1, false, null));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_RoomNull_Computer() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", 1, true, null));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_RoomEmpty_Human() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", 1, true, ""));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_RoomEmpty_Computer() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", 1, true, ""));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_SameName() {
    testWorld.addPlayerToGame("Test", 1, true, "Kitchen");
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", 1, true, "Library"));
  }

  @Test
  public void testAddPlayerToGame_Unsuccessful_RoomNotExist() {
    assertThrows(IllegalArgumentException.class, () ->
            testWorld.addPlayerToGame("Test", 1, true, "TestRoom"));
  }

  @Test
  public void testAddPlayerToGame_ValidHuman() {
    testWorld.addPlayerToGame("Test", 1, false, "Kitchen");
    assertEquals("Name: Test\n"
            + "Current Room: Kitchen\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAddPlayerToGame_ValidComputer() {
    testWorld.addPlayerToGame("Test2", 1, true, "Kitchen");
    assertEquals("Name: Test2\n"
            + "Current Room: Kitchen\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testLookAround_NoNeighboursNoWeapons() {
    testWorld.addPlayerToGame("Test", 2, true, "Nursery");
    assertEquals("-----Look Around Details------\n"
            + "------Current Room Details------\n"
            + "Name: Nursery\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n"
            + "\n", testWorld.lookAroundSpace());
  }

  @Test
  public void testLookAround_HasOneNeighboursOneWeapon() {
    testWorld.addPlayerToGame("Test", 2, true, "Billiard Room");
    assertEquals("-----Look Around Details------\n"
            + "------Current Room Details------\n"
            + "Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes\n"
            + "\n"
            + "------Neighboring Room Details------\n"
            + "\n"
            + "Name: Dining Hall\n"
            + "Neighbours: Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n", testWorld.lookAroundSpace());
  }

  @Test
  public void testLookAround_VariableNeighboursManyWeapons() {
    testWorld.addPlayerToGame("Test", 2, false, "Dining Hall");
    testWorld.addPlayerToGame("Test2", 2, false, "Billiard Room");
    testWorld.addPlayerToGame("Test3", 2, false, "Kitchen");
    testWorld.addPlayerToGame("Test4", 2, false, "Kitchen");


    assertEquals("-----Look Around Details------\n"
            + "------Current Room Details------\n"
            + "Name: Dining Hall\n"
            + "Neighbours: Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n"
            + "\n"
            + "------Neighboring Room Details------\n"
            + "\n"
            + "Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: Test3, Test4\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n"
            + "\n"
            + "Name: Parlor\n"
            + "Neighbours: Dining Hall, Kitchen\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n", testWorld.lookAroundSpace());
  }

  @Test
  public void testLookAround_NeighbourWithPetDoesNotShow() {
    testWorld.addPlayerToGame("Test", 2, true, "Dining Hall");
    assertEquals("-----Look Around Details------\n"
            + "------Current Room Details------\n"
            + "Name: Dining Hall\n"
            + "Neighbours: Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n"
            + "\n"
            + "------Neighboring Room Details------\n"
            + "\n"
            + "Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n"
            + "\n"
            + "Name: Parlor\n"
            + "Neighbours: Dining Hall, Kitchen\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n", testWorld.lookAroundSpace());

    // Pet moved to other room using DFS - previously hidden room now shows up in neighbouring rooms

    assertEquals("-----Look Around Details------\n"
            + "------Current Room Details------\n"
            + "Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes\n"
            + "\n"
            + "------Neighboring Room Details------\n"
            + "\n"
            + "Name: Billiard Room\n"
            + "Neighbours: -\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n"
            + "\n"
            + "Name: Kitchen\n"
            + "Neighbours: Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n"
            + "\n"
            + "Name: Parlor\n"
            + "Neighbours: Kitchen\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n", testWorld.lookAroundSpace());
  }

  @Test
  public void testLookAroundWithTargetInSameRoom() {

  }

  @Test
  public void testMovePlayer_NegativexCoordinate() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.handleRoomClick(-1, 10));
  }

  @Test
  public void testMovePlayer_NegativeyCoordinate() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.handleRoomClick(10, -1));
  }

  @Test
  public void testMovePlayer_InvalidxCoordinate() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.handleRoomClick(100, 10));
  }

  @Test
  public void testMovePlayer_InvalidyCoordinate() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.handleRoomClick(10, 100));
  }

  @Test
  public void testMovePlayer_NotNeighbor() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.handleRoomClick(17, 22));
  }

  @Test
  public void testMovePlayer_SameRoom() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.handleRoomClick(18, 7));
  }

  @Test
  public void testMovePlayer_InvisibleNeighbor() {
    testWorld.addPlayerToGame("Test", 2, false, "Dining Hall");
    assertThrows(IllegalArgumentException.class, () -> testWorld.handleRoomClick(18, 26));
  }

  @Test
  public void testMovePlayer_VisibleNeighbor() {
    testWorld.addPlayerToGame("Test", 2, false, "Dining Hall");
    assertThrows(IllegalArgumentException.class, () -> testWorld.handleRoomClick(12, 8));
  }

  @Test
  public void testPickWeapon_WeaponNameEmpty_Human() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.pickWeapon(""));
  }

  @Test
  public void testPickWeapon_WeaponNameNull_Human() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.pickWeapon(null));
  }

  @Test
  public void testPickWeapon_NoWeaponsInRoom_Human() {
    testWorld.addPlayerToGame("Test", 2, false, "Nursery");
    assertThrows(IllegalArgumentException.class, () -> testWorld.pickWeapon("Shovel"));
  }

  @Test
  public void testPickWeapon_WeaponNotInCurrentRoom_Human() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.pickWeapon("Rod"));
  }

  @Test
  public void testPickWeapon_WeaponLimitReached_Human() {
    testWorld.addPlayerToGame("Test", 1, false, "Kitchen");
    testWorld.pickWeapon("Pan");
    assertThrows(IllegalArgumentException.class, () -> testWorld.pickWeapon("Rope"));
  }

  @Test
  public void testPickWeapon_WeaponInCurrentRoom_Human() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    testWorld.pickWeapon("Pan");
    assertEquals("Name: Kitchen\n"
            + "Neighbours: Parlor\n"
            + "Weapons: Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getCurrentPlayerRoomInformation());
  }

  @Test
  public void testPickWeapon_NoWeaponsInRoom_Computer() {
    testWorld.addPlayerToGame("Test", 2, true, "Nursery");
    assertThrows(IllegalArgumentException.class, () -> testWorld.pickWeapon(null));
  }

  @Test
  public void testPickWeapon_WeaponLimitReached_Computer() {
    testWorld.addPlayerToGame("Test", 1, true, "Kitchen");
    testWorld.pickWeapon(null);
    assertThrows(IllegalArgumentException.class, () -> testWorld.pickWeapon(null));
  }

  @Test
  public void testPickWeapon_WeaponInCurrentRoom_Computer() {
    testWorld.addPlayerToGame("Test", 2, true, "Billiard Room");
    testWorld.pickWeapon(null);
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getCurrentPlayerRoomInformation());

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
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    testWorld.movePet("Billiard Room");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: -\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));
    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));
  }

  @Test
  public void testMovePet_Successful_NotNeighbourOfPetsCurrentRoom() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    testWorld.movePet("Nursery");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));
    assertEquals("Name: Nursery\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Nursery"));
  }

  @Test
  public void testMovePet_Successful_NeighbourOfPetsCurrentRoom() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    testWorld.movePet("Dining Hall");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));
  }

  @Test
  public void testMovePet_Successful_ToIsolatedRoom() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));
    testWorld.movePet("Nursery");
    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));
    assertEquals("Name: Nursery\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Nursery"));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_WeaponEmpty_Human() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.attackTargetPlayer(""));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_WeaponNull_Human() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    assertThrows(IllegalArgumentException.class, () -> testWorld.attackTargetPlayer(null));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_WeaponNotWithPlayer_Human() {
    testWorld.addPlayerToGame("Test", 2, false, "Dining Hall");
    testWorld.pickWeapon("Rod");
    assertThrows(IllegalArgumentException.class, () -> testWorld.attackTargetPlayer("rope"));
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_TargetNotInSameRoom_Human() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    testWorld.pickWeapon("pan");
    assertEquals("Name: Kitchen\n"
            + "Neighbours: Parlor\n"
            + "Weapons: Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Kitchen"));
    testWorld.attackTargetPlayer("pan");
    assertEquals("Name: Test\n"
            + "Current Room: Kitchen\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_TargetNotInSameRoom_Computer() {
    testWorld.addPlayerToGame("Computer", 2, true, "Billiard Room");
    testWorld.pickWeapon(null);

    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Attack on target was not successful.", testWorld.attackTargetPlayer(null));
    assertEquals("Name: Computer\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testAttackTargetPlayer_Successful_KillsTarget_Human() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    testWorld.lookAroundSpace();
    testWorld.pickWeapon("Rope");
    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan (Damage: 3)\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));
    testWorld.attackTargetPlayer("Rope");
    assertEquals("Name: Test\n"
            + "Current Room: Kitchen\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testAttackTargetPlayer_Successful_KillsTarget_Computer() {
    testWorld.addPlayerToGame("Test", 2, true, "Billiard Room");
    testWorld.pickWeapon(null);
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();


    testWorld.attackTargetPlayer(null);
    assertEquals("Name: Test\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
    assertEquals(
            "Name: Doctor Lucky\n"
                    + "Health: 0\n"
                    + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomPetAndPlayers_NeighbourNoPetNoPlayers() {

    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    testWorld.addPlayerToGame("Test2", 2, false, "Kitchen");

    testWorld.pickWeapon("Pan");
    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Rope (Damage: 2)\n"
            + "Players: Test, Test2\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    assertEquals("Attack on target was not successful.", testWorld.attackTargetPlayer("pan"));
    testWorld.lookAroundSpace();

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Parlor", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Current Room: Kitchen\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomPetAndPlayers_NeighbourOnlyPlayers() {
    // Neighbours: players, no pet

    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    testWorld.addPlayerToGame("Test2", 2, false, "Parlor");
    testWorld.addPlayerToGame("Test3", 2, false, "Dining Hall");

    testWorld.pickWeapon("Pan");
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    testWorld.handleRoomClick(276, 210);
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();


    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: Test3\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Parlor\n"
            + "Neighbours: Dining Hall, Kitchen\n"
            + "Weapons: -\n"
            + "Players: Test2, Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Parlor"));

    assertEquals("Attack on target was not successful.", testWorld.attackTargetPlayer("pan"));
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Current Room: Parlor\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomPlayersOnly_NeighbourNoPetNoPlayers() {

    testWorld.addPlayerToGame("Test", 2, false, "Billiard Room");
    testWorld.addPlayerToGame("Test2", 2, false, "Billiard Room");

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
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: -\n"
            + "Players: Test, Test2\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    assertEquals("Attack on target was not successful.", testWorld.attackTargetPlayer("loud "
            + "noise"));
    testWorld.lookAroundSpace();

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomPlayersOnly_NeighbourPetOnly() {

    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    testWorld.addPlayerToGame("Test2", 2, false, "Dining Hall");

    testWorld.lookAroundSpace();
    testWorld.pickWeapon("rod");
    testWorld.lookAroundSpace();
    testWorld.handleRoomClick(420, 186);
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: Test, Test2\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Kitchen"));

    assertEquals("Attack on target was not successful.", testWorld.attackTargetPlayer("rod"));
    testWorld.lookAroundSpace();

    assertEquals(
            "Name: Doctor Lucky\n"
                    + "Health: 2\n"
                    + "Current Room: Parlor", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test2\n"
            + "Current Room: Kitchen\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomPlayersOnly_NeighbourPlayersOnly() {

    testWorld.addPlayerToGame("Test", 2, false, "Billiard Room");
    testWorld.addPlayerToGame("Test2", 2, false, "Dining Hall");

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
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: Test2\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: -\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    assertEquals("Attack on target was not successful.", testWorld.attackTargetPlayer("loud "
            + "noise"));
    testWorld.lookAroundSpace();

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomPlayersOnly_NeighbourPetAndPlayers() {

    testWorld.addPlayerToGame("Test2", 2, false, "Billiard Room");
    testWorld.addPlayerToGame("Test3", 2, false, "Dining Hall");
    testWorld.addPlayerToGame("Test", 2, false, "Billiard Room");

    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.pickWeapon("loud noise");
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: Test3\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: Test2, Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    assertEquals("Attack on target was not successful.", testWorld.attackTargetPlayer("loud "
            + "noise"));
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Nursery", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAttackTargetPlayer_Successful_SameRoomPetOnly_NeighbourNoPetNoPlayers() {

    testWorld.addPlayerToGame("Test", 2, false, "Dining Hall");

    testWorld.pickWeapon("rod");

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: -\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: -\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    assertEquals("Attack on target was successful.", testWorld.attackTargetPlayer("rod"));

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 1\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Current Room: Dining Hall\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAttackTargetPlayer_Successful_SameRoomPetOnly_NeighbourPlayersOnly() {

    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    testWorld.addPlayerToGame("Test2", 2, false, "Dining Hall");

    testWorld.pickWeapon("pan");
    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: Test2\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    assertEquals("Attack on target was successful.", testWorld.attackTargetPlayer("pan"));
    testWorld.lookAroundSpace();

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Nursery", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test2\n"
            + "Current Room: Dining Hall\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomNoPetNoPlayers_NeighbourPlayersOnly() {

    testWorld.addPlayerToGame("Test", 2, false, "Billiard Room");
    testWorld.addPlayerToGame("Test2", 2, false, "Dining Hall");

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
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: Test2\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: -\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    assertEquals("Attack on target was not successful.", testWorld.attackTargetPlayer("loud "
            + "noise"));
    testWorld.lookAroundSpace();

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAttackTargetPlayer_Successful_SameRoomNoPetNoPlayers_NeighbourPetOnly() {

    testWorld.addPlayerToGame("Test", 2, false, "Dining Hall");

    testWorld.pickWeapon("rod");
    testWorld.handleRoomClick(276, 210);
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    assertEquals("Name: Parlor\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: -\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Parlor"));

    assertEquals("Attack on target was successful.", testWorld.attackTargetPlayer("rod"));
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 1\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Current Room: Parlor\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAttackTargetPlayer_Unsuccessful_SameRoomNoPetNoPlayers_NeighbourPetAndPlayers() {

    testWorld.addPlayerToGame("Test", 2, false, "Dining Hall");
    testWorld.addPlayerToGame("Test2", 2, false, "Billiard Room");

    testWorld.lookAroundSpace();
    testWorld.pickWeapon("loud noise");
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: Test2\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    assertEquals("Attack on target was not successful.", testWorld.attackTargetPlayer("loud "
            + "noise"));
    testWorld.lookAroundSpace();

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test2\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAttackTargetPlayer_Successful_SameRoomNoPetNoPlayers_NeighbourNoPetNoPlayers() {

    testWorld.addPlayerToGame("Test", 2, false, "Billiard Room");

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
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: -\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    assertEquals("Attack on target was successful.", testWorld.attackTargetPlayer("loud noise"));

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAttackTargetPlayer_Successful_AttackWithPoke_Computer() {

    testWorld.addPlayerToGame("Test", 2, true, "Billiard Room");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    assertEquals("Attack on target was successful.", testWorld.attackTargetPlayer(null));

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 1\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAttackTargetPlayer_Successful_AttackWithPoke_Human() {

    testWorld.addPlayerToGame("Test", 2, false, "Billiard Room");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    assertEquals("Attack on target was successful.", testWorld.attackTargetPlayer("poke"));

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 1\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Current Room: Billiard Room\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testAttackTargetPlayer_Computer_MultipleWeapons() {
    testWorld.addPlayerToGame("Test", 2, true, "Kitchen");
    testWorld.pickWeapon(null); // Damage value 2
    testWorld.pickWeapon(null); // Damage value 3
    assertEquals("Name: Test\n"
                    + "Current Room: Kitchen\n"
                    + "Weapons: Pan (Damage: 3), Rope (Damage: 2)",

            testWorld.getCurrentPlayerInformation());
    testWorld.attackTargetPlayer(null);
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test\n"
            + "Current Room: Kitchen\n"
            + "Weapons: Rope (Damage: 2)", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testIsGameOver_No() {
    assertFalse(testWorld.isGameOver());
  }

  @Test
  public void testIsGameOver_Yes_TargetKilled_Human() {
    testWorld.addPlayerToGame("Human", 2, false, "Kitchen");
    testWorld.addPlayerToGame("Computer", 2, true, "Billiard Room");
    testWorld.pickWeapon("pan");
    testWorld.lookAroundSpace();
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    testWorld.attackTargetPlayer("pan");
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    assertTrue(testWorld.isGameOver());
    assertEquals("Human", testWorld.getWinner());
  }

  @Test
  public void testIsGameOver_Yes_TargetKilled_Computer() {
    testWorld.addPlayerToGame("Computer", 2, true, "Kitchen");
    testWorld.addPlayerToGame("Human", 2, false, "Billiard Room");
    testWorld.pickWeapon(null);
    testWorld.lookAroundSpace();
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    testWorld.attackTargetPlayer(null);
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    assertTrue(testWorld.isGameOver());
    assertEquals("Computer", testWorld.getWinner());
  }

  @Test
  public void testGetWinnerName_NoWinner() {
    testWorld.addPlayerToGame("Human", 2, false, "Kitchen");
    assertEquals("Game not over. No winner yet!", testWorld.getWinner());
  }

  @Test
  public void testGetWinnerName_Human() {
    testWorld.addPlayerToGame("Human", 2, false, "Kitchen");
    testWorld.addPlayerToGame("Computer", 2, true, "Billiard Room");
    testWorld.pickWeapon("pan");
    testWorld.lookAroundSpace();
    assertEquals(
            "Name: Doctor Lucky\n"
                    + "Health: 2\n"
                    + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    testWorld.attackTargetPlayer("pan");
    assertEquals(
            "Name: Doctor Lucky\n"
                    + "Health: 0\n"
                    + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    assertEquals("Human", testWorld.getWinner());
  }

  @Test
  public void testGetWinnerName_Computer() {
    testWorld.addPlayerToGame("Computer", 2, true, "Kitchen");
    testWorld.addPlayerToGame("Human", 2, false, "Billiard Room");
    testWorld.pickWeapon(null);
    testWorld.lookAroundSpace();
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    testWorld.attackTargetPlayer(null);
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    assertEquals("Computer", testWorld.getWinner());
  }

  @Test
  public void testGetTargetPlayerDetails() {
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testPetMovesInDfs_IfNotMovedInBetween() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Parlor\n"
            + "Neighbours: Dining Hall, Kitchen\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Parlor"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));
  }

  @Test
  public void testPetMovesInDfs_RestartsAfterPetMovedByPlayer() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Parlor\n"
            + "Neighbours: Dining Hall, Kitchen\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Parlor"));

    testWorld.movePet("Dining Hall");

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Dining Hall"));

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: -\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Billiard Room"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Kitchen"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Parlor\n"
            + "Neighbours: Kitchen\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Parlor"));
  }

  @Test
  public void testPetMovesInDfs_PetMovedToIsolatedRoom() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));


    assertEquals("Name: Nursery\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Nursery"));

    testWorld.movePet("Nursery");

    assertEquals("Name: Nursery\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Nursery"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Nursery\n"
            + "Neighbours: -\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Nursery"));
  }

  @Test
  public void testPetMovesAfterLookAround() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.lookAroundSpace();

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));
  }

  @Test
  public void testPetMovesAfterMovePlayer() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.handleRoomClick(276, 210);

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));
  }

  @Test
  public void testPetMovesAfterPickWeapon() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.pickWeapon("pan");

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));

  }

  @Test
  public void testPetMovesAfterAttackTarget() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");

    testWorld.pickWeapon("pan");
    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    testWorld.attackTargetPlayer("pan");

    assertEquals("Name: Parlor\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: -\n"
            + "Players: -\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Parlor"));

  }

  @Test
  public void testPetMovesAfterMovePet() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");

    assertEquals("Name: Billiard Room\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Loud Noise (Damage: 2)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Billiard Room"));

    testWorld.movePet("Kitchen");

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No", testWorld.getRoomInformation("Kitchen"));

    assertEquals("Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Kitchen, Parlor\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Dining Hall"));
  }

  @Test
  public void testTargetPlayerMovesAfterPlayerMoves() {
    testWorld.addPlayerToGame("Test", 2, false, "Billiard Room");
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    testWorld.handleRoomClick(426, 372);
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testTargetPlayerMovesAfterPlayerPicksWeapon() {
    testWorld.addPlayerToGame("Test", 2, false, "Billiard Room");
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    testWorld.pickWeapon("loud noise");
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testTargetPlayerMovesAfterLookAround() {
    testWorld.addPlayerToGame("Test", 2, false, "Billiard Room");
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    testWorld.lookAroundSpace();
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testTargetPlayerMovesAfterPetMoves() {
    testWorld.addPlayerToGame("Test", 2, false, "Billiard Room");
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    testWorld.movePet("Nursery");
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Dining Hall", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testTargetPlayerMovesAfterAttackTarget() {
    testWorld.addPlayerToGame("Test", 2, false, "Kitchen");
    testWorld.pickWeapon("pan");
    testWorld.lookAroundSpace();
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 2\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
    testWorld.attackTargetPlayer("pan");
    assertEquals("Name: Doctor Lucky\n"
            + "Health: 0\n"
            + "Current Room: Kitchen", testWorld.getTargetPlayerDetails());
  }

  @Test
  public void testTurnAlternateBetweenPlayersInOrderAndEachPlayerGetsOneTurn() {
    testWorld.addPlayerToGame("P1", 2, false, "Kitchen");
    testWorld.addPlayerToGame("P2", 2, true, "Billiard room");
    testWorld.addPlayerToGame("P3", 2, false, "Dining Hall");

    assertEquals("P1", testWorld.getCurrentPlayerName());
    testWorld.lookAroundSpace();
    assertEquals("P2", testWorld.getCurrentPlayerName());
    testWorld.lookAroundSpace();
    assertEquals("P3", testWorld.getCurrentPlayerName());
    testWorld.lookAroundSpace();

    assertEquals("P1", testWorld.getCurrentPlayerName());
    testWorld.handleRoomClick(426, 372);
    assertEquals("P2", testWorld.getCurrentPlayerName());
    testWorld.handleRoomClick(426, 372);
    assertEquals("P3", testWorld.getCurrentPlayerName());
    testWorld.handleRoomClick(420, 642);

    assertEquals("P1", testWorld.getCurrentPlayerName());
    testWorld.lookAroundSpace();
    assertEquals("P2", testWorld.getCurrentPlayerName());
    testWorld.pickWeapon(null);
    assertEquals("P3", testWorld.getCurrentPlayerName());
    testWorld.pickWeapon("loud noise");

    assertEquals("P1", testWorld.getCurrentPlayerName());
    testWorld.movePet("Dining Hall");
    assertEquals("P2", testWorld.getCurrentPlayerName());
    testWorld.movePet("Dining Hall");
    assertEquals("P3", testWorld.getCurrentPlayerName());
    testWorld.movePet("Billiard Room");


    assertEquals("P1", testWorld.getCurrentPlayerName());
  }

  @Test
  public void testPlayerIconClicked_Successful() {
    testWorld.addPlayerToGame("P1", 2, false, "Billiard Room");
    assertTrue(testWorld.isPlayerIconClicked(472, 694));
  }

  @Test
  public void testPlayerIconClicked_InvalidRow() {
    assertThrows(IllegalArgumentException.class, () -> testWorld.isPlayerIconClicked(-10, 10));
  }

  @Test
  public void testPlayerIconClicked_InvalidColumn() {
    assertThrows(IllegalArgumentException.class, () -> testWorld.isPlayerIconClicked(10, -10));
  }

  @Test
  public void testPlayerIconClicked_PlayerDoesntExist() {
    assertThrows(IllegalArgumentException.class, () -> testWorld.isPlayerIconClicked(10, 10));
  }

  @Test
  public void testTakeTurnForComputerPlayer_AttackTarget_TargetInSameRoom_PlayerNotSeen() {
    RandomClass predictableRandom = new RandomClass(0);
    testWorld = new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            predictableRandom, maxNumberOfTurns);

    testWorld.addPlayerToGame("Test", 2, true, "Kitchen");
    testWorld.addPlayerToGame("Test2", 2, false, "Dining Hall");

    testWorld.pickWeapon(null);
    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    assertEquals("Attack on target was successful.", testWorld.takeTurnForComputerPlayer());

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 3\n"
            + "Current Room: Nursery", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test2\n"
            + "Current Room: Dining Hall\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testTakeTurnForComputerPlayer_MoveToNeighbor() {
    RandomClass predictableRandom = new RandomClass(1);
    testWorld = new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            predictableRandom, maxNumberOfTurns);

    testWorld.addPlayerToGame("Test", 2, true, "Kitchen");
    testWorld.addPlayerToGame("Test2", 2, false, "Dining Hall");

    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    String expected = "Player has successfully moved to room Parlor.";
    assertEquals(expected, testWorld.takeTurnForComputerPlayer());

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 6\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test2\n"
            + "Current Room: Dining Hall\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testTakeTurnForComputerPlayer_MovePet() {
    RandomClass predictableRandom = new RandomClass(3);
    testWorld = new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            predictableRandom, maxNumberOfTurns);

    testWorld.addPlayerToGame("Test", 2, true, "Kitchen");
    testWorld.addPlayerToGame("Test2", 2, false, "Dining Hall");

    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    String expected = "Player has successfully moved the pet to Nursery.";
    assertEquals(expected, testWorld.takeTurnForComputerPlayer());

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 6\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test2\n"
            + "Current Room: Dining Hall\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testTakeTurnForComputerPlayer_LookAround() {
    RandomClass predictableRandom = new RandomClass(0);
    testWorld = new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            predictableRandom, maxNumberOfTurns);

    testWorld.addPlayerToGame("Test", 2, true, "Kitchen");
    testWorld.addPlayerToGame("Test2", 2, false, "Dining Hall");

    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    String expected = "-----Look Around Details------\n"
            + "------Current Room Details------\n"
            + "Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes\n"
            + "\n"
            + "------Neighboring Room Details------\n"
            + "\n"
            + "Name: Dining Hall\n"
            + "Neighbours: Billiard Room, Parlor\n"
            + "Weapons: -\n"
            + "Players: Test2\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: No\n"
            + "\n"
            + "Name: Parlor\n"
            + "Neighbours: Dining Hall\n"
            + "Weapons: Rod (Damage: 1)\n"
            + "Players: -\n"
            + "Is Target Present: Yes\n"
            + "Is Pet Present: No\n";
    assertEquals(expected, testWorld.takeTurnForComputerPlayer());

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 6\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test2\n"
            + "Current Room: Dining Hall\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

  @Test
  public void testTakeTurnForComputerPlayer_PickWeapon() {
    RandomClass predictableRandom = new RandomClass(2);
    numWeapons++;
    weaponRoomIds.add(2);
    weaponDamageValues.add(4);
    weaponNames.add("Stick");
    testWorld = new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames, targetPetName,
            predictableRandom, maxNumberOfTurns);

    testWorld.addPlayerToGame("Test", 2, true, "Kitchen");
    testWorld.addPlayerToGame("Test2", 2, false, "Dining Hall");

    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();
    testWorld.lookAroundSpace();

    assertEquals("Name: Kitchen\n"
            + "Neighbours: Dining Hall, Parlor\n"
            + "Weapons: Stick (Damage: 4), Pan (Damage: 3), Rope (Damage: 2)\n"
            + "Players: Test\n"
            + "Is Target Present: No\n"
            + "Is Pet Present: Yes", testWorld.getRoomInformation("Kitchen"));

    String expected = "Player has successfully picked up Rope.";
    assertEquals(expected, testWorld.takeTurnForComputerPlayer());

    assertEquals("Name: Doctor Lucky\n"
            + "Health: 6\n"
            + "Current Room: Billiard Room", testWorld.getTargetPlayerDetails());
    assertEquals("Name: Test2\n"
            + "Current Room: Dining Hall\n"
            + "Weapons: -", testWorld.getCurrentPlayerInformation());
  }

}