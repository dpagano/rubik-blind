package de.pagano.rubik.model;

/** The orientations of a cube. */
public enum EOrientation {
	TOP(0), FRONT(1), LEFT(2), BACK(3), RIGHT(4), BOTTOM(5);

	/** The index of the orientation. Used to order the orientations among them. */
	private final int index;

	/** The orientation order. */
	private static final EOrientation[] ORIENTATIONS = { TOP, FRONT, LEFT, BACK, RIGHT, BOTTOM };

	/** Constructor. */
	private EOrientation(int value) {
		this.index = value;
	}

	/** @see index. */
	public int getIndex() {
		return index;
	}

	/** Gets the orientation object for the specified face index. */
	public static EOrientation getOrientation(int index) {
		return ORIENTATIONS[index];
	}

	@Override
	public String toString() {
		return name();
	}

}
