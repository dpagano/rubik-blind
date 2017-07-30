package de.pagano.rubik.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// TODO (DP): We could extract an abstract class and have different orientations
// as subclasses, such that different cubes can be represented.
public class CubeOrientation {

	/** Gets the opposite color of the specified color. */
	public static EFace getOppositeFace(EFace color) throws CubeException {
		switch (color) {
		case TOP:
			return EFace.BOTTOM;
		case FRONT:
			return EFace.BACK;
		case LEFT:
			return EFace.RIGHT;
		case BACK:
			return EFace.FRONT;
		case RIGHT:
			return EFace.LEFT;
		case BOTTOM:
			return EFace.TOP;
		default:
			throw new CubeException("Unknown color: " + color);
		}
	}

	/** Gets the adjacent colors of the specified face in the specified order. */
	public static List<EFace> getAdjacentColors(EFace face, boolean clockwise) {
		List<EFace> colors;
		switch (face) {
		case TOP:
			colors = Arrays.asList(EFace.FRONT, EFace.LEFT, EFace.BACK, EFace.RIGHT);
			break;
		case FRONT:
			colors = Arrays.asList(EFace.TOP, EFace.RIGHT, EFace.BOTTOM, EFace.LEFT);
			break;
		case LEFT:
			colors = Arrays.asList(EFace.TOP, EFace.FRONT, EFace.BOTTOM, EFace.BACK);
			break;
		case BACK:
			colors = Arrays.asList(EFace.TOP, EFace.LEFT, EFace.BOTTOM, EFace.RIGHT);
			break;
		case RIGHT:
			colors = Arrays.asList(EFace.TOP, EFace.BACK, EFace.BOTTOM, EFace.FRONT);
			break;
		case BOTTOM:
			colors = Arrays.asList(EFace.FRONT, EFace.RIGHT, EFace.BACK, EFace.LEFT);
			break;
		default:
			colors = Arrays.asList();
		}

		if (!clockwise) {
			Collections.reverse(colors);
		}
		return colors;
	}

	/**
	 * Gets the indexes of the pieces on the adjacent face. Returns an empty array
	 * for faces that are not adjacent.
	 */
	public static int[][] getAdjacentIndexes(EFace face, EFace adjacentFace) {
		if (face == EFace.TOP || face == EFace.FRONT && adjacentFace == EFace.TOP
				|| face == EFace.FRONT && adjacentFace == EFace.BOTTOM) {
			return new int[][] { { 0, 0 }, { 0, 1 }, { 0, 2 } };
		}

		if (face == EFace.BOTTOM || face == EFace.BACK && adjacentFace == EFace.TOP
				|| face == EFace.BACK && adjacentFace == EFace.BOTTOM) {
			return new int[][] { { 2, 2 }, { 2, 1 }, { 2, 0 } };
		}
		if (face == EFace.LEFT && adjacentFace == EFace.FRONT || face == EFace.LEFT && adjacentFace == EFace.BOTTOM) {
			return new int[][] { { 0, 0 }, { 1, 0 }, { 2, 0 } };
		}

		if (face == EFace.FRONT && adjacentFace == EFace.LEFT || face == EFace.LEFT && adjacentFace == EFace.TOP
				|| face == EFace.LEFT && adjacentFace == EFace.BACK || face == EFace.BACK && adjacentFace == EFace.RIGHT
				|| face == EFace.RIGHT && adjacentFace == EFace.BOTTOM
				|| face == EFace.RIGHT && adjacentFace == EFace.FRONT) {
			return new int[][] { { 0, 2 }, { 1, 2 }, { 2, 2 } };
		}

		if (face == EFace.FRONT && adjacentFace == EFace.RIGHT || face == EFace.BACK && adjacentFace == EFace.LEFT
				|| face == EFace.RIGHT && adjacentFace == EFace.TOP
				|| face == EFace.RIGHT && adjacentFace == EFace.BACK) {
			return new int[][] { { 2, 0 }, { 1, 0 }, { 0, 0 } };
		}

		// Faces not adjacent
		return new int[][] {};
	}

}
