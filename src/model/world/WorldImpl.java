package model.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import model.pet.PetImpl;
import model.pet.PetInterface;
import model.players.ComputerPlayer;
import model.players.HumanPlayer;
import model.players.PlayerInterface;
import model.random.RandomGenerator;
import model.room.RoomImpl;
import model.room.RoomInterface;
import model.target.TargetPlayerImpl;
import model.target.TargetPlayerInterface;
import model.worldbuilder.WorldBuilder;

/**
 * WorldImpl represents the entire world of Kill Doctor Lucky.
 * It consists of world name, world coordinates, list of rooms and target player data.
 */
public class WorldImpl implements WorldInterface {

  private final String worldName;
  private final List<Integer> worldCoordinates;
  private final List<RoomInterface> rooms;
  private PetInterface targetPet;
  private TargetPlayerInterface targetPlayer;
  private final List<PlayerInterface> players;
  private PlayerInterface currentTurn;
  private int dfsStartRoom;
  private final Stack<Integer> dfsStack;
  private final Set<Integer> dfsVisited;
  private BufferedImage worldView;
  private BufferedImage playersView;
  private final int zoomFactor;
  private final RandomGenerator random;
  private int remainingTurns;

  /**
   * Construct a WorldImpl object that represents the world.
   *
   * @param worldCoordinates   represents the coordinates of the world
   * @param worldName          represents the name of the world
   * @param targetPlayerHealth represents the health of the target player
   * @param targetPlayerName   represents the name of the target player
   * @param numRooms           represents the number of rooms in the world
   * @param roomCoordinates    represents the list of coordinates of each rooms in the world
   * @param roomNames          represents the list of name of each rooms in the world
   * @param numWeapons         represents the number of weapons in the world
   * @param weaponRoomIds      represents the list of id of the room each weapon is in
   * @param weaponDamageValues represents the list of damage values of each weapon
   * @param weaponNames        represents the list of name of each weapon
   * @throws IllegalArgumentException when any of the parameter is invalid.
   */
  public WorldImpl(List<Integer> worldCoordinates, String worldName,
                   int targetPlayerHealth, String targetPlayerName,
                   int numRooms, List<List<Integer>> roomCoordinates, List<String> roomNames,
                   int numWeapons, List<Integer> weaponRoomIds, List<Integer> weaponDamageValues,
                   List<String> weaponNames, String targetPetName, RandomGenerator random,
                   int maxTurns) throws IllegalArgumentException {
    if (worldCoordinates == null || worldCoordinates.size() == 0) {
      throw new IllegalArgumentException("World coordinates cannot be null/empty.");
    } else if (worldName == null || ("").equals(worldName)) {
      throw new IllegalArgumentException("World name cannot be null/empty.");
    } else if (targetPlayerHealth <= 0) {
      throw new IllegalArgumentException("Target player health cannot be non-positive.");
    } else if (targetPlayerName == null || "".equals(targetPlayerName)) {
      throw new IllegalArgumentException("Target player name cannot be null/empty.");
    } else if (numRooms <= 0) {
      throw new IllegalArgumentException("Number of rooms cannot be non-positive.");
    } else if (roomCoordinates == null) {
      throw new IllegalArgumentException("Room coordinates cannot be null.");
    } else if (roomNames == null) {
      throw new IllegalArgumentException("Room names cannot be null.");
    } else if (numWeapons <= 0) {
      throw new IllegalArgumentException("Number of weapons cannot be non-positive.");
    } else if (weaponRoomIds == null) {
      throw new IllegalArgumentException("Weapon room id cannot be null.");
    } else if (weaponDamageValues == null) {
      throw new IllegalArgumentException("Weapon damage values cannot be null.");
    } else if (weaponNames == null) {
      throw new IllegalArgumentException("Weapon room names cannot be null.");
    } else if (targetPetName == null || "".equals(targetPetName)) {
      throw new IllegalArgumentException("Target pet name cannot be null/empty.");
    } else if (random == null) {
      throw new IllegalArgumentException("Random cannot be null.");
    } else if (maxTurns <= 0) {
      throw new IllegalArgumentException("Number of turns cannot be non-positive.");
    }

    this.worldName = worldName;
    this.worldCoordinates = new ArrayList<>(worldCoordinates);
    this.rooms = new ArrayList<>();
    this.players = new ArrayList<>();
    this.currentTurn = null;
    this.random = random;
    this.remainingTurns = maxTurns;

    initializeRooms(roomCoordinates, roomNames, worldCoordinates);
    initializeWeapons(numRooms, weaponNames, weaponDamageValues, weaponRoomIds);
    initializeTargetPlayer(targetPlayerName, targetPlayerHealth);
    initializeTargetPet(targetPetName);
    calculateNeighboursOfAllRooms();

    this.dfsStartRoom = 0;
    this.dfsVisited = new HashSet<>();
    this.dfsStack = new Stack<>();
    this.dfsVisited.add(0);
    this.dfsStack.push(0);

    this.zoomFactor = 24;
    this.worldView = new BufferedImage(
            (worldCoordinates.get(1) + 1) * zoomFactor,
            (worldCoordinates.get(0) + 1) * zoomFactor,
            BufferedImage.TYPE_INT_RGB);
    this.playersView = new BufferedImage(
            (worldCoordinates.get(1) + 1) * zoomFactor,
            (worldCoordinates.get(0) + 1) * zoomFactor,
            BufferedImage.TYPE_INT_ARGB);
    drawWorld();
  }

