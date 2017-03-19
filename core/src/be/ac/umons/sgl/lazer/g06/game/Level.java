package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import be.ac.umons.sgl.lazer.g06.Files;
/**
 * 
 */
public class Level {
	static final String LEVEL_PATH = "levels";
	
	private static Array<Level> levels;
	
	String name;
	Map map;
	String difficulty;
	String type;
	
	public Level(String file) {
		String content = Files.getContent(file);
		if(content == null) {
			throw new GdxRuntimeException("File "+file+" does not exists.");
		}
		
		Gdx.app.debug("Level.Level", "creating level from file "+file);
		XmlReader reader = new XmlReader();
		Element level = reader.parse(content);
		setName(level.getAttribute("name", ""));
		setMap(level.getAttribute("map", ""));
		setDifficulty(level.getAttribute("difficulty", "medium"));
		setType(level.getAttribute("type", "standard"));
		Array<Element> blocks = level.getChildByName("blocks").getChildrenByName("block");
		for(Element block : blocks) {
			// TODO implement Block
			//map.addBlock(block);
		}
	}
	
	private void setName(String name) {
		if(name == null || name.isEmpty()) {
			throw new GdxRuntimeException("Name cannot be null or empty.");
		}
		this.name = name;
	}

	private void setMap(String mapFilename) {
		this.map = new Map(mapFilename);
	}

	private void setDifficulty(String difficulty) {
		difficulty = difficulty.toUpperCase();
		switch(difficulty) {
		case "EASY":
		case "MEDIUM":
		case "HARD":
			this.difficulty = difficulty;
			break;
		default:
			throw new GdxRuntimeException("Invalid difficulty "+difficulty+". Should be EASY, MEDIUM or HARD.");
		}
	}
	
	private void setType(String type) {
		type = type.toUpperCase();
		switch(type) {
		case "STANDARD":
		case "ADVANCED":
			this.type = type;
			break;
		default:
			throw new GdxRuntimeException("Invalid type "+type+". Should be STANDARD or ADVANCED.");
		}
	}
	
	public String getName() {
		return name;
	}
	
	public Map getMap() {
		return map;
	}
	
	public String getDifficulty() {
		return difficulty;
	}

	public String getType() {
		return type;
	}

	public enum Mode {
		ARCADE, TRAINING
	}
	
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
	
	public static Array<Level> getLevels() {
		return getLevels(false);
	}
	
	public static Array<Level> getLevels(boolean refresh) {
		if(refresh || levels == null) {
			refreshLevels();
		}
		return levels;
	}
	
	public static Array<Level> getLevels(String type) {
		return getLevels(type, false);
	}
	
	public static Array<Level> getLevels(String type, boolean refresh) {
		getLevels(refresh);
		Array<Level> filtered = new Array<Level>(levels.size);
		
		for(Level level : levels) {
			if(level.getType().equals(type)) {
				filtered.add(level);
			}
		}
		
		return filtered;
	}
}
