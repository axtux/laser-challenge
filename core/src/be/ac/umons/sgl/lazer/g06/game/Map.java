package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Map {
	static final String MAP_PATH = "maps";
	private TiledMap map;
	TiledMapTileLayer ground;
	TiledMapTileLayer blocks;
	
	int mapWidth, mapHeight, tileSize;
	
	public Map(String name) {
		Gdx.app.debug("Map.Map", "creating map from file "+name+".tmx");
		// load map from local files
		TmxMapLoader loader = new TmxMapLoader(new LocalFileHandleResolver());
		
		map = loader.load(MAP_PATH+"/"+name+".tmx");
		
		setSizes();
		
		ground = (TiledMapTileLayer)map.getLayers().get("ground");
		if(ground == null) {
			throw new GdxRuntimeException("no ground layer found in map");
		}
		blocks = new TiledMapTileLayer(ground.getWidth(), ground.getHeight(), (int) ground.getTileWidth(), (int) ground.getTileHeight());
		
	}
	
	private void setSizes() {
		MapProperties props = map.getProperties();
		
		mapWidth = props.get("width", -1, int.class);
		mapHeight = props.get("height", -1, int.class);
		if(mapWidth < 1 || mapHeight < 1) {
			throw new GdxRuntimeException("Map width or map height is < 1 or missing.");
		}
		
		int tileWidth = props.get("tilewidth", -1, int.class);
		int tileHeight = props.get("tileheight", -1, int.class);
		if(tileWidth < 1 || tileHeight < 1) {
			throw new GdxRuntimeException("Tile width or tile height is < 1 or missing.");
		}
		
		if(tileWidth != tileHeight) {
			throw new GdxRuntimeException("Tile width does not match tile height.");
		}
		
		tileSize = tileWidth;
	}
	
	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public int getTileSize() {
		return tileSize;
	}
	
	public TiledMap getTiledMap() {
		return map;
	}
	
	public String getProp(String prop) {
		return getProp(prop, "");
	}

	public String getProp(String prop, String defaultValue) {
		return map.getProperties().get(prop, defaultValue, String.class);
	}
	
	public int getIntProp(String prop) {
		return getIntProp(prop, -1);
	}
	
	public int getIntProp(String prop, int defaultValue) {
		return map.getProperties().get(prop, defaultValue, int.class);
	}
	
	public String getCellProp(int x, int y, String prop) {
		return getCellProp(x, y, prop, "");
	}
	
	public String getCellProp(int x, int y, String prop, String defaultValue) {
		Cell c = ground.getCell(x, y);
		return c.getTile().getProperties().get(prop, defaultValue, String.class);
	}
}
