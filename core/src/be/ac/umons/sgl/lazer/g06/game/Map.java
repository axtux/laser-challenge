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
import be.ac.umons.sgl.lazer.g06.game.orientations.Orientation;

public class Map extends Observable {
	private static final String MAP_PATH = "maps";
	private static final int GROUND_LAYER = 0;
	private static final int BLOCKS_LAYER = 1;
	private static final int LASERS_LAYER = 2;
	
	private TiledMap map;
	private int mapWidth, mapHeight, tileSize;
	private Location loc;
	/**
	 * Create Map with references from an other Map instance
	 * Usually used for inventory
	 * @param width
	 * @param minSize
	 * @param tileSize
	 * @param loc
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
		fillLayer(GROUND_LAYER, getColorCell(Color.WHITE));
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
		addLayer("lasers");
		fillLasersLayer();
	}
	/**
	 * add layer
	 * @param name of the layer
	 */
	private void addLayer(String name) {
		TiledMapTileLayer layer = new TiledMapTileLayer(mapWidth, mapHeight, tileSize, tileSize);
		layer.setName(name);
		map.getLayers().add(layer);
	}
	
	private Cell getColorCell(Color c) {
		Texture tex = LazerChallenge.getInstance().getSkin().getTexture(c, tileSize);
		TiledMapTile tile = new StaticTiledMapTile(new TextureRegion(tex));
		Cell cell = new Cell();
		cell.setTile(tile);
		return cell;
	}
	private void fillLayer(int layer, Cell cell) {
		TiledMapTileLayer tmtl = getLayer(layer);
		for(int x = 0; x < mapWidth; ++x) {
			for(int y = 0; y < mapHeight; ++y) {
				tmtl.setCell(x, y, cell);
			}
		}
	}
	private void fillLasersLayer() {
		TiledMapTileLayer tmtl = getLayer(LASERS_LAYER);
		for(int x = 0; x < mapWidth; ++x) {
			for(int y = 0; y < mapHeight; ++y) {
				tmtl.setCell(x, y, new Lasers());
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
	/**
	 * @return the width
	 */
	public int getWidth() {
		return mapWidth;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return mapHeight;
	}
	/**
	 * @return the size of tile
	 */
	public int getTileSize() {
		return tileSize;
	}
	/** 
	 * @return the location
	 */
	public Location getLocation() {
		return loc;
	}
	
	/**
	 * Check if the position is in the map
	 * @param position
	 * @return result
	 */
	public boolean inMap(Position position) {
		if(position == null) {
			return false;
		}
		
		if(position.getX() < 0 || position.getX() >= mapWidth) {
			return false;
		}
		if(position.getY() < 0 || position.getY() >= mapHeight) {
			return false;
		}
		
		return true;
	}
	/**
	 * Try to place a cell in map
	 * @param layer
	 * @param cell
	 * @param p the position where the cell will be placed
	 * @return false if the layer does not exist, or the position is not in map. Otherwise return true
	 */
	private boolean setCell(int layer, Cell cell, Position p) {
		TiledMapTileLayer tmtl = getLayer(layer);
		if(tmtl == null) {
			return false;
		}
		
		if(!inMap(p)) {
			return false;
		}
		
		tmtl.setCell(p.getX(), p.getY(), cell);
		
		this.changed();
		return true;
	}
	/**
	 * Try to place a block 
	 * @param block
	 * @param pos
	 * @return result
	 */
	public boolean setBlock(Block block, Position pos) {
		return setCell(BLOCKS_LAYER, block, pos);
	}
	/**
	 * 
	 * @param position
	 * @param fromOrientation
	 * @return
	 */
	public boolean addInputLaser(Position position, Orientation fromOrientation) {
		Lasers lasers = getLasers(position);
		if(lasers == null) {
			return false;
		}
		
		lasers.addInput(fromOrientation);
		return true;
	}
	public boolean addOutputLaser(Position position, Orientation toOrientation) {
		Lasers lasers = getLasers(position);
		if(lasers == null) {
			return false;
		}
		
		lasers.addOutput(toOrientation);
		return true;
	}
	
	private TiledMapTileLayer getLayer(int i) {
		if(i < 0 || i >= map.getLayers().getCount()) {
			return null;
		}
		
		return (TiledMapTileLayer) map.getLayers().get(i);
	}
	
	private Cell getCell(int layer, Position p) {
		TiledMapTileLayer tmtl = getLayer(layer);
		if(tmtl == null) {
			return null;
		}
		
		if(!inMap(p)) {
			return null;
		}
		
		return tmtl.getCell(p.getX(), p.getY());
	}
	/**
	 * Get the cell 
	 * @param pos the position of the cell
	 * @return the cell
	 */
	public Cell getGround(Position pos) {
		return getCell(GROUND_LAYER, pos);
	}
	
	public String getGroundProp(Position pos, String name) {
		Cell cell = getCell(GROUND_LAYER, pos);
		if(cell == null) {
			return null;
		}
		
		MapProperties props = cell.getTile().getProperties();
		if(!props.containsKey(name)) {
			return null;
		}
		
		return props.get(name).toString();
	}
	
	public boolean getGroundBoolProp(Position pos, String name) {
		String value = getGroundProp(pos, name);
		if(value == null) {
			return false;
		}
		
		value = value.toLowerCase();
		if(value.equals("true") || value.equals("1")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param pos in the layer
	 * @return the block at this position
	 */
	public Block getBlock(Position pos) {
		return (Block) getCell(BLOCKS_LAYER, pos);
	}
	/**
	 * @param pos the position in the layer
	 * @return the laser at this position
	 */
	public Lasers getLasers(Position pos) {
		return (Lasers) getCell(LASERS_LAYER, pos);
	}
	
	public boolean hasRequiredInputs() {
		Block block;
		Position pos;
		
		for(int x = 0; x < mapWidth; ++x) {
			for(int y = 0; y < mapHeight; ++y) {
				pos = new Position(x, y);
				block = this.getBlock(pos);
				if(block != null && !block.hasRequiredInputs()) {
					return false;
				}
			}
		}
		
		return true;
	}
	/**
	 * Launch laser on map
	 */
	public void startLasers() {
		// clear previous lasers
		fillLasersLayer();
		
		Position pos;
		for(int x = 0; x < mapWidth; ++x) {
			for(int y = 0; y < mapHeight; ++y) {
				pos = new Position(x, y);
				laserInput(pos, null);
			}
		}
		
		changed();
	}
	
	private void laserInput(Position position, Orientation inputFrom) {
		if(!inMap(position)) {
			return;
		}
		
		Orientation inputTo = null;
		if(inputFrom != null) {
			// display input on map
			addInputLaser(position, inputFrom);
			inputTo = inputFrom.reverse();
		}
		
		Block block = getBlock(position);
		if(block == null) {
			// empty case, no block here
			if(inputFrom == null) {
				return;
			}
			
			// don't accept crossing lasers
			if(getLasers(position).getInputs().size > 1) {
				return;
			}
			
			// continue with same inputFrom
			addOutputLaser(position, inputTo);
			laserInput(inputTo.nextPosition(position), inputFrom);
			return;
		}
		
		Array<Orientation> outputs = block.input(inputFrom);
		for(Orientation output : outputs) {
			addOutputLaser(position, output);
			laserInput(output.nextPosition(position), output.reverse());
		}
	}
	
	private void changed() {
		this.setChanged();
		this.notifyObservers();
	}
	
}
