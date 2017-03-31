package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import be.ac.umons.sgl.lazer.g06.game.orientations.Orientation;

public class Block extends TiledMapTileLayer.Cell {
	BlockType type;
	Orientation orientation;
	ObjectMap<Orientation, Boolean> originInputs;
	
	public Block(BlockType type, Orientation orientation) {
		super();
		this.type = type;
		setTile(new StaticTiledMapTile(type.getTextureRegion()));
		setOrientation(orientation);
		clearInputs();
	}
	/**
	 * check if the block contain a property that prevent him to move or to rotate
	 * @param name The name of the property 
	 * @return true if the block contain the property, otherwise false
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
	 * check if the block can move
	 * @return true if it can move, otherwise return false
	 */
	public boolean canMove() {
		return !getBoolProp("fixedposition");
	}
	
	/**
	 * check if the block can rotate
	 * @return true if it can rotate, otherwise return false
	 */
	public boolean canRotate() {
		return !getBoolProp("fixedorientation");
	}
	
	/**
	 * move the block
	 * @return true if the block is moved, otherwise return false
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
	 * @param orientation Initial orientation of block
	 */
	private void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		this.setRotation(orientation.getAngle());
	}
	
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
	
	
	private boolean hasOriginInput(Orientation input) {
		if(!originInputs.containsKey(input)) {
			return false;
		}
		
		return originInputs.get(input).booleanValue();
	}
	
	
	public boolean hasRequiredInputs() {
		for(Orientation requiredInput : type.getRequiredInputs()) {
			if(!hasOriginInput(requiredInput)) {
				return false;
			}
		}
		return true;
	}
	
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
