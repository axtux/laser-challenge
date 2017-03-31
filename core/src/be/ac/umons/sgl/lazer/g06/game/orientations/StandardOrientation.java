package be.ac.umons.sgl.lazer.g06.game.orientations;

/**
 * create an enumeration with the standard orientation
 *
 */
public enum StandardOrientation implements Orientation {
	UP(0, 1),
	RIGHT(1, 0),
	DOWN(0, -1),
	LEFT(-1, 0);
	
	/**
	 * @return the orientation at default
	 */
	public static Orientation staticFirst() {
		return UP;
	}
	
	/**
	 * @return the orientation at default
	 */
	public Orientation first() {
		return UP;
	}
	
	int x, y;
	private StandardOrientation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Orientation[] getValues() {
		return values();
	}
	
	public Orientation getValueOf(String s) {
		return valueOf(s);
	}
	
}
