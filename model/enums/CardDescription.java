package model.enums;

public enum CardDescription {

    /* CHANCE CARDS */

    // movement cards
    CHEST_01("Advance to States Avenue"),
    CHEST_02("Advance to Baltic Avenue"),
    CHEST_03("Advance to Vermont Avenue"),
    CHEST_04("Advance to Atlantic Avenue"),
    CHEST_05("Advance 2 spaces"),
    CHEST_06("Go back 4 spaces"),
    CHEST_07("Advance to the nearest Utility"),

    // money cards
    CHEST_08("Your life insurance reaches its advanced stage (collect $100)"),
    CHEST_09("You came in 2nd place in a beauty contest (collect $10)"),
    CHEST_10("Bank error in your favor (collect $200)"),
    CHEST_11("Receive $25 for your services"),
    CHEST_12("Pay $75 to assist in city reconstruction"),
    CHEST_13("Pay $100 to the hospital"),
    CHEST_14("You won a crossword competition (collect $100)"),
    CHEST_15("Speeding fine (pay $45)"),

    // "Go to Jail" card
    CHEST_16("Go directly to jail"),


    /* COMMUNITY CHEST CARDS */

    // movement cards
    CHANCE_01("Advance to Go"),
    CHANCE_02("Advance to the Park Place"),
    CHANCE_03("Advance to Virginia Avenue"),
    CHANCE_04("Advance to St. James Place"),
    CHANCE_05("Advance 5 spaces"),
    CHANCE_06("Go back 3 spaces"),
    CHANCE_07("Advance to the nearest Metro Station"),

    // money cards
    CHANCE_08("You were elected chairman of the board. Pay $50."),
    CHANCE_09("Bank error in your favor (collect $75)"),
    CHANCE_10("Medical service fee (pay $50)"),
    CHANCE_11("Your Christmas fund grows (collect $100)"),
    CHANCE_12("The Bank pays you a dividend of $50"),
    CHANCE_13("Your real estate credit society grows (collect $150)"),
    CHANCE_14("You had a car accident (pay $40)"),
    CHANCE_15("It's your birthday! (collect $200)"),

    // "Go to Jail" card
    CHANCE_16("Go directly to jail");


    private final String description;

    private CardDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
