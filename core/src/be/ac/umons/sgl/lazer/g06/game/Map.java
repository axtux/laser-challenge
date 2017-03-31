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
/**
 * Map containing a TiledMap with multiple layers and special methods to be used from LaserChallenge game.
 * The first layer is used for the ground, the second layer for {@link Block}s and third layer for {@link Lasers}s.
 */
public class Map extends Observable {
	private static final String MAP_PATH = "maps";
	private static final int GROUND_LAYER = 0;
	private static final int BLOCKS_LAYER = 1;
	private static final int LASERS_LAYER = 2;
	
	private TiledMap map;
	private int mapWidth, mapHeight, tileSize;
	private Location loc;
	/**
	 * Create Map with fixed width, minimum size, tile size and a location. Used to create LaserChallenge inventory.
	 * @param width Fixed map width.
	 * @param minSize Minimum size. Height will be adapted to get at least minSize cells available per layer.
	 * @param tileSize Size of the square tile in pixels.
	 * @param loc Location of this map.
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
	/**
	 * Create map loading a TiledMap from tmx_map_name with location loc. Used for LaserChallenge playing map.
	 * @param tmx_map_name Map file located in maps directory, without extension.
	 * @param loc Location of this map.
	 */
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
	 * Add layer to the tiled map with the name name.
	 * @param name Name of the newly created layer.
	 */
	private void addLayer(String name) {
		TiledMapTileLayer layer = new TiledMapTileLayer(mapWidth, mapHeight, tileSize, tileSize);
		layer.setName(name);
		map.getLayers().add(layer);
	}
	/**
	 * @param c Color used to fill cell background.
	 * @return Cell with TextureRegion matching color c.
	 */
	private Cell getColorCell(Color c) {
		Texture tex = LazerChallenge.getInstance().getSkin().getTexture(c, tileSize);
		TiledMapTile tile = new StaticTiledMapTile(new TextureRegion(tex));
		Cell cell = new Cell();
		cell.setTile(tile);
		return cell;
	}
	/**
	 * Make all cells of the layer to be cell.
	 * @param layer Layer to change cells from.
	 * @param cell The cell to place on the layer.
	 */
	private void fillLayer(int layer, Cell cell) {
		TiledMapTileLayer tmtl = getLayer(layer);
		for(int x = 0; x < mapWidth; ++x) {
			for(int y = 0; y < mapHeight; ++y) {
				tmtl.setCell(x, y, cell);
			}
		}
	}
	/**
	 * Fill laser layer with Lasers cells.
	 */
	private void fillLasersLayer() {
		TiledMapTileLayer tmtl = getLayer(LASERS_LAYER);
		for(int x = 0; x < mapWidth; ++x) {
			for(int y = 0; y < mapHeight; ++y) {
				tmtl.setCell(x, y, new Lasers());
			}
		}
	}
	/**
	 * Check that map sizes match layer sizes and check that tile is squared.
	 */
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
	 * @return Map width.
	 */
	public int getWidth() {
		return mapWidth;
	}
	/**
	 * @return Map height.
	 */
	public int getHeight() {
		return mapHeight;
	}
	/**
	 * @return Tile size in pixels.
	 */
	public int getTileSize() {
		return tileSize;
	}
	/** 
	 * @return Location of this map.
	 */
	public Location getLocation() {
		return loc;
	}
	
	/**
	 * Check if the position is in the map
	 * @param position Position to check.
	 * @return True if position is in the map, false otherwise.
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
	 * Set a cell at position p on layer layer.
	 * @param layer Layer on which to set the cell.
	 * @param cell The cell to set.
	 * @param p The position where the cell will be placed.
	 * @return True on success, false on error.
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
	 * Set a block at position pos on blocks layer.
	 * @param block The block to set.
	 * @param pos The position where to set the block.
	 * @return True on success, false on error.
	 */
	public boolean setBlock(Block block, Position pos) {
		return setCell(BLOCKS_LAYER, block, pos);
	}
	/**
	 * Add input laser.
	 * @param position Position on which to add the laser.
	 * @param fromOrientation Orientation from which the laser is coming from.
	 * @return True on success, false on error.
	 */
	public boolean addInputLaser(Position position, Orientation fromOrientation) {
		Lasers lasers = getLasers(position);
		if(lasers == null) {
			return false;
		}
		
		lasers.addInput(fromOrientation);
		return true;
	}
	/**
	 * Add output laser.
	 * @param position Position on which to add the laser.
	 * @param toOrientation Orientation to which the laser is going.
	 * @return True on success, false on error.
	 */
	public boolean addOutputLaser(Position position, Orientation toOrientation) {
		Lasers lasers = getLasers(position);
		if(lasers == null) {
			return false;
		}
		
		lasers.addOutput(toOrientation);
		return true;
	}
	/**
	 * @param i Layer number.
	 * @return The layer associated with the number i or null if this layer does not exists.
	 */
	private TiledMapTileLayer getLayer(int i) {
		if(i < 0 || i >= map.getLayers().getCount()) {
			return null;
		}
		
		return (TiledMapTileLayer) map.getLayers().get(i);
	}
	/**
	 * @param layer The layer on which to get the cell.
	 * @param p The position of the cell
	 * @return The cell at position p on layer layer or null on error.
	 */
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
	 * Get cell on ground layer.
	 * @param pos The position of the cell
	 * @return Cell representing ground of position pos or null on error.
	 */
	public Cell getGround(Position pos) {
		return getCell(GROUND_LAYER, pos);
	}
	/**
	 * @param pos Position at which to get property.
	 * @param name Name if the property.
	 * @return String property from ground layer cell.
	 */
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
	/**
	 * 
	 * @param pos Position at which to get property.
	 * @param name Name if the property.
	 * @return True if property exists and has value of 1 or true, false otherwise.
	 */
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
	 * Get cell on blocks layer and cast it into Block object.
	 * @param pos Position of the block.
	 * @return The block at position pos or null on error.
	 */
	public Block getBlock(Position pos) {
		return (Block) getCell(BLOCKS_LAYER, pos);
	}
	/**
	 * @param pos the position in the layer
	 * @return the laser at this position
	 */
	/**
	 * Get cell on lasers layer and cast it into Lasers object.
	 * @param pos Position of the lasers.
	 * @return The lasers at position pos or null on error.
	 */
	public Lasers getLasers(Position pos) {
		return (Lasers) getCell(LASERS_LAYER, pos);
	}
	/**
	 * @return Whether or not all blocks on this map have required inputs.
	 */
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
	 * Launch lasers on map
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
	/**
	 * Simulate laser input on a position.
	 * @param position Position on which the laser is coming.
	 * @param inputFrom Orientation from which the laser is coming.
	 */
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
	/**
	 * Notify observers because this object has been changed.
	 */
	private void changed() {
		this.setChanged();
		this.notifyObservers();
	}
	
}