  /**
   * Gives a WorldBuilder object used to initialize the values to construct the World
   * that we have a way to get the builder.
   *
   * @return a builder to use to create a Customer.
   */
  public static WorldBuilder getBuilder() {
    return new WorldBuilder();
  }

  /*
   *************************
   * HELPER METHODS TO
   * INITIALIZE WORLD
   *************************
   */

  /**
   * Helper class that creates the rooms of the world.
   *
   * @param roomCoordinates  represents the list of coordinates of each rooms in the world
   * @param roomNames        represents the list of name of each rooms in the world
   * @param worldCoordinates represents the coordinates of the world
   * @throws IllegalArgumentException if 2 rooms have the same name.
   */
  private void initializeRooms(List<List<Integer>> roomCoordinates, List<String> roomNames,
                               List<Integer> worldCoordinates) throws IllegalArgumentException {

    for (int i = 0; i < roomNames.size(); i++) {
      String name = roomNames.get(i);
      List<Integer> coordinates = roomCoordinates.get(i);
      validateRoomWithWorld(coordinates, worldCoordinates);
      RoomInterface newRoom = new RoomImpl(i, name, coordinates);
      if (roomNames.stream().filter(name::equalsIgnoreCase).count() > 1) {
        throw new IllegalArgumentException("World cannot have rooms with same name.");
      }
      this.rooms.add(newRoom);
    }
  }

  /**
   * Helper class that initializes the weapons of the world in each room.
   *
   * @param numRooms           represents the name of the player
   * @param weaponRoomIds      represents the list of id of the room each weapon is in
   * @param weaponDamageValues represents the list of damage values of each weapon
   * @param weaponNames        represents the list of name of each weapon
   * @throws IllegalArgumentException when the weapon id or weapon name is invalid.
   */
  private void initializeWeapons(int numRooms, List<String> weaponNames,
                                 List<Integer> weaponDamageValues, List<Integer> weaponRoomIds)
          throws IllegalArgumentException {
    for (int i = 0; i < weaponNames.size(); i++) {
      String weaponName = weaponNames.get(i);
      int weaponRoomId = weaponRoomIds.get(i);
      int weaponDamageValue = weaponDamageValues.get(i);
      if (weaponRoomId < 0 || weaponRoomId >= numRooms) {
        throw new IllegalArgumentException("Room id for weapon is invalid.");
      }
      if (weaponNames.stream().filter(weaponName::equalsIgnoreCase).count() > 1) {
        throw new IllegalArgumentException("World cannot have weapons with same name.");
      }
      RoomInterface weaponRoom = rooms.get(weaponRoomId);
      weaponRoom.addWeaponToRoom(i, weaponName, weaponDamageValue);
    }
  }


  /**
   * Helper class that initializes the target pet.
   *
   * @param targetPlayerName represents the name of the pet
   * @throws IllegalArgumentException when the target name or health is invalid.
   */
  private void initializeTargetPlayer(String targetPlayerName, int targetPlayerHealth) throws IllegalArgumentException {
    this.targetPlayer = new TargetPlayerImpl(
            targetPlayerName, targetPlayerHealth, this.rooms.get(0)
    );
  }

