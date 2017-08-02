package de.pagano.rubik.model;

import de.pagano.rubik.model.colors.EColor;

/** An edge is a combination of two colors. */
public class Edge {

	/** The first color of the edge. */
	private EColor firstColor;

	/** The second color of the edge. */
	private EColor secondColor;

	/** Constructor. */
	public Edge(EColor firstValue, EColor secondValue) {
		this.firstColor = firstValue;
		this.secondColor = secondValue;
	}

	/** Gets the inverse of this edge. */
	public Edge getInvertedEdge() {
		return new Edge(secondColor, firstColor);
	}

	/** Gets the first color. */
	public EColor getFirstColor() {
		return firstColor;
	}

	/** Gets the second color. */
	public EColor getSecondColor() {
		return secondColor;
	}
}
