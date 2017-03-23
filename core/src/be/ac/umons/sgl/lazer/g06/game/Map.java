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
		map.getLayers().add(blocks);
		
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
	
	public Block getBlock(Position pos) {
		int x = pos.getX();
		int y = pos.getY();
		
		if(x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) {
			return null;
		}
		
		return (Block) blocks.getCell(x, y);
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
	
	public Array<Cell> getCells(int x, int y) {
		int size = map.getLayers().getCount();
		
		Array<Cell> cells = new Array<Cell>(size);
		Cell tmp;
		TiledMapTileLayer tmtl;
		
		for(int i = 0; i < size; ++i) {
			tmtl = (TiledMapTileLayer) map.getLayers().get(i);
			tmp = tmtl.getCell(x, y);
			if(tmp != null) {
				cells.add(tmp);
			}
		}
		
		return cells;
	}
	
	public TiledMap getTiledMap() {
		return map;
	}
	
}