  /**
   * Helper class that initializes the target pet.
   *
   * @param targetPetName represents the name of the player
   */
  private void initializeTargetPet(String targetPetName) {
    this.targetPet = new PetImpl(targetPetName, this.rooms.get(0));
  }

  /**
   * Helper class that calculate the neighbours of all rooms in the world.
   */
  private void calculateNeighboursOfAllRooms() {

    for (RoomInterface mainRoom : this.rooms) {
      List<RoomInterface> neighbours = new ArrayList<>();
      for (RoomInterface otherRoom : this.rooms) {
        if (mainRoom.getRoomId() != otherRoom.getRoomId()) {

          List<Integer> mainRoomCoordinates = mainRoom.getRoomCoordinates();
          List<Integer> otherRoomCoordinates = otherRoom.getRoomCoordinates();

          int room1row1 = mainRoomCoordinates.get(0);
          int room1col1 = mainRoomCoordinates.get(1);
          int room1row2 = mainRoomCoordinates.get(2);
          int room1col2 = mainRoomCoordinates.get(3);
          int room2row1 = otherRoomCoordinates.get(0);
          int room2col1 = otherRoomCoordinates.get(1);
          int room2row2 = otherRoomCoordinates.get(2);
          int room2col2 = otherRoomCoordinates.get(3);

          validateNonOverlappingRooms(room1row1, room1col1, room1row2, room1col2,
                  room2row1, room2col1, room2row2, room2col2);

          // Vertical Neighbours
          if (((room2row1 == room1row2 + 1) || (room2row2 == room1row1 - 1))
                  && (
                  Objects.equals(room2col1, room1col1)
                          || Objects.equals(room2col2, room1col2)
                          || ((room1col1 <= room2col1) && (room2col1 <= room1col2))
                          || ((room1col1 <= room2col2) && (room2col2 <= room1col2)))) {
            neighbours.add(otherRoom);
          }

          // Horizontal Neighbours
          if (((room2col1 == room1col2 + 1) || (room2col2 == room1col1 - 1))
                  && (
                  Objects.equals(room2row1, room1row1)
                          || Objects.equals(room2row2, room1row2)
                          || ((room1row1 <= room2row1) && (room2row1 <= room1row2))
                          || ((room1row1 <= room2row2) && (room2row2 <= room1row2)))) {
            neighbours.add(otherRoom);
          }
        }
        mainRoom.setRoomNeighbours(neighbours);
      }
    }
  }

  /**
   * Validates that room size is in accordance with the world dimensions.
   *
   * @param coordinates      represents the coordinates of a room in the world
   * @param worldCoordinates represents the coordinates of the world
   * @throws IllegalArgumentException if the room is bigger than the world
   */
  private void validateRoomWithWorld(List<Integer> coordinates, List<Integer> worldCoordinates)
          throws IllegalArgumentException {
    int worldRow = worldCoordinates.get(0);
    int worldCol = worldCoordinates.get(1);
    int row1 = coordinates.get(0);
    int col1 = coordinates.get(1);
    int row2 = coordinates.get(2);
    int col2 = coordinates.get(3);
    if (row1 >= worldRow || row2 >= worldRow
            || col1 >= worldCol || col2 >= worldCol
            || row2 - row1 > worldRow || col2 - col1 > worldCol) {
      throw new IllegalArgumentException("Room cannot be bigger than the world.");
    }
  }

