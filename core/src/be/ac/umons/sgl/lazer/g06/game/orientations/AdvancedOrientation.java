package be.ac.umons.sgl.lazer.g06.game.orientations;
/**
 * Advanced orientation is 8-directions Orientation implementation.
 */
public enum AdvancedOrientation implements Orientation {
	/**
	 * Defines orientations
	 */
	UP(0, 1),
	UP_RIGHT(1, 1),
	RIGHT(1, 0),
	DOWN_RIGHT(1, -1),
	DOWN(0, -1),
	DOWN_LEFT(-1, -1),
	LEFT(-1, 0),
	UP_LEFT(-1, 1);
	/**
	 * Get first instance of this enumeration in a static way.
	 * @return First orientation.
	 */
	public static Orientation staticFirst() {
		return UP;
	}
	/**
	 * Get first instance of this enumeration in a non-static way.
	 * @return First orientation.
	 */
	public Orientation first() {
		return UP;
	}
	
	int x, y;
	/**
	 * Create Orientation with custom coordinates.
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	private AdvancedOrientation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * @return X coordinate
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return Y coordinate
	 */
	public int getY() {
		return y;
	}
	/**
	 * Get all values of this Orientation
	 */
	public Orientation[] getValues() {
		return values();
	}
	/**
	 * Get Orientation from string value
	 */
	public Orientation getValueOf(String s) {
		return valueOf(s);
	}
}
