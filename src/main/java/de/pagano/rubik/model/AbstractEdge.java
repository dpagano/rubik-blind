package de.pagano.rubik.model;

import java.util.Objects;

/** Generic base class for edges. */
public abstract class AbstractEdge<T> {

	/** The first value of the edge. */
	private T firstValue;

	/** The second value of the edge. */
	private T secondValue;

	/** Constructor. */
	public AbstractEdge(T firstValue, T secondValue) {
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}

	/** @see #firstValue */
	public T getFirstValue() {
		return firstValue;
	}

	/** @see #secondValue */
	public T getSecondValue() {
		return secondValue;
	}

	/** Gets the inverse of this edge. */
	public abstract AbstractEdge<T> getInvertedEdge();

	@Override
	public String toString() {
		return "Edge: " + firstValue + " & " + secondValue;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof AbstractEdge) {
			@SuppressWarnings("unchecked")
			AbstractEdge<T> other = (AbstractEdge<T>) obj;
			return firstValue.equals(other.firstValue) && secondValue.equals(other.secondValue);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstValue, secondValue);
	}
}
