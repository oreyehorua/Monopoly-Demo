package model.cards;

import model.enums.CardDescription;

/**
 * Card that will move the player forward or backward.
 */
public class RelativeMovementCard extends MovementCard {

	private final int displacement;

	/**
	 * Constructor for RelativeMovementCard.
	 *
	 * @param description Description of the card
	 * @param displacement Number of spaces the player will be moved
	 *                     (positive = forward / negative = backward)
	 */
	public RelativeMovementCard(CardDescription description, int displacement) {
		super(description);
		this.displacement = displacement;
	}

	/**
	 * Get the number of spaces the player will be moved.
	 *
	 * @return Number of spaces (positive = forward / negative = backward)
	 */
	public int getDisplacement() {
		return displacement;
	}

}
