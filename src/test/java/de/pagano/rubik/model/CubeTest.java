package de.pagano.rubik.model;

import static de.pagano.rubik.model.CubeTestUtils.assertCubesAreEqual;
import static de.pagano.rubik.model.CubeTestUtils.assertFaceEquals;

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
	 * Tests that rotating the cube as a whole yields the correct orientation of all
	 * faces.
	 */
	@Test
	public void rotatingCubeShouldYieldCorrectFaceOrientation() throws CubeException {
		// TODO (DP): Rotate cube; get top face etc. => Get face by orientation
	}

	/** Gets an {@link EColor} array of the specified color and size. */
	private EColor[] getArrayOfSameColor(EColor color, int size) {
		EColor[] colors = new EColor[size];
		Arrays.fill(colors, color);
		return colors;
	}
}
