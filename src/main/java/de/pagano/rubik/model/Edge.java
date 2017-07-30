package de.pagano.rubik.model;

import de.pagano.rubik.model.colors.EColor;

/** An edge is a combination of two colors. */
public class Edge extends AbstractEdge<EColor> {

	/** Constructor. */
	public Edge(EColor firstValue, EColor secondValue) {
		super(firstValue, secondValue);
	}

	@Override
	public AbstractEdge<EColor> getInvertedEdge() {
		return new Edge(getSecondValue(), getFirstValue());
	}

	/** Gets the first color. */
	public EColor getFirstColor() {
		return getFirstValue();
	}

	/** Gets the second color. */
	public EColor getSecondColor() {
		return getSecondValue();
	}
}
