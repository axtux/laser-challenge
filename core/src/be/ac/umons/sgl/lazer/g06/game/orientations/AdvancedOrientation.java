package be.ac.umons.sgl.lazer.g06.game.orientations;

public enum AdvancedOrientation implements Orientation {
	UP(0, 1),
	UP_RIGHT(1, 1),
	RIGHT(1, 0),
	DOWN_RIGHT(1, -1),
	DOWN(0, -1),
	DOWN_LEFT(-1, -1),
	LEFT(-1, 0),
	UP_LEFT(-1, 1);
	
	public static Orientation staticFirst() {
		return UP;
	}
	
	public Orientation first() {
		return UP;
	}
	
	int x, y;
	private AdvancedOrientation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Orientation[] getValues() {
		return values();
	}
	
	public Orientation getValueOf(String s) {
		return valueOf(s);
	}
	
}
