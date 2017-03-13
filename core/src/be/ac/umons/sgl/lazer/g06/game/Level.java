package be.ac.umons.sgl.lazer.g06.game;

public class Level {
	Mode mode;
	Type type;
	int number;
	
	public Level(Mode mode, Type type, int number) {
		this.mode = mode;
		this.type = type;
		this.number = number;
	}

	public enum Mode {
		ARCADE, TRAINING
	}
	public enum Type {
		STANDARD, ADVANCED
	}
	
	public static Level[] listLevels() {
		
		return null;
	}
}
