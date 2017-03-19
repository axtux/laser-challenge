package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.listeners.TileClickListener;

public class MapTable extends Table {
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;
	
	int mapWidth, mapHeight, tileSize;
	TiledMapTileLayer ground;
	Button[][] buttons;
	
	public MapTable(TiledMap map) {
		this.map = map;
		MapProperties props = map.getProperties();
		
		mapWidth = props.get("width", -1, int.class);
		mapHeight = props.get("height", -1, int.class);
		if(mapWidth < 1 || mapHeight < 1) {
			throw new GdxRuntimeException("Map width or map height is < 1 or missing.");
		}
		
		int tileWidth = props.get("tilewidth", -1, int.class);
		int tileHeight = props.get("tileheight", -1, int.class);
		if(tileWidth < 1 || tileHeight < 1) {
			throw new GdxRuntimeException("Tile width or tile height is < 1 or missing.");
		}
		
		if(tileWidth != tileHeight) {
			throw new GdxRuntimeException("Tile width does not match tile height.");
		}
		
		tileSize = tileWidth;
		
		ground = (TiledMapTileLayer) map.getLayers().get("ground");
		buttons = new Button[ground.getHeight()][ground.getWidth()];
		// indicies start from bottom left
		for(int y = ground.getHeight()-1; y >=0; --y) {
			for(int x = 0; x < ground.getWidth(); ++x) {
				buttons[y][x] = addButton(this, ground.getCell(x, y), x, y);
			}
			this.row();
		}
		
		// test rotation, works but size calculated before rotation
		//this.setTransform(true);
		//this.rotateBy(90);
	}
	
	public Button addButton(Table container, Cell cell, int x, int y) {
		TextureRegionDrawable d = new TextureRegionDrawable(cell.getTile().getTextureRegion());
		
		ButtonStyle style = new ButtonStyle(d, d, d);
		style.over = d.tint(Color.LIGHT_GRAY);
		style.checked = d.tint(Color.GRAY);
		style.checkedOver = d.tint(Color.DARK_GRAY);
		
		Button button = new Button(style);
		button.addListener(new TileClickListener(Input.Buttons.LEFT, x, y, "ACTION_MOVE"));
		button.addListener(new TileClickListener(Input.Buttons.RIGHT, x, y, "ACTION_ROTATE"));
		button.setTransform(true);
		button.setOrigin(button.getWidth()/2, button.getHeight()/2);
		container.add(button);
		
		return button;
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		// for rotation on center
		this.setOrigin(this.getWidth()/2, this.getHeight()/2);
	}
	
	public void rotationChanged() {
		super.rotationChanged();
		
	}
}
