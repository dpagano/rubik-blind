package de.pagano.rubik.model;

/** The position of an edge. This is a combination of two faces. */
public class EdgePosition {

	/** The first face of the position. */
	private EFace firstFace;

	/** The second face of the position. */
	private EFace secondFace;

	/** Constructor. */
	public EdgePosition(EFace firstFace, EFace secondFace) {
		this.firstFace = firstFace;
		this.secondFace = secondFace;
	}

	/** Gets the inverted position. */
	public EdgePosition getInvertedPosition() {
		return new EdgePosition(secondFace, firstFace);
	}

	/** Gets the first face. */
	public EFace getFirstFace() {
		return firstFace;
	}

	/** Gets the second face. */
	public EFace getSecondFace() {
		return secondFace;
	}
}
