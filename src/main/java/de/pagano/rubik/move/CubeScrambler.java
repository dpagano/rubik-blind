package de.pagano.rubik.move;

import java.util.Random;

import de.pagano.rubik.model.CubeException;

/** Generates scramble strings for the cube. */
public class CubeScrambler {

	/** Scrambles the cube. */
	public String getScramble(int scrambleLength, boolean extended, double doubleMoveProbability) throws CubeException {
		// TODO (DP): Extract moves into EMove and use those constants
		String moves = "UDFBRL";
		String extendedMoves = "MES";
		if (extended) {
			moves += extendedMoves;
		}

		String scrambleString = "";
		Random random = new Random();
		int i = 0;
		String lastMove = "";
		while (i < scrambleLength) {
			int nextMoveIndex = random.nextInt(moves.length());
			String nextMove = moves.substring(nextMoveIndex, nextMoveIndex + 1);

			if (nextMove.equals(lastMove)) {
				continue;
			}

			lastMove = nextMove;

			if (random.nextDouble() <= 0.5) {
				nextMove += "'";
			}

			if (random.nextDouble() <= doubleMoveProbability) {
				if (nextMove.endsWith("'")) {
					nextMove = nextMove.substring(0, nextMove.length() - 1);
				}
				nextMove += "2";
			}
			scrambleString += nextMove + " ";

			i++;
		}

		return scrambleString;
	}
}
