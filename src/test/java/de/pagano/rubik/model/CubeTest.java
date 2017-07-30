package de.pagano.rubik.model;

import static de.pagano.rubik.model.CubeTestUtils.assertCubesAreEqual;
import static de.pagano.rubik.model.CubeTestUtils.assertFaceEquals;
import static de.pagano.rubik.model.CubeTestUtils.assertFaceEqualsColorsForSchema;
import static de.pagano.rubik.model.CubeTestUtils.assertFacesAreEqual;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import de.pagano.rubik.model.colors.EColor;
import de.pagano.rubik.model.colors.ICubeColorSchema;

/** Tests for a cube. */
public class CubeTest {

	/** Common cube used in test cases. */
	private Cube cube;

	/** Creates a new cube for all test cases. */
	@Before
	public void before() throws CubeException {
		// Using default color schema
		cube = new Cube();
	}

	/**
	 * Tests that rotating a face does not change the colors of the rotating face.
	 */
	@Test
	public void rotationShouldNotChangeRotatingFace() throws CubeException {
		cube.rotateFace(EFace.FRONT, true);
		assertFaceEquals(cube.getFace(EFace.FRONT),
				getArrayOfSameColorForColorSchema(cube.getColorSchema(), EFace.FRONT, 9));

		cube.rotateFace(EFace.BACK, false);
		assertFaceEquals(cube.getFace(EFace.BACK),
				getArrayOfSameColorForColorSchema(cube.getColorSchema(), EFace.BACK, 9));
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
		// We currently only rotate around the front axis. It would be good to have a
		// somewhat reusable rotation test and test all six rotations.
		cube.rotateFace(EFace.FRONT, false);
		assertFaceEqualsColorsForSchema(cube.getFace(EFace.TOP), cube.getColorSchema(), EFace.RIGHT, EFace.RIGHT,
				EFace.RIGHT, EFace.TOP, EFace.TOP, EFace.TOP, EFace.TOP, EFace.TOP, EFace.TOP);
		assertFaceEqualsColorsForSchema(cube.getFace(EFace.LEFT), cube.getColorSchema(), EFace.LEFT, EFace.LEFT,
				EFace.TOP, EFace.LEFT, EFace.LEFT, EFace.TOP, EFace.LEFT, EFace.LEFT, EFace.TOP);
		assertFaceEqualsColorsForSchema(cube.getFace(EFace.BOTTOM), cube.getColorSchema(), EFace.LEFT, EFace.LEFT,
				EFace.LEFT, EFace.BOTTOM, EFace.BOTTOM, EFace.BOTTOM, EFace.BOTTOM, EFace.BOTTOM, EFace.BOTTOM);
		assertFaceEqualsColorsForSchema(cube.getFace(EFace.RIGHT), cube.getColorSchema(), EFace.BOTTOM, EFace.RIGHT,
				EFace.RIGHT, EFace.BOTTOM, EFace.RIGHT, EFace.RIGHT, EFace.BOTTOM, EFace.RIGHT, EFace.RIGHT);
		assertFaceEqualsColorsForSchema(cube.getFace(EFace.BACK), cube.getColorSchema(),
				getArrayOfSameFaces(EFace.BACK, 9));
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
		assertFacesAreEqual(cube.getFace(cube.getRightFace()), copy.getFace(copy.getRightFace()));
		assertFacesAreEqual(cube.getFace(cube.getFrontFace()), copy.getFace(copy.getTopFace()));
		assertFacesAreEqual(cube.getFace(CubeGeometry.getOppositeFace(cube.getTopFace())),
				copy.getFace(copy.getFrontFace()));
	}

	/**
	 * Tests that rotating the cube as a whole around the Y axis yields the correct
	 * orientation.
	 */
	@Test
	public void rotatingCubeYShouldChangeOrientationCorrectly() throws CubeException {
		Cube copy = cube.copy();
		copy.rotateY(true, 1);
		assertFacesAreEqual(cube.getFace(cube.getTopFace()), copy.getFace(copy.getTopFace()));
		assertFacesAreEqual(cube.getFace(cube.getRightFace()), copy.getFace(copy.getFrontFace()));
		assertFacesAreEqual(cube.getFace(CubeGeometry.getOppositeFace(cube.getFrontFace())),
				copy.getFace(copy.getRightFace()));
	}

	/**
	 * Tests that rotating the cube as a whole around the Y axis yields the correct
	 * orientation.
	 */
	@Test
	public void rotatingCubeZShouldChangeOrientationCorrectly() throws CubeException {
		Cube copy = cube.copy();
		copy.rotateZ(true, 1);
		assertFacesAreEqual(cube.getFace(cube.getFrontFace()), copy.getFace(copy.getFrontFace()));
		assertFacesAreEqual(cube.getFace(cube.getTopFace()), copy.getFace(copy.getRightFace()));
		assertFacesAreEqual(cube.getFace(CubeGeometry.getOppositeFace(cube.getRightFace())),
				copy.getFace(copy.getTopFace()));
	}

	/** Tests that performing the T perm PLL yields the expected outcome. */
	@Test
	public void performingPLLMoveShouldYieldCorrectResult() throws CubeException {
		cube.move("R U R' U' R' F R2 U' R' U' R U R' F'");
		assertFaceEqualsColorsForSchema(cube.getFace(EFace.TOP), cube.getColorSchema(),
				getArrayOfSameFaces(EFace.TOP, 9));
		assertFaceEqualsColorsForSchema(cube.getFace(EFace.FRONT), cube.getColorSchema(), EFace.FRONT, EFace.FRONT,
				EFace.RIGHT, EFace.FRONT, EFace.FRONT, EFace.FRONT, EFace.FRONT, EFace.FRONT, EFace.FRONT);
		assertFaceEqualsColorsForSchema(cube.getFace(EFace.LEFT), cube.getColorSchema(), EFace.LEFT, EFace.RIGHT,
				EFace.LEFT, EFace.LEFT, EFace.LEFT, EFace.LEFT, EFace.LEFT, EFace.LEFT, EFace.LEFT);
		assertFaceEqualsColorsForSchema(cube.getFace(EFace.BACK), cube.getColorSchema(), EFace.RIGHT, EFace.BACK,
				EFace.BACK, EFace.BACK, EFace.BACK, EFace.BACK, EFace.BACK, EFace.BACK, EFace.BACK);
		assertFaceEqualsColorsForSchema(cube.getFace(EFace.RIGHT), cube.getColorSchema(), EFace.BACK, EFace.LEFT,
				EFace.FRONT, EFace.RIGHT, EFace.RIGHT, EFace.RIGHT, EFace.RIGHT, EFace.RIGHT, EFace.RIGHT);
		assertFaceEqualsColorsForSchema(cube.getFace(EFace.BOTTOM), cube.getColorSchema(),
				getArrayOfSameFaces(EFace.BOTTOM, 9));
	}

	/** Gets an {@link EColor} array of the specified color and size. */
	private EColor[] getArrayOfSameColorForColorSchema(ICubeColorSchema colorSchema, EFace face, int size) {
		return getArrayOfSameColor(colorSchema.getColor(face), size);
	}

	/** Gets an {@link EColor} array of the specified color and size. */
	private EColor[] getArrayOfSameColor(EColor color, int size) {
		EColor[] colors = new EColor[size];
		Arrays.fill(colors, color);
		return colors;
	}

	/** Gets an {@link EFace} array of the specified face and size. */
	private EFace[] getArrayOfSameFaces(EFace face, int size) {
		EFace[] faces = new EFace[size];
		Arrays.fill(faces, face);
		return faces;
	}

}
