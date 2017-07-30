package de.pagano.rubik.model;

/** The faces of a cube. */
public enum E_Face {
	TOP(0), FRONT(1), LEFT(2), BACK(3), RIGHT(4), BOTTOM(5);

	/** The index of the face. Used to order the faces among them. */
	private final int index;

	/** The face order. */
	private static final E_Face[] FACES = { TOP, FRONT, LEFT, BACK, RIGHT, BOTTOM };

	/** Constructor. */
	private E_Face(int index) {
		this.index = index;
	}

	/** @see index. */
	public int getIndex() {
		return index;
	}

	/** Gets the {@link E_Face} for the specified face index. */
	public static E_Face getOrientation(int index) {
		return FACES[index];
	}

	@Override
	public String toString() {
		return name();
	}

}
