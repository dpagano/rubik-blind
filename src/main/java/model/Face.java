package model;

import org.junit.Assert;

import model.Cube.EColor;

public class Face {
	private byte[][] pieces = new byte[3][3];
	private byte faceColor;

	public Face(byte faceColor) {
		this.faceColor = faceColor;
		setAllPieces(faceColor);
	}

	public Face copy() {
		Face copy = new Face(faceColor);
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				copy.setPiece(x, y, getPiece(x, y));
			}
		}
		return copy;
	}

	void setPiece(int x, int y, byte color) {
		Assert.assertTrue(x < 3 && y < 3);
		pieces[x][y] = color;
	}

	byte getPiece(int x, int y) {
		Assert.assertTrue(x < 3 && y < 3);
		return pieces[x][y];
	}

	private void setAllPieces(byte color) {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				setPiece(x, y, color);
			}
		}
	}

	public void rotate(boolean clockwise) {
		if (clockwise) {
			rotateClockwise();
		} else {
			rotateAntiClockwise();
		}
	}

	public void rotateClockwise() {
		swapPieces(0, 2, 0, 0);
		swapPieces(0, 0, 2, 0);
		swapPieces(2, 0, 2, 2);

		swapPieces(0, 1, 1, 0);
		swapPieces(1, 0, 2, 1);
		swapPieces(2, 1, 1, 2);
	}

	public void rotateAntiClockwise() {
		swapPieces(0, 0, 0, 2);
		swapPieces(0, 2, 2, 2);
		swapPieces(2, 2, 2, 0);

		swapPieces(0, 1, 1, 2);
		swapPieces(1, 2, 2, 1);
		swapPieces(2, 1, 1, 0);
	}

	private void swapPieces(int x0, int y0, int x1, int y1) {
		byte buffer = pieces[x0][y0];
		pieces[x0][y0] = pieces[x1][y1];
		pieces[x1][y1] = buffer;
	}

	@Override
	public String toString() {
		String value = "Face (" + faceColor + "):\r\n";
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				value += EColor.getColor(pieces[x][y]) + " ";
			}
			value += "\r\n";
		}
		return value;
	}

	public static void main(String[] args) {
		Face test = new Face((byte) 4);
		System.out.println(test);

		byte c = 0;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				test.setPiece(x, y, c++);
			}
		}

		System.out.println(test);
		test.rotateClockwise();

		System.out.println(test);
		test.rotateAntiClockwise();

		System.out.println(test);
	}
}
