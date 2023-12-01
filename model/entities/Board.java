package model.entities;

import java.util.ArrayList;

import model.enums.PropertyColor;
import model.enums.SpaceName;
import model.enums.CardType;
import model.spaces.*;

/**
 * Game board, responsible for player movement on it.
 */
public class Board {

    private final ArrayList<Space> spaces;
    private final ArrayList<Lot> brownLots;
    private final ArrayList<Lot> lightBlueLots;
    private final ArrayList<Lot> pinkLots;
    private final ArrayList<Lot> orangeLots;
    private final ArrayList<Lot> redLots;
    private final ArrayList<Lot> yellowLots;
    private final ArrayList<Lot> greenLots;
    private final ArrayList<Lot> blueLots;

    /**
     * Initializes the board spaces and groups lots by color.
     */
    public Board() {
        spaces = new ArrayList<>();
        spaces.add(new StartingPoint(SpaceName.START_POINT));
        spaces.add(new Lot(SpaceName.Mediterranean_Avenue, 60, PropertyColor.BROWN, 50, 2, 10));
        spaces.add(new CardSpace(SpaceName.CHEST, CardType.CHEST));
        spaces.add(new Lot(SpaceName.Baltic_Av, 60, PropertyColor.BROWN, 50, 4, 20));
        spaces.add(new IncomeTax(SpaceName.INCOME_TAX));
        spaces.add(new MetroStation(SpaceName.Reading_railroad, 200));
        spaces.add(new Lot(SpaceName.Oriental_Av, 100, PropertyColor.LIGHT_BLUE, 50, 6, 30));
        spaces.add(new CardSpace(SpaceName.CHANCE, CardType.CHANCE));
        spaces.add(new Lot(SpaceName.Vermont_Av, 100, PropertyColor.LIGHT_BLUE, 50, 6, 30));
        spaces.add(new Lot(SpaceName.Connecticut_Av, 120, PropertyColor.LIGHT_BLUE, 50, 8, 40));
        spaces.add(new Jail(SpaceName.JAIL));
        spaces.add(new Lot(SpaceName.St_Charles_place, 140, PropertyColor.PINK, 100, 10, 50));
        spaces.add(new Utility(SpaceName.ELECTRIC_COMPANY, 150));
        spaces.add(new Lot(SpaceName.States_Av, 140, PropertyColor.PINK, 100, 10, 50));
        spaces.add(new Lot(SpaceName.Virginia_Av, 160, PropertyColor.PINK, 100, 12, 60));
        spaces.add(new MetroStation(SpaceName.Pennsylvania_railroad, 200));
        spaces.add(new Lot(SpaceName.St_James_place, 180, PropertyColor.ORANGE, 100, 14, 70));
        spaces.add(new CardSpace(SpaceName.CHEST, CardType.CHEST));
        spaces.add(new Lot(SpaceName.Tennessee_Av, 180, PropertyColor.ORANGE, 100, 14, 70));
        spaces.add(new Lot(SpaceName.New_York_Av, 200, PropertyColor.ORANGE, 100, 16, 80));
        spaces.add(new FreeParking(SpaceName.FREE_PARKING));
        spaces.add(new Lot(SpaceName.Kentucky_Av, 220, PropertyColor.RED, 150, 18, 90));
        spaces.add(new CardSpace(SpaceName.CHANCE, CardType.CHANCE));
        spaces.add(new Lot(SpaceName.Indiana_Av, 220, PropertyColor.RED, 150, 18, 90));
        spaces.add(new Lot(SpaceName.Illinois_Av, 240, PropertyColor.RED, 150, 20, 100));
        spaces.add(new MetroStation(SpaceName.B_O_railroad, 200));
        spaces.add(new Lot(SpaceName.Atlantic_Av, 260, PropertyColor.YELLOW, 150, 22, 110));
        spaces.add(new Lot(SpaceName.Ventnor_Av, 260, PropertyColor.YELLOW, 150, 22, 110));
        spaces.add(new Utility(SpaceName.WATER_COMPANY, 150));
        spaces.add(new Lot(SpaceName.Marvin_gardens, 280, PropertyColor.YELLOW, 150, 24, 120));
        spaces.add(new GoToJail(SpaceName.GO_TO_JAIL));
        spaces.add(new Lot(SpaceName.Regent_street, 300, PropertyColor.GREEN, 200, 26, 130));
        spaces.add(new Lot(SpaceName.Oxford_street, 300, PropertyColor.GREEN, 200, 26, 130));
        spaces.add(new CardSpace(SpaceName.CHEST, CardType.CHEST));
        spaces.add(new Lot(SpaceName.Bond_street, 320, PropertyColor.GREEN, 200, 28, 150));
        spaces.add(new MetroStation(SpaceName.Short_line, 200));
        spaces.add(new CardSpace(SpaceName.CHANCE, CardType.CHANCE));
        spaces.add(new Lot(SpaceName.Park_place, 350, PropertyColor.BLUE, 200, 35, 175));
        spaces.add(new WealthTax(SpaceName.WEALTH_TAX));
        spaces.add(new Lot(SpaceName.Boardwalk, 400, PropertyColor.BLUE, 200, 50, 200));

        brownLots = new ArrayList<>();
        brownLots.add((Lot) spaces.get(1));
        brownLots.add((Lot) spaces.get(3));

        lightBlueLots = new ArrayList<>();
        lightBlueLots.add((Lot) spaces.get(6));
        lightBlueLots.add((Lot) spaces.get(8));
        lightBlueLots.add((Lot) spaces.get(9));

        pinkLots = new ArrayList<>();
        pinkLots.add((Lot) spaces.get(11));
        pinkLots.add((Lot) spaces.get(13));
        pinkLots.add((Lot) spaces.get(14));

        orangeLots = new ArrayList<>();
        orangeLots.add((Lot) spaces.get(16));
        orangeLots.add((Lot) spaces.get(18));
        orangeLots.add((Lot) spaces.get(19));

        redLots = new ArrayList<>();
        redLots.add((Lot) spaces.get(21));
        redLots.add((Lot) spaces.get(23));
        redLots.add((Lot) spaces.get(24));

        yellowLots = new ArrayList<>();
        yellowLots.add((Lot) spaces.get(26));
        yellowLots.add((Lot) spaces.get(27));
        yellowLots.add((Lot) spaces.get(29));

        greenLots = new ArrayList<>();
        greenLots.add((Lot) spaces.get(31));
        greenLots.add((Lot) spaces.get(32));
        greenLots.add((Lot) spaces.get(34));

        blueLots = new ArrayList<>();
        blueLots.add((Lot) spaces.get(37));
        blueLots.add((Lot) spaces.get(39));
    }

