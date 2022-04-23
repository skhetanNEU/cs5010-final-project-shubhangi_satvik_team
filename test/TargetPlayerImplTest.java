import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import model.room.RoomImpl;
import model.room.RoomInterface;
import model.target.TargetPlayerImpl;
import model.target.TargetPlayerInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class for the TargetPlayerImpl class.
 */
public class TargetPlayerImplTest {

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
  public void testTargetPlayerConstructor_Successful() {
    TargetPlayerInterface t = new TargetPlayerImpl("Dr. Lucky", 50, room0);
    String expectedString = "Name: Dr. Lucky\n"
            + "Health: 50\n"
            + "Current Room: Armory";
    assertEquals(expectedString, t.toString());
  }

  @Test
  public void testTargetPlayerConstructor_Unsuccessful_NameEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new TargetPlayerImpl("", 50, room0));
  }

  @Test
  public void testTargetPlayerConstructor_Unsuccessful_NameNull() {
    assertThrows(IllegalArgumentException.class, () -> new TargetPlayerImpl(null, 50, room0));
  }

  @Test
  public void testTargetPlayerConstructor_Unsuccessful_HealthNegative() {
    assertThrows(IllegalArgumentException.class,
            () -> new TargetPlayerImpl("Dr. Lucky", -50, room0));
  }

  @Test
  public void testTargetPlayerConstructor_Unsuccessful_HealthZero() {
    assertThrows(IllegalArgumentException.class, () -> new TargetPlayerImpl("Dr. Lucky", 0, room0));
  }

  @Test
  public void testTargetPlayerConstructor_Unsuccessful_RoomIdNotZero() {
    assertThrows(IllegalArgumentException.class, () ->
            new TargetPlayerImpl("Dr. Lucky", 10, room1));
  }

  @Test
  public void testTargetPlayerConstructor_Unsuccessful_RoomNull() {
    assertThrows(IllegalArgumentException.class, () ->
            new TargetPlayerImpl("Dr. Lucky", 10, null));
  }

  @Test
  public void testGetTargetPlayerHealth_Tc1() {
    TargetPlayerInterface t = new TargetPlayerImpl("Dr. Lucky", 50, room0);
    assertEquals(50, t.getTargetPlayerHealth());
  }

  @Test
  public void testGetTargetPlayerHealth_Tc2() {
    TargetPlayerInterface t = new TargetPlayerImpl("Doctor Lucky", 10, room0);
    assertEquals(10, t.getTargetPlayerHealth());
  }

  @Test
  public void testGetTargetPlayerHealth_Tc3() {
    TargetPlayerInterface t = new TargetPlayerImpl("doctor", 1, room0);
    assertEquals(1, t.getTargetPlayerHealth());
  }

  @Test
  public void testReduceTargetPlayerHealth_Unsuccessful_DamageDoneNegative() {
    TargetPlayerInterface t = new TargetPlayerImpl("doctor", 10, room0);
    assertThrows(IllegalArgumentException.class, () -> t.reduceTargetPlayerHealth(-2));
  }

  @Test
  public void testReduceTargetPlayerHealth_Successful_DamageDoneZero() {
    TargetPlayerInterface t = new TargetPlayerImpl("doctor", 10, room0);
    t.reduceTargetPlayerHealth(0);
    assertEquals(10, t.getTargetPlayerHealth());
  }

  @Test
  public void testReduceTargetPlayerHealth_Successful_DamageDonePositive_MoreThanTargetHealth() {
    TargetPlayerInterface t = new TargetPlayerImpl("doctor", 10, room0);
    t.reduceTargetPlayerHealth(100);
    assertEquals(0, t.getTargetPlayerHealth());
  }

  @Test
  public void testReduceTargetPlayerHealth_Successful_DamageDonePositive_EqualToTargetHealth() {
    TargetPlayerInterface t = new TargetPlayerImpl("doctor", 10, room0);
    t.reduceTargetPlayerHealth(10);
    assertEquals(0, t.getTargetPlayerHealth());
  }

  @Test
  public void testReduceTargetPlayerHealth_Successful_DamageDonePositive_LessThanTargetHealth() {
    TargetPlayerInterface t = new TargetPlayerImpl("doctor", 10, room0);
    t.reduceTargetPlayerHealth(2);
    assertEquals(8, t.getTargetPlayerHealth());
  }

  @Test
  public void testGetTargetPlayerRoom_Successful_RoomIdZero() {
    TargetPlayerInterface t1 = new TargetPlayerImpl("Dr. Lucky", 50, room0);
    assertEquals(0, t1.getTargetPlayerRoom().getRoomId());
  }

  @Test
  public void testGetTargetPlayerRoom_Successful_RoomIdNotZero() {
    TargetPlayerInterface t1 = new TargetPlayerImpl("Dr. Lucky", 50, room0);
    t1.setTargetPlayerRoom(room1);
    assertEquals(1, t1.getTargetPlayerRoom().getRoomId());
  }

  @Test
  public void testSetTargetPlayerRoom_Unsuccessful_Null() {
    TargetPlayerInterface t1 = new TargetPlayerImpl("Dr. Lucky", 50, room0);
    assertThrows(IllegalArgumentException.class, () -> t1.setTargetPlayerRoom(null));
  }

  @Test
  public void testSetTargetPlayerRoom_Successful_RoomIdNotZero() {
    TargetPlayerInterface t1 = new TargetPlayerImpl("Dr. Lucky", 50, room0);
    t1.setTargetPlayerRoom(room2);
    assertEquals("Name: Dr. Lucky\n"
            + "Health: 50\n"
            + "Current Room: Dining Hall", t1.toString());
  }

  @Test
  public void testSetTargetPlayerRoom_Successful_RoomIdZero() {
    TargetPlayerInterface t1 = new TargetPlayerImpl("Dr. Lucky", 50, room0);
    t1.setTargetPlayerRoom(room0);
    assertEquals("Name: Dr. Lucky\n"
            + "Health: 50\n"
            + "Current Room: Armory", t1.toString());
  }

  @Test
  public void testToString_Tc1() {
    TargetPlayerInterface t1 = new TargetPlayerImpl("Dr. Lucky", 50, room0);
    String expectedString1 = "Name: Dr. Lucky\n"
            + "Health: 50\n"
            + "Current Room: Armory";
    assertEquals(expectedString1, t1.toString());
  }

  @Test
  public void testToString_Tc2() {
    TargetPlayerInterface t2 = new TargetPlayerImpl("Doctor", 20, room0);
    String expectedString2 = "Name: Doctor\n"
            + "Health: 20\n"
            + "Current Room: Armory";
    assertEquals(expectedString2, t2.toString());
  }

  @Test
  public void testToString_Tc3() {
    TargetPlayerInterface t3 = new TargetPlayerImpl("Lucky", 1, room0);
    String expectedString3 = "Name: Lucky\n"
            + "Health: 1\n"
            + "Current Room: Armory";
    assertEquals(expectedString3, t3.toString());
  }

}