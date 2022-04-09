package model.random;

/**
 * This is the interface used to implement a random number generator.
 */
public interface RandomGenerator {
  /**
   * Returns a random number between 0 and 4.
   * @return a random number
   */
  int getRandom();

  /**
   * Returns a random number between 0 and the upper bound for the rooms index.
   * @param bound represents the upper bound
   * @return a random number
   */
  int getRandomWithinBound(int bound);
}