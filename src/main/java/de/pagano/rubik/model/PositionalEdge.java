package de.pagano.rubik.model;

import de.pagano.rubik.model.colors.ICubeColorSchema;

/** A positional edge is a combination of two faces. */
public class PositionalEdge extends AbstractEdge<EFace> {

	/** Constructor. */
	public PositionalEdge(EFace firstValue, EFace secondValue) {
		super(firstValue, secondValue);
	}

	@Override
	public AbstractEdge<EFace> getInvertedEdge() {
		return new PositionalEdge(getSecondValue(), getFirstValue());
	}

	/** Gets the first face. */
	public EFace getFirstFace() {
		return getFirstValue();
	}

	/** Gets the second face. */
	public EFace getSecondFace() {
		return getSecondValue();
	}

	/**
	 * Converts this positional edge into a color based edge using the provided
	 * color schema.
	 */
	public Edge toEdge(ICubeColorSchema colorSchema) {
		return new Edge(colorSchema.getColor(getFirstFace()), colorSchema.getColor(getSecondFace()));
	}
}
