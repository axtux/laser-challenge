package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import be.ac.umons.sgl.lazer.g06.Files;
/**
 * 
 */
public class Level {
	Mode mode;
	String type;
	int number;
	
	String name;
	TiledMap map;
	TiledMapTileLayer ground;

	public Level(String file) {
		String content = Files.getContent(file);
		if(content == null) {
			throw new GdxRuntimeException("File "+file+" does not exists.");
		}
		
		Gdx.app.debug("Level.Level", "creating level from file "+file);
		XmlReader reader = new XmlReader();
		Element level = reader.parse(content);
		name = level.getAttribute("name");
		String map = level.getAttribute("map");
		loadMap(map);
		
		
	}
	
	public Level(Mode mode, String type, int number) {
		this.mode = mode;
		this.type = type;
		this.number = number;
	}
	
	private boolean loadMap(String mapName) {
		TmxMapLoader loader = new TmxMapLoader();
		// uses internal
		map = loader.load("maps/"+mapName+".tmx");
		ground = (TiledMapTileLayer)map.getLayers().get("ground");
		String keys = String.join("|", (Iterable<? extends CharSequence>) ground.getCell(2, 0).getTile().getProperties().getKeys());
		Gdx.app.debug("keys", keys);
		return true;
	}

	public String getMapProp(String prop) {
		return getMapProp(prop, "");
	}
	
	public String getMapProp(String prop, String defaultValue) {
		return map.getProperties().get(prop, defaultValue, String.class);
	}
	
	public String getCellProp(int x, int y, String prop) {
		return getCellProp(x, y, prop, "");
	}
	
	public String getCellProp(int x, int y, String prop, String defaultValue) {
		Cell c = ground.getCell(x, y);
		return c.getTile().getProperties().get(prop, defaultValue, String.class);
	}
	
	public TiledMap getMap() {
		return map;
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
