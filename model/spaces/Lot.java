package model.spaces;

import model.enums.*;

/**
 * Represents a property on the board.
 */
public class Lot extends Property {

    private final PropertyColor color;
    private final int constructionCost;
    private final int rentWithoutConstruction;
    private final int rentWithConstruction;
    private boolean hasHouse;
    private boolean hasHotel;

    /**
     * @param constructionCost - cost of building a house/hotel
     */
    public Lot(SpaceName name, int price, PropertyColor color, int constructionCost, int rentWithoutConstruction, int rentWithConstruction) {
        super(name, price);

        this.color = color;

        if (constructionCost >= 0 && rentWithoutConstruction >= 0 && rentWithConstruction >= 0) {
            this.constructionCost = constructionCost;
            this.rentWithoutConstruction = rentWithoutConstruction;
            this.rentWithConstruction = rentWithConstruction;
        } else {
            throw new IllegalArgumentException("Prices cannot be negative!");
        }
    }

    public PropertyColor getColor() {
        return color;
    }

    /**
     * @return Cost of building a house/hotel
     */
    public int getConstructionCost() {
        return constructionCost;
    }

    public boolean hasHouse() {
        return hasHouse;
    }

    public boolean hasHotel() {
        return hasHotel;
    }

    /**
     * @return If the lot has a house or hotel
     */
    public boolean hasConstruction() {
        return hasHouse() || hasHotel();
    }

    public void buildHouse() {
        hasHouse = true;
    }

    public void destroyHouse() {
        hasHouse = false;
    }

    public void buildHotel() {
        hasHotel = true;
    }

    public void destroyHotel() {
        hasHotel = false;
    }

    /**
     * @param price - not used
     */
    @Override
    public int calculateRent(Integer price) {
        if (hasConstruction())
            return rentWithConstruction;
        else
            return rentWithoutConstruction;
    }

    /**
     * Name of the lot, its color, and whether it has a house or hotel.
     */
    @Override
    public String toString() {
        String output = String.format("%s (%s", super.toString(), getColor());

        if (hasHouse)
            output += " - with house";
        else if (hasHotel)
            output += " - with hotel";

        output += ")";

        return output;
    }

}
