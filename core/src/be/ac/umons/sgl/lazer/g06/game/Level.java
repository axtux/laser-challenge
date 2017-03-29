package be.ac.umons.sgl.lazer.g06.game;

import java.util.Observable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import be.ac.umons.sgl.lazer.g06.Files;
import be.ac.umons.sgl.lazer.g06.game.Position.Location;
/**
 * Class that manages the game part of the LazerChallenge
 * Notify observers about selected level, time and score updates
 */
public class Level extends Observable {
	static final String LEVELS_PATH = "levels";
	static final Difficulty defaultDifficulty = Difficulty.MEDIUM;
	static final String defaultOrientation = "UP";
	/**
	 * Keep all instances of Level to avoid too many disk access.
	 */
	private static Array<Level> levels;
	
	String xml;
	LazerChallenge game;
	String name;
	Map map;
	Map inventory;
	Difficulty difficulty;
	LevelType type;
	int time;
	int elapsedTime;
	// state for interaction, maybe they should go elsewhere
	Position selected;
	boolean moving;
	Array<Switch> history;
	
	/**
	 * Create level from file
	 * @param file XML filename from which to load level
	 * @throws GdxRuntimeException if file does not exists in levels directory,
	 * if XML format is not valid (description into level_desc.txt),
	 * if map TMX file referenced into XML is not fount or is not valid,
	 * if any asset referenced into TMX file cannot be loaded.
	 */
	public Level(String file) {
		xml = Files.getContent(file);
		if(xml == null) {
			throw new GdxRuntimeException("File "+file+" does not exists.");
		}
		
		game = LazerChallenge.getInstance();
		Gdx.app.debug("Level.Level", "creating level from file "+file);
		reset();
	}
	
