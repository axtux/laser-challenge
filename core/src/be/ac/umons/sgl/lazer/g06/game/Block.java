package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import be.ac.umons.sgl.lazer.g06.game.orientations.Orientation;
/**
 * Block to be inserted into map. Extends Cell to use already existing LibGDX map classes.
 */
public class Block extends TiledMapTileLayer.Cell {
	BlockType type;
	Orientation orientation;
	ObjectMap<Orientation, Boolean> originInputs;
	/**
	 * Create a block
	 * @param type The type associated with this block.
	 * This will define block sprite and laser input/output management.
	 * @param orientation Orientation of this block.
	 */
	public Block(BlockType type, Orientation orientation) {
		super();
		this.type = type;
		setTile(new StaticTiledMapTile(type.getTextureRegion()));
		setOrientation(orientation);
		clearInputs();
	}
	/**
	 * Get block boolean property.
	 * @param name The name of the property.
	 * @return True if the lower-cased property equals true or 1. False otherwise.
	 */
	public boolean getBoolProp(String name) {
		MapProperties props = getTile().getProperties();
		if(!props.containsKey(name)) {
			return false;
		}
		
		String value = props.get(name).toString().toLowerCase();
		if(value.equals("true") || value.equals("1")) {
			return true;
		}
		
		return false;
	}
	/**
	 * Check if the block can move using boolean property "fixedposition".
	 * @return true if it can move, otherwise return false
	 */
	public boolean canMove() {
		return !getBoolProp("fixedposition");
	}
	
	/**
	 * Check if the block can rotate using boolean property "fixedorientation"
	 * @return true if it can rotate, otherwise return false
	 */
	public boolean canRotate() {
		return !getBoolProp("fixedorientation");
	}
	
	/**
	 * Rotate block to its next orientation.
	 * @return True if the block is rotated, false otherwise
	 */
	public boolean rotate() {
		if(!canRotate()) {
			return false;
		}
		
		setOrientation(orientation.next());
		LazerChallenge.getInstance().getLevel().changed();
		return true;
	}
	/**
	 * Change the orientation of block
	 * @param orientation New orientation of the block.
	 */
	private void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		this.setRotation(orientation.getAngle());
	}
	/**
	 * Add input to this block.
	 * @param originInput Orientation of laser input.
	 * @return Outputs resulting from laser input originInput.
	 */
	public Array<Orientation> input(Orientation originInput) {
		// input null to start source
		if(originInput != null) {
			// get unrotated input
			originInput = originInput.unRotateBy(this.orientation);
			originInputs.put(originInput, new Boolean(true));
		}
		
		Array<Orientation> originOutputs = type.input(originInput);
		Array<Orientation> outputs = new Array<Orientation>(originOutputs.size);
		for(Orientation o : originOutputs) {
			// rotate output to this orientation
			outputs.add(o.rotateBy(this.orientation));
		}
		
		return outputs;
	}
	/**
	 * Check if a laser input entered this block.
	 * @param input Orientation of laser input.
	 * @return True if input has been entered, false otherwise.
	 */
	private boolean hasOriginInput(Orientation input) {
		if(!originInputs.containsKey(input)) {
			return false;
		}
		
		return originInputs.get(input).booleanValue();
	}
	/**
	 * Check whether all required inputs by blockType has been satisfied.
	 * @return True if all required inputs are satisfied, false otherwise.
	 */
	public boolean hasRequiredInputs() {
		for(Orientation requiredInput : type.getRequiredInputs()) {
			if(!hasOriginInput(requiredInput)) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Clear entered laser inputs in this block.
	 */
	public void clearInputs() {
		originInputs = new ObjectMap<Orientation, Boolean>();
	}
	/**
	 * @return the type of block
	 */
	public BlockType getType() {
		return type;
	}
	
}
