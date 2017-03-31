package be.ac.umons.sgl.lazer.g06.game;

import java.util.Observable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.XmlReader.Element;

import be.ac.umons.sgl.lazer.g06.Files;
import be.ac.umons.sgl.lazer.g06.game.Position.Location;
import be.ac.umons.sgl.lazer.g06.game.orientations.Orientation;
/**
 * Class that manages the game part of the LazerChallenge
 * Notify observers about selected level, time and score updates
 */
public class Level extends Observable {
	static final Difficulty defaultDifficulty = Difficulty.MEDIUM;
	static final String defaultOrientation = "UP";
	
	Element level;
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
		level = Files.parseXML(file);
		if(level == null) {
			throw new GdxRuntimeException("Error parsing file "+file);
		}
		
		game = LazerChallenge.getInstance();
		Gdx.app.debug("Level.Level", "creating level from file "+file);
		reset();
	}
	
	public void reset() {
		stop();
		
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
		this.type = LevelTypes.getLevelType(type);
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
			orientation = type.getOrientation().fromString(orientationStr);
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
				invBlocks.add(block);
				continue;
			}
			
			int x = positionElement.getIntAttribute("x", -1);
			int y = positionElement.getIntAttribute("y", -1);
			// if x or y out of map, block goes to inventory
			pos = new Position(x, y);
			if(map.getBlock(pos) != null) {
				Gdx.app.error("Level.setBlocks", "level "+name+" : position "+pos.toString()+" already taken");
				invBlocks.add(block);
			}
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
			startLasers();
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
			Gdx.app.debug("Level.getBlock", "pos is null");
			return null;
		}
		
		if(pos.getLocation() == null) {
			Gdx.app.debug("Level.getBlock", "location is null");
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
	
	public void startLasers() {
		map.startLasers();
		
		if(game.getMode().hasScore()) {
			// one-time laser
			end();
		}
	}
	
	public void start(boolean load) {
		if(load) {
			load();
		}
		
		if(game.getMode().hasScore()) {
			// arcade mode with timer
			this.elapsedTime = 0;
			Timer.schedule(new Timer.Task() {
				public void run() {
					LazerChallenge.getInstance().getLevel().timerTick();
				}
			}, 1, 1, this.time-1);
		} else {
			// training mode, continuous laser
			startLasers();
		}
		changed();
	}
	
	public boolean canLoad() {
		Array<Switch> userHistory = game.getUser().loadHistory(name);
		return userHistory != null && userHistory.size > 0;
	}
	public boolean load() {
		Gdx.app.debug("Level.load", "trying to load");
		if(!canLoad()) {
			return false;
		}
		
		Array<Switch> userHistory = game.getUser().loadHistory(name);
		for(Switch s : userHistory) {
			if(!moveTo(s.getOldPos(), s.getNewPos())) {
				Gdx.app.error("Level.load", "Unable to load user history for level "+name);
				return false;
			}
		}
		
		return true;
	}
	
	public void stop() {
		if(game.getMode() != null && game.getMode().hasScore()) {
			Timer.instance().clear();
			changed();
		} else if(game.getUser() != null && history.size != 0){
			game.getUser().saveHistory(name, history);
		}
	}
	// only called in arcade mode
	private void end() {
		stop();
		if(isWon()) {
			game.getUser().saveScore(name, getScore());
		}
		game.act("MENU_LEVEL_FINISHED");
	}
	
	public boolean isWon() {
		if(getRemainingTime() <= 0) {
			return false;
		}
		
		if(!map.hasRequiredInputs() || !inventory.hasRequiredInputs()) {
			return false;
		}
		
		return true;
	}
	
	public void timerTick() {
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
	
	private boolean moveTo(Position oldPos, Position newPos) {
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
		
		// one-time
		if(isOneTime(oldPos)) {
			Gdx.app.debug("Level.moveTo", "old pos is one-time");
			return false;
		}
		
		// other restrictions
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
		return history.size > 0 && !isOneTime(history.get(history.size-1).getNewPos());
	}
	
	public boolean undo() {
		if(!canUndo()) {
			return false;
		}
		
		Switch last = history.pop();
		if(!moveTo(last.getOldPos(), last.getNewPos())) {
			Gdx.app.error("Level.undo", "unable to undo "+last.getOldPos().toString()+"/"+last.getNewPos().toString());
			return false;
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
	
	public boolean isOneTime(Position pos) {
		String restriction = getRestriction(pos);
		return restriction != null && restriction.equals("one-time");
	}
	
	public boolean isAllowed(Position pos, Block block) {
		// null block is allowed everywhere, don't test for one-time here
		if(block == null || isOneTime(pos)) {
			return true;
		}
		
		String restriction = getRestriction(pos);
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
		return getBlock(selected);
	}
	
	public void rotate(Position pos) {
		Block block = getBlock(pos);
		if(block != null) {
			block.rotate();
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
		EASY("facile"),
		MEDIUM("moyenne"),
		HARD("difficile");
		
		String s;
		private Difficulty(String s) {
			this.s = s;
		}
		public String toString() {
			return s;
		}
	}
	
}
