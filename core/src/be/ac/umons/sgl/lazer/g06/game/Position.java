package be.ac.umons.sgl.lazer.g06.game;

public class Position {
	public enum Location {
		MAP, INVENTARY;
	}
	
	private int x, y;
	private Location loc;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position(int x, int y, Location loc) {
		this(x, y);
		this.loc = loc;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Location getLocation() {
		return loc;
	}
	
}