package de.pagano.rubik.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;

import de.pagano.rubik.model.colors.EColor;
import de.pagano.rubik.model.colors.ICubeColorSchema;

/** Utils for testing cubes and faces. */
public class CubeTestUtils {

	/**
	 * Asserts that the provided faces are equal by comparing the corresponding
	 * pieces using {@link #assertPiecesAreEqual(EColor, EColor)}.
	 */
	public static void assertFacesAreEqual(Face firstFace, Face secondFace) throws CubeException {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				assertPiecesAreEqual(firstFace.getPiece(x, y), secondFace.getPiece(x, y));
			}
		}
	}

	/** Asserts that the provided pieces (i.e. colors) are equal. */
	public static void assertPiecesAreEqual(EColor colorOfFirstPiece, EColor colorOfSecondPiece) {
		Assert.assertTrue("Pieces are not equal: " + colorOfFirstPiece + " & " + colorOfSecondPiece,
				colorOfFirstPiece.equals(colorOfSecondPiece));
	}

	/**
	 * Asserts that the provided face has the specified colors. The order of the
	 * colors is line-based from left to right and top to bottom.
	 */
	public static void assertFaceEqualsColorsForSchema(Face face, ICubeColorSchema colorSchema, EFace... faces)
			throws CubeException {
		List<EColor> colors = Stream.of(faces).map(theface -> colorSchema.getColor(theface))
				.collect(Collectors.toList());
		assertFaceEquals(face, colors.toArray(new EColor[colors.size()]));
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

	/**
	 * Asserts that the provided cubes are equal by comparing their faces using
	 * {@link #assertFacesAreEqual(Face, Face)}.
	 */
	public static void assertCubesAreEqual(Cube firstCube, Cube secondCube) throws CubeException {
		for (int faceIndex = 0; faceIndex < 6; faceIndex++) {
			assertFacesAreEqual(firstCube.getFace(EFace.getFace(faceIndex)),
					secondCube.getFace(EFace.getFace(faceIndex)));
		}
	}

}
