package be.ac.umons.sgl.lazer.g06.game;

import java.util.Observable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.game.Position.Location;
import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

public class Map extends Observable {
	static final String MAP_PATH = "maps";
	public static final int GROUND_LAYER = 0;
	public static final int BLOCKS_LAYER = 1;
	
	private TiledMap map;
	int mapWidth, mapHeight, tileSize;
	Location loc;
	/**
	 * Create Map with references from an other Map instance
	 * Usually used for inventory
	 * @param reference
	 */
	public Map(int width, int minSize, int tileSize, Location loc) {
		this.loc = loc;
		
		this.mapWidth = width;
		int height = minSize/width;
		this.mapHeight = (minSize == width*height ? height : height+1);
		this.tileSize = tileSize;
		
		map = new TiledMap();
		map.getProperties().put("width", mapWidth);
		map.getProperties().put("height", mapHeight);
		map.getProperties().put("tilewidth", tileSize);
		map.getProperties().put("tileheight", tileSize);
		
		addLayer("ground");
		fillLayer(GROUND_LAYER, Color.GRAY);
		addLayer("blocks");
	}
	
	public Map(String tmx_map_name, Location loc) {
		this.loc = loc;
		
		Gdx.app.debug("Map.Map", "creating map from file "+tmx_map_name+".tmx");
		// load map from local files
		TmxMapLoader loader = new TmxMapLoader(new LocalFileHandleResolver());
		
		map = loader.load(MAP_PATH+"/"+tmx_map_name+".tmx");
		
		if(map.getLayers().getCount() != 1) {
			throw new GdxRuntimeException("map must contain exactly one layer");
		}
		
		if(!getLayer(0).getName().equals("ground")) {
			throw new GdxRuntimeException("no ground layer found in map");
		}
		
		setSizes();
		addLayer("blocks");
	}
	
	private void addLayer(String name) {
		TiledMapTileLayer layer = new TiledMapTileLayer(mapWidth, mapHeight, tileSize, tileSize);
		layer.setName(name);
		map.getLayers().add(layer);
	}
	
	private void fillLayer(int layer, Color c) {
		Texture tex = LazerChallenge.getInstance().getSkin().getTexture(c, tileSize);
		TiledMapTile tile = new StaticTiledMapTile(new TextureRegion(tex));
		Cell cell = new Cell();
		cell.setTile(tile);
		
		TiledMapTileLayer tmtl = getLayer(layer);
		for(int x = 0; x < mapWidth; ++x) {
			for(int y = 0; y < mapHeight; ++y) {
				tmtl.setCell(x, y, cell);
			}
		}
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
	
	public Location getLocation() {
		return loc;
	}
	
	private boolean setCell(Cell cell, int layer, Position p) {
		TiledMapTileLayer tmtl = getLayer(layer);
		if(tmtl == null) {
			return false;
		}
		
		int x = p.getX();
		int y = p.getY();
		
		if(x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) {
			return false;
		}
		
		tmtl.setCell(x, y, cell);
		this.notifyObservers();
		return true;
	}
	
	public boolean setBlock(Block block, Position pos) {
		return setCell(block, BLOCKS_LAYER, pos);
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
