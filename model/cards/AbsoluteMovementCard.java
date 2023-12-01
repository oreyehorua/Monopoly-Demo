package model.cards;

import model.enums.CardDescription;
import model.enums.SpaceName;

/**
 * Card that will move the player to a specific space on the board.
 */
public class AbsoluteMovementCard extends MovementCard {

	private final SpaceName destination;

	/**
	 * Constructor for AbsoluteMovementCard.
	 *
	 * @param description Description of the card
	 * @param destination Destination space for the player
	 */
	public AbsoluteMovementCard(CardDescription description, SpaceName destination) {
		super(description);
		this.destination = destination;
	}

	/**
	 * Get the destination space for the player.
	 *
	 * @return Destination space
	 */
	public SpaceName getDestination() {
		return destination;
	}

}
