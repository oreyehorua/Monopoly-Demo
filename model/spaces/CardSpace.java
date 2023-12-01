package model.spaces;

import model.enums.SpaceName;
import model.enums.CardType;

/**
 * Space where the player draws a card.
 */
public class CardSpace extends Space {

    private final CardType type;

    /**
     * @param type - type of the card to be drawn
     */
    public CardSpace(SpaceName name, CardType type) {
        super(name);
        this.type = type;
    }

    /**
     * @return Type of the card to be drawn
     */
    public CardType getType() {
        return type;
    }
}
