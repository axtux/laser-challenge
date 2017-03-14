package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.Gdx;

import be.ac.umons.sgl.lazer.g06.Files;

public class Level {
	Mode mode;
	String type;
	int number;

	public Level(String file) {
		Gdx.app.debug("Level.Level", "creating level from file "+file);
	}
		
	public Level(Mode mode, String type, int number) {
		this.mode = mode;
		this.type = type;
		this.number = number;
	}

	public enum Mode {
		ARCADE, TRAINING
	}
	
	public static Level[] listLevels(String type) {
		String levelsDir = "levels/"+type.toLowerCase();
		String levelFiles[] = Files.list(levelsDir);
		
		if(levelFiles == null) {
			Gdx.app.error("Level.listLevels", "Got null while listing directory "+levelsDir);
			return null;
		}
		
		Level levels[] = new Level[levelFiles.length];
		for(int i = 0; i < levelFiles.length; ++i) {
			levels[i] = new Level(levelFiles[i]);
		}
		return null;
	}
}
