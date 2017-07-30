package de.pagano.rubik.model;

import java.util.Objects;

public class Edge {

	private EFace[] faces;

	public Edge(EFace firstFace, EFace secondFace) {
		faces = new EFace[] { firstFace, secondFace };
	}

	public EFace getFirstFace() {
		return faces[0];
	}

	public EFace getSecondFace() {
		return faces[1];
	}

	public Edge getInvertedEdge() {
		return new Edge(faces[1], faces[0]);
	}

	@Override
	public String toString() {
		return "Edge: " + faces[0] + " & " + faces[1];
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof Edge) {
			Edge other = (Edge) obj;
			return faces[0].equals(other.faces[0]) && faces[1].equals(other.faces[1]);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(faces[0], faces[1]);
	}
}
