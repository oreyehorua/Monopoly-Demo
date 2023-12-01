package model.spaces;

import model.enums.SpaceName;

/**
 * Represents a metro station space in the game.
 * Extends the Property class.
 */
public class MetroStation extends Property {

    /**
     * Constructor for the MetroStation class.
     *
     * @param name   - name of the space
     * @param price  - purchase price of the property
     */
    public MetroStation(SpaceName name, int price) {
        super(name, price);
    }

    /**
     * Calculates the rent for the metro station.
     * It starts at 25 and doubles for each new station bought by the player.
     *
     * @param value - not used
     * @return the calculated rent
     */
    @Override
    public int calculateRent(Integer value) {
        return 25 * (int) Math.pow(2, this.getOwner().getMonopolies().size() - 1);
    }
}
