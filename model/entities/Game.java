package model.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import model.cards.*;
import model.enums.*;
import model.spaces.*;

public class Game {

    private final String[] players;
    private final Scanner scanner;

    private Board board;
    private ArrayList<Player> remainingPlayers;
    private Dice[] dice;
    private Bank bank;
    private CardDeck chestDeck;
    private CardDeck chanceDeck;
    private Player currentPlayer;


    /**
     * @param players - names of the players
     * @param scanner - scanner to be used for user interaction
     */
    public Game(String[] players, Scanner scanner) {
        if (players.length >= 2)
            this.players = players;
        else
            throw new IllegalArgumentException("The game must have at least 2 players!");
        this.scanner = scanner;
    }


    /**
     * Starts a new game
     */
    public void start() {
        initializeAttributes();
        System.out.println();

        for (Player x : remainingPlayers)
            board.moveTo(x, SpaceName.START_POINT);
        System.out.printf("The game has started! All players are at: %s\n", SpaceName.START_POINT);
        System.out.println();

        for (int i = 0; i < remainingPlayers.size(); ) {
            currentPlayer = remainingPlayers.get(i);

            printStatus(currentPlayer);
            System.out.println();

            /* player won */
            if (validateVictory()) {
                System.out.print("You won the game, congratulations!!!\n");
                return;
            }

            /* player in jail */
            if (currentPlayer.isInJail()) {
                if (tryRelease()) {
                    System.out.println();
                    System.out.print("You have been released!\n");
                    System.out.println();
                } else {
                    System.out.println();
                    System.out.print("You were not released\n");
                    System.out.println();

                    if (currentPlayer.isBankrupt()) {
                        declareBankruptcy();
                        continue;
                    }
                }
            }

            if (!currentPlayer.isInJail()) {
                String command = "[1] Roll the dice";
                readOption(command, 1);
                System.out.println();

                int result1 = currentPlayer.rollDice(dice[0]);
                int result2 = currentPlayer.rollDice(dice[1]);
                System.out.printf("The results were: %d and %d\n", result1, result2);
                System.out.println();

                /* rolled a double */
                if (result1 == result2) {
                    if (currentPlayer.hasRolledThreeDoubles()) {
                        currentPlayer.jail();
                        board.moveTo(currentPlayer, SpaceName.JAIL);
                        System.out.print("You rolled three doubles in a row and got arrested\n");
                        System.out.println();
                    } else {
                        System.out.print("You rolled a double and will play again next round!\n");
                        System.out.println();

                        i--; //cancels the i++ at the end of the loop and goes back to the current player
                    }
                }

                if (!currentPlayer.isInJail()) {    //if the player is in jail, the round ends there
                    /* move player */
                    if (board.move(currentPlayer, result1 + result2)) {
                        goAroundBoard();
                        System.out.println();
                    }
                    System.out.printf("You moved to: %s\n", currentPlayer.getPosition());
                    System.out.println();

                    while (true) {
                        /* interact with the space the player landed on */
                        if (currentPlayer.getPosition() instanceof CardSpace) {
                            if (interactWithCardSpace()) {
                                System.out.println();
                                continue;
                            }
                        } else if (currentPlayer.getPosition() instanceof IncomeTax) {
                            interactWithIncomeTax();
                        } else if (currentPlayer.getPosition() instanceof WealthTax) {
                            interactWithWealthTax();
                        } else if (currentPlayer.getPosition() instanceof GoToJail) {
                            interactWithGoToJail();
                        } else if (currentPlayer.getPosition() instanceof Property) {
                            interactWithProperty(result1 + result2);
                        } else {
                            System.out.print("Nothing happens in this space\n");
                        }

                        System.out.println();
                        break;
                    }
                }
            }

            /* player won */
            if (!currentPlayer.isBankrupt() && validateVictory()) {
                System.out.print("You won, congratulations!!!\n");
                return;
            }

            /* circular iteration */
            if (!currentPlayer.isBankrupt())  //bankrupt players are removed from the list
                i = ++i % remainingPlayers.size();
            System.out.print("-----------------------------------------\n");
            System.out.println();
        }
    }


