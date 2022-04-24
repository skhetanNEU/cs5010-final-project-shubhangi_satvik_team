package model.worldbuilder;

import model.random.RandomGenerator;
import model.world.WorldImpl;

/**
 * World Builder interface which provides functionality to build a world.
 */
public interface WorldBuilderInterface {

    /**
     * A public method used to parse the input text file and
     * set the variables required to instantiate the world.
     *
     * @param file Input text file as a Readable
     * @return Reference of the WorldBuilder Class
     * @throws IllegalArgumentException if there is an error in input file
     */
    WorldBuilder parseInputFile(Readable file);

    /**
     * A public method used to set the random number generator in the world.
     *
     * @param rand represents the random class
     * @return Reference of the WorldBuilder Class
     * @throws IllegalArgumentException if the random class is null
     */
    WorldBuilder setRandomGenerator(RandomGenerator rand);

    /**
     * Method to set the maximum number of turns in the game.
     * @param maxTurns Maximum number of turns in the game.
     * @return WorldBuilder object with max number of turns set accordingly.
     * @throws IllegalArgumentException When max number of turns is zero or negative.
     */
    WorldBuilder setMaxTurns(int maxTurns);

    /**
     * A method used to create an instance of WorldInterface based on the variables of WorldBuilder.
     *
     * @return New instance of WorldImpl
     */
    WorldImpl build();
}
