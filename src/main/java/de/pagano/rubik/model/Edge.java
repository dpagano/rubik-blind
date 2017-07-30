package de.pagano.rubik.model;

import de.pagano.rubik.model.colors.EColor;

/** An edge is a combination of two colors. */
public class Edge extends AbstractEdge<EColor> {

	public Edge(EColor firstValue, EColor secondValue) {
		super(firstValue, secondValue);
	}

	@Override
	public AbstractEdge<EColor> getInvertedEdge() {
		return new Edge(getSecondValue(), getFirstValue());
	}

	public EColor getFirstColor() {
		return getFirstValue();
	}

	public EColor getSecondColor() {
		return getSecondValue();
	}
}
