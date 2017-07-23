package model;

/** The colors of a cube. */
public enum EColor {
	WHITE((byte) 0), GREEN((byte) 1), ORANGE((byte) 2), BLUE((byte) 3), RED((byte) 4), YELLOW((byte) 5);

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