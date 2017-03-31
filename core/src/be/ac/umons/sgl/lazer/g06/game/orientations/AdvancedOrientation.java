package be.ac.umons.sgl.lazer.g06.game.orientations;

import be.ac.umons.sgl.lazer.g06.game.Position;

public enum AdvancedOrientation {
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
	private AdvancedOrientation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public AdvancedOrientation reverse() {
		return fromInt(ordinal()+AdvancedOrientation.values().length/2);
	}
	
	public AdvancedOrientation next() {
		return fromInt(ordinal()+1);
	}
	
	public AdvancedOrientation prev() {
		return fromInt(ordinal()-1);
	}
	
	public int getAngle() {
		return ordinal()*(360/AdvancedOrientation.values().length);
	}
	
	public AdvancedOrientation rotateBy(AdvancedOrientation other) {
		return fromInt(this.ordinal()+other.ordinal());
	}
	
	public AdvancedOrientation unRotateBy(AdvancedOrientation other) {
		return fromInt(this.ordinal()-other.ordinal());
	}
	
	public Position nextPosition(Position position) {
		return new Position(position.getX()+x, position.getY()+y, position.getLocation());
	}
	
	private static AdvancedOrientation fromInt(int i) {
		AdvancedOrientation[] orientations = AdvancedOrientation.values();
		while(i < 0) i+= orientations.length;
		return orientations[i%orientations.length];
	}
	/**
	 * Get orientation from string
	 * @param str String representation of orientation
	 * @return null if orientation does not exists instead of throwing an exception
	 */
	public static AdvancedOrientation fromString(String str) {
		try {
			return AdvancedOrientation.valueOf(str);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
