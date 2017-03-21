package be.ac.umons.sgl.lazer.g06.game;

import java.util.Observable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Map extends Observable {
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
		
		ground = (TiledMapTileLayer)map.getLayers().get("ground");
		if(ground == null) {
			throw new GdxRuntimeException("no ground layer found in map");
		}
		
		setSizes();
		
		blocks = new TiledMapTileLayer(mapWidth, mapHeight, tileSize, tileSize);
		
	}
	
	private void setSizes() {
		// extract size from map properties
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
		
		// check ground size
		if(mapWidth != ground.getWidth()) {
			throw new GdxRuntimeException("mapWidth "+mapWidth+" does not equal ground width "+ground.getWidth());
		}
		if(mapHeight != ground.getHeight()) {
			throw new GdxRuntimeException("mapHeight "+mapHeight+" does not equal ground height "+ground.getHeight());
		}
		if(tileSize != ground.getTileHeight() || tileSize != ground.getTileWidth()) {
			throw new GdxRuntimeException("ground tile size must equal map tile size");
		}
	}
	
	public int getWidth() {
		return mapWidth;
	}
	
	public int getHeight() {
		return mapHeight;
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public boolean setBlock(Block block, int x, int y) {
		if(x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) {
			return false;
		}
		
		blocks.setCell(x, y, block);
		this.notifyObservers();
		return true;
	}
	
	private Cell getVisibleCell(int x, int y) {
		Cell c = blocks.getCell(x, y);
		if(c != null) {
			return c;
		}
		return ground.getCell(x, y);
	}
	
	public TextureRegion getVisibleTextureRegion(int x, int y) {
		return getVisibleCell(x, y).getTile().getTextureRegion();
	}
	
	public int getVisibleRotation(int x, int y) {
		Cell c = getVisibleCell(x, y);
		switch(c.getRotation()) {
		case Cell.ROTATE_0:
			return 0;
		case Cell.ROTATE_90:
			return 90;
		case Cell.ROTATE_180:
			return 180;
		case Cell.ROTATE_270:
			return 270;
		default:
			Gdx.app.error("Map.getVisibleOrientation", "Orientation not within static defined");
			return c.getRotation();
		}
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
