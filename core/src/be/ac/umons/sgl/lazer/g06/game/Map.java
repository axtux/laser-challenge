package be.ac.umons.sgl.lazer.g06.game;

import java.util.Observable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Map extends Observable {
	static final String MAP_PATH = "maps";
	public static final int GROUND_LAYER = 0;
	public static final int BLOCKS_LAYER = 1;
	
	private TiledMap map;
	int mapWidth, mapHeight, tileSize;
	
	public Map(String name) {
		Gdx.app.debug("Map.Map", "creating map from file "+name+".tmx");
		// load map from local files
		TmxMapLoader loader = new TmxMapLoader(new LocalFileHandleResolver());
		
		map = loader.load(MAP_PATH+"/"+name+".tmx");
		
		if(map.getLayers().getCount() != 1) {
			throw new GdxRuntimeException("map must contain exactly one layer");
		}
		
		if(!getLayer(0).getName().equals("ground")) {
			throw new GdxRuntimeException("no ground layer found in map");
		}
		
		setSizes();
		
		TiledMapTileLayer layer = new TiledMapTileLayer(mapWidth, mapHeight, tileSize, tileSize);
		layer.setName("blocks");
		map.getLayers().add(layer);
		
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
		TiledMapTileLayer ground = getLayer(GROUND_LAYER);
		
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
		
		getLayer(BLOCKS_LAYER).setCell(x, y, block);
		this.notifyObservers();
		return true;
	}
	
	public TiledMapTileLayer getLayer(int i) {
		if(i < 0 || i >= map.getLayers().getCount()) {
			return null;
		}
		
		return (TiledMapTileLayer) map.getLayers().get(i);
	}
	
	public Cell getCell(int layer, Position p) {
		TiledMapTileLayer tmtl = getLayer(layer);
		if(tmtl == null) {
			return null;
		}
		
		int x = p.getX();
		int y = p.getY();
		
		if(x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) {
			return null;
		}
		
		return tmtl.getCell(x, y);
	}
	
	public Array<Cell> getCells(Position pos) {
		int size = map.getLayers().getCount();
		Array<Cell> cells = new Array<Cell>(size);
		
		Cell tmp;
		for(int i = 0; i < size; ++i) {
			tmp = getCell(i, pos);
			if(tmp != null) {
				cells.add(tmp);
			}
		}
		
		return cells;
	}
	
	public Cell getGround(Position pos) {
		return getCell(GROUND_LAYER, pos);
	}
	
	public Block getBlock(Position pos) {
		return (Block) getCell(BLOCKS_LAYER, pos);
	}
	
	public boolean rotate(Position pos) {
		Cell c = getBlock(pos);
		if(c == null) {
			return false;
		}
		// TODO change this to real rotation
		c.setRotation(c.getRotation()+90);
		return true;
	}
	
	public TiledMap getTiledMap() {
		return map;
	}
	
}
