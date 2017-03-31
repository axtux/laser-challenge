package be.ac.umons.sgl.lazer.g06.game;
/**
 * Position on map.
 */
public class Position {
	/**
	 * Location used to separate positions from map and inventory.
	 */
	public enum Location {
		MAP, INVENTORY;
	}
	
	private int x, y;
	private Location loc;
	/**
	 * Required for JSON class to create this object
	 */
	@SuppressWarnings(value = { "unused" })
	private Position() {}
	/**
	 * Create position without location.
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Create position with location.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param loc Map location.
	 */
	public Position(int x, int y, Location loc) {
		this(x, y);
		this.loc = loc;
	}
	/**
	 * @return X coordinate of this position
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return Y coordinate of this position
	 */
	public int getY() {
		return y;
	}
	/**
	 * @return Map Location of this position
	 */
	public Location getLocation() {
		return loc;
	}
	/**
	 * String representation of this position.
	 */
	public String toString() {
		String s = "Position("+Integer.toString(x)+", "+Integer.toString(y);
		if(loc != null) {
			s += ", "+loc.toString();
		}
		s += ")";
		return s;
	}
	/**
	 * Test equality with another Position object.
	 */
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		
		Position other;
		try {
			other = (Position) o;
		} catch (ClassCastException e) {
			return false;
		}
		return this.x == other.x && this.y == other.y && this.loc == other.loc;
	}
	
}