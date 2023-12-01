package model.entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import model.cards.Card;
import model.enums.PropertyColor;
import model.spaces.*;

/**
 * Represents a player in the game.
 */
public class Player {

    private final String name;
    private final ArrayList<Property> properties = new ArrayList<>();
    private final ArrayList<PropertyColor> monopolies = new ArrayList<>();
    private final Queue<Boolean> lastRolls = new LinkedList<>();
    private Integer firstRoll;
    private int balance = 1500;
    private Space position;
    private boolean inJail;
    private boolean bankrupt;
    private int roundsInJail;

    /**
     * Initializes the player with a balance of 1500.
     *
     * @param name - player's name
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * @return Copy of the player's properties list
     */
    public ArrayList<Property> getProperties() {
        return new ArrayList<>(properties);
    }

    /**
     * @param p - property to be added
     * @return If the property was added
     */
    public boolean addProperty(Property p) {
        return properties.add(p);
    }

    /**
     * @param p - property to be removed
     * @return If the property was removed
     */
    public boolean removeProperty(Property p) {
        return properties.remove(p);
    }

    /**
     * @param index - index in the property array to be removed
     * @return Removed property
     */
    public Property removeProperty(int index) {
        return properties.remove(index);
    }

    /**
     * @return Copy of the player's lot list
     */
    public ArrayList<Lot> getLots() {
        ArrayList<Lot> lots = new ArrayList<>();

        for (Property x : properties)
            if (x instanceof Lot)
                lots.add((Lot) x);

        return lots;
    }

    /**
     * @param color - color of lots to be returned
     * @return Copy of the player's lot list with the specified color
     */
    public ArrayList<Lot> getLots(PropertyColor color) {
        ArrayList<Lot> lots = new ArrayList<>();

        for (Property x : properties)
            if (x instanceof Lot) {
                Lot lot = (Lot) x;
                if (lot.getColor() == color)
                    lots.add(lot);
            }

        return lots;
    }

    /**
     * @return Copy of the player's utility list
     */
    public ArrayList<Utility> getUtilities() {
        ArrayList<Utility> utilities = new ArrayList<>();

        for (Property x : properties)
            if (x instanceof Utility)
                utilities.add((Utility) x);

        return utilities;
    }

    /**
     * @return Copy of the player's metro stations list
     */
    public ArrayList<MetroStation> getMetroStations() {
        ArrayList<MetroStation> metroStations = new ArrayList<>();

        for (Property x : properties)
            if (x instanceof MetroStation)
                metroStations.add((MetroStation) x);

        return metroStations;
    }

    /**
     * @return Result of the dice roll
     */
    public int rollDice(Dice dice) {
        int result = dice.roll();

        // this is the first die of the roll
        if (firstRoll == null) {
            firstRoll = result;
        }
        // this is the second die of the roll
        else {
            addRoll(firstRoll == result);
            firstRoll = null;
        }

        return result;
    }

    /**
     * Adds the result of a roll to the lastRolls history.
     *
     * @param isDouble - if the last roll was a double
     */
    private void addRoll(boolean isDouble) {
        lastRolls.add(isDouble);

        // the queue will always have a maximum of the last 3 rolls
        while (lastRolls.size() > 3)
            lastRolls.remove();
    }

    /**
     * Clears the history of doubles from the player's last 3 rolls.
     */
    public void clearDoublesHistory() {
        lastRolls.clear();
    }

    /**
     * Checks if the last three rolls were doubles and, if positive, clears the doubles history.
     *
     * @return If the last three rolls were doubles
     */
    public boolean hasRolledThreeDoubles() {
        if (lastRolls.size() < 3)
            return false;

        for (boolean x : lastRolls)
            if (!x)
                return false;

        clearDoublesHistory();

        return true;
    }

    public int getBalance() {
        return balance;
    }

    /**
     * @param amount - amount to increase the balance
     */
    public void increaseBalance(int amount) {
        if (amount >= 0)
            this.balance += amount;
        else
            throw new IllegalArgumentException("Negative amount!");
    }

    /**
     * @param amount - amount to decrease the balance
     */
    public void decreaseBalance(int amount) {
        if (amount >= 0)
            this.balance -= amount;
        else
            throw new IllegalArgumentException("Negative amount!");
    }

    public Space getPosition() {
        return position;
    }

    public void setPosition(Space position) {
        this.position = position;
    }

    public boolean isInJail() {
        return inJail;
    }

    /**
     * Sets the player as in jail.
     */
    public void jail() {
        inJail = true;
    }

    /**
     * Sets the player as not in jail and resets their rounds in jail.
     */
    public void release() {
        inJail = false;
        roundsInJail = 0;
        clearDoublesHistory();
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public void goBankrupt() {
        this.bankrupt = true;
    }

    public int getRoundsInJail() {
        return roundsInJail;
    }

    /**
     * Increases the number of rounds in jail by 1.
     */
    public void increaseRoundsInJail() {
        roundsInJail++;
    }

    /**
     * @return Copy of the player's monopolies list
     */
    public ArrayList<PropertyColor> getMonopolies() {
        return new ArrayList<>(monopolies);
    }

    /**
     * @return Number of monopolies the player has
     */
    public int getNumberOfMonopolies() {
        return monopolies.size();
    }

    /**
     * @param color - color of the lots under monopoly
     * @return If the monopoly was successfully added
     */
    public boolean addMonopoly(PropertyColor color) {
        return monopolies.add(color);
    }

    /**
     * @param color - color of the lots under monopoly
     * @return If the monopoly was successfully removed
     */
    public boolean removeMonopoly(PropertyColor color) {
        return monopolies.remove(color);
    }

    /**
     * @param color - color of the lots under monopoly
     * @return If the player has a monopoly over lots of the specified color
     */
    public boolean hasMonopoly(PropertyColor color) {
        return monopolies.contains(color);
    }

    /**
     * @param deck - deck from which to draw a card
     * @return Card drawn from the deck
     */
    public Card drawCard(CardDeck deck) {
        return deck.draw();
    }

    /**
     * @return Player's name
     */
    @Override
    public String toString() {
        return name;
    }
}
