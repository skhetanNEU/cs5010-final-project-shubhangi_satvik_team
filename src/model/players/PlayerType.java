package model.players;

/**
 * Represents the type of player.
 */
public enum PlayerType {
  HUMAN("Human"),
  COMPUTER("Computer");

  private final String display;

  private PlayerType(String display) {
    this.display = display;
  }

  @Override
  public String toString() {
    return display;
  }
}
