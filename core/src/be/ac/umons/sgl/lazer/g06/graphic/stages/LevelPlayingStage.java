package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.game.Level;
import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

public class LevelPlayingStage extends AbstractStage {
	String mode;
	boolean score;
	
	Table map;
	
	Level level;
	OrthogonalTiledMapRenderer renderer;
	
	public LevelPlayingStage(LazerChallenge game, String mode) {
		super(game, "Jeu : Niveau");
		setMode(mode);
		
		map = new Table();
		content.row();
		content.add(map);
		
		addMenuButton("Lancer le lazer", "ACTION_GAME_LAZER");
		
		level = game.getLevel();
		initMapRenderer();
	}
	
	public void draw() {
		//super.draw();
		render();
	}
	
	public void render () {
		// clear the screen
		Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render();
	}
	
	private void initMapRenderer() {
		MapProperties props = level.getMap().getProperties();
		int tx = props.get("tilewidth", -1, int.class);
		int ty = props.get("tileheight", -1, int.class);
		if(tx < 1 || ty < 1) {
			throw new GdxRuntimeException("Tile width or tile height is < 1 or missing.");
		}
		if(tx != ty) {
			throw new GdxRuntimeException("Tile width does not match tile height.");
		}
		
		renderer = new OrthogonalTiledMapRenderer(level.getMap(), 1/(float)tx);
		OrthographicCamera camera = new OrthographicCamera();
		
		int x = props.get("width", -1, int.class);
		int y = props.get("height", -1, int.class);
		if(x < 1 || y < 1) {
			throw new GdxRuntimeException("Map width or map height is < 1 or missing.");
		}
		
		// always display all map for this game
		camera.setToOrtho(false, x, y);
		camera.update();
		renderer.setView(camera);
		render();
	}
	
	private boolean setMode(String mode) {
		score = false;
		
		switch(mode) {
		case "ARCADE":
			score = true;
		case "TRAINING":
			this.mode = mode;
			return true;
		default:
			Gdx.app.error("LevelPlayingStage.setMode", "mode "+mode+" not implemented");
			return false;
		}
	}
	

}
