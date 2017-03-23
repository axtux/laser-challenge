package be.ac.umons.sgl.lazer.g06.graphic;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
/* Auto renderer but not movable
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
//*/
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import be.ac.umons.sgl.lazer.g06.game.Map;
import be.ac.umons.sgl.lazer.g06.game.Position;

public class MapTable extends Table implements Observer {
	Map map;
	/* Auto renderer but not movable
	OrthogonalTiledMapRenderer renderer;
	//*/
	
	MapButton[][] buttons;
	
	public MapTable(Map map) {
		this.map = map;
		map.addObserver(this);
		
		int h = map.getHeight();
		int w = map.getWidth();
		buttons = new MapButton[h][w];
		// indicies start from bottom left
		for(int y = h-1; y >=0; --y) {
			for(int x = 0; x < w; ++x) {
				buttons[y][x] = addMapButton(x, y);
			}
			this.row();
		}
		
		// test rotation, works but size calculated before rotation
		//this.setTransform(true);
		//this.rotateBy(90);
	}
	
	public MapButton addMapButton(int x, int y) {
		Position pos = new Position(x, y, Position.Location.MAP);
		MapButton button = new MapButton(map, pos);
		
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
		buttons[y][x].update();
	}
	
	/* Auto renderer but not movable
	public void initRenderer(Batch batch) {
		//renderer = new OrthogonalTiledMapRenderer(map, 1/(float)tx);
		//renderer = new OrthogonalTiledMapRenderer(map);
		renderer = new OrthogonalTiledMapRenderer(map.getTiledMap(), batch);
		OrthographicCamera camera = new OrthographicCamera();
		// always display all map for this game
		//camera.setToOrtho(false, x, y);
		camera.setToOrtho(false, map.getWidth()*map.getTileSize(), map.getHeight()*map.getTileSize());
		//camera.translate(0*tx, 5);
		//camera.rotate(45);
			camera.update();
			renderer.setView(camera);
	}
	//*/
	
	public void rotationChanged() {
		super.rotationChanged();
		
	}
	// Observer method
	public void update(Observable o, Object arg) {
		this.refreshAll();
	}
}
