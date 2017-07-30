package de.pagano.rubik.model;

import java.util.Arrays;
import java.util.List;

public enum Move {
	UP, DOWN, FRONT, BACK, RIGHT, LEFT, MIDDLE, EQUATORIAL, STANDING, X, Y, Z, MODIFIER_DOUBLE, MODIFIER_REVERSE;

	private static final List<Move> moves = Arrays.asList(UP, DOWN, FRONT, BACK, RIGHT, LEFT, MIDDLE, EQUATORIAL,
			STANDING, X, Y, Z, MODIFIER_DOUBLE, MODIFIER_REVERSE);
	private static final List<String> moveStrings = Arrays.asList("U", "D", "F", "B", "R", "L", "M", "E", "S", "X", "Y",
			"Z", "2", "'");

	@Override
	public String toString() {
		int moveIndex = moves.indexOf(this);
		return moveStrings.get(moveIndex);
	}

	public static Move fromString(String move) {
		int moveIndex = moveStrings.indexOf(move);
		return moves.get(moveIndex);
	}

}
