package de.pagano.rubik.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.pagano.rubik.model.colors.DefaultCubeColorSchema;
import de.pagano.rubik.model.colors.EColor;
import de.pagano.rubik.model.colors.ICubeColorSchema;
import de.pagano.rubik.model.moves.EMove;

/**
 * A cube, which is able to rotate faces, interpret moves, and scramble.
 *
 * // TODO (DP): In the future, only rotation should be kept here.
 * Interpretation and scrambling should be moved to extra classes.
 */
public class Cube {

	/** The face currently facing up. */
	private EFace topFace;

	/** The face currently facing to the front. */
	private EFace frontFace;

	/** The face currently facing to the right. */
	private EFace rightFace;

	/** The faces of the cube. */
	private final Face[] faces = new Face[6];

	/** The color schema of this cube */
	private final ICubeColorSchema colorSchema;

	/** Constructor. */
	public Cube() throws CubeException {
		this(new DefaultCubeColorSchema());
	}

	/** Constructor. */
	public Cube(ICubeColorSchema colorSchema) throws CubeException {
		this.colorSchema = colorSchema;
		for (int i = 0; i < 6; i++) {
			faces[i] = new Face(this.colorSchema.getColor(EFace.getFace(i)));
		}

		topFace = EFace.TOP;
		frontFace = EFace.FRONT;
		rightFace = EFace.RIGHT;
	}

	/**
	 * Creates an identical copy of this cube.
	 */
	public Cube copy() throws CubeException {
		Cube copy = new Cube(this.colorSchema);
		for (int faceIndex = 0; faceIndex < 6; faceIndex++) {
			copy.faces[faceIndex] = faces[faceIndex].copy();
		}
		return copy;
	}

	/** Rotates the specified face. */
	public void rotateFace(EFace face, boolean clockwise) throws CubeException {
		faces[face.getIndex()].rotate(clockwise);
		rotateAdjacentFaces(face, clockwise);
	}

	public Face getFace(EFace face) {
		return faces[face.getIndex()];
	}

	public EFace getTopFace() {
		return topFace;
	}

	public EFace getFrontFace() {
		return frontFace;
	}

	public EFace getRightFace() {
		return rightFace;
	}

	public ICubeColorSchema getColorSchema() {
		return colorSchema;
	}

	/** Rotates the adjacent pieces on the adjacent faces of the specified face. */
	private void rotateAdjacentFaces(EFace face, boolean clockwise) throws CubeException {
		List<EFace> adjacentFaces = new ArrayList<>(CubeGeometry.getAdjacentFaces(face, !clockwise));

		List<int[][]> indexesToRotate = adjacentFaces.stream().map(adjacentFace -> {
			return CubeGeometry.getAdjacentIndexes(face, adjacentFace);
		}).collect(Collectors.toList());

		// Add the buffer to the end, so that we can use one simple for loop to
		// rotate
		indexesToRotate.add(indexesToRotate.get(0));
		adjacentFaces.add(adjacentFaces.get(0));
		Face buffer = faces[adjacentFaces.get(0).getIndex()].copy();

		for (int faceIndex = 0; faceIndex < 4; faceIndex++) {
			EFace currentFace = adjacentFaces.get(faceIndex);
			int[][] currentIndexes = indexesToRotate.get(faceIndex);

			EFace nextFace = adjacentFaces.get(faceIndex + 1);
			int[][] nextIndexes = indexesToRotate.get(faceIndex + 1);

			for (int i = 0; i < 3; i++) {
				Face nextFaceOfThisCube = faces[nextFace.getIndex()];
				if (faceIndex == 3) {
					nextFaceOfThisCube = buffer;
				}
				EColor nextColor = nextFaceOfThisCube.getPiece(nextIndexes[i][0], nextIndexes[i][1]);
				faces[currentFace.getIndex()].setPiece(currentIndexes[i][0], currentIndexes[i][1], nextColor);
			}
		}
	}

	// TODO (DP): Extract one method with "rotationFace" and other two params
	/** Rotates the cube along the X axis. */
	public void rotateX(boolean clockwise, int numberOfRotations) {
		List<EFace> adjacentFacesClockwise = CubeGeometry.getAdjacentFaces(rightFace, clockwise);
		int currentTopFace = adjacentFacesClockwise.indexOf(topFace);
		topFace = adjacentFacesClockwise.get((currentTopFace + 4 - numberOfRotations) % 4);
		int currentFrontFace = adjacentFacesClockwise.indexOf(frontFace);
		frontFace = adjacentFacesClockwise.get((currentFrontFace + 4 - numberOfRotations) % 4);
	}

	/** Rotates the cube along the Y axis. */
	public void rotateY(boolean clockwise, int numberOfRotations) {
		List<EFace> adjacentFacesClockwise = CubeGeometry.getAdjacentFaces(topFace, clockwise);
		int currentRightFace = adjacentFacesClockwise.indexOf(rightFace);
		rightFace = adjacentFacesClockwise.get((currentRightFace + 4 - numberOfRotations) % 4);
		int currentFrontFace = adjacentFacesClockwise.indexOf(frontFace);
		frontFace = adjacentFacesClockwise.get((currentFrontFace + 4 - numberOfRotations) % 4);
	}

