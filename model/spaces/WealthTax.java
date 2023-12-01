package model.spaces;

import model.enums.SpaceName;

/**
 * Represents a space that makes the player pay a wealth tax.
 */
public class WealthTax extends Space {

    private final int value = 200;

    public WealthTax(SpaceName name) {
        super(name);
    }

    public int getValue() {
        return value;
    }

}
