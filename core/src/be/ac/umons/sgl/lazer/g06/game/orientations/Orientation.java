package be.ac.umons.sgl.lazer.g06.game.orientations;

import be.ac.umons.sgl.lazer.g06.game.Position;
/**
 * Interface defining methods to manipulate an Orientation.
 */
public interface Orientation {
	/**
	 * @return Reversed orientation (180-degrees).
	 */
	public default Orientation reverse() {
		return fromInt(ordinal()+size()/2);
	}
	/**
	 * @return The next orientation rotating clockwise.
	 */
	public default Orientation next() {
		return fromInt(ordinal()+1);
	}
	/**
	 * @return The next orientation rotating counter-clockwise.
	 */
	public default Orientation prev() {
		return fromInt(ordinal()-1);
	}
	/**
	 * @return Angle corresponding to this orientation in degrees.
	 */
	public default int getAngle() {
		return ordinal()*(360/size());
	}
	/**
	 * Rotate an orientation by an other.
	 * @param other Orientation used to rotate current one.
	 * @return Rotated orientation.
	 */
	public default Orientation rotateBy(Orientation other) {
		return fromInt(this.ordinal()+other.ordinal());
	}
	/**
	 * Reverse of {@link #rotateBy(Orientation)}.
	 * @param other Orientation used to rotate current one.
	 * @return Rotated orientation.
	 */
	public default Orientation unRotateBy(Orientation other) {
		return fromInt(this.ordinal()-other.ordinal());
	}
	/**
	 * Get the next position this orientation is pointing to.
	 * @param position Position to start with.
	 * @return Next position pointed by this orientation.
	 */
	public default Position nextPosition(Position position) {
		return new Position(position.getX()+getX(), position.getY()+getY(), position.getLocation());
	}
	/**
	 * Get orientation from integer. Used to easily manage orientation movements.
	 * @param i Orientation number. If number not within allowed values, values size is added or substracted till it is.
	 * @return The orientation corresponding to this number.
	 */
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
	 * Method to get size of the set
	 * @return Size of the set.
	 */
	public default int size() {
		return this.getValues().length;
	}
	/**
	 * Methods overridden by enumeration
	 * @return The number corresponding to this Orientation.
	 */
	public int ordinal();
	/*
	 * Start methods to implement yourself, with recommended code when used with enumerations
	 */
	/**
	 * Method to get first instance of enumeration
	 * @return First orientation.
	 */
	public Orientation first();
	/*
	 * Get special values, implement yourself
	 */
	public int getX();
	public int getY();
	/**
	 * Method to get all enumerations values, should call static values()
	 * @return Array containing all values.
	 */
	public Orientation[] getValues();
	/**
	 * Method to get all enumeration from string, should call static valueOf(String)
	 * @param s String representation of this orientation.
	 * @return Orientation corresponding to this values.
	 */
	public Orientation getValueOf(String s);

}
