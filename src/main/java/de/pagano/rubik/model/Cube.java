package de.pagano.rubik.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * A cube, which is able to rotate faces, interpret moves, and scramble.
 *
 * // TODO (DP): In the future, only rotation should be kept here.
 * Interpretation and scrambling should be moved to extra classes.
 */
public class Cube {

	/** The color of the face currently facing up. */
	private EColor topFace;

	/** The color of the face currently facing to the front. */
	private EColor frontFace;

	/** The color of the face currently facing to the right. */
	private EColor rightFace;

	/** The faces of the cube. */
	private final Face[] faces = new Face[6];

	/** Constructor. */
	public Cube() throws CubeException {
		for (int i = 0; i < 6; i++) {
			faces[i] = new Face(EColor.getColor(i));
		}

		topFace = EColor.WHITE;
		frontFace = EColor.GREEN;
		rightFace = EColor.RED;
	}

	/**
	 * Creates a copy of this cube which has the same colors.
	 */
	public Cube copy() throws CubeException {
		Cube copy = new Cube();
		for (int faceIndex = 0; faceIndex < 6; faceIndex++) {
			copy.faces[faceIndex] = faces[faceIndex].copy();
		}
		return copy;
	}

	/** Rotates the specified face. */
	public void rotateFace(EColor face, boolean clockwise) throws CubeException {
		faces[face.getValue()].rotate(clockwise);
		rotateAdjacentFaces(face, clockwise);
	}

	public Face getFace(EColor centerColor) {
		return faces[centerColor.getValue()];
	}

	public EColor getTopColor() {
		return topFace;
	}

	public EColor getFrontColor() {
		return frontFace;
	}

	public EColor getRightColor() {
		return rightFace;
	}

	/** Rotates the adjacent pieces on the adjacent faces of the specified face. */
	private void rotateAdjacentFaces(EColor face, boolean clockwise) throws CubeException {
		List<EColor> adjacentFaces = new ArrayList<>(CubeOrientation.getAdjacentColors(face, !clockwise));

		List<int[][]> indexesToRotate = adjacentFaces.stream().map(adjacentFace -> {
			return getAdjacentIndexes(face, adjacentFace);
		}).collect(Collectors.toList());

		// Add the buffer to the end, so that we can use one simple for loop to
		// rotate
		indexesToRotate.add(indexesToRotate.get(0));
		adjacentFaces.add(adjacentFaces.get(0));
		Face buffer = faces[adjacentFaces.get(0).getValue()].copy();

		for (int faceIndex = 0; faceIndex < 4; faceIndex++) {
			EColor currentFaceColor = adjacentFaces.get(faceIndex);
			int[][] currentIndexes = indexesToRotate.get(faceIndex);

			EColor nextFaceColor = adjacentFaces.get(faceIndex + 1);
			int[][] nextIndexes = indexesToRotate.get(faceIndex + 1);

			for (int i = 0; i < 3; i++) {
				Face nextFace = faces[nextFaceColor.getValue()];
				if (faceIndex == 3) {
					nextFace = buffer;
				}
				EColor nextColor = nextFace.getPiece(nextIndexes[i][0], nextIndexes[i][1]);
				faces[currentFaceColor.getValue()].setPiece(currentIndexes[i][0], currentIndexes[i][1], nextColor);
			}
		}
	}

	/**
	 * Gets the indexes of the pieces on the adjacent face. Returns an empty array
	 * for colors that are not adjacent.
	 */
	// TODO (DP): This method should work with orientations, not colors. It should
	// be possible to have a differently colored cube and still work with it.
	private int[][] getAdjacentIndexes(EColor face, EColor adjacentFace) {
		if (face == EColor.WHITE || face == EColor.GREEN && adjacentFace == EColor.WHITE
				|| face == EColor.GREEN && adjacentFace == EColor.YELLOW) {
			return new int[][] { { 0, 0 }, { 0, 1 }, { 0, 2 } };
		}

		if (face == EColor.YELLOW || face == EColor.BLUE && adjacentFace == EColor.WHITE
				|| face == EColor.BLUE && adjacentFace == EColor.YELLOW) {
			return new int[][] { { 2, 2 }, { 2, 1 }, { 2, 0 } };
		}
		if (face == EColor.ORANGE && adjacentFace == EColor.GREEN
				|| face == EColor.ORANGE && adjacentFace == EColor.YELLOW) {
			return new int[][] { { 0, 0 }, { 1, 0 }, { 2, 0 } };
		}

		if (face == EColor.GREEN && adjacentFace == EColor.ORANGE
				|| face == EColor.ORANGE && adjacentFace == EColor.WHITE
				|| face == EColor.ORANGE && adjacentFace == EColor.BLUE
				|| face == EColor.BLUE && adjacentFace == EColor.RED
				|| face == EColor.RED && adjacentFace == EColor.YELLOW
				|| face == EColor.RED && adjacentFace == EColor.GREEN) {
			return new int[][] { { 0, 2 }, { 1, 2 }, { 2, 2 } };
		}

		if (face == EColor.GREEN && adjacentFace == EColor.RED || face == EColor.BLUE && adjacentFace == EColor.ORANGE
				|| face == EColor.RED && adjacentFace == EColor.WHITE
				|| face == EColor.RED && adjacentFace == EColor.BLUE) {
			return new int[][] { { 2, 0 }, { 1, 0 }, { 0, 0 } };
		}

		// Faces not adjacent
		return new int[][] {};
	}

