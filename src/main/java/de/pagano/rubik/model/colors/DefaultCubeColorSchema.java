package de.pagano.rubik.model.colors;

import org.apache.commons.collections4.bidimap.TreeBidiMap;

import de.pagano.rubik.model.EFace;

public class DefaultCubeColorSchema implements ICubeColorSchema {
	private static final TreeBidiMap<EFace, EColor> schema = new TreeBidiMap<>();

	public DefaultCubeColorSchema() {
		schema.put(EFace.TOP, EColor.WHITE);
		schema.put(EFace.FRONT, EColor.GREEN);
		schema.put(EFace.LEFT, EColor.ORANGE);
		schema.put(EFace.BACK, EColor.BLUE);
		schema.put(EFace.RIGHT, EColor.RED);
		schema.put(EFace.BOTTOM, EColor.YELLOW);
	}

	@Override
	public EColor getColor(EFace face) {
		return schema.get(face);
	}

	@Override
	public EFace getFace(EColor color) {
		return schema.getKey(color);
	}

}
