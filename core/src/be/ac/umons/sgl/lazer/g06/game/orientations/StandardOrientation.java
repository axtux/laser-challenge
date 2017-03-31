package be.ac.umons.sgl.lazer.g06.game.orientations;

import be.ac.umons.sgl.lazer.g06.game.Position;

public enum StandardOrientation {
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
	private StandardOrientation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public StandardOrientation reverse() {
		return fromInt(ordinal()+StandardOrientation.values().length/2);
	}
	
	public StandardOrientation next() {
		return fromInt(ordinal()+1);
	}
	
	public StandardOrientation prev() {
		return fromInt(ordinal()-1);
	}
	
	public int getAngle() {
		return ordinal()*(360/StandardOrientation.values().length);
	}
	
	public StandardOrientation rotateBy(StandardOrientation other) {
		return fromInt(this.ordinal()+other.ordinal());
	}
	
	public StandardOrientation unRotateBy(StandardOrientation other) {
		return fromInt(this.ordinal()-other.ordinal());
	}
	
	public Position nextPosition(Position position) {
		return new Position(position.getX()+x, position.getY()+y, position.getLocation());
	}
	
	private static StandardOrientation fromInt(int i) {
		StandardOrientation[] orientations = StandardOrientation.values();
		while(i < 0) i+= orientations.length;
		return orientations[i%orientations.length];
	}
	/**
	 * Get orientation from string
	 * @param str String representation of orientation
	 * @return null if orientation does not exists instead of throwing an exception
	 */
	public static StandardOrientation fromString(String str) {
		try {
			return StandardOrientation.valueOf(str);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
