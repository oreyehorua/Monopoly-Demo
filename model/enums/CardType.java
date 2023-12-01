package model.enums;

/**
 * Types of cards in the game, including Community Chest and Chance cards.
 */
public enum CardType {

    CHEST("Community Chest"),
    CHANCE("Chance");

    private final String type;

    private CardType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
