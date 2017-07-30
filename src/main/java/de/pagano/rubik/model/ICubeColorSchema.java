package de.pagano.rubik.model;

public interface ICubeColorSchema {
	public EColor getColor(EFace face);

	public EFace getFace(EColor color);
}