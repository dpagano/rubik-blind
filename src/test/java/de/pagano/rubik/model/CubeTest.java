package de.pagano.rubik.model;

import static de.pagano.rubik.model.CubeTestUtils.assertCubesAreEqual;
import static de.pagano.rubik.model.CubeTestUtils.assertFaceEquals;
import static de.pagano.rubik.model.CubeTestUtils.assertPiecesAreEqual;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/** Tests for a cube. */
public class CubeTest {

	/** Common cube used in test cases. */
	private Cube cube;

	/** Creates a new cube for all test cases. */
	@Before
	public void before() throws CubeException {
		cube = new Cube();
	}

	/** Tests that rotating a face does not change the rotating face. */
	@Test
	public void rotationShouldNotChangeRotatingFace() throws CubeException {
		cube.rotateFace(EColor.GREEN, true);
		assertFaceEquals(cube.getFace(EColor.GREEN), getArrayOfSameColor(EColor.GREEN, 9));

		cube.rotateFace(EColor.BLUE, false);
		assertFaceEquals(cube.getFace(EColor.BLUE), getArrayOfSameColor(EColor.BLUE, 9));
	}

	/** Tests that a copied cube has the same colors as the original cube. */
	@Test
	public void cubeCopyShouldHaveIdenticalColors() throws CubeException {
		Cube copy = cube.copy();
		assertCubesAreEqual(cube, copy);
	}

	/** Tests that rotating a face back and forth does not change the cube. */
	@Test
	public void rotatingBackAndForthShouldRestoreOriginalState() throws CubeException {
		Cube copy = cube.copy();
		copy.rotateFace(EColor.RED, false);
		copy.rotateFace(EColor.RED, true);
		assertCubesAreEqual(cube, copy);
	}

	/** Tests that rotating one face four times does not change the cube. */
	@Test
	public void rotatingFourTimesShouldRestoreOriginalState() throws CubeException {
		Cube copy = cube.copy();
		copy.rotateFace(EColor.GREEN, false);
		copy.rotateFace(EColor.GREEN, false);
		copy.rotateFace(EColor.GREEN, false);
		copy.rotateFace(EColor.GREEN, false);
		assertCubesAreEqual(cube, copy);
	}

	/**
	 * Tests that rotating one face yields the correct colors on the other faces.
	 */
	@Test
	public void rotatingShouldYieldCorrectAdjacentFaces() throws CubeException {
		// We currently only rotate around the green axis. It would be good to have a
		// somewhat reusable rotation test and test all six rotations.
		cube.rotateFace(EColor.GREEN, false);
		assertFaceEquals(cube.getFace(EColor.WHITE), EColor.RED, EColor.RED, EColor.RED, EColor.WHITE, EColor.WHITE,
				EColor.WHITE, EColor.WHITE, EColor.WHITE, EColor.WHITE);
		assertFaceEquals(cube.getFace(EColor.ORANGE), EColor.ORANGE, EColor.ORANGE, EColor.WHITE, EColor.ORANGE,
				EColor.ORANGE, EColor.WHITE, EColor.ORANGE, EColor.ORANGE, EColor.WHITE);
		assertFaceEquals(cube.getFace(EColor.YELLOW), EColor.ORANGE, EColor.ORANGE, EColor.ORANGE, EColor.YELLOW,
				EColor.YELLOW, EColor.YELLOW, EColor.YELLOW, EColor.YELLOW, EColor.YELLOW);
		assertFaceEquals(cube.getFace(EColor.RED), EColor.YELLOW, EColor.RED, EColor.RED, EColor.YELLOW, EColor.RED,
				EColor.RED, EColor.YELLOW, EColor.RED, EColor.RED);
		assertFaceEquals(cube.getFace(EColor.BLUE), getArrayOfSameColor(EColor.BLUE, 9));
	}

