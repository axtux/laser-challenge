package be.ac.umons.sgl.lazer.g06.game.orientations;

import be.ac.umons.sgl.lazer.g06.game.Position;

public interface Orientation {
	public default Orientation reverse() {
		return fromInt(ordinal()+size()/2);
	}
	
	public default Orientation next() {
		return fromInt(ordinal()+1);
	}
	
	public default Orientation prev() {
		return fromInt(ordinal()-1);
	}
	
	public default int getAngle() {
		return ordinal()*(360/size());
	}
	
	public default Orientation rotateBy(Orientation other) {
		return fromInt(this.ordinal()+other.ordinal());
	}
	
	public default Orientation unRotateBy(Orientation other) {
		return fromInt(this.ordinal()-other.ordinal());
	}
	
	public default Position nextPosition(Position position) {
		return new Position(position.getX()+getX(), position.getY()+getY(), position.getLocation());
	}
	
	public default Orientation fromInt(int i) {
		Orientation[] orientations = first().getValues();
		while(i < 0) i+= orientations.length;
		return orientations[i%orientations.length];
	}
	/**
	 * Get orientation from string
	 * @param str String representation of orientation
	 * @return null if orientation does not exists instead of throwing an exception
	 */
	public default Orientation fromString(String str) {
		try {
			return first().getValueOf(str);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	/**
	 * Method to get size of enumeration
	 */
	public default int size() {
		return this.getValues().length;
	}
	/**
	 * Methods overridden by enumeration
	 */
	public int ordinal();
	/*
	 * Start methods to implement yourself, with recommended code
	 */
	/**
	 * Method to get first instance of enumeration
	 */
	public Orientation first();
	/**
	 * Get special values, implement yourself
	 * @return
	 */
	public int getX();
	public int getY();
	/**
	 * Method to get all enumerations values, should call static values()
	 */
	public Orientation[] getValues();
	/**
	 * Method to get all enumeration from string, should call static valueOf(String)
	 */
	public Orientation getValueOf(String s);

}
