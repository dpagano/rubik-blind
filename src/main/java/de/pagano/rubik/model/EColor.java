package de.pagano.rubik.model;

/** The colors of a cube. */
public enum EColor {
	WHITE(0), GREEN(1), ORANGE(2), BLUE(3), RED(4), YELLOW(5);

	/** The value of the color. Used to order the colors. */
	private final int value;

	/** The colors. */
	private static final EColor[] COLORS = { WHITE, GREEN, ORANGE, BLUE, RED, YELLOW };

	/** Constructor. */
	private EColor(int value) {
		this.value = value;
	}

	/** @see value. */
	public int getValue() {
		return value;
	}

	/** Gets the color for a specific value. */
	public static EColor getColor(int value) {
		return COLORS[value];
	}

	@Override
	public String toString() {
		return name();
	}
}