package model.random;

import java.util.Random;

/**
 * This class implements {@link RandomGenerator} and represents a random number generator class
 * can be used as both predictable and unpredictable based on the constructor params.
 */
public class RandomClass implements RandomGenerator {

  private static final Random r = new Random();
  private int randomNumber;
  private final boolean isTrueRandom;

  /**
   * Constructor for a true random class.
   *
   * @param isTrueRandom represents if the class is a true or false random
   */
  public RandomClass(boolean isTrueRandom) {
    this.isTrueRandom = true;
    this.randomNumber = 0;
  }

  /**
   * Constructor for a false random class.
   *
   * @param isTrueRandom      represents if the class is a true or false random
   * @param falseRandomNumber represents the false random option chosen
   */
  public RandomClass(boolean isTrueRandom, int falseRandomNumber) {
    this.isTrueRandom = false;
    this.randomNumber = falseRandomNumber;
  }

  @Override
  public int getRandom() {
    if (isTrueRandom) {
      this.randomNumber = r.nextInt(4);
    }
    return this.randomNumber;
  }

  @Override
  public int getRandomWithinBound(int bound) {
    return r.nextInt(bound);
  }

}