	// TODO (DP): Extract one method with "rotationFace" and other two params
	/** Rotates the cube along the X axis. */
	public void rotateX(boolean clockwise, int numberOfRotations) {
		List<EColor> adjacentColorsClockwise = CubeOrientation.getAdjacentColors(rightFace, clockwise);
		int currentTopFace = adjacentColorsClockwise.indexOf(topFace);
		topFace = adjacentColorsClockwise.get((currentTopFace + 4 - numberOfRotations) % 4);
		int currentFrontFace = adjacentColorsClockwise.indexOf(frontFace);
		frontFace = adjacentColorsClockwise.get((currentFrontFace + 4 - numberOfRotations) % 4);
	}

	/** Rotates the cube along the Y axis. */
	public void rotateY(boolean clockwise, int numberOfRotations) {
		List<EColor> adjacentColorsClockwise = CubeOrientation.getAdjacentColors(topFace, clockwise);
		int currentRightFace = adjacentColorsClockwise.indexOf(rightFace);
		rightFace = adjacentColorsClockwise.get((currentRightFace + 4 - numberOfRotations) % 4);
		int currentFrontFace = adjacentColorsClockwise.indexOf(frontFace);
		frontFace = adjacentColorsClockwise.get((currentFrontFace + 4 - numberOfRotations) % 4);
	}

	/** Rotates the cube along the Z axis. */
	public void rotateZ(boolean clockwise, int numberOfRotations) {
		List<EColor> adjacentColorsClockwise = CubeOrientation.getAdjacentColors(frontFace, clockwise);
		int currentRightFace = adjacentColorsClockwise.indexOf(rightFace);
		rightFace = adjacentColorsClockwise.get((currentRightFace + 4 - numberOfRotations) % 4);
		int currentTopFace = adjacentColorsClockwise.indexOf(topFace);
		topFace = adjacentColorsClockwise.get((currentTopFace + 4 - numberOfRotations) % 4);
	}

	/**
	 * Interprets the provided scramble string and performs the corresponding moves
	 * on the cube.
	 */
	public void move(String scrambleString) throws CubeException {
		String[] moves = scrambleString.toUpperCase().split("\\s+");

		for (String move : moves) {
			performSingleMove(move);
		}
	}

	/** Interprets the provided move on the cube and performs it. */
	private void performSingleMove(String move) throws CubeException {
		boolean clockwise = !move.endsWith(Move.MODIFIER_REVERSE.toString());
		int numberOfRotations = 1;
		if (move.endsWith(Move.MODIFIER_DOUBLE.toString())) {
			numberOfRotations = 2;
		}
		String firstLetter = move.substring(0, 1);
		Move interpretedMove = Move.fromString(firstLetter);
		switch (interpretedMove) {
		case UP:
			performRotation(topFace, clockwise, numberOfRotations);
			break;
		case DOWN:
			performRotation(CubeOrientation.getOppositeFace(topFace), clockwise, numberOfRotations);
			break;
		case FRONT:
			performRotation(frontFace, clockwise, numberOfRotations);
			break;
		case BACK:
			performRotation(CubeOrientation.getOppositeFace(frontFace), clockwise, numberOfRotations);
			break;
		case RIGHT:
			performRotation(rightFace, clockwise, numberOfRotations);
			break;
		case LEFT:
			performRotation(CubeOrientation.getOppositeFace(rightFace), clockwise, numberOfRotations);
			break;
		case MIDDLE:
			performRotation(rightFace, clockwise, numberOfRotations);
			performRotation(CubeOrientation.getOppositeFace(rightFace), !clockwise, numberOfRotations);
			rotateX(!clockwise, numberOfRotations);
			break;
		case EQUATORIAL:
			performRotation(topFace, clockwise, numberOfRotations);
			performRotation(CubeOrientation.getOppositeFace(topFace), !clockwise, numberOfRotations);
			rotateY(!clockwise, numberOfRotations);
			break;
		case STANDING:
			performRotation(frontFace, !clockwise, numberOfRotations);
			performRotation(CubeOrientation.getOppositeFace(frontFace), clockwise, numberOfRotations);
			rotateZ(clockwise, numberOfRotations);
			break;
		case X:
			rotateX(clockwise, numberOfRotations);
			break;
		case Y:
			rotateY(clockwise, numberOfRotations);
			break;
		case Z:
			rotateZ(clockwise, numberOfRotations);
			break;
		default:
			throw new CubeException("Unknown move: " + move);
		}

	}

