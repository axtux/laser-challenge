package be.ac.umons.sgl.lazer.g06.game;

public enum Orientation {
	//* Standard orientations
	UP(0, 1),
	RIGHT(1, 0),
	DOWN(0, -1),
	LEFT(-1, 0);
	//*/
	/* Advanced orientations
	UP(0, 1),
	UP_RIGHT(1, 1),
	RIGHT(1, 0),
	DOWN_RIGHT(1, -1),
	DOWN(0, -1),
	DOWN_LEFT(-1, -1),
	LEFT(-1, 0),
	UP_LEFT(-1, 1);
	//*/
	
	int x, y;
	private Orientation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Orientation reverse() {
		return fromInt(ordinal()+Orientation.values().length/2);
	}
	
	public Orientation next() {
		return fromInt(ordinal()+1);
	}
	
	public Orientation prev() {
		return fromInt(ordinal()-1);
	}
	
	public int getAngle() {
		return ordinal()*(360/Orientation.values().length);
	}
	
	public Orientation rotateBy(Orientation other) {
		return fromInt(this.ordinal()+other.ordinal());
	}
	
	public Orientation unRotateBy(Orientation other) {
		return fromInt(this.ordinal()-other.ordinal());
	}
	
	public Position nextPosition(Position position) {
		return new Position(position.getX()+x, position.getY()+y, position.getLocation());
	}
	
	private static Orientation fromInt(int i) {
		Orientation[] orientations = Orientation.values();
		while(i < 0) i+= orientations.length;
		return orientations[i%orientations.length];
	}
	/**
	 * Get orientation from string
	 * @param str String representation of orientation
	 * @return null if orientation does not exists instead of throwing an exception
	 */
	public static Orientation fromString(String str) {
		try {
			return Orientation.valueOf(str);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
