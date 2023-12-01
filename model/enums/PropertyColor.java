package model.enums;

/**
 * Represents the color of a property lot in the game.
 *
 * Each color corresponds to a group of properties on the game board.
 *
 * Colors:
 * - BROWN
 * - LIGHT_BLUE
 * - PINK
 * - ORANGE
 * - RED
 * - YELLOW
 * - GREEN
 * - BLUE
 */
public enum PropertyColor {

    BROWN("Brown"),
    LIGHT_BLUE("Light Blue"),
    PINK("Pink"),
    ORANGE("Orange"),
    RED("Red"),
    YELLOW("Yellow"),
    GREEN("Green"),
    BLUE("Blue");

    private final String color;

    private PropertyColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}
