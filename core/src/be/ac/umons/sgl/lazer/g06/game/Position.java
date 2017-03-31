package be.ac.umons.sgl.lazer.g06.game;

public class Position {
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
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position(int x, int y, Location loc) {
		this(x, y);
		this.loc = loc;
	}
	/**
	 * @return the attribute x of object Position
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return the attribute y of object Position
	 */
	public int getY() {
		return y;
	}
	/**
	 * @return the Location object 
	 */
	public Location getLocation() {
		return loc;
	}
	
	public String toString() {
		String s = "Position("+Integer.toString(x)+", "+Integer.toString(y);
		if(loc != null) {
			s += ", "+loc.toString();
		}
		s += ")";
		return s;
	}
	
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