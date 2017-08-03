package de.pagano.rubik.model;

import java.util.Objects;

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

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Edge) {
			return ((Edge) obj).firstColor.equals(firstColor) && ((Edge) obj).secondColor.equals(secondColor);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstColor, secondColor);
	}

	@Override
	public String toString() {
		return "Edge: " + firstColor + " & " + secondColor;
	}
}
