package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import be.ac.umons.sgl.lazer.g06.game.Block;
import be.ac.umons.sgl.lazer.g06.game.Lasers;
import be.ac.umons.sgl.lazer.g06.game.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.game.Map;
import be.ac.umons.sgl.lazer.g06.game.Orientation;
import be.ac.umons.sgl.lazer.g06.game.Position;
import be.ac.umons.sgl.lazer.g06.listeners.MapClickListener;

public class MapButton extends Button {
	Map map;
	Position pos;
	TextureRegion inputTR;
	TextureRegion outputTR;
	Block block;
	Lasers lasers;
	
	public MapButton(Map map, Position pos) {
		super();
		this.map = map;
		this.pos = pos;
		
		inputTR = LazerChallenge.getInstance().getLevel().getType().getInput();
		outputTR = LazerChallenge.getInstance().getLevel().getType().getOutput();
		
		this.addListener(new MapClickListener(Input.Buttons.LEFT, pos, "ACTION_LEVEL_SELECT"));
		this.addListener(new MapClickListener(Input.Buttons.RIGHT, pos, "ACTION_LEVEL_ROTATE"));
		
		update();
		setBackground();
	}
	
	public void update() {
		block = map.getBlock(pos);
		lasers = map.getLasers(pos);
	}
	
	public void setBackground() {
		Cell bg = map.getGround(pos);
		TextureRegionDrawable d = new TextureRegionDrawable(bg.getTile().getTextureRegion());
		
		ButtonStyle style = new ButtonStyle(d, d, d);
		style.over = d.tint(Color.LIGHT_GRAY);
		style.checked = d.tint(Color.GRAY);
		style.checkedOver = d.tint(Color.DARK_GRAY);
		
		this.setStyle(style);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		drawBlock(batch);
		drawLasers(batch);
	}
	
	private void drawBlock(Batch batch) {
		if(block == null) {
			return;
		}
		
		draw(batch, block.getTile().getTextureRegion(), block.getRotation());
	}
	private void drawLasers(Batch batch) {
		if(lasers == null) {
			return;
		}
		
		for(Orientation input : lasers.getInputs()) {
			drawInput(batch, input);
		}
		for(Orientation output : lasers.getOutputs()) {
			drawOutput(batch, output);
		}
	}
	private void drawInput(Batch batch, Orientation inputFrom) {
		// laser input sprite comes from DOWN, inputFrom from UP
		inputFrom = inputFrom.reverse();
		draw(batch, inputTR, inputFrom.getAngle());
	}
	private void drawOutput(Batch batch, Orientation outputTo) {
		draw(batch, outputTR, outputTo.getAngle());
	}
	
	private void draw(Batch batch, TextureRegion tr, int rotation) {
		setOrigin(Align.center);
		batch.draw(tr, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1, 1, -rotation);
	}
}
