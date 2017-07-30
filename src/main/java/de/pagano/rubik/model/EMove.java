package de.pagano.rubik.model;

import java.util.Arrays;
import java.util.List;

/** Describes moves as well as special modifiers for that moves. */
public enum EMove {
	UP, DOWN, FRONT, BACK, RIGHT, LEFT, MIDDLE, EQUATORIAL, STANDING, X, Y, Z, MODIFIER_DOUBLE, MODIFIER_REVERSE;

	private static final List<EMove> MOVES = Arrays.asList(UP, DOWN, FRONT, BACK, RIGHT, LEFT, MIDDLE, EQUATORIAL,
			STANDING, X, Y, Z, MODIFIER_DOUBLE, MODIFIER_REVERSE);
	private static final List<String> MOVE_STRINGS = Arrays.asList("U", "D", "F", "B", "R", "L", "M", "E", "S", "X",
			"Y", "Z", "2", "'");

	@Override
	public String toString() {
		int moveIndex = MOVES.indexOf(this);
		if (moveIndex < 0 || moveIndex >= MOVE_STRINGS.size()) {
			throw new AssertionError("No move string defined for " + this);
		}
		return MOVE_STRINGS.get(moveIndex);
	}

	/** Creates an instance of {@link EMove} from the specified move string. */
	public static EMove fromString(String move) {
		int moveIndex = MOVE_STRINGS.indexOf(move);
		if (moveIndex < 0 || moveIndex >= MOVES.size()) {
			throw new AssertionError("No move defined for string " + move);
		}
		return MOVES.get(moveIndex);
	}

}
