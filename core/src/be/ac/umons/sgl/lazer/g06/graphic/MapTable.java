package be.ac.umons.sgl.lazer.g06.graphic;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;

import be.ac.umons.sgl.lazer.g06.game.Map;
import be.ac.umons.sgl.lazer.g06.game.Position;
import be.ac.umons.sgl.lazer.g06.listeners.TileClickListener;

public class MapTable extends Table implements Observer {
	Map map;
	OrthogonalTiledMapRenderer renderer;
	
	Button[][] buttons;
	
	public MapTable(Map map) {
		this.map = map;
		map.addObserver(this);
		
		int h = map.getHeight();
		int w = map.getWidth();
		buttons = new Button[h][w];
		// indicies start from bottom left
		for(int y = h-1; y >=0; --y) {
			for(int x = 0; x < w; ++x) {
				buttons[y][x] = addTileButton(x, y);
			}
			this.row();
		}
		
		// test rotation, works but size calculated before rotation
		//this.setTransform(true);
		//this.rotateBy(90);
	}
	
	public Button addTileButton(int x, int y) {
		TextureRegionDrawable d = new TextureRegionDrawable(map.getVisibleTextureRegion(x, y));
		
		ButtonStyle style = new ButtonStyle(d, d, d);
		style.over = d.tint(Color.LIGHT_GRAY);
		style.checked = d.tint(Color.GRAY);
		style.checkedOver = d.tint(Color.DARK_GRAY);
		
		Button button = new Button(style);
		Position pos = new Position(x, y, Position.Location.MAP);
		button.addListener(new TileClickListener(Input.Buttons.LEFT, pos, "ACTION_LEVEL_TILE_SELECT"));
		button.addListener(new TileClickListener(Input.Buttons.RIGHT, pos, "ACTION__LEVEL_TILE_ROTATE"));
		//button.setTransform(true);
		//button.setOrigin(button.getWidth()/2, button.getHeight()/2);
		/* Use this to scale map to available space
		this.add(button).grow();
		//*/this.add(button);
		
		return button;
	}
	
	public void refreshAll() {
		Gdx.app.debug("MapTable.refreshAll", "ALL");
		int h = map.getHeight();
		int w = map.getWidth();
		// start at top left corner, walking through lines then columns
		for(int y = h-1; y >=0; --y) {
			for(int x = 0; x < w; ++x) {
				refresh(x, y);
			}
			this.row();
		}
	}
	
	public void refresh(int x, int y) {
		TextureRegionDrawable d = new TextureRegionDrawable(map.getVisibleTextureRegion(x, y));
		
		ButtonStyle style = new ButtonStyle(d, d, d);
		style.over = d.tint(Color.LIGHT_GRAY);
		style.checked = d.tint(Color.GRAY);
		style.checkedOver = d.tint(Color.DARK_GRAY);
		
		buttons[y][x].setStyle(style);
		setButtonRotation(buttons[y][x], map.getVisibleRotation(x, y));
	}
	
	private void setButtonRotation(Button button, int rotation) {
		button.setRotation(rotation);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		// for rotation on center
		this.setOrigin(this.getWidth()/2, this.getHeight()/2);
	}
	
	public void rotationChanged() {
		super.rotationChanged();
		
	}
	// Observer method
	public void update(Observable o, Object arg) {
		this.refreshAll();
	}
}
