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
		cube.rotateFace(EFace.FRONT, true);
		assertFaceEquals(cube.getFace(EFace.FRONT), getArrayOfSameColor(EFace.FRONT, 9));

		cube.rotateFace(EFace.BACK, false);
		assertFaceEquals(cube.getFace(EFace.BACK), getArrayOfSameColor(EFace.BACK, 9));
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
		copy.rotateFace(EFace.RIGHT, false);
		copy.rotateFace(EFace.RIGHT, true);
		assertCubesAreEqual(cube, copy);
	}

	/** Tests that rotating one face four times does not change the cube. */
	@Test
	public void rotatingFourTimesShouldRestoreOriginalState() throws CubeException {
		Cube copy = cube.copy();
		copy.rotateFace(EFace.FRONT, false);
		copy.rotateFace(EFace.FRONT, false);
		copy.rotateFace(EFace.FRONT, false);
		copy.rotateFace(EFace.FRONT, false);
		assertCubesAreEqual(cube, copy);
	}

	/**
	 * Tests that rotating one face yields the correct colors on the other faces.
	 */
	@Test
	public void rotatingShouldYieldCorrectAdjacentFaces() throws CubeException {
		// We currently only rotate around the green axis. It would be good to have a
		// somewhat reusable rotation test and test all six rotations.
		cube.rotateFace(EFace.FRONT, false);
		assertFaceEquals(cube.getFace(EFace.TOP), EFace.RIGHT, EFace.RIGHT, EFace.RIGHT, EFace.TOP, EFace.TOP,
				EFace.TOP, EFace.TOP, EFace.TOP, EFace.TOP);
		assertFaceEquals(cube.getFace(EFace.LEFT), EFace.LEFT, EFace.LEFT, EFace.TOP, EFace.LEFT,
				EFace.LEFT, EFace.TOP, EFace.LEFT, EFace.LEFT, EFace.TOP);
		assertFaceEquals(cube.getFace(EFace.BOTTOM), EFace.LEFT, EFace.LEFT, EFace.LEFT, EFace.BOTTOM,
				EFace.BOTTOM, EFace.BOTTOM, EFace.BOTTOM, EFace.BOTTOM, EFace.BOTTOM);
		assertFaceEquals(cube.getFace(EFace.RIGHT), EFace.BOTTOM, EFace.RIGHT, EFace.RIGHT, EFace.BOTTOM, EFace.RIGHT,
				EFace.RIGHT, EFace.BOTTOM, EFace.RIGHT, EFace.RIGHT);
		assertFaceEquals(cube.getFace(EFace.BACK), getArrayOfSameColor(EFace.BACK, 9));
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
		assertFaceEquals(cube.getFace(EFace.TOP), getArrayOfSameColor(EFace.TOP, 9));
		assertFaceEquals(cube.getFace(EFace.FRONT), EFace.FRONT, EFace.FRONT, EFace.RIGHT, EFace.FRONT, EFace.FRONT,
				EFace.FRONT, EFace.FRONT, EFace.FRONT, EFace.FRONT);
		assertFaceEquals(cube.getFace(EFace.LEFT), EFace.LEFT, EFace.RIGHT, EFace.LEFT, EFace.LEFT,
				EFace.LEFT, EFace.LEFT, EFace.LEFT, EFace.LEFT, EFace.LEFT);
		assertFaceEquals(cube.getFace(EFace.BACK), EFace.RIGHT, EFace.BACK, EFace.BACK, EFace.BACK, EFace.BACK,
				EFace.BACK, EFace.BACK, EFace.BACK, EFace.BACK);
		assertFaceEquals(cube.getFace(EFace.RIGHT), EFace.BACK, EFace.LEFT, EFace.FRONT, EFace.RIGHT, EFace.RIGHT,
				EFace.RIGHT, EFace.RIGHT, EFace.RIGHT, EFace.RIGHT);
		assertFaceEquals(cube.getFace(EFace.BOTTOM), getArrayOfSameColor(EFace.BOTTOM, 9));
	}

	/** Gets an {@link EFace} array of the specified color and size. */
	private EFace[] getArrayOfSameColor(EFace color, int size) {
		EFace[] colors = new EFace[size];
		Arrays.fill(colors, color);
		return colors;
	}
}