    /**
     * Initializes the attributes and decides the order of the players
     */
    private void initializeAttributes() {
        board = new Board();
        remainingPlayers = new ArrayList<>();
        dice = new Dice[2];
        dice[0] = new Dice();
        dice[1] = new Dice();
        bank = new Bank();
        chestDeck = new CardDeck(CardType.CHEST);
        chanceDeck = new CardDeck(CardType.CHANCE);

        ArrayList<Player> temp = new ArrayList<>();
        for (String x : players)
            temp.add(new Player(x));

        System.out.print("Time to decide the play order\n");
        System.out.println();

        /* rolls dice to decide play order */
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            Player player = temp.get(i);
            String command = String.format("Player: %s\n", player);
            command += "[1] Roll the dice";
            readOption(command, 1);
            System.out.println();

            int result1 = player.rollDice(dice[0]);
            int result2 = player.rollDice(dice[1]);
            System.out.printf("The results were: %d and %d\n", result1, result2);

            player.clearDoublesHistory();    //doubles do not count yet

            if (!results.contains(result1 + result2)) {
                results.add(result1 + result2);
            } else {    //ordering makes complete ordering impossible
                System.out.println();
                System.out.print("Another player has already rolled the same result, roll again!\n");
                i--;
            }

            System.out.println();
        }

        ArrayList<Integer> sortedResults = new ArrayList<>(results);
        sortedResults.sort(Comparator.reverseOrder());

        /* fills the attribute with players ordered by the result of the dice */
        for (Integer x : sortedResults) {
            remainingPlayers.add(temp.get(results.indexOf(x)));
        }

        System.out.printf("Order: %s\n", remainingPlayers);
        System.out.println();

