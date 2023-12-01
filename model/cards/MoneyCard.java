package model.cards;

import model.enums.CardDescription;

/**
 * Card that will make the player receive or lose money.
 */
public class MoneyCard extends Card {

    private final int value;

    /**
     * Constructor for MoneyCard.
     *
     * @param description Description of the card
     * @param value       Value to be received or paid by the player
     */
    public MoneyCard(CardDescription description, int value) {
        super(description);
        this.value = value;
    }

    /**
     * Get the value to be received or paid by the player.
     *
     * @return Value
     */
    public int getValue() {
        return value;
    }

}
