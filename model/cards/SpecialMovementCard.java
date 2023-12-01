package model.cards;

import model.enums.CardDescription;
import model.spaces.Space;

/**
 * Card that will move the player to the next space of a certain type
 * (e.g., move the player to the nearest Utility space).
 */
public class SpecialMovementCard extends MovementCard {

	private final Class<? extends Space> spaceType;

	/**
	 * Constructor for SpecialMovementCard.
	 *
	 * @param description Description of the card
	 * @param spaceType   Type of space to which the player will be moved
	 */
	public SpecialMovementCard(CardDescription description, Class<? extends Space> spaceType) {
		super(description);
		this.spaceType = spaceType;
	}

	/**
	 * Get the type of space to which the player will be moved.
	 *
	 * @return Type of space
	 */
	public Class<? extends Space> getSpaceType() {
		return spaceType;
	}

}
