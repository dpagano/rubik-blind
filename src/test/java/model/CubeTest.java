package model;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/** Tests for a cube. */
public class CubeTest {

	/** Common cube used in test cases. */
	private Cube cube;

	@Before
	public void before() throws CubeException {
		cube = new Cube();
	}

	@Test
	public void cubeRotationShouldNotChangeRelativeColors() throws CubeException {
		cube.rotateFace(EColor.GREEN, true);
		CubeTestUtils.assertFaceEquals(cube.getFace(EColor.GREEN), getArrayOfSameColor(EColor.GREEN, 9));

		cube.rotateFace(EColor.BLUE, false);
		CubeTestUtils.assertFaceEquals(cube.getFace(EColor.BLUE), getArrayOfSameColor(EColor.BLUE, 9));
	}

	@Test
	public void cubeCopyShouldHaveIdenticalColors() throws CubeException {
		Cube copy = cube.copy();
		CubeTestUtils.assertCubesAreEqual(cube, copy);
	}

	@Test
	public void rotatingBackAndForthShouldRestoreOriginalState() throws CubeException {
		Cube copy = cube.copy();
		copy.rotateFace(EColor.RED, false);
		copy.rotateFace(EColor.RED, true);
		CubeTestUtils.assertCubesAreEqual(cube, copy);
	}

	@Test
	public void rotatingForTimesShouldRestoreOriginalState() throws CubeException {
		Cube copy = cube.copy();
		copy.rotateFace(EColor.GREEN, false);
		copy.rotateFace(EColor.GREEN, false);
		copy.rotateFace(EColor.GREEN, false);
		copy.rotateFace(EColor.GREEN, false);
		CubeTestUtils.assertCubesAreEqual(cube, copy);
	}

	@Test
	public void rotatingShouldYieldCorrectAdjacentFaces() throws CubeException {
		cube.rotateFace(EColor.GREEN, false);
		CubeTestUtils.assertFaceEquals(cube.getFace(EColor.WHITE), EColor.RED, EColor.RED, EColor.RED, EColor.WHITE,
				EColor.WHITE, EColor.WHITE, EColor.WHITE, EColor.WHITE, EColor.WHITE);
		CubeTestUtils.assertFaceEquals(cube.getFace(EColor.ORANGE), EColor.ORANGE, EColor.ORANGE, EColor.WHITE,
				EColor.ORANGE, EColor.ORANGE, EColor.WHITE, EColor.ORANGE, EColor.ORANGE, EColor.WHITE);
		CubeTestUtils.assertFaceEquals(cube.getFace(EColor.YELLOW), EColor.ORANGE, EColor.ORANGE, EColor.ORANGE,
				EColor.YELLOW, EColor.YELLOW, EColor.YELLOW, EColor.YELLOW, EColor.YELLOW, EColor.YELLOW);
		CubeTestUtils.assertFaceEquals(cube.getFace(EColor.RED), EColor.YELLOW, EColor.RED, EColor.RED, EColor.YELLOW,
				EColor.RED, EColor.RED, EColor.YELLOW, EColor.RED, EColor.RED);
	}

	@Test
	public void rotatingCubeShouldYieldCorrectFaceOrientation() throws CubeException {
		// TODO (DP): Rotate cube; get top face etc. => Get face by orientation
	}

	private EColor[] getArrayOfSameColor(EColor color, int size) {
		EColor[] colors = new EColor[size];
		Arrays.fill(colors, color);
		return colors;
	}

}
