package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.game.Level;
import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

public class LevelPlayingStage extends AbstractStage {
	boolean displayScore;
	
	Table map;
	
	Level level;
	OrthogonalTiledMapRenderer renderer;
	
	public LevelPlayingStage(LazerChallenge game) {
		super(game, game.getLevel().getName());
		
		if(game.getMode() == null) {
			throw new GdxRuntimeException("game mode cannot be null.");
		}
		displayScore = game.getMode().equals("ARCADE");
		
		addHeaderButton("Retour", "MENU_LEVEL_INFOS");
		
		if(game.getLevel() == null) {
			throw new GdxRuntimeException("game level cannot be null.");
		}
		level = game.getLevel();
		
		map = new Table();
		content.row();
		content.add(map);
		
		addMenuButton("Lancer le lazer", "ACTION_GAME_LAZER");
		
		initMapRenderer();
	}
	
	public void draw() {
		super.draw();
		renderer.render();
	}
	
	private void initMapRenderer() {
		MapProperties props = level.getMap().getTiledMap().getProperties();
		int tx = props.get("tilewidth", -1, int.class);
		int ty = props.get("tileheight", -1, int.class);
		if(tx < 1 || ty < 1) {
			throw new GdxRuntimeException("Tile width or tile height is < 1 or missing.");
		}
		if(tx != ty) {
			throw new GdxRuntimeException("Tile width does not match tile height.");
		}
		
		//renderer = new OrthogonalTiledMapRenderer(level.getMap(), 1/(float)tx);
		renderer = new OrthogonalTiledMapRenderer(level.getMap().getTiledMap(), this.getBatch());
		OrthographicCamera camera = new OrthographicCamera();
		
		int x = props.get("width", -1, int.class);
		int y = props.get("height", -1, int.class);
		if(x < 1 || y < 1) {
			throw new GdxRuntimeException("Map width or map height is < 1 or missing.");
		}
		
		// always display all map for this game
		//camera.setToOrtho(false, x, y);
		camera.setToOrtho(false, x*tx, y*tx);
		//camera.translate(0*tx, 5);
		//camera.rotate(45);
		camera.update();
		renderer.setView(camera);
		//renderer.setView((OrthographicCamera) getCamera());
	}
	
	public void dispose() {
		renderer.dispose();
		super.dispose();
	}
	

}