        System.out.print("-----------------------------------------\n");
    }


    /**
     * @return Whether the current player is the winner
     */
    private boolean validateVictory() {

        if (remainingPlayers.size() == 1 || currentPlayer.getNumberOfMonopolies() >= 2) {
            return true;
        } else {
            for (Lot x : currentPlayer.getLots())
                if (x.hasHotel())
                    return true;

            return false;
        }
    }


    /**
     * @return Whether the current player managed to get out of jail
     */
    private boolean tryRelease() {
        System.out.print("You are in jail\n");

        int bail = ((Jail) currentPlayer.getPosition()).getBail();

        // 1st round in jail: can pay bail or try rolling a double
        // 2nd round in jail: can pay bail or try rolling a double
        // 3rd round in jail: must try rolling a double
        // 4th round in jail: must pay bail (if not possible, the player goes bankrupt)

        if (currentPlayer.getRoundsInJail() < 3) {
            if (currentPlayer.getRoundsInJail() < 2) {
                while (true) {
                    String command = String.format("[1] Pay bail of $%d\n", bail);
                    command += "[2] Roll the dice";
                    int choice = readOption(command, 2);
                    System.out.println();

                    if (choice == 1) {
                        if (currentPlayer.getBalance() >= bail) {
                            bank.receive(currentPlayer, bail);
                            System.out.printf("You successfully paid the bail (updated balance: $%d)\n", currentPlayer.getBalance());
                            currentPlayer.release();
                            return true;
                        } else {
                            System.out.print("Insufficient funds to pay the bail\n");
                            System.out.println();
                        }
                    } else {
                        break;
                    }
                }
            } else {
                String command = "[1] Roll the dice";
                readOption(command, 1);
                System.out.println();
            }

            int roll1 = currentPlayer.rollDice(dice[0]);
            int roll2 = currentPlayer.rollDice(dice[1]);
            System.out.printf("The results were: %d and %d\n", roll1, roll2);

            if (roll1 == roll2) {
                System.out.println();
                System.out.print("You rolled a double!\n");
                currentPlayer.release();
                return true;
            } else {
                currentPlayer.increaseRoundsInJail();
                return false;
            }
        } else {
            String command = String.format("[1] Pay bail of $%d", bail);
            readOption(command, 1);
            System.out.println();

            bank.receive(currentPlayer, 50);

            if (!currentPlayer.isBankrupt()) {
                System.out.printf("You successfully paid the bail (updated balance: $%d)\n", currentPlayer.getBalance());
                currentPlayer.release();
                return true;
            } else {
                System.out.print("You couldn't pay the bail\n");
                return false;
            }
        }
    }


    /**
     * @return Whether it is necessary to interact with the card space again
     */
    private boolean interactWithCardSpace() {
        CardSpace position = (CardSpace) currentPlayer.getPosition();

        String command = "[1] Draw a card";
        readOption(command, 1);
        System.out.println();

        /* draw a card */
        Card temp;
        if (position.getName() == SpaceName.CHEST)
            temp = currentPlayer.drawCard(chestDeck);
        else // chance
            temp = currentPlayer.drawCard(chanceDeck);
        System.out.printf("Card drawn: %s\n", temp);
        System.out.println();

        boolean interactAgain = false;

        if (temp instanceof MoneyCard) {
            MoneyCard card = (MoneyCard) temp;
            int absoluteValue = Math.abs(card.getValue());

            if (card.getValue() > 0) {
                String command_ = String.format("[1] Receive $%d", absoluteValue);
                readOption(command_, 1);
                System.out.println();

                bank.pay(currentPlayer, absoluteValue);
                System.out.printf("You received $%d! (updated balance: $%d)\n", absoluteValue, currentPlayer.getBalance());
            } else {
                String command_ = String.format("[1] Pay $%d", absoluteValue);
                readOption(command_, 1);
                System.out.println();

                bank.receive(currentPlayer, absoluteValue);
                if (!currentPlayer.isBankrupt())
                    System.out.printf("You paid $%d (updated balance: $%d)\n", absoluteValue, currentPlayer.getBalance());
                else
                    declareBankruptcy();
            }
        } else if (temp instanceof GoToJailCard) {
            currentPlayer.jail();
            board.moveTo(currentPlayer, SpaceName.JAIL);
            System.out.print("You went to jail\n");
        } else if (temp instanceof AbsoluteMovementCard) {
            AbsoluteMovementCard card = (AbsoluteMovementCard) temp;

            String command_ = String.format("[1] Advance to %s", card.getDestination());
            readOption(command_, 1);
            System.out.println();

            if (board.moveTo(currentPlayer, card.getDestination())) {
                goAroundBoard();
                System.out.println();
            }

            System.out.printf("You advanced to %s\n", currentPlayer.getPosition());

            interactAgain = true;
        } else if (temp instanceof RelativeMovementCard) {
            RelativeMovementCard card = (RelativeMovementCard) temp;
            int steps = Math.abs(card.getDisplacement());

            if (card.getDisplacement() > 0) {
                String command_ = String.format("[1] Advance %d spaces", steps);
                readOption(command_, 1);
                System.out.println();

                if (board.move(currentPlayer, steps)) {
                    goAroundBoard();
                    System.out.println();
                }

                System.out.printf("You advanced %d spaces and are now at: %s\n", steps, currentPlayer.getPosition());
            } else {
                String command_ = String.format("[1] Go back %d spaces", steps);
                readOption(command_, 1);
                System.out.println();

                board.moveBack(currentPlayer, steps);
                System.out.printf("You went back %d spaces and are now at: %s\n", steps, currentPlayer.getPosition());
            }

            interactAgain = true;
        } else { // SpecialMovementCard
            SpecialMovementCard card = (SpecialMovementCard) temp;

            String command_ = "[1] Advance";
            readOption(command_, 1);
            System.out.println();

            if (board.moveToNext(currentPlayer, card.getSpaceType())) {
                goAroundBoard();
                System.out.println();
            }

            System.out.printf("You advanced and are now at: %s\n", currentPlayer.getPosition());

            interactAgain = true;
        }

        return interactAgain;
    }

    private void interactWithIncomeTax() {
        IncomeTax position = (IncomeTax) currentPlayer.getPosition();
        String command = String.format("[1] Pay $%d\n", position.getValue());
        command += "[2] Pay 10% of your total wealth";
        int option = readOption(command, 2);
        System.out.println();

        int value;
        if (option == 1)
            value = position.getValue();
        else
            value = position.getValue(currentPlayer);

        bank.receive(currentPlayer, value);
        if (!currentPlayer.isBankrupt()) {
            System.out.printf("You successfully paid income tax of $%d (updated balance: $%d)\n", value, currentPlayer.getBalance());
        } else {
            System.out.printf("You couldn't pay income tax of $%d\n", value);
            System.out.println();
            declareBankruptcy();
        }
    }

    private void interactWithWealthTax() {
        WealthTax position = (WealthTax) currentPlayer.getPosition();

        String command = String.format("[1] Pay wealth tax ($%d)", position.getValue());
        readOption(command, 1);
        System.out.println();

        bank.receive(currentPlayer, position.getValue());

        if (!currentPlayer.isBankrupt()) {
            System.out.printf("You successfully paid the wealth tax (updated balance: $%d)\n", currentPlayer.getBalance());
        } else {
            System.out.print("You couldn't pay the wealth tax\n");
            System.out.println();
            declareBankruptcy();
        }
    }

    private void interactWithGoToJail() {
        currentPlayer.jail();
        board.moveTo(currentPlayer, SpaceName.JAIL);
        System.out.print("You have been arrested\n");
    }

    private void interactWithProperty(int diceResult) {
        Property position = (Property) currentPlayer.getPosition();

        if (position.getOwner() == null) {
            String command = String.format("The property is for sale for $%d, do you want to buy?\n", position.getPrice());
            command += "[1] Yes\n";
            command += "[2] No";
            int choice = readOption(command, 2);

            if (choice == 1) {
                System.out.println();
                if (currentPlayer.getBalance() >= position.getPrice()) {
                    bank.receive(currentPlayer, position.getPrice());
                    currentPlayer.addProperty(position);
                    position.setOwner(currentPlayer);
                    System.out.printf("You acquired the property successfully! (updated balance: $%d)\n", currentPlayer.getBalance());

                    if (position instanceof Lot) {
                        PropertyColor color = ((Lot) position).getColor();
                        if (validateMonopoly(color)) {
                            currentPlayer.addMonopoly(color);
                            System.out.println();
                            System.out.printf("You achieved a monopoly on lots of color: %s!\n", color);
                        }
                    }
                } else {
                    System.out.print("Your balance is insufficient to acquire the property\n");
                }
            }
        } else if (position instanceof Lot && currentPlayer.hasMonopoly(((Lot) position).getColor())) {
            Lot lot = (Lot) position;

            if (!(lot.hasHouse() || lot.hasHotel())) {
                String command = String.format("Building a house is available on the lot ($%d), do you want to build?\n", lot.getConstructionCost());
                command += "[1] Yes\n";
                command += "[2] No";
                int choice = readOption(command, 2);
                System.out.println();

                if (choice == 1) {
                    if (currentPlayer.getBalance() >= lot.getConstructionCost()) {
                        bank.receive(currentPlayer, lot.getConstructionCost());
                        lot.buildHouse();
                        System.out.printf("You built a house successfully! (updated balance: $%d)\n", currentPlayer.getBalance());
                    } else {
                        System.out.print("Your balance is insufficient to build a house\n");
                    }
                }
            } else if (!lot.hasHotel()) {
                boolean allHaveHouse = true; // if all lots in the monopoly have a house
                for (Lot x : currentPlayer.getLots())
                    if (x.getColor() == lot.getColor() && !x.hasHouse())
                        allHaveHouse = false;

                if (allHaveHouse) {
                    String command = String.format("Building a hotel is available on the lot ($%d), do you want to build?\n", lot.getConstructionCost());
                    command += "[1] Yes\n";
                    command += "[2] No";
                    int choice = readOption(command, 2);

                    if (choice == 1) {
                        System.out.println();
                        if (currentPlayer.getBalance() >= lot.getConstructionCost()) {
                            bank.receive(currentPlayer, lot.getConstructionCost());
                            lot.destroyHouse();
                            lot.buildHotel();
                            System.out.printf("You built a hotel successfully! It replaced the house on the lot (updated balance: $%d)\n", currentPlayer.getBalance());
                        } else {
                            System.out.print("Your balance is insufficient to build a hotel\n");
                        }
                    }
                }
            }
        } else if (position.getOwner() != currentPlayer) {
            System.out.printf("Property of: %s\n", position.getOwner());
            System.out.println();

            if (position.getOwner().isBankrupt()) {
                System.out.print("The owner of the property is arrested and cannot receive rent\n");
            } else {
                // Monopoly doubles the rent of lots
                int rent = position.calculateRent(diceResult);
                if (position instanceof Lot && currentPlayer.hasMonopoly(((Lot) position).getColor()))
                    rent *= 2;

                String command = String.format("[1] Pay rent of $%d", rent);
                readOption(command, 1);
                System.out.println();

                bank.transfer(position.getOwner(), currentPlayer, rent);
                if (!currentPlayer.isBankrupt()) {
                    System.out.printf("You paid the rent successfully (updated balance: $%d)\n", currentPlayer.getBalance());
                } else {
                    System.out.print("Rent could not be paid\n");
                    System.out.println();
                    declareBankruptcy();
                    return;
                }
            }
            System.out.println();

            while (true) {
                String command = String.format("[%s] Do you want to make an offer for the property?\n", currentPlayer);
                command += "[1] Yes\n";
                command += "[2] No";
                int option = readOption(command, 2);

                if (option == 1) {
                    System.out.println();
                    if (currentPlayer.getBalance() <= 0) {
                        System.out.print("You do not have sufficient balance to make an offer!\n");
                        break;
                    } else if (negotiateProperty(position)) {
                        break;
                    } else {
                        System.out.println();
                    }
                } else {
                    break;
                }
            }
        } else {
            System.out.print("This property is yours\n");
        }
    }

    private boolean negotiateProperty(Property property) {
        Player seller = property.getOwner();
        Player buyer = currentPlayer;
        int value;

        while (true) {
            try {
                System.out.printf("[%s] Offer value: ", buyer);
                value = Integer.parseInt(scanner.nextLine());

                if (value < 0)
                    System.out.print("The value cannot be negative!\n");
                else if (value > buyer.getBalance())
                    System.out.print("The value cannot be greater than your balance!\n");
                else
                    break;
            } catch (NumberFormatException e) {
                System.out.print("The value must be an integer!\n");
            }

            System.out.println();
        }

        System.out.println();
        String command = String.format("[%s] Do you accept the offer of $%d for the property?\n", seller, value);
        command += "[1] Yes\n";
        command += "[2] No";
        int option = readOption(command, 2);
        System.out.println();

        if (option == 1) {
            bank.sellProperty(property, buyer, seller, value);
            System.out.printf("[%s] You sold the property successfully! (updated balance: $%d)\n", seller, seller.getBalance());

            if (property instanceof Lot) {
                PropertyColor color = ((Lot) property).getColor();
                if (seller.hasMonopoly(color)) {
                    seller.removeMonopoly(color);
                    System.out.printf("[%s] You lost the monopoly on lots of color: %s\n", seller, color);
                }
            }

            System.out.println();
            System.out.printf("[%s] You bought the property successfully! (updated balance: $%d)\n", buyer, buyer.getBalance());

            if (property instanceof Lot) {
                PropertyColor color = ((Lot) property).getColor();
                if (validateMonopoly(color)) {
                    buyer.addMonopoly(color);
                    System.out.printf("[%s] You achieved the monopoly on lots of color: %s!\n", buyer, color);
                }
            }

            return true;
        } else {
            System.out.printf("[%s] Your offer was declined\n", buyer);
            return false;
        }
    }


    /**
     * Reads and returns the user's choice.
     *
     * @param command    - what will be requested from the user
     * @param numOptions - number of options
     * @return Read option
     */
    private int readOption(String command, int numOptions) {
        int option;

        while (true) {
            try {
                System.out.println(command);
                System.out.print("Option: ");
                option = Integer.parseInt(scanner.nextLine());

                if (option > 0 && option <= numOptions) {
                    break;
                } else {
                    System.out.println();
                    System.out.print("Incorrect option!\n");
                }
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.print("Input must be an integer!\n");
            }
            System.out.println();
        }

        return option;
    }

    private boolean validateMonopoly(PropertyColor color) {
        ArrayList<Lot> lotGroup = board.getLots(color);

        boolean monopoly = true;
        for (Lot x : lotGroup)
            if (x.getOwner() != currentPlayer) {
                monopoly = false;
                break;
            }

        return monopoly;
    }

    /**
     * Rewards the player who went around the board
     */
    private void goAroundBoard() {
        StartingPoint start = (StartingPoint) board.getSpace(SpaceName.START_POINT);
        bank.pay(currentPlayer, start.getValue());
        System.out.printf("You went around the board and received $%d! (updated balance: $%d)\n", start.getValue(), currentPlayer.getBalance());
    }

    private void printStatus(Player player) {
        /* print player status */
        System.out.printf("Player: %s\n", player);
        System.out.printf("Current balance: $%d\n", player.getBalance());
        System.out.print("Properties: ");
        if (!currentPlayer.getProperties().isEmpty()) {
            System.out.println(currentPlayer.getProperties());
            showMonopolyProgress();
        } else {
            System.out.print("none\n");
        }
        System.out.printf("Current position: %s\n", player.getPosition());
    }

    /**
     * Shows how close the current player is to achieving monopolies
     */
    private void showMonopolyProgress() {
        ArrayList<String> progress = new ArrayList<>();

        PropertyColor color = PropertyColor.YELLOW;
        ArrayList<Lot> possessions = currentPlayer.getLots(color);
        ArrayList<Lot> allLots = board.getLots(color);
        if (!possessions.isEmpty())
            progress.add(String.format("%s (%d/%d)", color, possessions.size(), allLots.size()));

        color = PropertyColor.BLUE;
        possessions = currentPlayer.getLots(color);
        allLots = board.getLots(color);
        if (!possessions.isEmpty())
            progress.add(String.format("%s (%d/%d)", color, possessions.size(), allLots.size()));

        color = PropertyColor.LIGHT_BLUE;
        possessions = currentPlayer.getLots(color);
        allLots = board.getLots(color);
        if (!possessions.isEmpty())
            progress.add(String.format("%s (%d/%d)", color, possessions.size(), allLots.size()));

        color = PropertyColor.ORANGE;
        possessions = currentPlayer.getLots(color);
        allLots = board.getLots(color);
        if (!possessions.isEmpty())
            progress.add(String.format("%s (%d/%d)", color, possessions.size(), allLots.size()));

        color = PropertyColor.BROWN;
        possessions = currentPlayer.getLots(color);
        allLots = board.getLots(color);
        if (!possessions.isEmpty())
            progress.add(String.format("%s (%d/%d)", color, possessions.size(), allLots.size()));

        color = PropertyColor.PINK;
        possessions = currentPlayer.getLots(color);
        allLots = board.getLots(color);
        if (!possessions.isEmpty())
            progress.add(String.format("%s (%d/%d)", color, possessions.size(), allLots.size()));

        color = PropertyColor.GREEN;
        possessions = currentPlayer.getLots(color);
        allLots = board.getLots(color);
        if (!possessions.isEmpty())
            progress.add(String.format("%s (%d/%d)", color, possessions.size(), allLots.size()));

        color = PropertyColor.RED;
        possessions = currentPlayer.getLots(color);
        allLots = board.getLots(color);
        if (!possessions.isEmpty())
            progress.add(String.format("%s (%d/%d)", color, possessions.size(), allLots.size()));

        if (!progress.isEmpty())
            System.out.printf("Monopoly Progress: %s\n", progress);
    }

    private void declareBankruptcy() {
        System.out.print("You went bankrupt!\n");
        remainingPlayers.remove(currentPlayer);
    }
}