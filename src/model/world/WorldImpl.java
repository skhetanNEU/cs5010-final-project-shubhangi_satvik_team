package model.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import javax.imageio.ImageIO;
import model.pet.PetImpl;
import model.pet.PetInterface;
import model.players.PlayerImpl;
import model.players.PlayerInterface;
import model.random.RandomGenerator;
import model.room.RoomImpl;
import model.room.RoomInterface;
import model.target.TargetPlayerImpl;
import model.target.TargetPlayerInterface;
import model.weapon.WeaponInterface;
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
  private final RandomGenerator random;

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
   */
  public WorldImpl(List<Integer> worldCoordinates, String worldName,
                   int targetPlayerHealth, String targetPlayerName,
                   int numRooms, List<List<Integer>> roomCoordinates, List<String> roomNames,
                   int numWeapons, List<Integer> weaponRoomIds, List<Integer> weaponDamageValues,
                   List<String> weaponNames, String targetPetName, RandomGenerator random) {
    if (worldCoordinates == null || worldCoordinates.size() == 0) {
      throw new IllegalArgumentException("ERROR: World coordinates cannot be null/empty");
    } else if (worldName == null || ("").equals(worldName)) {
      throw new IllegalArgumentException("ERROR: World name cannot be null/empty");
    } else if (targetPlayerHealth <= 0) {
      throw new IllegalArgumentException("ERROR: Target player health cannot be non-positive");
    } else if (targetPlayerName == null || "".equals(targetPlayerName)) {
      throw new IllegalArgumentException("ERROR: Target player name cannot be empty");
    } else if (numRooms <= 0) {
      throw new IllegalArgumentException("ERROR: Number of rooms cannot be non-positive");
    } else if (roomCoordinates == null) {
      throw new IllegalArgumentException("ERROR: Room coordinates cannot be null");
    } else if (roomNames == null) {
      throw new IllegalArgumentException("ERROR: Room names cannot be null");
    } else if (numWeapons <= 0) {
      throw new IllegalArgumentException("ERROR: Number of weapons cannot be non-positive");
    } else if (weaponRoomIds == null) {
      throw new IllegalArgumentException("ERROR: Weapon room id cannot be null");
    } else if (weaponDamageValues == null) {
      throw new IllegalArgumentException("ERROR: Weapon damage values cannot be null");
    } else if (weaponNames == null) {
      throw new IllegalArgumentException("ERROR: Weapon room names cannot be null");
    } else if (targetPetName == null || "".equals(targetPetName)) {
      throw new IllegalArgumentException("ERROR: Target pet name cannot be empty");
    } else if (random == null) {
      throw new IllegalArgumentException("ERROR: Random cannot be null");
    }

    this.worldName = worldName;
    this.worldCoordinates = new ArrayList<>(worldCoordinates);
    this.rooms = new ArrayList<>();
    this.players = new ArrayList<>();
    this.currentTurn = null;
    this.random = random;

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
        throw new IllegalArgumentException("ERROR: Cannot have rooms with same name");
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
   */
  private void initializeWeapons(int numRooms, List<String> weaponNames,
                                 List<Integer> weaponDamageValues, List<Integer> weaponRoomIds)
          throws IllegalArgumentException {
    for (int i = 0; i < weaponNames.size(); i++) {
      String weaponName = weaponNames.get(i);
      int weaponRoomId = weaponRoomIds.get(i);
      int weaponDamageValue = weaponDamageValues.get(i);
      if (weaponRoomId < 0 || weaponRoomId >= numRooms) {
        throw new IllegalArgumentException("ERROR: Incorrect weapon room id!");
      }
      if (weaponNames.stream().filter(weaponName::equalsIgnoreCase).count() > 1) {
        throw new IllegalArgumentException("ERROR: Cannot have weapons with same name");
      }
      RoomInterface weaponRoom = rooms.get(weaponRoomId);
      weaponRoom.addWeaponToRoom(i, weaponName, weaponDamageValue);
    }
  }

  /**
   * Helper class that initializes the target pet.
   *
   * @param targetPlayerName represents the name of the pet
   */
  private void initializeTargetPlayer(String targetPlayerName, int targetPlayerHealth) {
    this.rooms.get(0).updateTargetPlayerPresence(true);
    this.targetPlayer = new TargetPlayerImpl(
            targetPlayerName, targetPlayerHealth, this.rooms.get(0));
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
      throw new IllegalArgumentException("ERROR: Room cannot be bigger than the world");
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
      throw new IllegalArgumentException("ERROR: Rooms are overlapping");
    }
  }

  /**
   * Checks if there are any players in the world with same name.
   *
   * @param playerName name of the player
   * @return if the player exists with same name
   */
  private boolean checkIfPlayerAlreadyExistsWithSameName(String playerName) {
    for (PlayerInterface player : this.players) {
      if (player.getPlayerName().equalsIgnoreCase(playerName)) {
        return true;
      }
    }
    return false;
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
            "ERROR: Room with name '%s' does not exist.", roomName));
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
      String currentRoomNeighbours = currentRoom.getRoomNeighbours(true);
      if (!Objects.equals(currentRoomNeighbours, "No neighbours")) {
        String[] neighbours = currentRoomNeighbours.split(",");
        for (int i = 0; i <= neighbours.length - 1; i++) {
          RoomInterface neigh = getRoomByRoomName(neighbours[i]);
          if (!this.dfsVisited.contains(neigh.getRoomId())) {
            targetPet.setPetRoom(neigh);
            this.dfsVisited.add(neigh.getRoomId());
            this.dfsStack.push(neigh.getRoomId());
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
   * Checks if there is alteast one player to take a turn and play the game.
   */
  private void checkIfPlayersExistToPlayGame() {
    if (this.players.size() <= 0) {
      throw new IllegalArgumentException(
              "ERROR: Game must have atleast 1 player. Please add players to use this option.");
    }
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
    int neighbourRoomPlayerCount = 0;
    String currentRoomNeighbours = currentRoom.getRoomNeighbours(false);
    if (!"No neighbours".equals(currentRoomNeighbours)) {
      String[] neighbours = currentRoomNeighbours.split(",");
      for (String n : neighbours) {
        RoomInterface nei = getRoomByRoomName(n);
        if (!nei.isPetInRoom()) {
          neighbourRoomPlayerCount += nei.getNumberOfPlayersInRoom();
        }
      }
    }
    return neighbourRoomPlayerCount > 0;
  }

  private String getRoomCellClicked(int r, int c) {
    int zoomFactor = 25;
    for (RoomInterface room : this.rooms) {
      List<Integer> roomCoordinates = room.getRoomCoordinates();
      int r1 = (roomCoordinates.get(0) * zoomFactor) - zoomFactor / 2;
      int r2 = (roomCoordinates.get(2) * zoomFactor) + zoomFactor / 2;
      int c1 = (roomCoordinates.get(1) * zoomFactor) - zoomFactor / 2 + 30;
      int c2 = (roomCoordinates.get(3) * zoomFactor) + zoomFactor / 2 + 30;
      if (r < r2 && r > r1 && c < c2 && c > c1) {
        return room.getRoomName();
      }
    }
    return null;
  }

  @Override
  public String getWorldName() {
    return this.worldName;
  }

  @Override
  public String getCurrentPlayerName() {
    if (currentTurn == null) {
      throw new IllegalArgumentException(
              "ERROR: Cannot play turn. Game must have atleast 1 player.");
    }
    return currentTurn.getPlayerName();
  }

  @Override
  public boolean isCurrentPlayerComputer() {
    if (currentTurn == null) {
      throw new IllegalArgumentException(
              "ERROR: Cannot play turn. Game must have atleast 1 player.");
    }
    return currentTurn.isComputerPlayer();
  }

  @Override
  public String getCurrentPlayerWeapons() {
    if (currentTurn == null) {
      return "";
    }
    return currentTurn.getPlayerWeapons(true);
  }

  @Override
  public String getCurrentPlayerRoomName() {
    if (currentTurn == null) {
      return "";
    }
    return currentTurn.getPlayerRoomName();
  }

  @Override
  public String getCurrentPlayerRoomWeapons(boolean includeDamageValues) {
    if (currentTurn == null) {
      return "";
    }
    RoomInterface currentPlayerRoom = getRoomByRoomName(currentTurn.getPlayerRoomName());
    return currentPlayerRoom.getAvailableWeapons(includeDamageValues);
  }

  @Override
  public BufferedImage displayWorld(boolean isLookAround) {

    int zoomFactor = 24;

    BufferedImage bi = new BufferedImage((worldCoordinates.get(1) + 1) * zoomFactor,
            (worldCoordinates.get(0) + 1) * zoomFactor, BufferedImage.TYPE_INT_RGB);

    Graphics g = bi.getGraphics();
    g.setFont(g.getFont().deriveFont(10f));
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, bi.getWidth(), bi.getHeight());

    for (RoomInterface room : this.rooms) {

      List<Integer> roomCoordinates = room.getRoomCoordinates();
      int r1 = (roomCoordinates.get(0) * zoomFactor) - zoomFactor / 2;
      int r2 = (roomCoordinates.get(2) * zoomFactor) + zoomFactor / 2;
      int c1 = (roomCoordinates.get(1) * zoomFactor) - zoomFactor / 2 + 30;
      int c2 = (roomCoordinates.get(3) * zoomFactor) + zoomFactor / 2 + 30;

      g.setColor(Color.BLACK);
      g.drawRect(c1, r1, c2 - c1, r2 - r1);
      g.drawString(room.getRoomName(), c1 + 5, r1 + 15);

      // Show current player to world
      if (currentTurn != null && room.getRoomName().equalsIgnoreCase(getCurrentPlayerRoomName())) {
        try {
          BufferedImage originalImage = ImageIO.read(new File("res/images/CurrentPlayer.png"));
          Image resultingImage = originalImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
          g.drawImage(resultingImage, c2 - 30, r2 - 30, null);

          if (!"No weapons".equals(room.getAvailableWeapons(false))) {
            BufferedImage weapon = ImageIO.read(new File("res/images/Weapons.png"));
            Image resultingWeapon = weapon.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            g.drawImage(resultingWeapon, c1 + 10, r2 - 30, null);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      // Show target player to world
      if (room.getRoomName().equalsIgnoreCase(targetPlayer.getTargetPlayerRoom().getRoomName())) {
        try {
          BufferedImage player = ImageIO.read(new File("res/images/TargetPlayer.png"));
          Image resultingPlayer = player.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
          g.drawImage(resultingPlayer,
                  ((int) (0.5 * (c1 + c2 + 1))) - 10,
                  ((int) (0.5 * (r1 + r2 + 1))) - 10,
                  null);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (isLookAround) {
        // TODO
      }

    }
    return bi;
  }

  @Override
  public String getRoomInformation(String roomName) {
    if ("".equals(roomName) || roomName == null) {
      throw new IllegalArgumentException("ERROR: Room name cannot be null/empty");
    }
    RoomInterface room = getRoomByRoomName(roomName);
    return room.toString();
  }

  @Override
  public String getPlayerInformation() {
    if (currentTurn == null) {
      throw new IllegalArgumentException(
              "ERROR: Cannot play turn. Game must have atleast 1 player.");
    }
    return currentTurn.toString();
  }

  @Override
  public String getTargetPlayerDetails() {
    return this.targetPlayer.toString();
  }

  @Override
  public boolean isGameOver() {
    return targetPlayer.getTargetPlayerHealth() <= 0;
  }

  @Override
  public void addPlayerToGame(String playerName, int weaponLimit,
                              boolean isComputerPlayer, String startingRoomName) {
    if (playerName == null || "".equals(playerName)) {
      throw new IllegalArgumentException("ERROR: Player name cannot be empty or null.");
    } else if (weaponLimit < 0 && weaponLimit != -1) {
      throw new IllegalArgumentException("ERROR: Player weapon limit cannot be negative.");
    } else if (startingRoomName == null || "".equals(startingRoomName)) {
      throw new IllegalArgumentException("ERROR: Player start room name cannot be empty or null.");
    }
    if (checkIfPlayerAlreadyExistsWithSameName(playerName)) {
      throw new IllegalArgumentException("ERROR: Player already exists with the same name.");
    } else {
      int newPlayerId = this.players.size();
      RoomInterface startRoom = getRoomByRoomName(startingRoomName);
      PlayerInterface newPlayer = new PlayerImpl(newPlayerId, playerName,
              weaponLimit, isComputerPlayer, startRoom);
      this.players.add(newPlayer);
      startRoom.addPlayerToRoom(newPlayer);
      if (currentTurn == null) {
        currentTurn = newPlayer;
      }
    }
  }

  @Override
  public String lookAroundSpace() {
    checkIfPlayersExistToPlayGame();
    StringBuilder result = new StringBuilder("------Current Room Details------").append("\n");
    String currentRoomName = currentTurn.getPlayerRoomName();
    result.append(getRoomInformation(currentRoomName)).append("\n\n");
    RoomInterface currentRoom = getRoomByRoomName(currentTurn.getPlayerRoomName());
    String currentPlayerRoomNeighbours = currentRoom.getRoomNeighbours(false);
    if (!Objects.equals(currentPlayerRoomNeighbours, "No neighbours")) {
      String[] neighbours = currentPlayerRoomNeighbours.split(",");
      result.append("------Neighboring Room Details------").append("\n");
      for (String n : neighbours) {
        result.append("\n");
        result.append(getRoomInformation(n)).append("\n");
      }
    }
    moveTargetPlayer();
    movePetAfterTurnDfs();
    currentTurn = getNextTurnPlayer();
    return result.toString();
  }

  @Override
  public String movePlayer(String roomName) {
    checkIfPlayersExistToPlayGame();
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException(
              "ERROR: Player destination room name cannot be empty or null.");
    }
    String currentPlayerRoomName = currentTurn.getPlayerRoomName();
    if (currentPlayerRoomName.equalsIgnoreCase(roomName)) {
      throw new IllegalArgumentException(
              "ERROR: Unable to move player. Destination room same as current room.");
    }
    RoomInterface destinationRoom = getRoomByRoomName(roomName);
    RoomInterface currentPlayerRoom = getRoomByRoomName(currentPlayerRoomName);
    currentPlayerRoom.checkIfRoomNeighbour(roomName, false);
    currentPlayerRoom.removePlayerFromRoom(currentTurn);
    currentTurn.setPlayerRoom(destinationRoom);
    destinationRoom.addPlayerToRoom(currentTurn);
    moveTargetPlayer();
    movePetAfterTurnDfs();
    currentTurn = getNextTurnPlayer();
    return new StringBuilder().append("Player has successfully moved to room ")
            .append(roomName).toString();
  }

  @Override
  public String movePlayer(int x, int y) {
    String roomName = this.getRoomCellClicked(x, y);
    if (roomName != null) {
      RoomInterface destinationRoom = getRoomByRoomName(roomName);
      RoomInterface currentPlayerRoom = getRoomByRoomName(getCurrentPlayerRoomName());
      currentPlayerRoom.checkIfRoomNeighbour(roomName, false);
      currentPlayerRoom.removePlayerFromRoom(currentTurn);
      currentTurn.setPlayerRoom(destinationRoom);
      destinationRoom.addPlayerToRoom(currentTurn);
      moveTargetPlayer();
      movePetAfterTurnDfs();
      currentTurn = getNextTurnPlayer();
      return new StringBuilder().append("Player has successfully moved to room ")
              .append(roomName).toString();
    }
    return new StringBuilder().append("ERROR: Player cannot be moved. Invalid room.").toString();
  }

  @Override
  public String pickWeapon(String weaponName) {
    checkIfPlayersExistToPlayGame();
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException(
              "ERROR: Player desired weapon name cannot be empty or null.");
    }
    RoomInterface currentPlayerRoom = getRoomByRoomName(currentTurn.getPlayerRoomName());
    WeaponInterface weapon = currentPlayerRoom.getWeaponByWeaponName(weaponName);
    currentTurn.addWeaponToPlayer(weapon);
    currentPlayerRoom.removeWeaponFromRoom(weapon);
    moveTargetPlayer();
    movePetAfterTurnDfs();
    currentTurn = getNextTurnPlayer();
    return new StringBuilder().append("Player has successfully picked up ")
            .append(weaponName).toString();
  }

  @Override
  public String movePet(String roomName) {
    checkIfPlayersExistToPlayGame();
    if (roomName == null || "".equals(roomName)) {
      throw new IllegalArgumentException(
              "ERROR: Pet destination room name cannot be empty or null.");
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
    return new StringBuilder().append("Player has successfully moved the pet to ").append(roomName)
            .toString();
  }

  @Override
  public String attackTargetPlayer(String weaponName) {
    checkIfPlayersExistToPlayGame();
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException(
              "ERROR: Player desired weapon name cannot be empty or null.");
    }

    RoomInterface currentPlayerRoom = getRoomByRoomName(currentTurn.getPlayerRoomName());
    boolean isAttackSuccessful = true;

    // Target not in same room || Room has more players
    if (!currentPlayerRoom.isTargetPlayerInRoom()
            || currentPlayerRoom.getNumberOfPlayersInRoom() - 1 > 0) {
      isAttackSuccessful = false;
    } else {
      // Neighbouring rooms have players
      String currentPlayerRoomNeighbours = currentPlayerRoom.getRoomNeighbours(true);
      if (!"No neighbours".equals(currentPlayerRoomNeighbours)) {
        int numPlayersInNeighbours = 0;
        String[] neighbours = currentPlayerRoomNeighbours.split(",");
        for (String n : neighbours) {
          numPlayersInNeighbours += getRoomByRoomName(n).getNumberOfPlayersInRoom();
        }
        if (numPlayersInNeighbours > 0 && !currentPlayerRoom.isPetInRoom()) {
          isAttackSuccessful = false;
        }
      }
    }

    WeaponInterface w = currentTurn.removeWeaponFromPlayer(weaponName);
    if (isAttackSuccessful) {
      targetPlayer.reduceTargetPlayerHealth(w.getWeaponValue());
    }

    moveTargetPlayer();
    movePetAfterTurnDfs();
    currentTurn = getNextTurnPlayer();
    return isAttackSuccessful ? "Attack on target was successful" :
            "Attack on target was not successful";
  }

  @Override
  public String takeTurnForComputerPlayer() {
    checkIfPlayersExistToPlayGame();
    int actionNumber = random.getRandom();
    RoomInterface currentPlayerRoom = getRoomByRoomName(currentTurn.getPlayerRoomName());
    StringBuilder result = new StringBuilder();

    if (currentPlayerRoom.isTargetPlayerInRoom()
            && !isCurrentPlayerSeenByPlayersInVisibleRooms()) {
      String maxDamageWeaponChosen = currentTurn.getPlayerMaxDamageWeapon();
      result.append(attackTargetPlayer(maxDamageWeaponChosen));
    } else if (actionNumber == 0) {
      result.append(lookAroundSpace());
    } else if (actionNumber == 1) {
      String neighbours = currentPlayerRoom.getRoomNeighbours(false);
      String[] neighbourList = neighbours.split(",");
      if (neighbourList.length == 1 && ("".equals(neighbourList[0].trim())
              || "No neighbours".equalsIgnoreCase(neighbours))) {
        throw new IllegalArgumentException(
                "ERROR: The current room does not have any neighbours to move to.");
      } else {
        String chosenRoomName = neighbourList[neighbourList.length - 1].trim();
        result.append(movePlayer(chosenRoomName));
      }
    } else if (actionNumber == 2) {
      String weapons = getCurrentPlayerRoomWeapons(false);
      if ("No weapons".equals(weapons)) {
        throw new IllegalArgumentException(
                "ERROR: The current room does not have any weapons to pick.");
      } else {
        String[] weaponList = weapons.split(",");
        String chosenWeaponName = weaponList[weaponList.length - 1].trim();
        result.append(pickWeapon(chosenWeaponName));
      }
    } else if (actionNumber == 3) {
      String randomRoomChosen = this.rooms.get(random.getRandomWithinBound(this.rooms.size()))
              .getRoomName();
      result.append(movePet(randomRoomChosen));
    }
    return result.toString();
  }

}
