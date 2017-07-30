package de.pagano.rubik.model;

/** The faces of a cube. */
public enum EFace {
	TOP(0), FRONT(1), LEFT(2), BACK(3), RIGHT(4), BOTTOM(5);

	/** The index of the face. Used to order the faces among them. */
	private final int index;

	/** The face order. */
	private static final EFace[] FACES = { TOP, FRONT, LEFT, BACK, RIGHT, BOTTOM };

	/** Constructor. */
	private EFace(int index) {
		this.index = index;
	}

	/** @see index. */
	public int getIndex() {
		return index;
	}

	/** Gets the {@link EFace} for the specified face index. */
	public static EFace getOrientation(int index) {
		return FACES[index];
	}

	@Override
	public String toString() {
		return name();
	}

}
