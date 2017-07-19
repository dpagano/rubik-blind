package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Tests for a cube face. */
public class FaceTest {

	/** Common face used in test cases. Has all pieces colored 0. */
	private Face face;

	/** Common face used in test cases. Has multi-colored pieces. */
	private Face multiColoredFace;

	@Before
	public void before() {
		face = new Face((byte) 0);
		multiColoredFace = new Face((byte) 0);
		multiColoredFace.setPiece(0, 0, (byte) 0);
		multiColoredFace.setPiece(0, 1, (byte) 1);
		multiColoredFace.setPiece(0, 2, (byte) 2);
		multiColoredFace.setPiece(1, 0, (byte) 3);
		multiColoredFace.setPiece(1, 1, (byte) 4);
		multiColoredFace.setPiece(1, 2, (byte) 5);
		multiColoredFace.setPiece(2, 0, (byte) 5);
		multiColoredFace.setPiece(2, 1, (byte) 2);
		multiColoredFace.setPiece(2, 2, (byte) 3);
	}

	@Test
	public void faceCopyShouldHaveIdenticalColors() {
		assertFacesAreEqual(multiColoredFace, multiColoredFace.copy());
	}

	@Test
	public void faceShouldGetAndSetCorrectColor() {
		face.setPiece(2, 1, (byte) 3);
		assertPiecesAreEqual(face.getPiece(2, 1), (byte) 3);
		assertPiecesAreEqual(face.getPiece(0, 0), (byte) 0);
	}

	@Test(expected = CubeException.class)
	public void faceShouldThrowExceptionForSetPieceOutOfBounds() {
		face.setPiece(3, 3, (byte) 1);
	}

	@Test(expected = CubeException.class)
	public void faceShouldThrowExceptionForGetPieceOutOfBounds() {
		face.getPiece(3, 3);
	}

	@Test
	public void rotateCWShouldRotateTheColorsCorrectly() {
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
	public void rotateCCWShouldRotateTheColorsCorrectly() {
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
	public void rotateCCWFourTimesShouldEqualIdentityTransform() {
		Face originalFace = multiColoredFace.copy();
		multiColoredFace.rotateCounterClockwise();
		multiColoredFace.rotateCounterClockwise();
		multiColoredFace.rotateCounterClockwise();
		multiColoredFace.rotateCounterClockwise();
		assertFacesAreEqual(multiColoredFace, originalFace);
	}

	@Test
	public void rotateCWFourTimesShouldEqualIdentityTransform() {
		Face originalFace = multiColoredFace.copy();
		multiColoredFace.rotateClockwise();
		multiColoredFace.rotateClockwise();
		multiColoredFace.rotateClockwise();
		multiColoredFace.rotateClockwise();
		assertFacesAreEqual(multiColoredFace, originalFace);
	}

	@Test
	public void rotateBackAndForthShouldEqualIdentityTransform() {
		Face originalFace = multiColoredFace.copy();
		multiColoredFace.rotateCounterClockwise();
		multiColoredFace.rotateClockwise();
		assertFacesAreEqual(multiColoredFace, originalFace);
	}

	private void assertFacesAreEqual(Face firstFace, Face secondFace) {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				assertPiecesAreEqual(firstFace.getPiece(x, y), secondFace.getPiece(x, y));
			}
		}
	}

	private void assertPiecesAreEqual(byte firstPiece, byte secondPiece) {
		Assert.assertTrue("Pieces are not equal: " + firstPiece + " & " + secondPiece,
				piecesAreEqual(firstPiece, secondPiece));
	}

	private boolean piecesAreEqual(byte firstPiece, byte secondPiece) {
		// TODO (DP): Switch to equals when we change to byte
		return (firstPiece == secondPiece);
	}

}
