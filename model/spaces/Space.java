package model.spaces;

import model.enums.SpaceName;

/**
 * Board space.
 */
public abstract class Space {

    private final SpaceName name;

    public Space(SpaceName name) {
        this.name = name;
    }

    public SpaceName getName() {
        return name;
    }

    /**
     * @return Name of the space.
     */
    @Override
    public String toString() {
        return name.toString();
    }
}
