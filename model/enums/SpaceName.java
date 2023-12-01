package model.enums;

/**
 * Names of spaces on the game board, including properties, utilities, and special spaces.
 * Space Types:
 * - PROPERTY
 * - METRO_STATION
 * - UTILITY
 * - CARD_SPACE
 * - CORNER
 * - TAX
 * - WEALTH_TAX
 */
public enum SpaceName {

    // Properties
    Mediterranean_Avenue("Mediterranean Avenue"),
    Vermont_Av("Vermont Avenue"),
    Connecticut_Av("Connecticut Avenue"),
    St_Charles_place("St. Charles Place"),
    States_Av("States Avenue"),
    Virginia_Av("Virginia Avenue"),
    St_James_place("St. James Place"),
    Tennessee_Av("Tennessee Avenue"),
    New_York_Av("New York Avenue"),
    Kentucky_Av("Kentucky Avenue"),
    Indiana_Av("Indiana Avenue"),
    Illinois_Av("Illinois Avenue"),
    Atlantic_Av("Atlantic Avenue"),
    Ventnor_Av("Ventnor Avenue"),
    Marvin_gardens("Marvin Gardens"),
    Regent_street("Regent Street"),
    Oxford_street("Oxford Street"),
    Bond_street("Bond Street"),
    Park_place("Park Place"),
    Boardwalk("Boardwalk"),
    Oriental_Av("Oriental Avenue"),
    Baltic_Av("Baltic Avenue"),

    // Metro Stations
    Reading_railroad("Reading Railroad"),
    Pennsylvania_railroad("Pennsylvania Railroad"),
    B_O_railroad("B. & O. Railroad"),
    Short_line("Short Line"),

    // Utilities
    ELECTRIC_COMPANY("Electric Company"),
    WATER_COMPANY("Water Works"),

    // Card Spaces
    CHEST("Community Chest"),
    CHANCE("Chance"),

    // Corners
    START_POINT("Go"),
    JAIL("In Jail"),
    FREE_PARKING("Free Parking"),
    GO_TO_JAIL("Go to Jail"),

    // Taxes
    INCOME_TAX("Income Tax"),

    // Wealth Tax
    WEALTH_TAX("Wealth Tax");

    private final String name;

    private SpaceName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
