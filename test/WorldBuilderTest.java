import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.StringReader;
import model.random.RandomClass;
import model.random.RandomGenerator;
import model.world.WorldImpl;
import org.junit.Test;

/**
 * A JUnit test class for the WorldBuilder class.
 */
public class WorldBuilderTest {

  @Test
  public void parseInputFileInvalid_FileNull() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(null));
    assertEquals("ERROR PARSING: Cannot find file.", exception.getMessage());
  }

  @Test
  public void parseInputFileValid() {
    Readable validFile = new StringReader("20 20 Test Mansion\n"
            + "10 Dr. Lucky\n"
            + "Fortune cat\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + "4\n"
            + "0 1 Brush\n"
            + "3 2 Dumbbells\n"
            + "1 1 Lamp\n"
            + "4 2 Wine Bottle");
    WorldImpl.getBuilder().parseInputFile(validFile);
  }

  @Test
  public void parseInputFileInvalid_WorldNameMissing() {
    Readable worldNameMissing = new StringReader("20 20\n"
            + "10 Dr. Lucky\n"
            + "Fortune cat\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + "4\n"
            + "0 1 Brush\n"
            + "3 2 Dumbbells\n"
            + "1 1 Lamp\n"
            + "4 2 Wine Bottle");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(worldNameMissing));
    assertEquals("ERROR PARSING: Error in world description.", exception.getMessage());
  }

  @Test
  public void parseInputFileInvalid_WorldCoordinatesMissing() {
    Readable worldCoordinatesMissing = new StringReader("20 Test Mansion\n"
            + "10 Dr. Lucky\n"
            + "Fortune cat\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + "4\n"
            + "0 1 Brush\n"
            + "3 2 Dumbbells\n"
            + "1 1 Lamp\n"
            + "4 2 Wine Bottle");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(worldCoordinatesMissing));
    assertEquals("ERROR PARSING: Error in world description.", exception.getMessage());
  }

  @Test
  public void parseInputFileInvalid_TargetPlayerNameMissing() {
    Readable targetPlayerNameMissing = new StringReader("20 20 Test Mansion\n"
            + "10\n"
            + "Fortune cat\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + "4\n"
            + "0 1 Brush\n"
            + "3 2 Dumbbells\n"
            + "1 1 Lamp\n"
            + "4 2 Wine Bottle");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(targetPlayerNameMissing));
    assertEquals("ERROR PARSING: Error in target player description.", exception.getMessage());
  }

  @Test
  public void parseInputFileInvalid_TargetPetNameMissing() {
    Readable targetPetNameMissing = new StringReader("20 20 Test Mansion\n"
            + "10 Dr. Lucky\n"
            + "\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + "4\n"
            + "0 1 Brush\n"
            + "3 2 Dumbbells\n"
            + "1 1 Lamp\n"
            + "4 2 Wine Bottle");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(targetPetNameMissing));
    assertEquals("ERROR PARSING: Error in target pet description.", exception.getMessage());
  }

  @Test
  public void parseInputFileInvalid_NumRoomsNotInt() {
    Readable numRoomsNotInt = new StringReader("20 20 Test Mansion\n"
            + "10 Dr. Lucky\n"
            + "Fortune cat\n"
            + "test\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + "4\n"
            + "0 1 Brush\n"
            + "3 2 Dumbbells\n"
            + "1 1 Lamp\n"
            + "4 2 Wine Bottle");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(numRoomsNotInt));
    assertEquals("ERROR PARSING: Error in reading number of rooms.", exception.getMessage());
  }

  @Test
  public void parseInputFileInvalid_RoomNameMissing() {
    Readable roomNameMissing = new StringReader("20 20 Test Mansion\n"
            + "10 Dr. Lucky\n"
            + "Fortune cat\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + "4\n"
            + "0 1 Brush\n"
            + "3 2 Dumbbells\n"
            + "1 1 Lamp\n"
            + "4 2 Wine Bottle");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(roomNameMissing));
    assertEquals("ERROR PARSING: Error in reading room data.", exception.getMessage());
  }

  @Test
  public void parseInputFileInvalid_RoomCoordinateMissing() {
    Readable roomCoordinateMissing = new StringReader("20 20 Test Mansion\n"
            + "10 Dr. Lucky\n"
            + "Fortune cat\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + "4\n"
            + "0 1 Brush\n"
            + "3 2 Dumbbells\n"
            + "1 1 Lamp\n"
            + "4 2 Wine Bottle");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(roomCoordinateMissing));
    assertEquals("ERROR PARSING: Error in reading room data.", exception.getMessage());
  }

  @Test
  public void parseInputFileInvalid_RoomDataLessThanNum() {
    Readable roomDataLessThanNum = new StringReader("20 20 Test Mansion\n"
            + "10 Dr. Lucky\n"
            + "Fortune cat\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "4\n"
            + "0 1 Brush\n"
            + "3 2 Dumbbells\n"
            + "1 1 Lamp\n"
            + "4 2 Wine Bottle");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(roomDataLessThanNum));
    assertEquals("ERROR PARSING: Error in reading room data.", exception.getMessage());
  }

  @Test
  public void parseInputFileInvalid_RoomsMoreThanNum() {
    Readable roomsMoreThanNum = new StringReader("20 20 Test Mansion\n"
            + "10 Dr. Lucky\n"
            + "Fortune cat\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + " 2  2  2  2 Test Room\n"
            + "4\n"
            + "0 1 Brush\n"
            + "3 2 Dumbbells\n"
            + "1 1 Lamp\n"
            + "4 2 Wine Bottle");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(roomsMoreThanNum));
    assertEquals("ERROR PARSING: Error in reading weapon data.", exception.getMessage());
  }

  @Test
  public void parseInputFileInvalid_NumWeaponsNotInt() {
    Readable numWeaponsNotInt = new StringReader("20 20 Test Mansion\n"
            + "10 Dr. Lucky\n"
            + "Fortune cat\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + "test\n"
            + "0 1 Brush\n"
            + "3 2 Dumbbells\n"
            + "1 1 Lamp\n"
            + "4 2 Wine Bottle");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(numWeaponsNotInt));
    assertEquals("ERROR PARSING: Error in reading number of weapons.", exception.getMessage());
  }

  @Test
  public void parseInputFileInvalid_WeaponNameMissing() {
    Readable weaponNameMissing = new StringReader("20 20 Test Mansion\n"
            + "10 Dr. Lucky\n"
            + "Fortune cat\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + "4\n"
            + "0 1 Brush\n"
            + "3 2\n"
            + "1 1 Lamp\n"
            + "4 2 Wine Bottle");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(weaponNameMissing));
    assertEquals("ERROR PARSING: Error in reading weapon data.", exception.getMessage());
  }

  @Test
  public void parseInputFileInvalid_WeaponDamageMissing() {
    Readable weaponDamageMissing = new StringReader("20 20 Test Mansion\n"
            + "10 Dr. Lucky\n"
            + "Fortune cat\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + "4\n"
            + "0 1 Brush\n"
            + "3 2 Dumbbells\n"
            + "1 Lamp\n"
            + "4 2 Wine Bottle");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(weaponDamageMissing));
    assertEquals("ERROR PARSING: Error in reading weapon data.", exception.getMessage());
  }

  @Test
  public void parseInputFileInvalid_WeaponRoomIdMissing() {
    Readable weaponRoomIdMissing = new StringReader("20 20 Test Mansion\n"
            + "10 Dr. Lucky\n"
            + "Fortune cat\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + "4\n"
            + "0 1 Brush\n"
            + "  2 Dumbbells\n"
            + "1 1 Lamp\n"
            + "4 2 Wine Bottle");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(weaponRoomIdMissing));
    assertEquals("ERROR PARSING: Error in reading weapon data.", exception.getMessage());
  }

  @Test
  public void parseInputFileInvalid_WeaponDataLessThanNum() {
    Readable weaponDataLessThanNum = new StringReader("20 20 Test Mansion\n"
            + "10 Dr. Lucky\n"
            + "Fortune cat\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + "4\n"
            + "0 1 Brush\n"
            + "3 2 Dumbbells\n"
            + "1 1 Lamp");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().parseInputFile(weaponDataLessThanNum));
    assertEquals("ERROR PARSING: Error in reading weapon data.", exception.getMessage());
  }

  @Test
  public void setRandomGenerator_Null() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().setRandomGenerator(null));
    assertEquals("Random number generator cannot be null.", exception.getMessage());
  }

  @Test
  public void setRandomGenerator_TrueRandom() {
    RandomGenerator r = new RandomClass();
    WorldImpl.getBuilder().setRandomGenerator(r);
  }

  @Test
  public void setRandomGenerator_FalseRandom() {
    RandomGenerator r = new RandomClass(2);
    WorldImpl.getBuilder().setRandomGenerator(r);
  }

  @Test
  public void testSetMaxTurns_Negative() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().setMaxTurns(-2));
    assertEquals("Number of turns cannot be non-positive.", exception.getMessage());
    ;
  }

  @Test
  public void testSetMaxTurns_Zero() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> WorldImpl.getBuilder().setMaxTurns(0));
    assertEquals("Number of turns cannot be non-positive.", exception.getMessage());
  }

  @Test
  public void testSetMaxTurns_Positive() {
    WorldImpl.getBuilder().setMaxTurns(2);
  }

  @Test
  public void build() {
    Readable validFile = new StringReader("40 40 Test Mansion\n"
            + "10 Dr. Lucky\n"
            + "Fortune cat\n"
            + "5\n"
            + " 4  0 14  3 Bathroom\n"
            + " 4  4 14 15 Bedroom\n"
            + "15  0 18 17 Dining Room\n"
            + " 4 16  9 30 Gym\n"
            + "10 16 14 20 Wine Cellar\n"
            + "4\n"
            + "0 1 Brush\n"
            + "3 2 Dumbbells\n"
            + "1 1 Lamp\n"
            + "4 2 Wine Bottle");
    RandomGenerator r = new RandomClass();
    WorldImpl.getBuilder().parseInputFile(validFile).setRandomGenerator(r).setMaxTurns(2).build();
  }

}