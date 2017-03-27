package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import be.ac.umons.sgl.lazer.g06.game.Block;
import be.ac.umons.sgl.lazer.g06.game.LevelType.BlockType;
import be.ac.umons.sgl.lazer.g06.game.LevelType.Orientation;
import be.ac.umons.sgl.lazer.g06.game.Map;
import be.ac.umons.sgl.lazer.g06.game.Position;
import be.ac.umons.sgl.lazer.g06.listeners.MapClickListener;

public class MapButton extends Button {
	Map map;
	Position pos;
	Array<Cell> cells;
	Block block;
	
	TextureRegion inputTR, outputTR;
	
	public MapButton(Map map, Position pos) {
		super();
		this.map = map;
		this.pos = pos;
		
		this.addListener(new MapClickListener(Input.Buttons.LEFT, pos, "ACTION_LEVEL_SELECT"));
		this.addListener(new MapClickListener(Input.Buttons.RIGHT, pos, "ACTION_LEVEL_ROTATE"));
		
		inputTR = LazerChallenge.getInstance().getLevel().getType().getInput();
		outputTR = LazerChallenge.getInstance().getLevel().getType().getOutput();
		
		update();
		setBackground();
	}
	
	public void update() {
		cells = map.getCells(pos);
		block = map.getBlock(pos);
		Gdx.app.debug("TileButton.update", "called for position "+pos.toString());
	}
	
	public void setBackground() {
		Cell bg = cells.get(0);
		TextureRegionDrawable d = new TextureRegionDrawable(bg.getTile().getTextureRegion());
		
		ButtonStyle style = new ButtonStyle(d, d, d);
		style.over = d.tint(Color.LIGHT_GRAY);
		style.checked = d.tint(Color.GRAY);
		style.checkedOver = d.tint(Color.DARK_GRAY);
		
		this.setStyle(style);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if(block == null) {
			return;
		}
		
		// display block
		draw(batch, block.getTile().getTextureRegion(), block.getRotation());
		
		// stop if laser not on block
		if(!block.processed()) {
			return;
		}
		
		Orientation input = block.getInput();
		if(input != null) {
			draw(batch, inputTR, input.getAngle());
		}
		
		for(Orientation output : block.getOutputs()) {
			draw(batch, outputTR, output.getAngle());
		}
		
		
		
		/*
		// don't draw background as already displayed by button
		//for(Cell cell : cells) {
		for(int i = 1; i < cells.size; ++i) { Cell cell = cells.get(i);
			tr = cell.getTile().getTextureRegion();
			angle = cell.getRotation();
			batch.draw(tr, getX(), getY(), getWidth()/2, getHeight()/2, getWidth(), getHeight(), 1, 1, -angle);
		}
		//*/
	}
	
	private void draw(Batch batch, TextureRegion tr, int rotation) {
		batch.draw(tr, getX(), getY(), getWidth()/2, getHeight()/2, getWidth(), getHeight(), 1, 1, -rotation);
	}
}
