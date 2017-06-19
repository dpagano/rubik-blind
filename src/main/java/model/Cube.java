package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cube {

	public static enum EColor {
		WHITE((byte) 0), GREEN((byte) 1), ORANGE((byte) 2), BLUE((byte) 3), RED((byte) 4), YELLOW((byte) 5);

		private final byte value;
		private static final EColor[] colors = { WHITE, GREEN, ORANGE, BLUE, RED, YELLOW };

		private EColor(byte value) {
			this.value = value;
		}

		public byte getValue() {
			return value;
		}

		public static EColor getColor(byte value) {
			return colors[value];
		}

		@Override
		public String toString() {
			return name();
		}
	}

	private EColor topFace;
	private EColor frontFace;
	private EColor rightFace;

	private Face[] faces = new Face[6];

	public Cube() {
		for (byte i = 0; i < 6; i++) {
			faces[i] = new Face(i);
		}

		topFace = EColor.WHITE;
		frontFace = EColor.GREEN;
		rightFace = EColor.RED;
	}

	public void rotateFace(EColor face, boolean clockwise) {
		faces[face.getValue()].rotate(clockwise);
		rotateAdjacentFaces(face, clockwise);
	}

	private void rotateAdjacentFaces(EColor face, boolean clockwise) {
		List<EColor> adjacentFaces = new ArrayList<>(getAdjacentColors(face, !clockwise));

		List<int[][]> indexesToRotate = adjacentFaces.stream()
				.map(adjacentFace -> getAdjacentIndexes(face, adjacentFace)).collect(Collectors.toList());

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
				byte nextColor = nextFace.getPiece(nextIndexes[i][0], nextIndexes[i][1]);
				faces[currentFaceColor.getValue()].setPiece(currentIndexes[i][0], currentIndexes[i][1], nextColor);
			}
		}
	}

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

		throw new AssertionError("This should never happen.");
	}

	private List<EColor> getAdjacentColors(EColor face, boolean clockwise) {
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

	private EColor getOppositeFace(EColor face) {
		switch (face) {
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
			throw new IllegalArgumentException("Unknown face: " + face);
		}
	}

	// TODO (DP): Extract one method with "rotationFace" and other two params
	public void rotateCubeX(boolean clockwise, int numberOfRotations) {
		List<EColor> adjacentColorsClockwise = getAdjacentColors(rightFace, clockwise);
		int currentTopFace = adjacentColorsClockwise.indexOf(topFace);
		topFace = adjacentColorsClockwise.get((currentTopFace + 4 - numberOfRotations) % 4);
		int currentFrontFace = adjacentColorsClockwise.indexOf(frontFace);
		frontFace = adjacentColorsClockwise.get((currentFrontFace + 4 - numberOfRotations) % 4);
	}

	public void rotateCubeY(boolean clockwise, int numberOfRotations) {
		List<EColor> adjacentColorsClockwise = getAdjacentColors(topFace, clockwise);
		int currentRightFace = adjacentColorsClockwise.indexOf(rightFace);
		rightFace = adjacentColorsClockwise.get((currentRightFace + 4 - numberOfRotations) % 4);
		int currentFrontFace = adjacentColorsClockwise.indexOf(frontFace);
		frontFace = adjacentColorsClockwise.get((currentFrontFace + 4 - numberOfRotations) % 4);
	}

	public void rotateCubeZ(boolean clockwise, int numberOfRotations) {
		List<EColor> adjacentColorsClockwise = getAdjacentColors(frontFace, clockwise);
		int currentRightFace = adjacentColorsClockwise.indexOf(rightFace);
		rightFace = adjacentColorsClockwise.get((currentRightFace + 4 - numberOfRotations) % 4);
		int currentTopFace = adjacentColorsClockwise.indexOf(topFace);
		topFace = adjacentColorsClockwise.get((currentTopFace + 4 - numberOfRotations) % 4);
	}

	public void interpret(String scrambleString) {
		String[] moves = scrambleString.toUpperCase().split("\\s+");

		for (String move : moves) {
			interpretMove(move);
		}
	}

	private void interpretMove(String move) {
		boolean clockwise = !move.endsWith("'");
		int numberOfRotations = 1;
		if (move.endsWith("2")) {
			numberOfRotations = 2;
		}
		String firstLetter = move.substring(0, 1);

		switch (firstLetter) {
		case "U":
			performMove(topFace, clockwise, numberOfRotations);
			break;
		case "D":
			performMove(getOppositeFace(topFace), clockwise, numberOfRotations);
			break;
		case "F":
			performMove(frontFace, clockwise, numberOfRotations);
			break;
		case "B":
			performMove(getOppositeFace(frontFace), clockwise, numberOfRotations);
			break;
		case "R":
			performMove(rightFace, clockwise, numberOfRotations);
			break;
		case "L":
			performMove(getOppositeFace(rightFace), clockwise, numberOfRotations);
			break;
		case "M":
			performMove(rightFace, clockwise, numberOfRotations);
			performMove(getOppositeFace(rightFace), !clockwise, numberOfRotations);
			rotateCubeX(!clockwise, numberOfRotations);
			break;
		case "E":
			performMove(topFace, clockwise, numberOfRotations);
			performMove(getOppositeFace(topFace), !clockwise, numberOfRotations);
			rotateCubeY(!clockwise, numberOfRotations);
			break;
		case "S":
			performMove(frontFace, !clockwise, numberOfRotations);
			performMove(getOppositeFace(frontFace), clockwise, numberOfRotations);
			rotateCubeZ(clockwise, numberOfRotations);
			break;
		case "X":
			rotateCubeX(clockwise, numberOfRotations);
			break;
		case "Y":
			rotateCubeY(clockwise, numberOfRotations);
			break;
		case "Z":
			rotateCubeZ(clockwise, numberOfRotations);
			break;
		default:
			throw new IllegalArgumentException("Unknown move: " + move);
		}

	}

	private void performMove(EColor rotationFace, boolean clockwise, int numberOfRotations) {
		for (int i = 0; i < numberOfRotations; i++) {
			rotateFace(rotationFace, clockwise);
		}
	}

	@Override
	public String toString() {
		Optional<String> cubeString = Arrays.stream(faces).map(face -> face.toString())
				.reduce((s1, s2) -> s1 + "\r\n" + s2);
		return cubeString.get() + "------\r\n";
	}

	public static void main(String[] args) {
		Cube myCube = new Cube();

		myCube.interpret("X Y Z");

		System.out.println(myCube.topFace);
		System.out.println(myCube.frontFace);
		System.out.println(myCube.rightFace);

		myCube.interpret("R U R' U' R' F R2 U' R' U' R U R' F'");
		// System.out.println(myCube);

		myCube.interpret("R U R' U' R' F R2 U' R' U' R U R' F'");
		System.out.println(myCube);

	}
}
