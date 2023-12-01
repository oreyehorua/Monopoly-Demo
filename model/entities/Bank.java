package model.entities;

import model.spaces.Lot;
import model.spaces.Property;

/**
 * Controls the monetary operations of the game.
 */
public class Bank {

	/**
	 * Gives money to a player.
	 */
	public void pay(Player player, int amount) {
		if (amount < 0)
			throw new IllegalArgumentException("Cannot pay a negative amount!");

		player.increaseBalance(amount);
	}

	/**
	 * Takes money from a player and bankrupts them if they can't pay.
	 */
	public void receive(Player player, int amount) {
		if (amount < 0)
			throw new IllegalArgumentException("Cannot receive a negative amount!");

		if (player.getBalance() >= amount) {
			player.decreaseBalance(amount);
		} else {
			player.decreaseBalance(player.getBalance());

			while (!player.getProperties().isEmpty()) {
				Property temp = player.removeProperty(0);
				temp.removeOwner();

				if (temp instanceof Lot) {
					Lot tempLot = (Lot) temp;
					tempLot.destroyHouse();
					tempLot.destroyHotel();
				}
			}

			player.goBankrupt();
		}
	}

	/**
	 * Transfers money from one player to another and bankrupts the payer if they can't pay.
	 *
	 * @param destination - player who will receive the money
	 * @param source      - player who will give the money
     */
	public void transfer(Player destination, Player source, int amount) {
		if (amount < 0)
			throw new IllegalArgumentException("Cannot transfer a negative amount!");

		if (source.getBalance() >= amount) {
			source.decreaseBalance(amount);
			destination.increaseBalance(amount);
		} else {
			destination.increaseBalance(source.getBalance());
			source.decreaseBalance(source.getBalance());

			while (!source.getProperties().isEmpty()) {
				Property temp = source.removeProperty(0);
				destination.addProperty(temp);
				temp.setOwner(destination);

				if (temp instanceof Lot) {
					Lot tempLot = (Lot) temp;
					tempLot.destroyHouse();
					tempLot.destroyHotel();
				}
			}

			source.goBankrupt();
		}
	}

	/**
	 * @param property - property to be sold
	 * @param offer    - value for which the buyer will buy the property
	 */
	public void sellProperty(Property property, Player buyer, Player seller, int offer) {
		transfer(seller, buyer, offer);
		seller.removeProperty(property);
		buyer.addProperty(property);
		property.setOwner(buyer);

		if (property instanceof Lot) {
			Lot lot = (Lot) property;
			lot.destroyHouse();
			lot.destroyHotel();
		}
	}

	/**
	 * @param property - property to be sold
     */
	public void sellProperty(Property property, Player buyer) {
		receive(buyer, property.getPrice());
		buyer.addProperty(property);
		property.setOwner(buyer);
	}
}
