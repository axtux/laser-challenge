package be.ac.umons.sgl.lazer.g06.game;

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
	
	public boolean rotate() {
		if(getTile().getProperties().containsKey("fixedorientation")) {
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
		input = orientation;
		return true;
	}
	
	public Orientation getInput() {
		return input;
	}
	
	public Array<Orientation> getOutputs() {
		return type.input(input);
	}
	
	public BlockType getType() {
		return type;
	}
	
	public void clearInput() {
		input = null;
		processed = false;
	}
}
