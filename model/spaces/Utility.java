package model.spaces;

import model.enums.SpaceName;

/**
 * Represents the Electric Company and the Water Distribution Company.
 */
public class Utility extends Property {

    public Utility(SpaceName name, int price) {
        super(name, price);
    }

    /**
     * Calculates the rent based on the value rolled on the dice.
     *
     * @param value - value rolled on the dice
     */
    @Override
    public int calculateRent(Integer value) {
        if (this.getOwner().getUtilities().size() == 1)
            return value * 4;
        else
            return value * 10;
    }

}
