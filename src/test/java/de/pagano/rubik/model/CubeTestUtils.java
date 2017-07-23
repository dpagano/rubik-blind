package de.pagano.rubik.model;

import org.junit.Assert;

/** Utils for testing cubes and faces. */
public class CubeTestUtils {

	/** Asserts that the provided faces are equal. */
	public static void assertFacesAreEqual(Face firstFace, Face secondFace) throws CubeException {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				assertPiecesAreEqual(firstFace.getPiece(x, y), secondFace.getPiece(x, y));
			}
		}
	}

	/** Asserts that the provided pieces (i.e. colors) are equal. */
	public static void assertPiecesAreEqual(EColor firstPiece, EColor secondPiece) {
		Assert.assertTrue("Pieces are not equal: " + firstPiece + " & " + secondPiece,
				piecesAreEqual(firstPiece, secondPiece));
	}

	private static boolean piecesAreEqual(EColor firstPiece, EColor secondPiece) {
		return (firstPiece.equals(secondPiece));
	}

	/**
	 * Asserts that the provided face has the specified colors. The order of the
	 * colors is line-based from left to right and top to bottom.
	 */
	public static void assertFaceEquals(Face face, EColor... colors) throws CubeException {
		Assert.assertEquals(face.getNumberOfPieces(), colors.length);
		int c = 0;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				assertPiecesAreEqual(face.getPiece(x, y), colors[c]);
				c++;
			}
		}
	}

	/** Asserts that the provided cubes are equal. */
	public static void assertCubesAreEqual(Cube firstCube, Cube secondCube) throws CubeException {
		for (int faceIndex = 0; faceIndex < 6; faceIndex++) {
			assertFacesAreEqual(firstCube.getFace(EColor.getColor(faceIndex)),
					secondCube.getFace(EColor.getColor(faceIndex)));
		}
	}

}