  /**
   * Validates that 2 rooms are not overlapping.
   *
   * @param room1col1 represents the left col number of room 1
   * @param room1col2 represents the right col number of room 1
   * @param room1row1 represents the left row number of room 1
   * @param room1row2 represents the right row number of room 1
   * @param room2col1 represents the left col number of room 2
   * @param room2col2 represents the right col number of room 2
   * @param room2row1 represents the left row number of room 2
   * @param room2row2 represents the right row number of room 2
   */
  private void validateNonOverlappingRooms(int room1row1, int room1col1,
                                           int room1row2, int room1col2,
                                           int room2row1, int room2col1,
                                           int room2row2, int room2col2)
          throws IllegalArgumentException {
    boolean rowConditions = (room1row1 <= room2row1 && room2row1 <= room1row2)
            || (room1row1 <= room2row2 && room2row2 <= room1row2)
            || (room2row1 <= room1row1 && room1row1 <= room2row2)
            || (room2row1 <= room1row2 && room1row2 <= room2row2);
    boolean colConditions = (room1col1 <= room2col1 && room2col1 <= room1col2)
            || (room1col1 <= room2col2 && room2col2 <= room1col2)
            || (room2col1 <= room1col1 && room1col1 <= room2col2)
            || (room2col1 <= room1col2 && room1col2 <= room2col2);
    if (rowConditions && colConditions) {
      throw new IllegalArgumentException("Rooms in world are overlapping.");
    }
  }

  /**
   * Checks if there are any players in the world with same name.
   *
   * @param playerName name of the player
   * @throws IllegalArgumentException when the player with same name already exists.
   */
  private void checkIfPlayerAlreadyExistsWithSameName(String playerName) throws IllegalArgumentException {
    for (PlayerInterface player : this.players) {
      if (player.getPlayerName().equalsIgnoreCase(playerName)) {
        throw new IllegalArgumentException("Player with same name already exists.");
      }
    }
  }

