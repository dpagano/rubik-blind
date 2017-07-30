package de.pagano.rubik.model;

import static de.pagano.rubik.model.CubeTestUtils.assertFacesAreEqual;
import static de.pagano.rubik.model.CubeTestUtils.assertPiecesAreEqual;

import org.junit.Before;
import org.junit.Test;

/** Tests for a cube face. */
public class FaceTest {

	/** Common face used in test cases. Has all pieces colored 0. */
	private Face face;

	/** Common face used in test cases. Has multi-colored pieces. */
	private Face multiColoredFace;

	/** Sets up {@link #face} and {@link #multiColoredFace}. */
	@Before
	public void before() throws CubeException {
		face = new Face(EFace.TOP);
		multiColoredFace = new Face(EFace.TOP);
		multiColoredFace.setPiece(0, 0, EFace.TOP);
		multiColoredFace.setPiece(0, 1, EFace.FRONT);
		multiColoredFace.setPiece(0, 2, EFace.LEFT);
		multiColoredFace.setPiece(1, 0, EFace.BACK);
		multiColoredFace.setPiece(1, 1, EFace.RIGHT);
		multiColoredFace.setPiece(1, 2, EFace.BOTTOM);
		multiColoredFace.setPiece(2, 0, EFace.BOTTOM);
		multiColoredFace.setPiece(2, 1, EFace.LEFT);
		multiColoredFace.setPiece(2, 2, EFace.RIGHT);
	}

	/** Tests that the copied face has the same colors as the original face. */
	@Test
	public void faceCopyShouldHaveIdenticalColors() throws CubeException {
		assertFacesAreEqual(multiColoredFace, multiColoredFace.copy());
	}

	/** Tests that setting and getting pieces works correctly. */
	@Test
	public void faceShouldGetAndSetCorrectColor() throws CubeException {
		face.setPiece(2, 1, EFace.RIGHT);
		assertPiecesAreEqual(face.getPiece(2, 1), EFace.RIGHT);
		assertPiecesAreEqual(face.getPiece(0, 0), EFace.TOP);
	}

	/** Tests that setting a piece out of bounds throws an exception. */
	@Test(expected = CubeException.class)
	public void faceShouldThrowExceptionForSetPieceOutOfBounds() throws CubeException {
		face.setPiece(3, 3, EFace.FRONT);
	}

	/** Tests that getting a piece out of bounds throws an exception. */
	@Test(expected = CubeException.class)
	public void faceShouldThrowExceptionForGetPieceOutOfBounds() throws CubeException {
		face.getPiece(3, 3);
	}

	/** Tests that rotating a face clockwise works correctly. */
	@Test
	public void rotateCWShouldRotateTheColorsCorrectly() throws CubeException {
		Face originalFace = multiColoredFace.copy();
		multiColoredFace.rotateClockwise();

		assertPiecesAreEqual(originalFace.getPiece(2, 0), multiColoredFace.getPiece(0, 0));
		assertPiecesAreEqual(originalFace.getPiece(1, 0), multiColoredFace.getPiece(0, 1));
		assertPiecesAreEqual(originalFace.getPiece(0, 0), multiColoredFace.getPiece(0, 2));
		assertPiecesAreEqual(originalFace.getPiece(2, 1), multiColoredFace.getPiece(1, 0));
		assertPiecesAreEqual(originalFace.getPiece(1, 1), multiColoredFace.getPiece(1, 1));
		assertPiecesAreEqual(originalFace.getPiece(0, 1), multiColoredFace.getPiece(1, 2));
		assertPiecesAreEqual(originalFace.getPiece(2, 2), multiColoredFace.getPiece(2, 0));
		assertPiecesAreEqual(originalFace.getPiece(1, 2), multiColoredFace.getPiece(2, 1));
		assertPiecesAreEqual(originalFace.getPiece(0, 2), multiColoredFace.getPiece(2, 2));
	}

	/** Tests that rotating a face counter clockwise works correctly. */
	@Test
	public void rotateCCWShouldRotateTheColorsCorrectly() throws CubeException {
		Face originalFace = multiColoredFace.copy();
		multiColoredFace.rotateCounterClockwise();

		assertPiecesAreEqual(originalFace.getPiece(0, 0), multiColoredFace.getPiece(2, 0));
		assertPiecesAreEqual(originalFace.getPiece(0, 1), multiColoredFace.getPiece(1, 0));
		assertPiecesAreEqual(originalFace.getPiece(0, 2), multiColoredFace.getPiece(0, 0));
		assertPiecesAreEqual(originalFace.getPiece(1, 0), multiColoredFace.getPiece(2, 1));
		assertPiecesAreEqual(originalFace.getPiece(1, 1), multiColoredFace.getPiece(1, 1));
		assertPiecesAreEqual(originalFace.getPiece(1, 2), multiColoredFace.getPiece(0, 1));
		assertPiecesAreEqual(originalFace.getPiece(2, 0), multiColoredFace.getPiece(2, 2));
		assertPiecesAreEqual(originalFace.getPiece(2, 1), multiColoredFace.getPiece(1, 2));
		assertPiecesAreEqual(originalFace.getPiece(2, 2), multiColoredFace.getPiece(0, 2));
	}

	/**
	 * Tests that rotating a face counter clockwise four times does not change the
	 * face.
	 */
	@Test
	public void rotateCCWFourTimesShouldEqualIdentityTransform() throws CubeException {
		Face originalFace = multiColoredFace.copy();
		multiColoredFace.rotateCounterClockwise();
		multiColoredFace.rotateCounterClockwise();
		multiColoredFace.rotateCounterClockwise();
		multiColoredFace.rotateCounterClockwise();
		assertFacesAreEqual(multiColoredFace, originalFace);
	}

	/** Tests that rotating a face clockwise four times does not change the face. */
	@Test
	public void rotateCWFourTimesShouldEqualIdentityTransform() throws CubeException {
		Face originalFace = multiColoredFace.copy();
		multiColoredFace.rotateClockwise();
		multiColoredFace.rotateClockwise();
		multiColoredFace.rotateClockwise();
		multiColoredFace.rotateClockwise();
		assertFacesAreEqual(multiColoredFace, originalFace);
	}

	/** Tests that rotating a face back and forth does not change the face. */
	@Test
	public void rotateBackAndForthShouldEqualIdentityTransform() throws CubeException {
		Face originalFace = multiColoredFace.copy();
		multiColoredFace.rotateCounterClockwise();
		multiColoredFace.rotateClockwise();
		assertFacesAreEqual(multiColoredFace, originalFace);
	}
}