	/**
	 * Tests that rotating the cube as a whole retains the correct orientation of
	 * all faces.
	 */
	@Test
	public void rotatingCubeShouldRetainFaceOrientation() throws CubeException {
		Cube copy = cube.copy();
		copy.rotateX(true, 1);
		assertCubesAreEqual(cube, copy);

		copy.rotateY(false, 1);
		assertCubesAreEqual(cube, copy);

		copy.rotateZ(true, 1);
		assertCubesAreEqual(cube, copy);
	}

	/**
	 * Tests that rotating the cube as a whole around the X axis yields the correct
	 * orientation.
	 */
	@Test
	public void rotatingCubeXShouldChangeOrientationCorrectly() throws CubeException {
		Cube copy = cube.copy();
		copy.rotateX(true, 1);
		assertPiecesAreEqual(cube.getRightColor(), copy.getRightColor());
		assertPiecesAreEqual(cube.getFrontColor(), copy.getTopColor());
		assertPiecesAreEqual(CubeOrientation.getOppositeFace(cube.getTopColor()), copy.getFrontColor());
	}

	/**
	 * Tests that rotating the cube as a whole around the Y axis yields the correct
	 * orientation.
	 */
	@Test
	public void rotatingCubeYShouldChangeOrientationCorrectly() throws CubeException {
		Cube copy = cube.copy();
		copy.rotateY(true, 1);
		assertPiecesAreEqual(cube.getTopColor(), copy.getTopColor());
		assertPiecesAreEqual(cube.getRightColor(), copy.getFrontColor());
		assertPiecesAreEqual(CubeOrientation.getOppositeFace(cube.getFrontColor()), copy.getRightColor());
	}

	/**
	 * Tests that rotating the cube as a whole around the Y axis yields the correct
	 * orientation.
	 */
	@Test
	public void rotatingCubeZShouldChangeOrientationCorrectly() throws CubeException {
		Cube copy = cube.copy();
		copy.rotateZ(true, 1);
		assertPiecesAreEqual(cube.getFrontColor(), copy.getFrontColor());
		assertPiecesAreEqual(cube.getTopColor(), copy.getRightColor());
		assertPiecesAreEqual(CubeOrientation.getOppositeFace(cube.getRightColor()), copy.getTopColor());
	}

	@Test
	public void performingPLLMoveShouldYieldCorrectResult() throws CubeException {
		cube.move("R U R' U' R' F R2 U' R' U' R U R' F'");
		assertFaceEquals(cube.getFace(EColor.WHITE), getArrayOfSameColor(EColor.WHITE, 9));
		assertFaceEquals(cube.getFace(EColor.GREEN), EColor.GREEN, EColor.GREEN, EColor.RED, EColor.GREEN, EColor.GREEN,
				EColor.GREEN, EColor.GREEN, EColor.GREEN, EColor.GREEN);
		assertFaceEquals(cube.getFace(EColor.ORANGE), EColor.ORANGE, EColor.RED, EColor.ORANGE, EColor.ORANGE,
				EColor.ORANGE, EColor.ORANGE, EColor.ORANGE, EColor.ORANGE, EColor.ORANGE);
		assertFaceEquals(cube.getFace(EColor.BLUE), EColor.RED, EColor.BLUE, EColor.BLUE, EColor.BLUE, EColor.BLUE,
				EColor.BLUE, EColor.BLUE, EColor.BLUE, EColor.BLUE);
		assertFaceEquals(cube.getFace(EColor.RED), EColor.BLUE, EColor.ORANGE, EColor.GREEN, EColor.RED, EColor.RED,
				EColor.RED, EColor.RED, EColor.RED, EColor.RED);
		assertFaceEquals(cube.getFace(EColor.YELLOW), getArrayOfSameColor(EColor.YELLOW, 9));
	}

	/** Gets an {@link EColor} array of the specified color and size. */
	private EColor[] getArrayOfSameColor(EColor color, int size) {
		EColor[] colors = new EColor[size];
		Arrays.fill(colors, color);
		return colors;
	}
}