  /**
   * Draw the world
   */
  private void drawWorld() {
    Graphics g = worldView.getGraphics();
    g.setFont(g.getFont().deriveFont(10f));
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, worldView.getWidth(), worldView.getHeight());
    for (RoomInterface room : this.rooms) {
      List<Integer> roomCoordinates = getRoomViewCoordinates(room);
      g.setColor(Color.WHITE);
      g.drawRect(roomCoordinates.get(2), roomCoordinates.get(0),
              roomCoordinates.get(3) - roomCoordinates.get(2),
              roomCoordinates.get(1) - roomCoordinates.get(0));
      g.drawString(room.getRoomName(), roomCoordinates.get(2) + 5, roomCoordinates.get(0) + 15);
    }
  }

  /*
   *************************
   * OTHER HELPER METHODS
   *************************
   */

  /**
   * Checks if there is alteast one player to take a turn and play the game.
   */
  private void checkIfPlayersExistToPlayGame() throws IllegalArgumentException {
    if (this.players.size() <= 0) {
      throw new IllegalArgumentException("There are no players in the game.");
    }
  }

  /**
   * Gets the next player in turn.
   *
   * @return the next player
   */
  private PlayerInterface getNextTurnPlayer() {
    int playerIndex = players.indexOf(currentTurn);
    int nextPlayerIndex = (playerIndex + 1) % this.players.size();
    return players.get(nextPlayerIndex);
  }

  /**
   * Moves the target player to the next room.
   */
  private void moveTargetPlayer() {
    RoomInterface targetPlayerCurrentLocation = this.targetPlayer.getTargetPlayerRoom();
    int targetPlayerLocationId = targetPlayerCurrentLocation.getRoomId();
    int targetPlayerNextLocationId = (targetPlayerLocationId + 1) % this.rooms.size();
    RoomInterface targetPlayerNextLocation = this.rooms.get(targetPlayerNextLocationId);
    targetPlayerCurrentLocation.updateTargetPlayerPresence(false);
    targetPlayerNextLocation.updateTargetPlayerPresence(true);
    targetPlayer.setTargetPlayerRoom(targetPlayerNextLocation);
  }

  /**
   * Moves the target pet using DFS.
   */
  private void movePetAfterTurnDfs() {
    if (this.dfsStack.size() == 1) {
      this.dfsVisited.clear();
      this.dfsVisited.add(this.dfsStartRoom);
    }
    if (!this.dfsStack.isEmpty()) {
      Integer currentRoomId = this.dfsStack.peek();
      RoomInterface currentRoom = this.rooms.get(currentRoomId);
      List<String> neighbours = currentRoom.getRoomNeighbours(true);
      if (neighbours.size() > 0) {
        for (String n : neighbours) {
          RoomInterface neighbour = getRoomByRoomName(n);
          if (!this.dfsVisited.contains(neighbour.getRoomId())) {
            targetPet.setPetRoom(neighbour);
            this.dfsVisited.add(neighbour.getRoomId());
            this.dfsStack.push(neighbour.getRoomId());
            return;
          }
        }
      }
      if (this.dfsStack.size() != 1) {
        this.dfsStack.pop();
        if (!this.dfsStack.isEmpty()) {
          Integer newRoomId = this.dfsStack.peek();
          RoomInterface newRoom = this.rooms.get(newRoomId);
          targetPet.setPetRoom(newRoom);
        }
      }
    } else {
      this.dfsVisited.clear();
      this.dfsStack.push(this.dfsStartRoom);
      this.dfsVisited.add(this.dfsStartRoom);
      targetPet.setPetRoom(this.rooms.get(this.dfsStartRoom));
    }
  }

  /**
   * Get the view coordinates of the room.
   * @param room Room whose coordinates are required.
   * @return List of integer representing the coordinates of the room.
   * @throws IllegalArgumentException When the room is null.
   */
  private List<Integer> getRoomViewCoordinates(RoomInterface room) throws IllegalArgumentException {
    if (room == null) {
      throw new IllegalArgumentException("Unable to get coordinates. Room is null.");
    }
    List<Integer> roomCoordinates = room.getRoomCoordinates();
    int r1 = (roomCoordinates.get(0) * zoomFactor) - zoomFactor / 2;
    int r2 = (roomCoordinates.get(2) * zoomFactor) + zoomFactor / 2;
    int c1 = (roomCoordinates.get(1) * zoomFactor) - zoomFactor / 2 + 30;
    int c2 = (roomCoordinates.get(3) * zoomFactor) + zoomFactor / 2 + 30;

    return new ArrayList<>(Arrays.asList(r1, r2, c1, c2));

  }

  /**
   * Get the room where the click has been made.
   * @param r Row coordinate of the click
   * @param c Column coordinate of the click
   * @return String representing the room name where the click was made
   * @throws IllegalArgumentException when the row coordinate or column coordinate is negative.
   */
  private String getRoomCellClicked(int r, int c) throws IllegalArgumentException {
    if (r < 0 || c < 0) {
      throw new IllegalArgumentException("Clicked coordinates cannot be negative.");
    }
    for (RoomInterface room : this.rooms) {
      List<Integer> roomCoordinates = getRoomViewCoordinates(room);
      if (r < roomCoordinates.get(1)
              && r > roomCoordinates.get(0)
              && c < roomCoordinates.get(3)
              && c > roomCoordinates.get(2)
      ) {
        return room.getRoomName();
      }
    }
    return null;
  }

  /**
   * Move the player to the specified room.
   * @param roomName Name of the room where the player should be moved to.
   * @return Stirng representing the result of the move.
   * @throws IllegalArgumentException When the room name is invalid.
   */
  private String movePlayer(String roomName) throws IllegalArgumentException {
    if (!isCurrentPlayerComputer() && (roomName == null || "".equals(roomName))) {
      throw new IllegalArgumentException("Room name is null/empty.");
    }
    String playerNewRoomName = currentTurn.setPlayerRoom(roomName);
    moveTargetPlayer();
    movePetAfterTurnDfs();
    currentTurn = getNextTurnPlayer();
    remainingTurns -= 1;
    return String.format("Player has successfully moved to room %s.", playerNewRoomName);
  }

  /**
   * Gets the room object based on the name of the room.
   *
   * @param roomName represents name of the room
   * @return the room object
   * @throws IllegalArgumentException if room does not exist with that name.
   */
  private RoomInterface getRoomByRoomName(String roomName) throws IllegalArgumentException {
    for (RoomInterface room : this.rooms) {
      if (room.getRoomName().equalsIgnoreCase(roomName.trim())) {
        return room;
      }
    }
    throw new IllegalArgumentException(String.format(
            "Room with name '%s' does not exist.", roomName));
  }

  /**
   * Checks if the player can be seen from current room & neighbouring rooms.
   *
   * @return true if the player cannot be seen
   */
  private boolean isCurrentPlayerSeenByPlayersInVisibleRooms() {
    RoomInterface currentRoom = getRoomByRoomName(currentTurn.getPlayerRoomName());
    int currentRoomPlayerCount = currentRoom.getNumberOfPlayersInRoom() - 1;
    if (currentRoomPlayerCount > 0) {
      return true;
    } else if (currentRoomPlayerCount == 0 && currentRoom.isPetInRoom()) {
      return false;
    }
    return currentRoom.getNumberOfPlayersInNeighbouringRoom(false) > 0;
  }

  /*
   *************************
   * READ ONLY INTERFACE
   *************************
   */

  @Override
  public String getWorldName() {
    return this.worldName;
  }

  @Override
  public String getCurrentPlayerName() {
    return currentTurn == null ? "-" : currentTurn.getPlayerName();
  }

  @Override
  public String getTargetPlayerDetails() {
    return this.targetPlayer.toString();
  }

  @Override
  public String getCurrentPlayerRoomInformation() {
    if (currentTurn == null) {
      return "";
    }
    RoomInterface currentRoom = getRoomByRoomName(currentTurn.getPlayerRoomName());
    return currentRoom.toString();
  }

  @Override
  public BufferedImage getWorldView() {

    BufferedImage combinedView = new BufferedImage(worldView.getWidth(), worldView.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
    Graphics g = combinedView.getGraphics();
    g.drawImage(worldView, 0, 0, null);
    g.drawImage(playersView, 0, 0, null);

    return combinedView;
  }

  @Override
  public boolean isGameOver() {
    return targetPlayer.getTargetPlayerHealth() <= 0 || remainingTurns == 0;
  }

  /*
   *************************
   * NORMAL INTERFACE
   *************************
   */

  @Override
  public List<String> getListOfRooms() {
    return this.rooms.stream().map(RoomInterface::getRoomName).collect(Collectors.toList());
  }

  @Override
  public String getRoomInformation(String roomName) throws IllegalArgumentException {
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException("ERROR: Room name cannot be null/empty");
    }
    RoomInterface room = getRoomByRoomName(roomName);
    return room.toString();
  }

  @Override
  public List<String> getCurrentPlayerWeapons() {
    return currentTurn == null
            ? new ArrayList<>()
            : currentTurn.getPlayerWeapons(true);
  }

  @Override
  public List<String> getCurrentPlayerRoomWeapons() {
    if (currentTurn == null) {
      return new ArrayList<>();
    }
    RoomInterface currentRoom = getRoomByRoomName(currentTurn.getPlayerRoomName());
    return currentRoom.getAvailableWeapons(true);
  }

  @Override
  public boolean isPlayerIconClicked(int r, int c) throws IllegalArgumentException {
    if (r < 0 || c < 0) {
      throw new IllegalArgumentException("Clicked coordinates cannot be negative.");
    }
    checkIfPlayersExistToPlayGame();
    String roomName = this.getRoomCellClicked(r, c);
    if (roomName != null && roomName.equalsIgnoreCase(currentTurn.getPlayerRoomName())) {
      List<Integer> roomCoordinates = getRoomViewCoordinates(getRoomByRoomName(roomName));
      return r >= roomCoordinates.get(1) - 25
              && r <= roomCoordinates.get(1) - 5
              && c >= roomCoordinates.get(3) - 25
              && c <= roomCoordinates.get(3) - 5;
    }
    return false;
  }

  @Override
  public void updateWorldView(boolean isLookAround) throws IllegalArgumentException {

    this.playersView = new BufferedImage(worldView.getWidth(), worldView.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
    Graphics g = playersView.getGraphics();

    // Show all players at all times
    Set<String> roomsWithPlayers = new HashSet<>();
    try {
      BufferedImage others = ImageIO.read(new File("res/images/OtherPlayers.png"));
      Image scaledOther = others.getScaledInstance(30, 20, Image.SCALE_SMOOTH);

      for (PlayerInterface p : this.players) {
        if (p != currentTurn) {
          RoomInterface playerRoom = getRoomByRoomName(p.getPlayerRoomName());
          if (!roomsWithPlayers.contains(playerRoom.getRoomName()) && !playerRoom.isPetInRoom()) {
            List<Integer> playerRoomCoordinates = getRoomViewCoordinates(playerRoom);
            g.drawImage(scaledOther,
                    playerRoomCoordinates.get(2) + 5,
                    playerRoomCoordinates.get(1) - 25, null);
            roomsWithPlayers.add(playerRoom.getRoomName());
          }
        }
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to read image file.");
    }

    // Show current player and lookAround to world
    if (currentTurn != null) {

      RoomInterface currentRoom = getRoomByRoomName(currentTurn.getPlayerRoomName());
      List<Integer> roomCoordinates = getRoomViewCoordinates(currentRoom);

      try {
        BufferedImage player = ImageIO.read(new File("res/images/CurrentPlayer.png"));
        BufferedImage weapons = ImageIO.read(new File("res/images/Weapons.png"));

        Image scaledPlayer = player.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        Image scaledWeapon = weapons.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        g.drawImage(scaledPlayer, roomCoordinates.get(3) - 25, roomCoordinates.get(1) - 25, null);

        if (currentRoom.getAvailableWeapons(false).size() > 0) {
          g.drawImage(scaledWeapon, roomCoordinates.get(2) + 5, roomCoordinates.get(1) - 50, null);
        }

        if (isLookAround) {
          List<String> neighbours = currentRoom.getRoomNeighbours(false);
          if (neighbours.size() > 0) {
            for (String n : neighbours) {
              RoomInterface nei = getRoomByRoomName(n);
              List<Integer> neiRoomCoordinates = getRoomViewCoordinates(nei);
              int r2 = neiRoomCoordinates.get(1);
              int c1 = neiRoomCoordinates.get(2);
              if (!nei.isPetInRoom()) {
                if (nei.getAvailableWeapons(false).size() > 0) {
                  g.drawImage(scaledWeapon, c1 + 5, r2 - 50, null);
                }
              }
            }
          }
        }

      } catch (IOException e) {
        throw new IllegalArgumentException("Unable to read image file.");
      }
    }

    // Show target player to world
    try {
      BufferedImage target = ImageIO.read(new File("res/images/TargetPlayer.png"));
      Image scaledTarget = target.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
      List<Integer> targetRoomCoordinates = getRoomViewCoordinates(
              targetPlayer.getTargetPlayerRoom());
      g.drawImage(scaledTarget,
              targetRoomCoordinates.get(3) - 24,
              targetRoomCoordinates.get(1) - 50,
              null);
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to read image file.");
    }

  }

  @Override
  public void addPlayerToGame(String playerName, int weaponLimit,
                              boolean isComputerPlayer, String startingRoomName) throws IllegalArgumentException {
    if (playerName == null || "".equals(playerName)) {
      throw new IllegalArgumentException("Player name cannot be null/empty.");
    } else if (weaponLimit <= 0 && weaponLimit != -1) {
      throw new IllegalArgumentException("Player weapon limit cannot be negative.");
    } else if (startingRoomName == null || "".equals(startingRoomName)) {
      throw new IllegalArgumentException("Player start room name cannot be null/empty");
    }
    checkIfPlayerAlreadyExistsWithSameName(playerName);
    RoomInterface startRoom = getRoomByRoomName(startingRoomName);
    PlayerInterface newPlayer;
    if (isComputerPlayer) {
      newPlayer = new ComputerPlayer(playerName,
              weaponLimit, startRoom, random);
    } else {
      newPlayer = new HumanPlayer(playerName,
              weaponLimit, startRoom);
    }

    this.players.add(newPlayer);
    startRoom.addPlayerToRoom(newPlayer);
    if (currentTurn == null) {
      currentTurn = newPlayer;
    }
  }

  @Override
  public String lookAroundSpace() {
    checkIfPlayersExistToPlayGame();
    StringBuilder result = new StringBuilder("-----Look Around Details------").append("\n");
    result.append("------Current Room Details------").append("\n");
    RoomInterface currentRoom = getRoomByRoomName(currentTurn.getPlayerRoomName());
    result.append(currentRoom).append("\n\n");
    List<String> neighbours = currentRoom.getRoomNeighbours(false);
    if (neighbours.size() > 0) {
      result.append("------Neighboring Room Details------").append("\n");
      neighbours.forEach(n -> result.append("\n").append(getRoomByRoomName(n)).append("\n"));
    }
    moveTargetPlayer();
    movePetAfterTurnDfs();
    currentTurn = getNextTurnPlayer();
    remainingTurns -= 1;
    return result.toString();
  }

  @Override
  public String handleRoomClick(int x, int y) throws IllegalArgumentException {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Clicked coordinates cannot be negative.");
    }
    String roomName = this.getRoomCellClicked(x, y);
    if (roomName == null) {
      throw new IllegalArgumentException("Invalid room selected.");
    }
    return movePlayer(roomName);
  }

  @Override
  public String getCurrentPlayerInformation() {
    checkIfPlayersExistToPlayGame();
    return currentTurn == null ? "-" : currentTurn.toString();
  }

  @Override
  public String pickWeapon(String weaponName) throws IllegalArgumentException {
    checkIfPlayersExistToPlayGame();
    if (!isCurrentPlayerComputer() && (weaponName == null || "".equals(weaponName))) {
      throw new IllegalArgumentException("Weapon name cannot be null/empty.");
    }
    String pickedWeapon = currentTurn.pickWeapon(weaponName);
    moveTargetPlayer();
    movePetAfterTurnDfs();
    currentTurn = getNextTurnPlayer();
    remainingTurns -= 1;
    return String.format("Player has successfully picked up %s.", pickedWeapon);
  }

  @Override
  public String movePet(String roomName) throws IllegalArgumentException {
    checkIfPlayersExistToPlayGame();
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException("Room name is null/empty.");
    }
    RoomInterface petDestinationRoom = getRoomByRoomName(roomName);
    targetPet.setPetRoom(petDestinationRoom);

    this.dfsVisited.clear();
    this.dfsStack.clear();
    this.dfsStartRoom = petDestinationRoom.getRoomId();
    this.dfsVisited.add(petDestinationRoom.getRoomId());
    this.dfsStack.push(petDestinationRoom.getRoomId());

    moveTargetPlayer();
    movePetAfterTurnDfs();
    currentTurn = getNextTurnPlayer();
    remainingTurns -= 1;
    return String.format("Player has successfully moved the pet to %s.", roomName);
  }

  @Override
  public String attackTargetPlayer(String weaponName) throws IllegalArgumentException {
    checkIfPlayersExistToPlayGame();
    if (!isCurrentPlayerComputer() && (weaponName == null || "".equals(weaponName))) {
      throw new IllegalArgumentException("Weapon name is null/empty.");
    }
    int damageOnTarget = currentTurn.attackTarget(weaponName);

    if (damageOnTarget != -1) {
      targetPlayer.reduceTargetPlayerHealth(damageOnTarget);
    }

    if (!isGameOver()) {
      moveTargetPlayer();
      movePetAfterTurnDfs();
      currentTurn = getNextTurnPlayer();
    }
    remainingTurns -= 1;
    return damageOnTarget != -1
            ? "Attack on target was successful."
            : "Attack on target was not successful.";
  }

  @Override
  public String takeTurnForComputerPlayer() {
    checkIfPlayersExistToPlayGame();

    int actionNumber = random.getRandom();
    StringBuilder result = new StringBuilder();

    Map<Integer, Runnable> randomActions = new HashMap<>();
    randomActions.put(0, () -> result.append(lookAroundSpace()));
    randomActions.put(1, () -> result.append(movePlayer(null)));
    randomActions.put(2, () -> result.append(pickWeapon(null)));
    randomActions.put(3, () -> {
      String randomRoomChosen = this.rooms.get(random.getRandomWithinBound(this.rooms.size()))
              .getRoomName();
      result.append(movePet(randomRoomChosen));
    });

    RoomInterface currentRoom = getRoomByRoomName(currentTurn.getPlayerRoomName());
    if (currentRoom.isTargetPlayerInRoom() && !isCurrentPlayerSeenByPlayersInVisibleRooms()) {
      result.append(attackTargetPlayer(null));
    } else {
      randomActions.get(actionNumber).run();
    }

    return result.toString();
  }

  @Override
  public String getWinner() {
    checkIfPlayersExistToPlayGame();
    if (isGameOver()) {
      return getCurrentPlayerName();
    }
    return "Game not over. No winner yet!";
  }

  @Override
  public boolean isCurrentPlayerComputer() {
    checkIfPlayersExistToPlayGame();
    return currentTurn instanceof ComputerPlayer;
  }

}