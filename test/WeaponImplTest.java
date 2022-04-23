import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

import model.weapon.WeaponImpl;
import model.weapon.WeaponInterface;
import org.junit.Test;

/**
 * A JUnit test class for the WeaponImpl class.
 *
 */
public class WeaponImplTest {

  @Test
  public void testWeaponConstructor_Successful() {
    WeaponInterface w = new WeaponImpl(0, "Blade", 2);
    String expectedString = "Blade (Damage: 2)";
    assertEquals(expectedString, w.toString());
  }

  @Test
  public void testWeaponConstructor_Unsuccessful_WeaponIdNegative() {
    assertThrows(IllegalArgumentException.class, () -> new WeaponImpl(-2, "Blade", 2));
  }

  @Test
  public void testWeaponConstructor_Unsuccessful_DamageValueNegative() {
    assertThrows(IllegalArgumentException.class, () -> new WeaponImpl(2, "Poison", -10));
  }

  @Test
  public void testWeaponConstructor_Unsuccessful_DamageValueZero() {
    assertThrows(IllegalArgumentException.class, () -> new WeaponImpl(2, "Rope", 0));
  }

  @Test
  public void testWeaponConstructor_Unsuccessful_WeaponNameEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new WeaponImpl(2, "", 2));
  }

  @Test
  public void testWeaponConstructor_Unsuccessful_WeaponNameNull() {
    assertThrows(IllegalArgumentException.class, () -> new WeaponImpl(2, null, 2));
  }

  @Test
  public void testGetWeaponId_Tc1() {
    WeaponInterface w1 = new WeaponImpl(0, "Blade", 2);
    assertEquals(0, w1.getWeaponId());
  }

  @Test
  public void testGetWeaponId_Tc2() {
    WeaponInterface w2 = new WeaponImpl(2, "Rope", 10);
    assertEquals(2, w2.getWeaponId());
  }

  @Test
  public void testGetWeaponId_Tc3() {
    WeaponInterface w3 = new WeaponImpl(4, "Rat Poison", 20);
    assertEquals(4, w3.getWeaponId());
  }

  @Test
  public void testGetWeaponName_Tc1() {
    WeaponInterface w1 = new WeaponImpl(0, "Blade", 2);
    assertEquals("Blade", w1.getWeaponName());
  }

  @Test
  public void testGetWeaponName_Tc2() {
    WeaponInterface w2 = new WeaponImpl(2, "Rope", 10);
    assertEquals("Rope", w2.getWeaponName());
  }

  @Test
  public void testGetWeaponName_Tc3() {
    WeaponInterface w3 = new WeaponImpl(4, "Rat Poison", 20);
    assertEquals("Rat Poison", w3.getWeaponName());
  }

  @Test
  public void testGetWeaponValue_Tc1() {
    WeaponInterface w1 = new WeaponImpl(0, "Blade", 2);
    assertEquals(2, w1.getWeaponValue());
  }

  @Test
  public void testGetWeaponValue_Tc2() {
    WeaponInterface w2 = new WeaponImpl(2, "Rope", 10);
    assertEquals(10, w2.getWeaponValue());
  }

  @Test
  public void testGetWeaponValue_Tc3() {
    WeaponInterface w3 = new WeaponImpl(4, "Rat Poison", 20);
    assertEquals(20, w3.getWeaponValue());
  }

  @Test
  public void testToString_Tc1() {
    WeaponInterface w1 = new WeaponImpl(0, "Blade", 2);
    String expectedString1 = "Blade (Damage: 2)";
    assertEquals(expectedString1, w1.toString());
  }

  @Test
  public void testToString_Tc2() {
    WeaponInterface w2 = new WeaponImpl(2, "Rope", 10);
    String expectedString2 = "Rope (Damage: 10)";
    assertEquals(expectedString2, w2.toString());
  }

  @Test
  public void testToString_Tc3() {
    WeaponInterface w3 = new WeaponImpl(4, "Rat Poison", 20);
    String expectedString3 = "Rat Poison (Damage: 20)";
    assertEquals(expectedString3, w3.toString());
  }

  @Test
  public void testCompareTo_WeaponValueGreater() {
    WeaponInterface w0 = new WeaponImpl(1, "Blade", 20);
    WeaponInterface w2 = new WeaponImpl(2, "Rope", 10);
    assertEquals(-1, w0.compareTo(w2));
  }

  @Test
  public void testCompareTo_WeaponValueSame() {
    WeaponInterface w0 = new WeaponImpl(1, "Blade", 2);
    WeaponInterface w1 = new WeaponImpl(1, "Blade", 2);
    assertEquals(0, w0.compareTo(w1));
  }

  @Test
  public void testCompareTo_WeaponValueSmaller() {
    WeaponInterface w0 = new WeaponImpl(1, "Blade", 2);
    WeaponInterface w3 = new WeaponImpl(0, "Rat Poison", 20);
    assertEquals(1, w0.compareTo(w3));
  }

  @Test
  public void testEquals_ObjectSame() {
    WeaponInterface w0 = new WeaponImpl(1, "Blade", 2);
    assertEquals(w0, w0);
  }

  @Test
  public void testEquals_ObjectDifferentSameValues() {
    WeaponInterface w0 = new WeaponImpl(1, "Blade", 2);
    WeaponInterface w1 = new WeaponImpl(1, "Blade", 2);
    assertEquals(w0, w1);
  }

  @Test
  public void testEquals_WeaponId_Different() {
    WeaponInterface w0 = new WeaponImpl(1, "Blade", 2);
    WeaponInterface w2 = new WeaponImpl(2, "Blade", 2);
    assertNotEquals(w0, w2);
  }

  @Test
  public void testEquals_WeaponName_Different() {
    WeaponInterface w0 = new WeaponImpl(1, "Blade", 2);
    WeaponInterface w3 = new WeaponImpl(1, "Rope", 2);
    assertNotEquals(w0, w3);
  }

  @Test
  public void testEquals_WeaponDamageValue_Different() {
    WeaponInterface w0 = new WeaponImpl(1, "Rat Poison", 2);
    WeaponInterface w4 = new WeaponImpl(1, "Rat Poison", 20);
    assertNotEquals(w0, w4);
  }

  @Test
  public void testEquals_AllValues_Different() {
    WeaponInterface w0 = new WeaponImpl(1, "Rat Poison", 2);
    WeaponInterface w5 = new WeaponImpl(2, "Knife", 20);
    assertNotEquals(w0, w5);
  }

  @Test
  public void testEquals_WithNull() {
    WeaponInterface w0 = new WeaponImpl(1, "Rat Poison", 2);
    assertNotEquals(null, w0);
  }

  @Test
  public void testHashCode_ObjectDifferentSameValues() {
    WeaponInterface w0 = new WeaponImpl(1, "Blade", 2);
    WeaponInterface w1 = new WeaponImpl(1, "Blade", 2);
    assertEquals(w0.hashCode(), w1.hashCode());
  }

  @Test
  public void testHashCode_WeaponId_Different() {
    WeaponInterface w0 = new WeaponImpl(1, "Blade", 2);
    WeaponInterface w2 = new WeaponImpl(2, "Blade", 2);
    assertNotEquals(w0.hashCode(), w2.hashCode());
  }

  @Test
  public void testHashCode_WeaponName_Different() {
    WeaponInterface w0 = new WeaponImpl(1, "Blade", 2);
    WeaponInterface w3 = new WeaponImpl(1, "Rope", 2);
    assertNotEquals(w0.hashCode(), w3.hashCode());
  }

  @Test
  public void testHashCode_WeaponDamageValue_Different() {
    WeaponInterface w0 = new WeaponImpl(1, "Rat Poison", 2);
    WeaponInterface w4 = new WeaponImpl(1, "Rat Poison", 20);
    assertNotEquals(w0.hashCode(), w4.hashCode());
  }

  @Test
  public void testHashCode_AllValues_Different() {
    WeaponInterface w0 = new WeaponImpl(1, "Rat Poison", 2);
    WeaponInterface w5 = new WeaponImpl(2, "Knife", 20);
    assertNotEquals(w0.hashCode(), w5.hashCode());
  }

}