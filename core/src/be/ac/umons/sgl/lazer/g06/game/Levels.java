package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.Files;
/**
 * Keep all levels into memory and refresh on demand
 */
public class Levels {
	static final String LEVELS_PATH = "levels";
	/**
	 * Keep all instances of Level to avoid too many disk access.
	 */
	private static Array<Level> levels;
	/**
	 * Refresh levels from disk. Levels directory is scanned and all valid levels are loaded into static array.
	 */
	private static void refresh() {
		Array<String> levelFiles = Files.listFiles(LEVELS_PATH);
		if(levelFiles == null) {
			throw new GdxRuntimeException("Got null while listing directory "+LEVELS_PATH);
		}
		levelFiles.sort();
		Gdx.app.debug("Level.getLevels files", String.join("|", levelFiles));
		
		levels = new Array<Level>(levelFiles.size);
		Level tmp;
		String name;
		for(String file : levelFiles) {
			if(!file.endsWith(".xml")) {
				continue;
			}
			
			try {
				name = file.substring(0, file.indexOf("."));
				tmp = new Level(name);
			} catch (GdxRuntimeException e) {
				Gdx.app.error("Level.getLevels", "Unable to create level with file "+file+" :\n"+e.getMessage());
				continue;
			}
			
			levels.add(tmp);
		}
		
	}
	/**
	 * 
	 * @return static levels without forcing refresh
	 */
	public static Array<Level> getLevels() {
		return getLevels(false);
	}
	/**
	 * 
	 * @param refresh whether or not refresh levels from disk
	 * @return static levels or refreshed levels if refresh is true
	 */
	public static Array<Level> getLevels(boolean refresh) {
		if(refresh || levels == null) {
			refresh();
		}
		return levels;
	}
	/**
	 * Filter levels without refreshing from disk.
	 * @param type Only levels of this type will be returned
	 * @return levels with type equals to type
	 */
	public static Array<Level> getLevels(LevelType type) {
		return getLevels(type, false);
	}
	/**
	 * Filter levels.
	 * @param type Only levels of this type will be returned
	 * @param refresh Whether or not refresh levels from disk
	 * @return Levels with type equals to type
	 */
	public static Array<Level> getLevels(LevelType type, boolean refresh) {
		getLevels(refresh);
		Array<Level> filtered = new Array<Level>(levels.size);
		
		for(Level level : levels) {
			if(level.getType().getName().equals(type.getName())) {
				filtered.add(level);
			}
		}
		
		return filtered;
	}
}
