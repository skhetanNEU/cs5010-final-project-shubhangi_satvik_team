import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import model.pet.PetImpl;
import model.pet.PetInterface;
import model.room.RoomImpl;
import model.room.RoomInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class for the PetImpl class.
 */
public class PetImplTest {

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
  public void testPetConstructor_Successful() {
    PetInterface p = new PetImpl("Fortune Cat", room0);
    String expectedString = "Pet Name: Fortune Cat\nPet Current Room: Armory";
    assertEquals(expectedString, p.toString());
  }

  @Test
  public void testPetConstructor_Unsuccessful_NameEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new PetImpl("", room0));
  }

  @Test
  public void testPetConstructor_Unsuccessful_NameNull() {
    assertThrows(IllegalArgumentException.class, () -> new PetImpl(null, room0));
  }

  @Test
  public void testPetConstructor_Unsuccessful_RoomIdNotZero() {
    assertThrows(IllegalArgumentException.class, () -> new PetImpl("Fortune Cat", room1));
  }

  @Test
  public void testPetConstructor_Unsuccessful_RoomNull() {
    assertThrows(IllegalArgumentException.class, () -> new PetImpl("Fortune Cat", null));
  }

  @Test
  public void testGetPetRoomName_RoomIdZero() {
    PetInterface p = new PetImpl("Fortune Cat 2", room0);
    assertEquals("Armory", p.getPetRoomName());
  }

  @Test
  public void testGetPetRoomName_RoomIdNotZero() {
    PetInterface p = new PetImpl("Fortune Cat 2", room0);
    p.setPetRoom(room2);
    assertEquals("Dining Hall", p.getPetRoomName());
  }

  @Test
  public void testSetPetRoom_Unsuccessful_Null() {
    PetInterface p = new PetImpl("Fortune Cat 2", room0);
    assertThrows(IllegalArgumentException.class, () -> p.setPetRoom(null));
  }

  @Test
  public void testSetPetRoom_Successful_RoomIdNotZero() {
    PetInterface p = new PetImpl("Fortune Cat 2", room0);
    p.setPetRoom(room2);
    assertEquals("Pet Name: Fortune Cat 2\nPet Current Room: Dining Hall", p.toString());
  }

  @Test
  public void testSetPetRoom_Successful_RoomIdZero() {
    PetInterface p = new PetImpl("Fortune Cat 2", room0);
    p.setPetRoom(room1);
    assertEquals("Billiard Room", p.getPetRoomName());
    p.setPetRoom(room0);
    assertEquals("Pet Name: Fortune Cat 2\nPet Current Room: Armory", p.toString());
  }

  @Test
  public void testToString_Tc1() {
    PetInterface p = new PetImpl("Fortune Cat 2", room0);
    assertEquals("Pet Name: Fortune Cat 2\nPet Current Room: Armory", p.toString());
  }

  @Test
  public void testToString_Tc2() {
    PetInterface p = new PetImpl("Fortune Cat", room0);
    p.setPetRoom(room1);
    assertEquals("Pet Name: Fortune Cat\nPet Current Room: Billiard Room", p.toString());
  }

  @Test
  public void testToString_Tc3() {
    PetInterface p = new PetImpl("Fortune", room0);
    p.setPetRoom(room1);
    p.setPetRoom(room2);
    assertEquals("Pet Name: Fortune\nPet Current Room: Dining Hall", p.toString());
  }

}