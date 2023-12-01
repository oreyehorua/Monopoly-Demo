package model.spaces;

import model.enums.SpaceName;

/**
 * Represents the starting point space on the board.
 */
public class StartingPoint extends Space {

    private final int value = 200;

    public StartingPoint(SpaceName name) {
        super(name);
    }

    /**
     * @return The amount that the player will receive when passing through the space.
     */
    public int getValue() {
        return value;
    }

}
