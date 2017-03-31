package be.ac.umons.sgl.lazer.g06.game.orientations;

public enum StandardOrientation implements Orientation {
	UP(0, 1),
	RIGHT(1, 0),
	DOWN(0, -1),
	LEFT(-1, 0);
	
	public static Orientation first() {
		return UP;
	}
	
	int x, y;
	private StandardOrientation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	public int size() {
		return values().length;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Orientation fromInt(int i) {
		Orientation[] orientations = StandardOrientation.values();
		while(i < 0) i+= orientations.length;
		return orientations[i%orientations.length];
	}
	/**
	 * Get orientation from string
	 * @param str String representation of orientation
	 * @return null if orientation does not exists instead of throwing an exception
	 */
	public Orientation fromString(String str) {
		try {
			return StandardOrientation.valueOf(str);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
