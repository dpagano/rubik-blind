package de.pagano.rubik.model.colors;

import org.apache.commons.collections4.bidimap.TreeBidiMap;

import de.pagano.rubik.model.EFace;

/** The default color schema of a 3x3 Rubik's cube. */
public class DefaultCubeColorSchema implements ICubeColorSchema {
	private static final TreeBidiMap<EFace, EColor> SCHEMA = new TreeBidiMap<>();
	
	// TODO: Test Comment for Teamscale

	/** Constructor. */
	public DefaultCubeColorSchema() {
		SCHEMA.put(EFace.TOP, EColor.WHITE);
		SCHEMA.put(EFace.FRONT, EColor.GREEN);
		SCHEMA.put(EFace.LEFT, EColor.ORANGE);
		SCHEMA.put(EFace.BACK, EColor.BLUE);
		SCHEMA.put(EFace.RIGHT, EColor.RED);
		SCHEMA.put(EFace.BOTTOM, EColor.YELLOW);
	}

	@Override
	public EColor getColor(EFace face) {
		return SCHEMA.get(face);
	}

	@Override
	public EFace getFace(EColor color) {
		return SCHEMA.getKey(color);
	}
}
