package mockmodel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import model.world.WorldInterface;

public class MockWorldComputerGameOver implements WorldInterface {

  private StringBuilder log;
  private final String uniqueCode;

  public MockWorldComputerGameOver(StringBuilder log, String uniqueCode) {
    this.log = log;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public String getWorldName() {
    log.append("Model getWorldName() called\n");
    return uniqueCode;
  }

  @Override
  public String getCurrentPlayerName() {
    log.append("Model getCurrentPlayerName() called\n");
    return uniqueCode;
  }

  @Override
  public String getTargetPlayerDetails() {
    log.append("Model getTargetPlayerDetails() called\n");
    return uniqueCode;
  }

  @Override
  public String getCurrentPlayerRoomInformation() {
    log.append("Model getCurrentPlayerRoomInformation() called\n");
    return uniqueCode;
  }

  @Override
  public BufferedImage getWorldView() {
    log.append("Model getWorldView() called\n");
    return null;
  }

  @Override
  public boolean isGameOver() {
    log.append("Model isGameOver() called\n");
    return true;
  }

  @Override
  public List<String> getListOfRooms() {
    log.append("Model getListOfRooms() called\n");
    return new ArrayList<>(List.of(uniqueCode));
  }

  @Override
  public List<String> getCurrentPlayerWeapons() {
    log.append("Model getCurrentPlayerWeapons() called\n");
    return new ArrayList<>(List.of(uniqueCode));
  }

  @Override
  public List<String> getCurrentPlayerRoomWeapons() {
    log.append("Model getCurrentPlayerRoomWeapons() called\n");
    return new ArrayList<>(List.of(uniqueCode));
  }

  @Override
  public boolean isPlayerIconClicked(int r, int c) {
    log.append("Model isPlayerIconClicked() called\n");
    log.append("Parameters: ").append(r).append(", ").append(c).append("\n");
    return false;
  }

  @Override
  public void updateWorldView(boolean isLookAround) {
    log.append("Model updateWorldView() called\n");
    log.append("Parameters: ").append(isLookAround).append("\n");
  }

  @Override
  public void addPlayerToGame(String playerName, int weaponLimit, boolean isComputerPlayer,
                              String startingRoomName) {
    log.append("Model addPlayerToGame() called\n");
    log.append(String.format("Parameters: %s, %s, %s, %s\n", playerName, weaponLimit,
            isComputerPlayer, startingRoomName));
    if (playerName == null || "".equals(playerName)) {
      log.append("Exception thrown - Player name cannot be null/empty\n");
      throw new IllegalArgumentException("Player name cannot be null/empty.");
    } else if (weaponLimit < 0 && weaponLimit != -1) {
      log.append("Exception thrown - Player weapon limit cannot be negative\n");
      throw new IllegalArgumentException("Player weapon limit cannot be negative.");
    } else if (startingRoomName == null || "".equals(startingRoomName)) {
      log.append("Exception thrown - Player start room name cannot be null/empty\n");
      throw new IllegalArgumentException("Player start room name cannot be null/empty");
    }
  }

  @Override
  public String lookAroundSpace() {
    log.append("Model lookAroundSpace() called\n");
    return uniqueCode;
  }

  @Override
  public String handleRoomClick(int x, int y) {
    log.append("Model handleRoomClick() called\n");
    log.append("Parameters: ").append(x).append(", ").append(y).append("\n");
    if (x < 0 || y < 0) {
      log.append("Exception thrown - Clicked coordinates cannot be negative.\n");
      throw new IllegalArgumentException("Clicked coordinates cannot be negative.");
    }
    return uniqueCode;
  }

  @Override
  public String getCurrentPlayerInformation() {
    log.append("Model getCurrentPlayerInformation() called\n");
    return uniqueCode;
  }

  @Override
  public String pickWeapon(String weaponName) {
    log.append("Model pickWeapon() called\n");
    log.append("Parameters: ").append(weaponName).append("\n");
    if (weaponName == null || "".equals(weaponName)) {
      log.append("Exception thrown - Weapon name cannot be null/empty\n");
      throw new IllegalArgumentException("Weapon name cannot be null/empty.");
    }
    return uniqueCode;
  }

  @Override
  public String movePet(String roomName) {
    log.append("Model movePet() called\n");
    log.append("Parameters: ").append(roomName).append("\n");
    if (roomName == null || "".equals(roomName)) {
      log.append("Exception thrown - Room name is null/empty\n");
      throw new IllegalArgumentException("Room name is null/empty.");
    }
    return uniqueCode;
  }

  @Override
  public String attackTargetPlayer(String weaponName) {
    log.append("Model attackTargetPlayer() called\n");
    log.append("Parameters: ").append(weaponName).append("\n");
    if (!isCurrentPlayerComputer() && (weaponName == null || "".equals(weaponName))) {
      log.append("Exception thrown - Weapon name is null/empty\n");
      throw new IllegalArgumentException("Weapon name is null/empty.");
    }
    return uniqueCode;
  }

  @Override
  public String takeTurnForComputerPlayer() {
    log.append("Model takeTurnForComputerPlayer() called\n");
    return uniqueCode;
  }

  @Override
  public String getWinner() {
    log.append("Model getWinner() called\n");
    return uniqueCode;
  }

  @Override
  public boolean isCurrentPlayerComputer() {
    log.append("Model isCurrentPlayerComputer() called\n");
    return true;
  }

  @Override
  public String getRoomInformation(String roomName) {
    log.append("Model getRoomInformation() called\n");
    return uniqueCode;
  }
}
