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
	
	/**
	 * Integer representation of orientation
	 * @return
	 */
	public int ordinal();
	/**
	 * Size of all possible orientations of this type
	 * @return
	 */
	public int size();
	public int getX();
	public int getY();
	
	public Orientation fromInt(int i);
	public Orientation fromString(String str);
}
