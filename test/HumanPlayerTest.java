import model.players.ComputerPlayer;
import model.players.HumanPlayer;
import model.random.RandomGenerator;
import model.world.WorldImpl;
import model.world.WorldInterface;
import model.worldbuilder.WorldBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.players.PlayerInterface;
import model.room.RoomImpl;
import model.room.RoomInterface;
import model.weapon.WeaponImpl;
import model.weapon.WeaponInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class HumanPlayerTest {
    private RoomInterface room0;
    private RoomInterface room1;
    private RoomInterface room2;

//    private WorldInterface testWorld;
//    private String worldName;
//    private List<Integer> worldCoordinates;
//    private int targetPlayerHealth;
//    private String targetPlayerName;
//    private int numRooms;
//    private List<List<Integer>> roomCoordinates;
//    private List<String> roomNames;
//    private int numWeapons;
//    private List<Integer> weaponRoomIds;
//    private List<Integer> weaponDamageValues;
//    private List<String> weaponNames;
//    private String targetPetName;

    /**
     * Method to initialize the variables for Rooms used in the test cases before they are run.
     */
    @Before
    public void setUp() throws FileNotFoundException {
        room0 = new RoomImpl(0, "Armory", new ArrayList<>(Arrays.asList(22, 19, 23, 26)));
        room1 = new RoomImpl(1, "Billiard Room", new ArrayList<>(Arrays.asList(16, 21, 21, 28)));
        room2 = new RoomImpl(2, "Dining Hall", new ArrayList<>(Arrays.asList(12, 11, 21, 20)));
        room2.addWeaponToRoom(1, "Ball", 4);

//        worldCoordinates = new ArrayList<>(Arrays.asList(36, 30));
//        worldName = "Doctor Lucky's Mansion";
//        targetPlayerHealth = 6;
//        targetPlayerName = "Doctor Lucky";
//
//        numRooms = 5;
//        roomCoordinates = new ArrayList<>();
//        roomCoordinates.add(Arrays.asList(16, 21, 21, 28));
//        roomCoordinates.add(Arrays.asList(12, 11, 21, 20));
//        roomCoordinates.add(Arrays.asList(16, 3, 21, 10));
//        roomCoordinates.add(Arrays.asList(0, 23, 3, 28));
//        roomCoordinates.add(Arrays.asList(10, 5, 15, 10));
//        roomNames = new ArrayList<>();
//        roomNames.add("Billiard Room");
//        roomNames.add("Dining Hall");
//        roomNames.add("Kitchen");
//        roomNames.add("Nursery");
//        roomNames.add("Parlor");
//
//        numWeapons = 5;
//        weaponRoomIds = new ArrayList<>(Arrays.asList(3, 2, 4, 2));
//        weaponDamageValues = new ArrayList<>(Arrays.asList(2, 3, 1, 2));
//        weaponNames = new ArrayList<>(Arrays.asList("Loud Noise", "Pan", "Rod", "Rope"));
//
//        targetPetName = "Fortune Cat";
//
//        Readable validFile = new StringReader("36 30 Doctor Lucky's Mansion\n"
//                + "2 Doctor Lucky\n"
//                + "Fortune Cat\n"
//                + "5\n"
//                + "16 21 21 28 Billiard Room\n"
//                + "12 11 21 20 Dining Hall\n"
//                + "16  3 21 10 Kitchen\n"
//                + " 0 23  3 28 Nursery\n"
//                + "10  5 15 10 Parlor\n"
//                + "4\n"
//                + "0 2 Loud Noise\n"
//                + "2 3 Pan\n"
//                + "1 1 Rod\n"
//                + "2 2 Rope");
//        WorldImpl.getBuilder()
//                .parseInputFile(validFile);
//        WorldImpl.getBuilder().setMaxTurns(10);
//        testWorld = WorldImpl.getBuilder().build();
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
                new HumanPlayer(null, 2, room0));;
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
        PlayerInterface p = new HumanPlayer("Test Human",1, room0);
        String expected = "Name: Test Human\n"
                + "Current Room: Armory\n"
                + "Weapons: -";
        assertEquals(expected, p.toString());
    }


    @Test
    public void testToString_ZeroWeapon() {
        PlayerInterface p = new HumanPlayer("Test Human",1, room0);
        String expected = "Name: Test Human\n"
                + "Current Room: Armory\n"
                + "Weapons: -";
        assertEquals(expected, p.toString());
    }

    @Test
    public void testToString_OneWeapon() {
        PlayerInterface p = new HumanPlayer("Test Human",1, room0);
        room0.addWeaponToRoom(2,"Stick",4);
        p.pickWeapon("Stick");
        String expected = "Name: Test Human\n"
                + "Current Room: Armory\n"
                + "Weapons: Stick (Damage: 4), Poke (Damage: 1)";
        assertEquals(expected, p.toString());
    }

    @Test
    public void testToString_MoreThanOneWeapon() {
        PlayerInterface p = new HumanPlayer("Test Human",2, room0);
        room0.addWeaponToRoom(2,"Stick",4);
        room0.addWeaponToRoom(3,"RAM",2);
        p.pickWeapon("Stick");
        p.pickWeapon("RAM");
        String expected = "Name: Test Human\n"
                + "Current Room: Armory\n"
                + "Weapons: Stick (Damage: 4), RAM (Damage: 2), Poke (Damage: 1)";
        assertEquals(expected, p.toString());
    }
}
