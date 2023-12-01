package model.entities;

import java.util.Random;

/**
 * Represents a six-sided dice used in the game.
 */
public class Dice {

    private final int numberOfSides = 6;
    private final Random generator;

    /**
     * Constructor for Dice.
     */
    public Dice() {
        this.generator = new Random();
    }

    /**
     * Rolls the dice and returns the result.
     *
     * @return Result of the roll
     */
    public int roll() {
        return generator.nextInt(this.numberOfSides) + 1;
    }

}
