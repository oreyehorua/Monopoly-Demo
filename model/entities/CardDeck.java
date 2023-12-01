package model.entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import model.cards.*;
import model.enums.CardDescription;
import model.enums.SpaceName;
import model.enums.CardType;
import model.spaces.MetroStation;
import model.spaces.Utility;

/**
 * Represents a deck of cards used in the game.
 */
public class CardDeck {

	private final CardType type;
	private final Queue<Card> cards = new LinkedList<>();

	/**
	 * Configures the deck with cards in random order.
	 *
	 * @param type - type of cards in the deck
	 */
	@SuppressWarnings("unchecked")
	public CardDeck(CardType type) {
		this.type = type;

		try {
			ArrayList<Card> temp = new ArrayList<>();

			if (type.equals(CardType.CHEST)) {
				temp.add(new AbsoluteMovementCard(CardDescription.CHEST_01, SpaceName.States_Av));
				temp.add(new AbsoluteMovementCard(CardDescription.CHEST_02, SpaceName.Baltic_Av));
				temp.add(new AbsoluteMovementCard(CardDescription.CHEST_03, SpaceName.Vermont_Av));
				temp.add(new AbsoluteMovementCard(CardDescription.CHEST_04, SpaceName.Atlantic_Av));
				temp.add(new RelativeMovementCard(CardDescription.CHEST_05, 2));
				temp.add(new RelativeMovementCard(CardDescription.CHEST_06, -4));
				temp.add(new SpecialMovementCard(CardDescription.CHEST_07, (Class<Utility>) Class.forName("model.spaces.Utility")));
				temp.add(new MoneyCard(CardDescription.CHEST_08, 100));
				temp.add(new MoneyCard(CardDescription.CHEST_09, 10));
				temp.add(new MoneyCard(CardDescription.CHEST_10, 200));
				temp.add(new MoneyCard(CardDescription.CHEST_11, 25));
				temp.add(new MoneyCard(CardDescription.CHEST_12, -75));
				temp.add(new MoneyCard(CardDescription.CHEST_13, -100));
				temp.add(new MoneyCard(CardDescription.CHEST_14, 100));
				temp.add(new MoneyCard(CardDescription.CHEST_15, -45));
				temp.add(new GoToJailCard(CardDescription.CHEST_16));
			} else {
				temp.add(new AbsoluteMovementCard(CardDescription.CHANCE_01, SpaceName.START_POINT));
				temp.add(new AbsoluteMovementCard(CardDescription.CHANCE_02, SpaceName.Park_place));
				temp.add(new AbsoluteMovementCard(CardDescription.CHANCE_03, SpaceName.Virginia_Av));
				temp.add(new AbsoluteMovementCard(CardDescription.CHANCE_04, SpaceName.St_James_place));
				temp.add(new RelativeMovementCard(CardDescription.CHANCE_05, 5));
				temp.add(new RelativeMovementCard(CardDescription.CHANCE_06, -3));
				temp.add(new SpecialMovementCard(CardDescription.CHANCE_07, (Class<MetroStation>) Class.forName("model.spaces.MetroStation")));
				temp.add(new MoneyCard(CardDescription.CHANCE_08, -50));
				temp.add(new MoneyCard(CardDescription.CHANCE_09, 75));
				temp.add(new MoneyCard(CardDescription.CHANCE_10, -50));
				temp.add(new MoneyCard(CardDescription.CHANCE_11, 100));
				temp.add(new MoneyCard(CardDescription.CHANCE_12, 50));
				temp.add(new MoneyCard(CardDescription.CHANCE_13, 150));
				temp.add(new MoneyCard(CardDescription.CHANCE_14, -40));
				temp.add(new MoneyCard(CardDescription.CHANCE_15, 200));
				temp.add(new GoToJailCard(CardDescription.CHANCE_16));
			}

			Random generator = new Random();

			while (!temp.isEmpty())
				cards.add(temp.remove(generator.nextInt(temp.size())));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Type of cards in the deck
	 */
	public CardType getType() {
		return type;
	}

	/**
	 * Removes a card from the top of the deck and puts it at the bottom.
	 *
	 * @return Card removed from the deck
	 */
	public Card draw() {
		Card drawnCard = cards.poll();
		cards.add(drawnCard);
		return drawnCard;
	}
}
