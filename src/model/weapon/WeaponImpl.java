package model.weapon;

import java.util.Comparator;
import java.util.Objects;

/**
 * WeaponImpl represents the weapon of Kill Doctor Lucky game.
 * It consists of weapon id, weapon name and weapon damage value.
 *
 */
public class WeaponImpl implements WeaponInterface {

  private final int weaponId;
  private final String weaponName;
  private final int weaponDamageValue;

  /**
   * Construct a WeaponImpl object that represents the world.
   *
   * @param weaponId          represents the coordinates of the world
   * @param weaponName        represents the name of the world
   * @param weaponDamageValue represents the health of the target player
   * @throws IllegalArgumentException if the weapon id is negative, damage value is non-positive
   *                                  or weapon name is empty.
   */
  public WeaponImpl(int weaponId, String weaponName, int weaponDamageValue)
          throws IllegalArgumentException {
    if (weaponId < 0) {
      throw new IllegalArgumentException("Weapon id cannot be negative.");
    }
    if (weaponDamageValue <= 0) {
      throw new IllegalArgumentException("Weapon damage value cannot be non-positive.");
    }
    if (weaponName == null || ("").equals(weaponName)) {
      throw new IllegalArgumentException("Weapon name cannot be null/empty.");
    }
    this.weaponId = weaponId;
    this.weaponName = weaponName;
    this.weaponDamageValue = weaponDamageValue;
  }

  @Override
  public int getWeaponId() {
    return this.weaponId;
  }

  @Override
  public String getWeaponName() {
    return this.weaponName;
  }

  @Override
  public int getWeaponValue() {
    return this.weaponDamageValue;
  }

  @Override
  public String toString() {
    return String.format("%s (Damage: %s)", weaponName, weaponDamageValue);
  }

  @Override
  public int compareTo(WeaponInterface that) {
    return Comparator.comparingInt(WeaponInterface::getWeaponValue).reversed()
            .thenComparingInt(WeaponInterface::getWeaponId)
            .compare(this, that);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WeaponImpl weapon = (WeaponImpl) o;
    return weaponId == weapon.weaponId
            && weaponDamageValue == weapon.weaponDamageValue
            && weaponName.equals(weapon.weaponName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(weaponId, weaponName, weaponDamageValue);
  }
}
