package model.spaces;

import model.entities.Player;
import model.enums.SpaceName;

/**
 * Space where the player pays income tax.
 */
public class IncomeTax extends Space {

    private final int fixedValue = 200;

    public IncomeTax(SpaceName name) {
        super(name);
    }

    /**
     * Method if the player chooses to pay a fixed value.
     */
    public int getValue() {
        return fixedValue;
    }

    /**
     * Method if the player chooses to pay 10% of their total wealth.
     *
     * @param player - player who will pay
     */
    public int getValue(Player player) {
        int wealth = 0;

        wealth += player.getBalance();

        for (Property x : player.getProperties()) {
            wealth += x.getPrice();

            if (x instanceof Lot) {
                Lot lot = (Lot) x;
                if (lot.hasConstruction())
                    wealth += lot.getConstructionCost();
            }
        }

        return (int) (wealth * 0.1);
    }
}
