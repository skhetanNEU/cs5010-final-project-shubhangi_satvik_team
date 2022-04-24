package view;

import java.util.List;

/**
 * Interface for the main game view.
 */
public interface MainGameViewInterface extends DefaultGameViewInterface {

  /**
   * Refresh the current screen of the view.
   */
  void refresh();

  /**
   * Showing a dialog for picking up a weapon.
   * @param roomWeapons List of weapons present in the player's current room.
   * @return String representing the weapon that was picked.
   * @throws IllegalArgumentException when the list of weapons is null.
   */
  String showPickWeaponDialog(List<String> roomWeapons);

  /**
   * Showing a dialog for moving the pet in the game.
   * @param roomList List of rooms present in the world where the pet can be moved.
   * @return String representing the room name where the pet was moved.
   * @throws IllegalArgumentException when the list of rooms is null.
   */
  String showMovePetDialog(List<String> roomList);

  /**
   * Showing a dialog for attacking the target.
   * @param playerWeapons List of weapons carried by the player.
   * @return String representing whether the attack was successful or not.
   * @throws IllegalArgumentException when the list of player's weapons is null.
   */
  String showAttackTargetDialog(List<String> playerWeapons);
}
