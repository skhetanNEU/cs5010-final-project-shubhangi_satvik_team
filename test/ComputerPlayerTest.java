import model.players.ComputerPlayer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import model.players.PlayerInterface;
import model.room.RoomImpl;
import model.room.RoomInterface;
import model.weapon.WeaponImpl;
import model.weapon.WeaponInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class ComputerPlayerTest {

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
        PlayerInterface t1 = new ComputerPlayer("Test Computer 1", 1, room0);
        String expectedString1 = "Name: Test Computer 1\n"
                + "Current Room: Armory\n"
                + "Weapons: -";
        assertEquals(expectedString1, t1.toString());

        PlayerInterface t2 = new ComputerPlayer("Test Computer 2", 2, room1);
        String expectedString2 = "Name: Test Computer 2\nCurrent Room: "
                + "Billiard Room\nWeapons: -";
        assertEquals(expectedString2, t2.toString());
    }

    @Test
    public void testPlayerConstructor_Unsuccessful_NameEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                new ComputerPlayer("", 1, room0));
    }

    @Test
    public void testPlayerConstructor_Unsuccessful_NameNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new ComputerPlayer(null, 1, room0));
    }

    @Test
    public void testPlayerConstructor_Unsuccessful_WeaponLimitNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new ComputerPlayer("P1", -10, room0));
    }

    @Test
    public void testPlayerConstructor_Unsuccessful_WeaponLimitZero() {
        assertThrows(IllegalArgumentException.class, () ->
                new ComputerPlayer("P1", 0, room0));
    }

    @Test
    public void testPlayerConstructor_Unsuccessful_RoomNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new ComputerPlayer("", 1, null));
    }

    @Test
    public void testGetPlayerName_Computer() {
        PlayerInterface t1 = new ComputerPlayer("Test Computer", 1, room0);
        assertEquals("Test Computer", t1.getPlayerName());
    }

    @Test
    public void testGetPlayerRoomName_Computer() {
        PlayerInterface p = new ComputerPlayer("Test Computer", 1, room0);
        assertEquals("Armory", p.getPlayerRoomName());
    }

    @Test
    public void testToString_Computer() {
        PlayerInterface p = new ComputerPlayer("Test Computer", 1, room0);
        String expected = "Name: Test Computer\n"
                + "Current Room: Armory\n"
                + "Weapons: -";
        assertEquals(expected, p.toString());
    }

    @Test
    public void testToString_OneWeapon() {
        PlayerInterface p = new ComputerPlayer("Test Computer", 1, room0);
        WeaponInterface w = new WeaponImpl(2, "Stick", 4);
        room0.addWeaponToRoom(2,"Stick",4);
        p.pickWeapon(null);
        String expected = "Name: Test Computer\n"
                + "Current Room: Armory\n"
                + "Weapons: Stick (Damage: 4)";
        assertEquals(expected, p.toString());
    }

    @Test
    public void testToString_ZeroWeapon() {
        PlayerInterface p = new ComputerPlayer("Test Computer", 1, room0);
        String expected = "Name: Test Computer\n"
                + "Current Room: Armory\n"
                + "Weapons: -";
        assertEquals(expected, p.toString());
    }
}
