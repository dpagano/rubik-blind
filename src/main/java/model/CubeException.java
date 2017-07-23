package model;

/**
 * Exception for problems related to cubes. Currently based on
 * {@link RuntimeException}, to be able to use within streams.
 */
public class CubeException extends Exception {

	/** Serial version id. */
	private static final long serialVersionUID = 1L;

	/** Constructor. */
	public CubeException(String message) {
		super(message);
	}

	/** Constructor. */
	public CubeException(Throwable cause) {
		super(cause);
	}

	/** Constructor. */
	public CubeException(String message, Throwable cause) {
		super(message, cause);
	}
}
