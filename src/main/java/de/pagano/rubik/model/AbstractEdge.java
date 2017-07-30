package de.pagano.rubik.model;

import java.util.Objects;

/** Generic base class for edges. */
public abstract class AbstractEdge<T> {

	private T firstValue;
	private T secondValue;

	public AbstractEdge(T firstValue, T secondValue) {
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}

	public T getFirstValue() {
		return firstValue;
	}

	public T getSecondValue() {
		return secondValue;
	}

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
