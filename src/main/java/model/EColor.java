package model;

public enum EColor {
	WHITE((byte) 0), GREEN((byte) 1), ORANGE((byte) 2), BLUE((byte) 3), RED((byte) 4), YELLOW((byte) 5);

	private final byte value;
	private static final EColor[] colors = { WHITE, GREEN, ORANGE, BLUE, RED, YELLOW };

	private EColor(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public static EColor getColor(byte value) {
		return colors[value];
	}

	@Override
	public String toString() {
		return name();
	}
}