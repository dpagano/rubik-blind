package model;

public class CubeException extends RuntimeException {

	/** Serial version id. */
	private static final long serialVersionUID = 1L;

	public CubeException(String message) {
		super(message);
	}

	public CubeException(Throwable cause) {
		super(cause);
	}

	public CubeException(String message, Throwable cause) {
		super(message, cause);
	}
}
