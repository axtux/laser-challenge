package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;

public class Block extends TiledMapTileLayer.Cell {
	BlockType type;
	Orientation orientation;
	// for laser
	boolean processed;
	Orientation input;
	Array<Orientation> outputs;
	
	public Block(BlockType type, Orientation orientation) {
		super();
		this.type = type;
		this.outputs = new Array<Orientation>(0);
		
		setTile(new StaticTiledMapTile(type.getTextureRegion()));
		setOrientation(orientation);
	}
	
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
	
	public boolean canMove() {
		return !getBoolProp("fixedposition");
	}
	
	public boolean canRotate() {
		return !getBoolProp("fixedorientation");
	}
	
	public boolean rotate() {
		if(!canRotate()) {
			return false;
		}
		
		setOrientation(orientation.next());
		LazerChallenge.getInstance().getLevel().changed();
		return true;
	}
	
	private void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		this.setRotation(orientation.getAngle());
	}
	
	public Array<Orientation> input(Orientation orientation) {
		if(processed) {
			return null;
		}
		
		processed = true;
		setInput(orientation);
		
		return getOutputs();
	}
	
	private void setInput(Orientation input) {
		this.input = input;
		getOutputsFromType();
	}
	
	private void getOutputsFromType() {
		Orientation originInput = input;
		// input null to start source
		if(originInput != null) {
			// get unrotated input
			originInput = originInput.unRotateBy(this.orientation);
		}
		
		Array<Orientation> originOutputs = type.input(originInput);
		outputs = new Array<Orientation>(originOutputs.size);
		for(Orientation o : originOutputs) {
			// rotate output to this orientation
			outputs.add(o.rotateBy(this.orientation));
		}
	}
	
	public boolean processed() {
		return processed;
	}
	
	public Orientation getInput() {
		return input;
	}
	
	public Array<Orientation> getOutputs() {
		return outputs;
	}
	
	public BlockType getType() {
		return type;
	}
	
	public void clearInput() {
		input = null;
		processed = false;
	}
}