	public void reset() {
		stop();
		
		XmlReader reader = new XmlReader();
		Element level = reader.parse(xml);
		setName(level.getAttribute("name", ""));
		setDifficulty(level.getAttribute("difficulty", ""));
		setType(level.getAttribute("type", ""));
		setTime(level.getIntAttribute("time", 0));
		
		setMap(level.getAttribute("map", ""));
		setBlocks(level.getChildByName("blocks"));
		history= new Array<Switch>();
		// changed during game
		moving  = false;
		selected = null;
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
		try {
			this.map = new Map(mapFilename, Location.MAP);
		} catch (SerializationException e) {
			throw new GdxRuntimeException("Unable to load map "+mapFilename+" : "+e.getMessage());
		}
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
	 * Set type
	 * @param type String representation of type, case sensitive
	 */
	private void setType(String type) {
		this.type = LevelType.getLevelType(type);
		if(this.type == null) {
			throw new GdxRuntimeException("Type "+type+" not found");
		}
	}
	/**
	 * Set maximum time a user has to finish this level in arcade mode.
	 * @param time Time in seconds.
	 */
	private void setTime(int time) {
		if(time < 2) {
			throw new GdxRuntimeException("Time should be > 1");
		}
		this.time = time;
	}
	private void setBlocks(Element blocksElement) {
		Array<Element> blockElements = blocksElement.getChildrenByName("block");
		
		String blockType;
		Block block;
		Element positionElement;
		String orientationStr;
		Orientation orientation;
		Position pos;
		Array<Block> invBlocks = new Array<Block>(blockElements.size);
		for(Element blockElement : blockElements) {
			blockType = blockElement.getAttribute("type", "");
			
			orientationStr = blockElement.getAttribute("orientation", defaultOrientation);
			orientation = Orientation.valueOf(orientationStr);
			if(orientation == null) {
				throw new GdxRuntimeException("No orientation "+orientationStr);
			}
			
			block = new Block(type.getBlockType(blockType), orientation);
			
			if(blockElement.getAttribute("fixedposition", "false").toLowerCase().equals("true")) {
				block.getTile().getProperties().put("fixedposition", true);
			}
			if(blockElement.getAttribute("fixedorientation", "false").toLowerCase().equals("true")) {
				block.getTile().getProperties().put("fixedorientation", true);
			}
			
			positionElement = blockElement.getChildByName("position");
			if(positionElement == null) {
				//Gdx.app.debug("Level.setBLocks", "Block "+blockType+" goes to inventory");
				invBlocks.add(block);
				continue;
			}
			
			int x = positionElement.getIntAttribute("x", -1);
			int y = positionElement.getIntAttribute("y", -1);
			//Gdx.app.debug("Level.setBLocks", "Block "+blockType+" goes to "+Integer.toString(x)+"x"+Integer.toString(y));
			// if x or y out of map, block goes to inventory
			pos = new Position(x, y);
			if(!map.setBlock(block, pos)) {
				Gdx.app.error("Level.setBlocks", "level "+name+" : block "+blockType+" out of map");
				invBlocks.add(block);
			}
		}
		
		addInventoryBlocks(invBlocks);
	}
	/**
	 * Set state to changed and notify observers
	 */
	public void changed() {
		// continuous laser
		if(game.getMode() != null && !game.getMode().hasScore()) {
			startLaser();
		}
		// notify observers
		this.setChanged();
		this.notifyObservers();
	}
	
	private void addInventoryBlocks(Array<Block> blocks) {
		inventory = new Map(5, blocks.size, map.getTileSize(), Location.INVENTORY);
		
		Position pos;
		int w = inventory.getWidth();
		
		for(int i = 0; i < blocks.size; ++i) {
			pos = new Position(i%w, i/w);
			inventory.setBlock(blocks.get(i), pos);
		}
	}
	
	public Block getBlock(Position pos) {
		if(pos == null) {
			Gdx.app.error("Level.getBlock", "pos cannot be null");
			return null;
		}
		
		if(pos.getLocation() == null) {
			Gdx.app.error("Level.getBlock", "location cannot be null");
			return null;
		}
		
		switch(pos.getLocation()) {
		case MAP:
			return map.getBlock(pos);
		case INVENTORY:
			return inventory.getBlock(pos);
		default:
			return null;
		}
	}
	
	private boolean setBlock(Block block, Position pos) {
		if(pos.getLocation() == null) {
			Gdx.app.error("Level.setBlock", "location cannot be null");
			return false;
		}
		switch(pos.getLocation()) {
		case MAP:
			return map.setBlock(block, pos);
		case INVENTORY:
			return inventory.setBlock(block, pos);
		default:
			return false;
		}
	}
	
	private void clearLaser() {
		map.clearLasers();
		Position pos;
		
		for(int x = 0; x < map.getWidth(); ++x) {
			for(int y = 0; y < map.getHeight(); ++y) {
				pos = new Position(x, y, Position.Location.MAP);
				clearLaser(pos);
			}
		}
	}
	
	private void clearLaser(Position position) {
		Block block = map.getBlock(position);
		if(block == null) {
			return;
		}
		
		block.clearInput();
	}
	
	public void startLaser() {
		clearLaser();
		
		Position pos;
		for(int x = 0; x < map.getWidth(); ++x) {
			for(int y = 0; y < map.getHeight(); ++y) {
				pos = new Position(x, y, Position.Location.MAP);
				laserInput(pos, null);
			}
		}
		
		if(game.getMode().hasScore()) {
			end();
		}
	}
	
	public boolean laserInput(Position position, Orientation orientation) {
		Gdx.app.debug("Level.laser_input", position.toString()+" from "+(orientation == null ? "null" : orientation.toString()));
		
		if(!map.inMap(position)) {
			return false;
		}
		
		Block block = map.getBlock(position);
		if(block == null) {
			// not block to start
			if(orientation == null) {
				return false;
			}
			// position on ground without block
			String pos_str = position.toString();
			String ori_str = orientation == null ? "null" : orientation.toString();
			boolean result = true;
			result = result && map.setLaserInput(position, orientation.reverse());
			result = result && map.setLaserOutput(position, orientation.reverse());
			Gdx.app.debug("Level.laser_input", "setting laser on "+pos_str+" to "+ori_str+" result: "+Boolean.toString(result));
			return result && laserInput(orientation.reverse().nextPosition(position), orientation);
		}
		
		if(block.processed()) {
			return false;
		}
		
		block.input(orientation);
		for(Orientation output : block.getOutputs()) {
			Position nextPosition = output.nextPosition(position);
			Orientation nextOrientation = output.reverse();
			Gdx.app.debug("Level.laser_input", "sending laser to "+nextPosition.toString()+" from "+nextOrientation.toString());
			laserInput(nextPosition, nextOrientation);
		}
		
		return true;
	}
	
	public void start() {
		if(game.getMode().hasScore()) {
			this.elapsedTime = 0;
			Timer.schedule(new Timer.Task() {
				public void run() {
					LazerChallenge.getInstance().getLevel().timerTick();
				}
			}, 1, 1, this.time-1);
		}
		changed();
	}
	
	public void stop() {
		Timer.instance().clear();
		changed();
	}
	
	private void end() {
		stop();
		game.act("MENU_LEVEL_FINISHED");
	}
	
	public boolean isWon() {
		if(getRemainingTime() <= 0) {
			return false;
		}
		// TODO check targets
		return false;
	}
	
	public void timerTick() {
		//Gdx.app.debug("Level.timer_tick", "elapsedTime="+Integer.toString(elapsedTime));
		this.elapsedTime += 1;
		if(getRemainingTime() == 0) {
			end();
		}
		changed();
	}
	
	public void select(Position pos) {
		this.selected = pos;
		changed();
	}
	
	public void moving(boolean moving) {
		if(moving) {
			Block block = getSelectedBlock();
			if(block == null || !block.canMove()) {
				return;
			}
		}
		this.moving = moving;
	}
	
	public boolean moving() {
		return moving;
	}
	
	public boolean moveSelectedTo(Position newPos) {
		return moveTo(selected, newPos);
	}
	
	public boolean moveTo(Position oldPos, Position newPos) {
		if(oldPos == null) {
			Gdx.app.error("Level.moveTo", "oldPos is null");
			return false;
		}
		if(newPos == null) {
			Gdx.app.error("Level.moveTo", "newPos is null");
			return false;
		}
		// unavailable
		if(!isAvailable(oldPos) || !isAvailable(newPos)) {
			Gdx.app.debug("Level.moveTo", "one pos not available");
			return false;
		}
		
		Block oldBlock = getBlock(oldPos);
		Block newBlock = getBlock(newPos);
		// fixed position
		if (oldBlock != null && !oldBlock.canMove()){
			Gdx.app.debug("Level.moveTo", "old can't move");
			return false;
		}
		// fixed position
		if (newBlock != null && !newBlock.canMove()){
			Gdx.app.debug("Level.moveTo", "new can't move");
			return false;
		}
		
		// restriction
		if(!isAllowed(oldPos, newBlock)) {
			Gdx.app.debug("Level.moveTo", "new to old movement not allowed");
			return false;
		}
		if(!isAllowed(newPos, oldBlock)) {
			Gdx.app.debug("Level.moveTo", "old to  new movement not allowed");
			return false;
		}
		
		history.add(new Switch(oldPos,newPos));
		
		return setBlock(oldBlock, newPos) && setBlock(newBlock, oldPos);
	}
	
	public boolean canUndo() {
		return history.size > 0;
	}
	
	public boolean undo() {
		if(!canUndo()) {
			return false;
		}
		
		Switch last = history.pop();
		if(!moveTo(last.getOldPos(), last.getNewPos())) {
			Gdx.app.error("Level.undo", "unable to undo "+last.getOldPos().toString()+"/"+last.getNewPos().toString());
		}
		// this move has been saved, remove it
		history.pop();
		
		return true;
	}
	
	public boolean isAvailable(Position pos) {
		if(pos == null) {
			return false;
		}
		if(pos.getLocation().equals(Position.Location.INVENTORY)) {
			return true;
		}
		return !map.getGroundBoolProp(pos, "unavailable");
	}
	
	public boolean isAllowed(Position pos, Block block) {
		// null block is allowed everywhere
		if(block == null) {
			return true;
		}
		
		if(pos == null) {
			return false;
		}
		
		if(pos.getLocation().equals(Position.Location.INVENTORY)) {
			return true;
		}
		
		String restriction = map.getGroundProp(pos, "restriction");
		// no restriction
		if(restriction == null || restriction.isEmpty()) {
			return true;
		}
		
		String name = block.getType().getName();
		// name must contain restriction to be allowed
		return name.contains(restriction);
	}
	
	public String getRestriction(Position pos) {
		if(pos == null || pos.getLocation().equals(Position.Location.INVENTORY)) {
			return null;
		}
		return map.getGroundProp(pos, "restriction");
	}
	
	public void rotateSelected() {
		rotate(selected);
	}
	
	public Block getSelectedBlock() {
		if(selected == null) {
			//Gdx.app.log("Level.getSelectedBlock", "selected is null");
			return null;
		}
		
		return getBlock(selected);
	}
	
	public void rotate(Position pos) {
		if(pos == null) {
			Gdx.app.error("Level.rotate", "pos is null");
			return;
		}
		
		if(pos.getLocation() == null) {
			Gdx.app.error("Level.rotate", "no location");
		}
		switch(pos.getLocation()) {
		case MAP:
			map.rotate(pos);
			break;
		case INVENTORY:
			inventory.rotate(pos);
			break;
		}
		this.selected = pos;
	}
	
	public Position getSelected() {
		return this.selected;
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
	 * @return inventory
	 */
	public Map getInventory() {
		return inventory;
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
	public LevelType getType() {
		return type;
	}
	/**
	 * 
	 * @return time
	 */
	public int getTime() {
		return time;
	}
	/**
	 * Can be negative
	 * @return
	 */
	public int getRemainingTime() {
		return time-elapsedTime;
	}
	
	public int getScore() {
		return time*time-elapsedTime*elapsedTime;
	}
	/**
	 * Refresh levels from disk. Levels directory is scanned and all valid levels are loaded into static array.
	 */
	private static void refresh() {
		Array<String> levelFiles = Files.listFiles(LEVELS_PATH);
		if(levelFiles == null) {
			throw new GdxRuntimeException("Got null while listing directory "+LEVELS_PATH);
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
				tmp = new Level(LEVELS_PATH+"/"+file);
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
			refresh();
		}
		return levels;
	}
	/**
	 * 
	 * @param type Only levels of this type will be returned
	 * @return levels with type equals to @type
	 */
	public static Array<Level> getLevels(LevelType type) {
		return getLevels(type, false);
	}
	/**
	 * 
	 * @param type Only levels of this type will be returned
	 * @param refresh Whether or not refresh levels from disk
	 * @return Levels with type equals to @type
	 */
	public static Array<Level> getLevels(LevelType type, boolean refresh) {
		getLevels(refresh);
		Array<Level> filtered = new Array<Level>(levels.size);
		
		for(Level level : levels) {
			if(level.getType().getName().equals(type.getName())) {
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
		ARCADE ("Mode arcade"),
		TRAINING("Mode entrainement");
		
		String s;
		private Mode(String s) {
			this.s = s;
		}
		public String toString() {
			return s;
		}
		
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
	
}
