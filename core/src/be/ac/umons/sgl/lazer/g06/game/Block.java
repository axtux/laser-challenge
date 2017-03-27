package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;

import be.ac.umons.sgl.lazer.g06.game.LevelType.BlockType;
import be.ac.umons.sgl.lazer.g06.game.LevelType.Orientation;

public class Block extends TiledMapTileLayer.Cell {
	BlockType type;
	// for laser
	Orientation input;
	boolean processed;
	
	public Block(BlockType type) {
		super();
		this.type = type;
		setTile(new StaticTiledMapTile(type.getTextureRegion()));
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
		if(!processed) {
			return new Array<Orientation>(0);
		}
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
