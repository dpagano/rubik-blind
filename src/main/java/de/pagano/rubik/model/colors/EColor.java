package de.pagano.rubik.model.colors;

/** The colors of a cube. */
public enum EColor {
	WHITE(0), GREEN(1), ORANGE(2), BLUE(3), RED(4), YELLOW(5);

	// TODO (DP): I think we don't need to order the colors here. The cube has to
	// have the bijection.
	/** The index of the color. Used to order the colors among them. */
	private final int index;

	/** The face order. */
	private static final EColor[] COLORS = { WHITE, GREEN, ORANGE, BLUE, RED, YELLOW };

	/** Constructor. */
	private EColor(int index) {
		this.index = index;
	}

	/** @see index. */
	public int getIndex() {
		return index;
	}

	/** Gets the {@link EColor} for the specified color index. */
	public static EColor getColor(int index) {
		return COLORS[index];
	}

	@Override
	public String toString() {
		return name();
	}

}
