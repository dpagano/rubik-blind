package de.pagano.rubik.model.colors;

import de.pagano.rubik.model.EFace;

/** Defines method of a color schema. */
public interface ICubeColorSchema {
	public EColor getColor(EFace face);

	public EFace getFace(EColor color);
}