	/**
	 * Performs the given number of rotations around the specified face in the
	 * specified direction.
	 */
	private void performRotation(EColor rotationFace, boolean clockwise, int numberOfRotations) throws CubeException {
		for (int i = 0; i < numberOfRotations; i++) {
			rotateFace(rotationFace, clockwise);
		}
	}

	/** Scrambles the cube. */
	public String scramble(int scrambleLength, boolean extended, double doubleMoveProbability) throws CubeException {
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

		move(scrambleString);

		return scrambleString;
	}

	/**
	 * Gets the edge at the position specified by the original position of the given
	 * edge.
	 */
	public Edge getEdgeAt(Edge edge) throws CubeException {
		return getEdgeAt(edge.getFirstFace(), edge.getSecondFace());
	}

	/** Gets the edge at the position specified by the two given colors. */
	public Edge getEdgeAt(EColor firstColor, EColor secondColor) throws CubeException {
		// TODO (DP): fix bug
		System.out.println("Getting edge at " + firstColor + " & " + secondColor);
		int[][] adjacentIndexesForFirstColor = getAdjacentIndexes(firstColor, secondColor);
		System.out.println("Adjacent indexes for " + firstColor + ": " + arrayString(adjacentIndexesForFirstColor));

		Face secondFace = faces[secondColor.getValue()];
		EColor secondEdgeColor = secondFace.getPiece(adjacentIndexesForFirstColor[1][0],
				adjacentIndexesForFirstColor[1][1]);

		int[][] adjacentIndexesForSecondColor = getAdjacentIndexes(secondColor, firstColor);
		System.out.println("Adjacent indexes for " + secondColor + ": " + arrayString(adjacentIndexesForSecondColor));

		// TODO (DP): Extract accessor
		Face firstFace = faces[firstColor.getValue()];
		EColor firstEdgeColor = firstFace.getPiece(adjacentIndexesForSecondColor[1][0],
				adjacentIndexesForSecondColor[1][1]);

		Edge edge = new Edge(firstEdgeColor, secondEdgeColor);
		return edge;
	}

	private static String arrayString(int[][] array) {
		String s = "{";
		for (int[] is : array) {
			s += "{";
			for (int i : is) {
				s += i + ", ";
			}
			s += "}, ";
		}
		s += "}";
		return s;
	}

	@Override
	public String toString() {
		Optional<String> cubeString = Arrays.stream(faces).map(face -> face.toString())
				.reduce((s1, s2) -> s1 + "\r\n" + s2);
		return cubeString.get() + "------\r\n";
	}

	/** Used for testing. */
	public static void main(String[] args) throws CubeException {
		Cube myCube = new Cube();

		// myCube.interpret("X Y Z");

		// System.out.println(myCube.topFace);
		// System.out.println(myCube.frontFace);
		// System.out.println(myCube.rightFace);

		myCube.move("R U R' U' R' F R2 U' R' U' R U R' F'");
		System.out.println(myCube);

		// myCube.interpret("R U R' U' R' F R2 U' R' U' R U R' F'");
		System.out.println(myCube);

		// String scramble = myCube.scramble(25, false, 0.3d);
		// System.out.println(scramble);

		System.out.println(myCube);

		Edge edge = myCube.getEdgeAt(EColor.ORANGE, EColor.GREEN);
		System.out.println(edge);
	}
}
