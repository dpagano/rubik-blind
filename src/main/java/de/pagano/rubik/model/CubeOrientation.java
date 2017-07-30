package de.pagano.rubik.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// TODO (DP): We could extract an abstract class and have different orientations
// as subclasses, such that different cubes can be represented.
public class CubeOrientation {

	/** Gets the opposite color of the specified color. */
	public static EColor getOppositeFace(EColor color) throws CubeException {
		switch (color) {
		case WHITE:
			return EColor.YELLOW;
		case GREEN:
			return EColor.BLUE;
		case ORANGE:
			return EColor.RED;
		case BLUE:
			return EColor.GREEN;
		case RED:
			return EColor.ORANGE;
		case YELLOW:
			return EColor.WHITE;
		default:
			throw new CubeException("Unknown color: " + color);
		}
	}

	/** Gets the adjacent colors of the specified face in the specified order. */
	public static List<EColor> getAdjacentColors(EColor face, boolean clockwise) {
		List<EColor> colors;
		switch (face) {
		case WHITE:
			colors = Arrays.asList(EColor.GREEN, EColor.ORANGE, EColor.BLUE, EColor.RED);
			break;
		case GREEN:
			colors = Arrays.asList(EColor.WHITE, EColor.RED, EColor.YELLOW, EColor.ORANGE);
			break;
		case ORANGE:
			colors = Arrays.asList(EColor.WHITE, EColor.GREEN, EColor.YELLOW, EColor.BLUE);
			break;
		case BLUE:
			colors = Arrays.asList(EColor.WHITE, EColor.ORANGE, EColor.YELLOW, EColor.RED);
			break;
		case RED:
			colors = Arrays.asList(EColor.WHITE, EColor.BLUE, EColor.YELLOW, EColor.GREEN);
			break;
		case YELLOW:
			colors = Arrays.asList(EColor.GREEN, EColor.RED, EColor.BLUE, EColor.ORANGE);
			break;
		default:
			colors = Arrays.asList();
		}

		if (!clockwise) {
			Collections.reverse(colors);
		}
		return colors;
	}

}
