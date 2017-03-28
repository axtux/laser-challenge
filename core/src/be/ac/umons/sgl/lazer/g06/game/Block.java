package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;

import be.ac.umons.sgl.lazer.g06.game.LevelType.BlockType;
import be.ac.umons.sgl.lazer.g06.game.LevelType.Orientation;

public class Block extends TiledMapTileLayer.Cell {
	BlockType type;
	Orientation orientation;
	// for laser
	Orientation input;
	boolean processed;
	
	public Block(BlockType type, Orientation orientation) {
		super();
		this.type = type;
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
		return true;
	}
	
	private void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		this.setRotation(orientation.getAngle());
	}
	
	public boolean processed() {
		return processed;
	}
	
	public boolean input(Orientation orientation) {
		if(processed) {
			return false;
		}
		
		processed = true;
		// input null to start source
		if(input != null) {
			input = orientation.unRotateBy(this.orientation);
		}
		
		return true;
	}
	
	public Orientation getInput() {
		return input;
	}
	
	public Array<Orientation> getOutputs() {
		Array<Orientation> typeOutputs = type.input(input);
		if(typeOutputs == null) {
			return new Array<Orientation>(0);
		}
		
		Array<Orientation> outputs = new Array<Orientation>(typeOutputs.size);
		for(Orientation o : typeOutputs) {
			outputs.add(o.rotateBy(this.orientation));
		}
		
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
