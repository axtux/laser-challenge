package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import be.ac.umons.sgl.lazer.g06.Files;
import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;
/**
 * Class that manages the game part of the LazerChallenge
 */
public class Level {
	static final String LEVEL_PATH = "levels";
	static final Difficulty defaultDifficulty = Difficulty.MEDIUM;
	static final Type defaultType = Type.STANDARD;
	
	private static Array<Level> levels;
	
	LazerChallenge game;
	String name;
	Map map;
	Difficulty difficulty;
	Type type;
	
	/**
	 * Create level from file
	 * @param XML filename from which to load level
	 * @throws GdxRuntimeException if file does not exists in levels directory,
	 * if XML format is not valid (description into level_desc.txt),
	 * if map TMX file referenced into XML is not fount or is not valid,
	 * if any asset referenced into TMX file cannot be loaded.
	 */
	public Level(String file) {
		String content = Files.getContent(file);
		if(content == null) {
			throw new GdxRuntimeException("File "+file+" does not exists.");
		}
		
		game = LazerChallenge.getInstance();
		
		Gdx.app.debug("Level.Level", "creating level from file "+file);
		XmlReader reader = new XmlReader();
		Element level = reader.parse(content);
		setName(level.getAttribute("name", ""));
		setMap(level.getAttribute("map", ""));
		setDifficulty(level.getAttribute("difficulty", ""));
		setType(level.getAttribute("type", ""));
		Array<Element> blocks = level.getChildByName("blocks").getChildrenByName("block");
		for(Element block : blocks) {
			// TODO implement Block
			//map.addBlock(block);
		}
	}
	/**
	 * Set level name
	 * @param name Name of the level
	 */
	private void setName(String name) {
		if(name == null || name.isEmpty()) {
			throw new GdxRuntimeException("Name cannot be null or empty.");
		}
		this.name = name;
	}
	/**
	 * Set game map
	 * @param mapFilename TMX filename (without extension) which has to be located into maps directory.
	 */
	private void setMap(String mapFilename) {
		this.map = new Map(mapFilename);
	}
	/**
	 * Set difficulty. defaultDifficulty is used if argument is not valid
	 * @param difficulty String representation of difficulty, case insensitive
	 */
	private void setDifficulty(String difficulty) {
		try {
			this.difficulty = Difficulty.valueOf(difficulty.toUpperCase());
		} catch (IllegalArgumentException e) {
			this.difficulty = defaultDifficulty;
		}
	}
	/**
	 * Set type. defaultType is used if argument is not valid
	 * @param type String representation of type, case insensitive
	 */
	private void setType(String type) {
		try {
			this.type = Type.valueOf(type.toUpperCase());
		} catch (IllegalArgumentException e) {
			this.type = defaultType;
		}
	}
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return map
	 */
	public Map getMap() {
		return map;
	}
	/**
	 * @return difficulty
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}
	/**
	 * @return type
	 */
	public Type getType() {
		return type;
	}
	/**
	 * Refresh levels from disk. Levels directory is scanned and all valid levels are loaded into static array.
	 */
	public static void refreshLevels() {
		Array<String> levelFiles = Files.listFiles(LEVEL_PATH);
		if(levelFiles == null) {
			throw new GdxRuntimeException("Got null while listing directory "+LEVEL_PATH);
		}
		levelFiles.sort();
		Gdx.app.debug("Level.getLevels files", String.join("|", levelFiles));
		
		levels = new Array<Level>(levelFiles.size);
		Level tmp;
		for(String file : levelFiles) {
			if(!file.endsWith(".xml")) {
				continue;
			}
			
			try {
				tmp = new Level(LEVEL_PATH+"/"+file);
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
	 * @return static levels or refreshed levels if @refresh is true
	 */
	public static Array<Level> getLevels(boolean refresh) {
		if(refresh || levels == null) {
			refreshLevels();
		}
		return levels;
	}
	/**
	 * 
	 * @param type Only levels of this type will be returned
	 * @return levels with type equals to @type
	 */
	public static Array<Level> getLevels(Type type) {
		return getLevels(type, false);
	}
	/**
	 * 
	 * @param type Only levels of this type will be returned
	 * @param refresh Whether or not refresh levels from disk
	 * @return Levels with type equals to @type
	 */
	public static Array<Level> getLevels(Type type, boolean refresh) {
		getLevels(refresh);
		Array<Level> filtered = new Array<Level>(levels.size);
		
		for(Level level : levels) {
			if(level.getType().equals(type)) {
				filtered.add(level);
			}
		}
		
		return filtered;
	}
	/**
	 * Mode of game : 
	 * ARCADE mode has time limit, score and one-time laser while
	 * TRAINING mode has no time limit, no score and continuous laser.
	 */
	public enum Mode {
		ARCADE, TRAINING;
		public boolean hasScore() {
			return this.equals(ARCADE);
		}
	}
	/**
	 * Difficulty of the game, information for the user from XML file.
	 */
	public enum Difficulty {
		EASY, MEDIUM, HARD;
	}
	/**
	 * Type of game :
	 * STANDARD has 4 directions and limited blocks while
	 * ADVANCED has 8 directions, laser intensity and some more blocks.
	 */
	public enum Type {
		STANDARD, ADVANCED;
	}
}