    /**
     * @param color - color of lots to be returned
     * @return List with all lots of the specified color
     */
    public ArrayList<Lot> getLots(PropertyColor color) {
        ArrayList<Lot> lots;

        if (color == PropertyColor.YELLOW)
            lots = yellowLots;
        else if (color == PropertyColor.BLUE)
            lots = blueLots;
        else if (color == PropertyColor.LIGHT_BLUE)
            lots = lightBlueLots;
        else if (color == PropertyColor.ORANGE)
            lots = orangeLots;
        else if (color == PropertyColor.BROWN)
            lots = brownLots;
        else if (color == PropertyColor.PINK)
            lots = pinkLots;
        else if (color == PropertyColor.GREEN)
            lots = greenLots;
        else
            lots = redLots;

        return new ArrayList<>(lots);
    }

    /**
     * @return Space whose name is the given one
     */
    public Space getSpace(SpaceName name) {
        int i;

        for (i = 0; i < spaces.size(); i++)
            if (spaces.get(i).getName() == name)
                break;

        return spaces.get(i);
    }

    /**
     * Move the player to a specific space
     *
     * @return If the player completed a lap on the board
     */
    public boolean moveTo(Player player, SpaceName destination) {
        if (player.getPosition() == null)
            player.setPosition(getSpace(SpaceName.START_POINT));

        boolean completedLap = false;

        while (player.getPosition().getName() != destination)
            if (move(player, 1))
                completedLap = true;

        return completedLap;
    }

    /**
     * Move the player to the next space of a certain type
     *
     * @return If the player completed a lap on the board
     */
    public boolean moveToNext(Player player, Class<? extends Space> destinationType) {
        if (player.getPosition() == null)
            player.setPosition(getSpace(SpaceName.START_POINT));

        boolean completedLap = false;

        while (!player.getPosition().getClass().equals(destinationType))
            if (move(player, 1))
                completedLap = true;

        return completedLap;
    }

    /**
     * Move the player a certain number of positions on the board
     *
     * @param count - number of spaces to be moved
     * @return If the player completed a lap on the board
     */
    public boolean move(Player player, int count) {
        if (count < 0)
            throw new IllegalArgumentException("A player cannot move a negative number of spaces!");

        if (player.getPosition() == null)
            player.setPosition(getSpace(SpaceName.START_POINT));

        int playerIndex = spaces.indexOf(player.getPosition());

        boolean completedLap = false;

        for (int i = 0; i < count; i++) {
            playerIndex++;

            // as the board is circular
            if (playerIndex == spaces.size()) {
                playerIndex = 0;
                completedLap = true;
            }
        }

        moveTo(player, playerIndex);

        return completedLap;
    }

    /**
     * Move the player back a certain number of positions on the board
     *
     * @param count - number of spaces to be moved back
     */
    public void moveBack(Player player, int count) {
        if (count < 0)
            throw new IllegalArgumentException("A player cannot move back a negative number of spaces!");

        if (player.getPosition() == null)
            player.setPosition(getSpace(SpaceName.START_POINT));

        int playerIndex = spaces.indexOf(player.getPosition());

        for (int i = 0; i < count; i++) {
            playerIndex--;

            // as the board is circular
            if (playerIndex == -1)
                playerIndex = 39;
        }

        moveTo(player, playerIndex);
    }

    /**
     * Move the player to a specific position on the board
     */
    private void moveTo(Player player, int position) {
        player.setPosition(spaces.get(position));
    }
}
