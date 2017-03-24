package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import be.ac.umons.sgl.lazer.g06.game.LevelType.BlockType;

public class Block extends TiledMapTileLayer.Cell {
	BlockType type;
	
	public Block(BlockType type) {
		super();
		this.type = type;
		setTile(new StaticTiledMapTile(type.getTextureRegion()));
	}
	
	public BlockType getType() {
		return type;
	}
}
