package model.cards;

import model.enums.CardDescription;

/**
 * Card that will send the player to jail.
 */
public class GoToJailCard extends Card {

    /**
     * Constructor for GoToJailCard.
     *
     * @param description Description of the card
     */
    public GoToJailCard(CardDescription description) {
        super(description);
    }

}
