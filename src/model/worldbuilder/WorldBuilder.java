package model.worldbuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import model.random.RandomGenerator;
import model.world.WorldImpl;

/**
 * Builder class used to build the world from the input text file.
 */
public class WorldBuilder {

  private final List<Integer> worldCoordinates;
  private String worldName;
  private int targetPlayerHealth;
  private String targetPlayerName;
  private String targetPetName;
  private int numRooms;
  private final List<List<Integer>> roomCoordinates;
  private final List<String> roomNames;
  private int numWeapons;
  private final List<Integer> weaponRoomIds;
  private final List<Integer> weaponDamageValues;
  private final List<String> weaponNames;
  private RandomGenerator random;
  private int maxTurns;

  /**
   * Construct a WorldBuilder object used to initialize the values
   * of the variables for constructing the world.
   */
  public WorldBuilder() {
    worldCoordinates = new ArrayList<>();
    worldName = "";
    targetPlayerHealth = 0;
    targetPlayerName = "";
    targetPetName = "";
    numRooms = 0;
    roomCoordinates = new ArrayList<>();
    roomNames = new ArrayList<>();
    numWeapons = 0;
    weaponRoomIds = new ArrayList<>();
    weaponDamageValues = new ArrayList<>();
    weaponNames = new ArrayList<>();
    maxTurns = 0;
  }

  /**
   * A public method used to parse the input text file and
   * set the variables required to instantiate the world.
   *
   * @param file Input text file as a Readable
   * @return Reference of the WorldBuilder Class
   * @throws IllegalArgumentException if there is an error in input file
   */
  public WorldBuilder parseInputFile(Readable file) throws IllegalArgumentException {

    if (file == null) {
      throw new IllegalArgumentException("ERROR PARSING: Cannot find file.");
    }

    Scanner scanner = new Scanner(file);
    CurrentIteration currentIteration = CurrentIteration.NONE;
    int lineNumber = 1;

    while (scanner.hasNext()) {
      if (lineNumber == 1) {
        try {
          worldCoordinates.add(scanner.nextInt());
          worldCoordinates.add(scanner.nextInt());
          worldName = scanner.nextLine().trim();
          if ("".equals(worldName)) {
            throw new IllegalArgumentException();
          }
          lineNumber += 1;
        } catch (IllegalArgumentException | NoSuchElementException | IllegalStateException e) {
          throw new IllegalArgumentException("ERROR PARSING: Error in world description.");
        }
      } else if (lineNumber == 2) {
        try {
          targetPlayerHealth = scanner.nextInt();
          targetPlayerName = scanner.nextLine().trim();
          if ("".equals(targetPlayerName)) {
            throw new IllegalArgumentException();
          }
          lineNumber += 1;
        } catch (IllegalArgumentException | NoSuchElementException | IllegalStateException e) {
          throw new IllegalArgumentException(
                  "ERROR PARSING: Error in target player description.");
        }
      } else if (lineNumber == 3) {
        try {
          targetPetName = scanner.nextLine().trim();
          if ("".equals(targetPetName)) {
            throw new IllegalArgumentException();
          }
          lineNumber += 1;
        } catch (IllegalArgumentException | NoSuchElementException | IllegalStateException e) {
          throw new IllegalArgumentException(
                  "ERROR PARSING: Error in target pet description.");
        }
      } else if (lineNumber == 4 && currentIteration == CurrentIteration.NONE) {
        try {
          numRooms = scanner.nextInt();
          currentIteration = CurrentIteration.ROOMS;
          scanner.nextLine();
          lineNumber += 1;
        } catch (IllegalArgumentException | NoSuchElementException | IllegalStateException e) {
          throw new IllegalArgumentException(
                  "ERROR PARSING: Error in reading number of rooms.");
        }
      } else {
        if (currentIteration == CurrentIteration.ROOMS) {
          int n = numRooms;
          try {
            while (scanner.hasNext() && n > 0) {
              List<Integer> coordinates = new ArrayList<>();
              coordinates.add(scanner.nextInt());
              coordinates.add(scanner.nextInt());
              coordinates.add(scanner.nextInt());
              coordinates.add(scanner.nextInt());
              roomCoordinates.add(coordinates);
              String name = scanner.nextLine().trim();
              if ("".equals(name)) {
                throw new IllegalArgumentException();
              }
              roomNames.add(name);
              lineNumber += 1;
              n -= 1;
            }
            currentIteration = CurrentIteration.WEAPONS;
          } catch (IllegalArgumentException | NoSuchElementException | IllegalStateException e) {
            throw new IllegalArgumentException("ERROR PARSING: Error in reading room data.");
          }
          try {
            numWeapons = scanner.nextInt();
          } catch (IllegalArgumentException | NoSuchElementException | IllegalStateException e) {
            throw new IllegalArgumentException(
                    "ERROR PARSING: Error in reading number of weapons.");
          }
          if (!"".equals(scanner.nextLine())) {
            throw new IllegalArgumentException("ERROR PARSING: Error in reading weapon data.");
          }
          lineNumber += 1;
        } else if (currentIteration == CurrentIteration.WEAPONS) {
          int n = numWeapons;
          try {
            while (scanner.hasNext() && n > 0) {
              weaponRoomIds.add(scanner.nextInt());
              weaponDamageValues.add(scanner.nextInt());
              String weaponName = scanner.nextLine().trim();
              if ("".equals(weaponName)) {
                throw new IllegalArgumentException();
              }
              weaponNames.add(weaponName);

              lineNumber += 1;
              n -= 1;
            }
            if (n != 0) {
              throw new IllegalArgumentException("ERROR PARSING: Error reading weapon data.");
            }
          } catch (IllegalArgumentException | NoSuchElementException | IllegalStateException e) {
            throw new IllegalArgumentException("ERROR PARSING: Error in reading weapon data.");
          }
        }
      }
    }
    scanner.close();
    return this;
  }

  /**
   * A public method used to set the random number generator in the world.
   *
   * @param rand represents the random class
   * @return Reference of the WorldBuilder Class
   * @throws IllegalArgumentException if the random class is null
   */
  public WorldBuilder setRandomGenerator(RandomGenerator rand) throws IllegalArgumentException {
    if (rand == null) {
      throw new IllegalArgumentException("Random number generator cannot be null.");
    }
    this.random = rand;
    return this;
  }

  public WorldBuilder setMaxTurns(int maxTurns) throws IllegalArgumentException {
    if (maxTurns <= 0) {
      throw new IllegalArgumentException("Number of turns cannot be non-positive.");
    }
    this.maxTurns = maxTurns;
    return this;
  }

  /**
   * A method used to create an instance of WorldInterface based on the variables of WorldBuilder.
   *
   * @return New instance of WorldImpl
   */
  public WorldImpl build() {
    return new WorldImpl(worldCoordinates, worldName,
            targetPlayerHealth, targetPlayerName,
            numRooms, roomCoordinates, roomNames,
            numWeapons, weaponRoomIds, weaponDamageValues, weaponNames,
            targetPetName, random, maxTurns);
  }

}