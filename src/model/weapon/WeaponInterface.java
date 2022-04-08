package model.weapon;

/**
 * An interface class for representing the weapon for Kill Doctor Lucky.
 *
 */
public interface WeaponInterface extends Comparable<WeaponInterface> {
  /**
   * Returns the id of the weapon.
   *
   * @return the id of the weapon
   */
  int getWeaponId();

  /**
   * Returns the name of the weapon.
   *
   * @return the name of the weapon
   */
  String getWeaponName();

  /**
   * Returns the damage value of the weapon.
   *
   * @return the damage value of the weapon
   */
  int getWeaponValue();
}
