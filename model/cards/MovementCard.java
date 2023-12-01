package model.cards;

import model.enums.CardDescription;

/**
 * Card that will cause player movement on the board.
 */
public abstract class MovementCard extends Card {

    /**
     * Constructor for MovementCard.
     *
     * @param description Description of the card
     */
    public MovementCard(CardDescription description) {
        super(description);
    }

}
