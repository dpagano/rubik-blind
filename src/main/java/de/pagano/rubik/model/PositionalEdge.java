package de.pagano.rubik.model;

/** A positional edge is a combination of two faces. */
public class PositionalEdge extends AbstractEdge<EFace> {

	public PositionalEdge(EFace firstValue, EFace secondValue) {
		super(firstValue, secondValue);
	}

	@Override
	public AbstractEdge<EFace> getInvertedEdge() {
		return new PositionalEdge(getSecondValue(), getFirstValue());
	}

	public EFace getFirstFace() {
		return getFirstValue();
	}

	public EFace getSecondFace() {
		return getSecondValue();
	}

	public Edge toEdge(ICubeColorSchema colorSchema) {
		return new Edge(colorSchema.getColor(getFirstFace()), colorSchema.getColor(getSecondFace()));
	}
}
