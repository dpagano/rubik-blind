package model;

/** A face of a 3x3 cube. */
public class Face {
	/** The pieces of the face as a two-dimensional array. */
	private EColor[][] pieces = new EColor[3][3];

	/**
	 * The color of this face. The 3x3 face has one piece which "does not move".
	 * This is the color of that piece.
	 */
	private EColor faceColor;

	/** Constructor. */
	public Face(EColor faceColor) {
		this.faceColor = faceColor;
		setAllPieces(faceColor);
	}

	/** Creates a copy of this face which has the same colors. */
	public Face copy() {
		Face copy = new Face(faceColor);
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				copy.setPiece(x, y, getPiece(x, y));
			}
		}
		return copy;
	}

	/** Sets the piece with the specified coordinates to the specified color. */
	/* package */ void setPiece(int x, int y, EColor color) {
		if (x >= 3 || y >= 3) {
			throw new CubeException("Cube size is 3x3.");
		}
		pieces[x][y] = color;
	}

	/** Gets the color of the piece with the specified coordinates. */
	/* package */ EColor getPiece(int x, int y) {
		if (x >= 3 || y >= 3) {
			throw new CubeException("Cube size is 3x3.");
		}
		return pieces[x][y];
	}

	/** Sets all pieces of this face to the specified color. */
	private void setAllPieces(EColor color) {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				setPiece(x, y, color);
			}
		}
	}

	/** Rotates this face. */
	public void rotate(boolean clockwise) {
		if (clockwise) {
			rotateClockwise();
		} else {
			rotateCounterClockwise();
		}
	}

	/**
	 * Rotates this face clockwise. Rotation is currently done by swapping
	 * pieces, as we now only handle 3x3 faces. We do not really gain from using
	 * rotation matrices and vectors here.
	 */
	public void rotateClockwise() {
		swapPieces(0, 2, 0, 0);
		swapPieces(0, 0, 2, 0);
		swapPieces(2, 0, 2, 2);

		swapPieces(0, 1, 1, 0);
		swapPieces(1, 0, 2, 1);
		swapPieces(2, 1, 1, 2);
	}

	/** Rotates this face anti-clockwise. */
	public void rotateCounterClockwise() {
		swapPieces(0, 0, 0, 2);
		swapPieces(0, 2, 2, 2);
		swapPieces(2, 2, 2, 0);

		swapPieces(0, 1, 1, 2);
		swapPieces(1, 2, 2, 1);
		swapPieces(2, 1, 1, 0);
	}

	/** Swaps the pieces with the specified coordinates. */
	private void swapPieces(int x0, int y0, int x1, int y1) {
		EColor buffer = pieces[x0][y0];
		pieces[x0][y0] = pieces[x1][y1];
		pieces[x1][y1] = buffer;
	}

	@Override
	public String toString() {
		String value = "Face (" + faceColor + "):\r\n";
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				value += pieces[x][y] + " ";
			}
			value += "\r\n";
		}
		return value;
	}

	public static void main(String[] args) {
		Face test = new Face(EColor.RED);
		System.out.println(test);

		byte c = 0;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				test.setPiece(x, y, EColor.getColor(c++));
			}
		}

		System.out.println(test);
		test.rotateClockwise();

		System.out.println(test);
		test.rotateCounterClockwise();

		System.out.println(test);
	}
}
