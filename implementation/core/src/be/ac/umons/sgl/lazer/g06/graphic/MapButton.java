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
import be.ac.umons.sgl.lazer.g06.game.Position;
import be.ac.umons.sgl.lazer.g06.game.orientations.Orientation;
import be.ac.umons.sgl.lazer.g06.listeners.MapClickListener;
/**
 * Display multiple layers of cell and run action on click.
 */
public class MapButton extends Button {
	private Map map;
	private Position pos;
	private TextureRegion inputTR;
	private TextureRegion outputTR;
	private Block block;
	private Lasers lasers;
	/**
	 * Create button representing position pos on map map.
	 * @param map Map from which get cells and other displaying options.
	 * @param pos Position on map to display.
	 */
	public MapButton(Map map, Position pos) {
		super();
		this.map = map;
		this.pos = pos;
		
		inputTR = LazerChallenge.getInstance().getLevel().getType().getInput();
		outputTR = LazerChallenge.getInstance().getLevel().getType().getOutput();
		
		this.addListener(new MapClickListener(Input.Buttons.LEFT, pos, "ACTION_LEVEL_SELECT"));
		this.addListener(new MapClickListener(Input.Buttons.RIGHT, pos, "ACTION_LEVEL_ROTATE"));

		updateBackground();
		update();
	}
	/**
	 * Update background from map.
	 */
	private void updateBackground() {
		Cell bg = map.getGround(pos);
		TextureRegionDrawable d = new TextureRegionDrawable(bg.getTile().getTextureRegion());
		
		ButtonStyle style = new ButtonStyle(d, d, d);
		style.over = d.tint(Color.LIGHT_GRAY);
		style.checked = d.tint(Color.GRAY);
		style.checkedOver = d.tint(Color.DARK_GRAY);
		
		this.setStyle(style);
	}
	/**
	 * Update display elements from map.
	 */
	public void update() {
		block = map.getBlock(pos);
		lasers = map.getLasers(pos);
	}
	/**
	 * Draw block and lasers above background.
	 */
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		drawBlock(batch);
		drawLasers(batch);
	}
	/**
	 * Draw the block from map if not null
	 * @param batch Batch on which to draw.
	 */
	private void drawBlock(Batch batch) {
		if(block == null) {
			return;
		}
		
		draw(batch, block.getTile().getTextureRegion(), block.getRotation());
	}
	/**
	 * Draw lasers from map if not null
	 * @param batch Batch on which to draw.
	 */
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
	/**
	 * Draw input
	 * @param batch Batch on which to draw.
	 */
	private void drawInput(Batch batch, Orientation inputFrom) {
		// laser input sprite comes from DOWN, inputFrom from UP
		inputFrom = inputFrom.reverse();
		draw(batch, inputTR, inputFrom.getAngle());
	}
	/**
	 * Draw outputs
	 * @param batch Batch on which to draw.
	 */
	private void drawOutput(Batch batch, Orientation outputTo) {
		draw(batch, outputTR, outputTo.getAngle());
	}
	/**
	 * Draw a TextureRegion on batch with clockwise rotation.
	 * @param batch Batch on which to draw.
	 * @param tr TextureRegion to draw.
	 * @param rotation Angle in degrees counted clockwise to rotate TextureRegion.
	 */
	private void draw(Batch batch, TextureRegion tr, int rotation) {
		setOrigin(Align.center);
		batch.draw(tr, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1, 1, -rotation);
	}
}
