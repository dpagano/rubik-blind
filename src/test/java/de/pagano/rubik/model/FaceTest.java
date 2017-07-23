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

	@Before
	public void before() throws CubeException {
		face = new Face(EColor.WHITE);
		multiColoredFace = new Face(EColor.WHITE);
		multiColoredFace.setPiece(0, 0, EColor.WHITE);
		multiColoredFace.setPiece(0, 1, EColor.GREEN);
		multiColoredFace.setPiece(0, 2, EColor.ORANGE);
		multiColoredFace.setPiece(1, 0, EColor.BLUE);
		multiColoredFace.setPiece(1, 1, EColor.RED);
		multiColoredFace.setPiece(1, 2, EColor.YELLOW);
		multiColoredFace.setPiece(2, 0, EColor.YELLOW);
		multiColoredFace.setPiece(2, 1, EColor.ORANGE);
		multiColoredFace.setPiece(2, 2, EColor.RED);
	}

	@Test
	public void faceCopyShouldHaveIdenticalColors() throws CubeException {
		assertFacesAreEqual(multiColoredFace, multiColoredFace.copy());
	}

	@Test
	public void faceShouldGetAndSetCorrectColor() throws CubeException {
		face.setPiece(2, 1, EColor.RED);
		assertPiecesAreEqual(face.getPiece(2, 1), EColor.RED);
		assertPiecesAreEqual(face.getPiece(0, 0), EColor.WHITE);
	}

	@Test(expected = CubeException.class)
	public void faceShouldThrowExceptionForSetPieceOutOfBounds() throws CubeException {
		face.setPiece(3, 3, EColor.GREEN);
	}

	@Test(expected = CubeException.class)
	public void faceShouldThrowExceptionForGetPieceOutOfBounds() throws CubeException {
		face.getPiece(3, 3);
	}

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

	@Test
	public void rotateCCWFourTimesShouldEqualIdentityTransform() throws CubeException {
		Face originalFace = multiColoredFace.copy();
		multiColoredFace.rotateCounterClockwise();
		multiColoredFace.rotateCounterClockwise();
		multiColoredFace.rotateCounterClockwise();
		multiColoredFace.rotateCounterClockwise();
		assertFacesAreEqual(multiColoredFace, originalFace);
	}

	@Test
	public void rotateCWFourTimesShouldEqualIdentityTransform() throws CubeException {
		Face originalFace = multiColoredFace.copy();
		multiColoredFace.rotateClockwise();
		multiColoredFace.rotateClockwise();
		multiColoredFace.rotateClockwise();
		multiColoredFace.rotateClockwise();
		assertFacesAreEqual(multiColoredFace, originalFace);
	}

	@Test
	public void rotateBackAndForthShouldEqualIdentityTransform() throws CubeException {
		Face originalFace = multiColoredFace.copy();
		multiColoredFace.rotateCounterClockwise();
		multiColoredFace.rotateClockwise();
		assertFacesAreEqual(multiColoredFace, originalFace);
	}
}