	/** Rotates the cube along the Z axis. */
	public void rotateZ(boolean clockwise, int numberOfRotations) {
		List<EFace> adjacentFacesClockwise = CubeGeometry.getAdjacentFaces(frontFace, clockwise);
		int currentRightFace = adjacentFacesClockwise.indexOf(rightFace);
		rightFace = adjacentFacesClockwise.get((currentRightFace + 4 - numberOfRotations) % 4);
		int currentTopFace = adjacentFacesClockwise.indexOf(topFace);
		topFace = adjacentFacesClockwise.get((currentTopFace + 4 - numberOfRotations) % 4);
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
		boolean clockwise = !move.endsWith(EMove.MODIFIER_REVERSE.toString());
		int numberOfRotations = 1;
		if (move.endsWith(EMove.MODIFIER_DOUBLE.toString())) {
			numberOfRotations = 2;
		}
		String firstLetter = move.substring(0, 1);
		EMove interpretedMove = EMove.fromString(firstLetter);
		switch (interpretedMove) {
		case UP:
			performRotation(topFace, clockwise, numberOfRotations);
			break;
		case DOWN:
			performRotation(CubeGeometry.getOppositeFace(topFace), clockwise, numberOfRotations);
			break;
		case FRONT:
			performRotation(frontFace, clockwise, numberOfRotations);
			break;
		case BACK:
			performRotation(CubeGeometry.getOppositeFace(frontFace), clockwise, numberOfRotations);
			break;
		case RIGHT:
			performRotation(rightFace, clockwise, numberOfRotations);
			break;
		case LEFT:
			performRotation(CubeGeometry.getOppositeFace(rightFace), clockwise, numberOfRotations);
			break;
		case MIDDLE:
			performRotation(rightFace, clockwise, numberOfRotations);
			performRotation(CubeGeometry.getOppositeFace(rightFace), !clockwise, numberOfRotations);
			rotateX(!clockwise, numberOfRotations);
			break;
		case EQUATORIAL:
			performRotation(topFace, clockwise, numberOfRotations);
			performRotation(CubeGeometry.getOppositeFace(topFace), !clockwise, numberOfRotations);
			rotateY(!clockwise, numberOfRotations);
			break;
		case STANDING:
			performRotation(frontFace, !clockwise, numberOfRotations);
			performRotation(CubeGeometry.getOppositeFace(frontFace), clockwise, numberOfRotations);
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
	private void performRotation(EFace rotationFace, boolean clockwise, int numberOfRotations) throws CubeException {
		for (int i = 0; i < numberOfRotations; i++) {
			rotateFace(rotationFace, clockwise);
		}
	}

	/**
	 * Takes the colors of the given edge and translates them into faces on this
	 * cube. Gets the edge sitting at this position.
	 */
	public Edge getEdgeAt(Edge edge) throws CubeException {
		return getEdgeAt(colorSchema.getFace(edge.getFirstColor()), colorSchema.getFace(edge.getSecondColor()));
	}

	/** Gets the edge at the position specified by the two given faces. */
	public Edge getEdgeAt(EFace firstFace, EFace secondFace) throws CubeException {
		System.out.println("Getting edge at " + firstFace + " & " + secondFace);
		int[][] adjacentIndexesForFirstFace = CubeGeometry.getAdjacentIndexes(firstFace, secondFace);
		System.out.println("Adjacent indexes for " + firstFace + ": " + arrayString(adjacentIndexesForFirstFace));

		Face secondFaceOnThisCube = faces[secondFace.getIndex()];
		EColor secondEdgeColor = secondFaceOnThisCube.getPiece(adjacentIndexesForFirstFace[1][0],
				adjacentIndexesForFirstFace[1][1]);

		int[][] adjacentIndexesForSecondFace = CubeGeometry.getAdjacentIndexes(secondFace, firstFace);
		System.out.println("Adjacent indexes for " + secondFace + ": " + arrayString(adjacentIndexesForSecondFace));

		Face firstFaceOnThisCube = faces[firstFace.getIndex()];
		EColor firstEdgeColor = firstFaceOnThisCube.getPiece(adjacentIndexesForSecondFace[1][0],
				adjacentIndexesForSecondFace[1][1]);

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

	/**
	 * Looks up the provided edge position and returns an edge with the
	 * corresponding colors.
	 */
	public Edge getEdge(EdgePosition position) {
		return new Edge(colorSchema.getColor(position.getFirstFace()), colorSchema.getColor(position.getSecondFace()));
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

		myCube.move("R U R' U' R' F R2 U' R' U' R U R' F'");
		System.out.println(myCube);

		Edge edge = myCube.getEdgeAt(EFace.LEFT, EFace.FRONT);
		System.out.println(edge);
	}
}
