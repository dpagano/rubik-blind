package de.pagano.rubik.model.colors;

/** The colors of a cube. */
public enum EColor {
	WHITE, GREEN, ORANGE, BLUE, RED, YELLOW;

	@Override
	public String toString() {
		return name();
	}
}
