package model.spaces;

import model.entities.Player;
import model.enums.SpaceName;

/**
 * Represents a space that can be owned by players.
 */
public abstract class Property extends Space {

    private final int price;
    private Player owner;

    public Property(SpaceName name, int price) {
        super(name);

        if (price >= 0)
            this.price = price;
        else
            throw new IllegalArgumentException("Prices cannot be negative!");
    }

    public int getPrice() {
        return price;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void removeOwner() {
        this.owner = null;
    }

    /**
     * @return The rent amount to be paid.
     */
    public abstract int calculateRent(Integer value);

}
