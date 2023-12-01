package model.spaces;

import model.enums.SpaceName;

/**
 * Space where the player goes when they are in jail.
 */
public class Jail extends Space {

	private final int bail = 50;

	public Jail(SpaceName name) {
		super(name);
	}

	public int getBail() {
		return bail;
	}

}
