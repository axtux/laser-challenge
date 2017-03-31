package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.OrderedMap;

import be.ac.umons.sgl.lazer.g06.Files;

public class LevelTypes {
	static final String LEVEL_TYPES_PATH = "level_types";
	/**
	 * Keep all instances of LevelType to avoid too many disk access.
	 */
	private static OrderedMap<String, LevelType> levelTypes;
	/**
	 * Refresh levelTypes from disk. LevelType directory is scanned and all valid levels are loaded into static array.
	 */
	private static void refresh() {
		Array<String> names = Files.listDirs(LEVEL_TYPES_PATH);
		if(names == null) {
			throw new GdxRuntimeException("Got null while listing directory "+LEVEL_TYPES_PATH);
		}
		Gdx.app.debug("LevelType.refresh", "Found types "+String.join("|", names));
		
		// set max size to avoid useless copies when increasing size
		levelTypes = new OrderedMap<String, LevelType>();
		LevelType lt;
		
		for(String name : names) {
			try {
				lt = new LevelType(name);
			} catch (GdxRuntimeException e) {
				Gdx.app.error("LevelType.refresh", "Unable to create LevelType "+name+" : "+e.getMessage());
				continue;
			}
			levelTypes.put(name, lt);
		}
		
	}
	/**
	 * Get level types from disk.
	 * @return Level types.
	 */
	public static Array<String> getLevelTypes() {
		return getLevelTypes(false);
	}
	/**
	 * Get level types from disk.
	 * @return Level types.
	 */
	public static Array<String> getLevelTypes(boolean refresh) {
		if(refresh || levelTypes == null) {
			refresh();
		}
		return levelTypes.orderedKeys();
	}
	/**
	 * Get level types from disk.
	 * @return Level types.
	 */
	public static LevelType getLevelType(String name) {
		return getLevelType(name, false);
	}
	/**
	 * Get level types from disk.
	 * @return Level types.
	 */
	public static LevelType getLevelType(String name, boolean refresh) {
		getLevelTypes(refresh);
		if(name == null || name.isEmpty()) {
			name = LevelType.defaultType;
		}
		return levelTypes.get(name);
	}
}
