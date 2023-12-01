package model.cards;

import model.enums.CardDescription;

/**
 * Abstract class representing a card.
 */
public abstract class Card {

    private final CardDescription description;

    public Card(CardDescription description) {
        this.description = description;
    }

    public CardDescription getDescription() {
        return description;
    }

    /**
     * @return Description of the card
     */
    @Override
    public String toString() {
        return description.toString();
    }

}
