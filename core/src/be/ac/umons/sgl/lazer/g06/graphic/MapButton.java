package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import be.ac.umons.sgl.lazer.g06.game.Block;
import be.ac.umons.sgl.lazer.g06.game.LazerChallenge;
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
		
		// display block and other upper layers
		// don't draw background as already displayed by button
		//for(Cell cell : cells) {
		for(int i = 1; i < cells.size; ++i) { Cell cell = cells.get(i);
			draw(batch, cell.getTile().getTextureRegion(), cell.getRotation());
			
		}
	}
	
	private void draw(Batch batch, TextureRegion tr, int rotation) {
		setOrigin(Align.center);
		batch.draw(tr, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1, 1, -rotation);
	}
}
