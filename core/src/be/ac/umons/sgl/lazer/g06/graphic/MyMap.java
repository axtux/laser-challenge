package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class MyMap extends Actor {
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;
	
	int mapWidth, mapHeight, tileSize;
	
	public MyMap(TiledMap map) {
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
	}
	
	public void initRenderer(Batch batch) {
		//renderer = new OrthogonalTiledMapRenderer(map, 1/(float)tx);
		//renderer = new OrthogonalTiledMapRenderer(map);
		renderer = new OrthogonalTiledMapRenderer(map, batch);
		OrthographicCamera camera = new OrthographicCamera();
		
		// always display all map for this game
		//camera.setToOrtho(false, x, y);
		camera.setToOrtho(false, mapWidth*tileSize, mapHeight*tileSize);
		//camera.translate(0*tx, 5);
		//camera.rotate(45);
		camera.update();
		renderer.setView(camera);
		//renderer.setView((OrthographicCamera) getCamera());
	}
	
	public void draw(Batch batch, float parentAlpha) {
		if(renderer == null) {
			initRenderer(batch);
		}
		batch.end();
		renderer.render();
		batch.begin();
		super.draw(batch, parentAlpha);
	}
	
	public void dispose() {
		renderer.dispose();
	}
}
