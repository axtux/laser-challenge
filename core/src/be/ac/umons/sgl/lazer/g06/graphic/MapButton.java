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

import be.ac.umons.sgl.lazer.g06.game.Map;
import be.ac.umons.sgl.lazer.g06.game.Position;
import be.ac.umons.sgl.lazer.g06.listeners.MapClickListener;

public class MapButton extends Button {
	Map map;
	Position pos;
	Array<Cell> cells;
	
	public MapButton(Map map, Position pos) {
		super();
		this.map = map;
		this.pos = pos;
		
		this.addListener(new MapClickListener(Input.Buttons.LEFT, pos, "ACTION_LEVEL_SELECT"));
		this.addListener(new MapClickListener(Input.Buttons.RIGHT, pos, "ACTION_LEVEL_ROTATE"));
		
		update();
		setBackground();
	}
	
	public void update() {
		cells = map.getCells(pos);
		Gdx.app.debug("TileButton.update", "called for position "+pos.toString());
	}
	
	public void setBackground() {
		Cell bg = cells.get(0);
		TextureRegionDrawable d = new TextureRegionDrawable(bg.getTile().getTextureRegion());
		
		ButtonStyle style = new ButtonStyle(d, d, d);
		style.checkedOver = style.over = d.tint(Color.LIGHT_GRAY);
		
		this.setStyle(style);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		TextureRegion tr;
		int angle = 0;
		
		// don't draw background as already displayed by button
		//for(Cell cell : cells) {
		for(int i = 1; i < cells.size; ++i) { Cell cell = cells.get(i);
			tr = cell.getTile().getTextureRegion();
			angle = cell.getRotation();
			batch.draw(tr, getX(), getY(), getWidth()/2, getHeight()/2, getWidth(), getHeight(), 1, 1, -angle);
		}
	}
}
