package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import be.ac.umons.sgl.lazer.g06.graphic.stages.ConnexionStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.ModeSelectionStage;

public class LazerChallenge extends Game {
	Batch batch;
	public static int WIDTH=1000;
	public static int HEIGHT=720;
	Stage stage;
	MySkin skin;
	
	@Override
	public void create () {
		//batch = new SpriteBatch();
		//this.setScreen(new MainMenu(this));
		//*
		skin = new MySkin();
		this.setStage(new ConnexionStage(this));
		batch = stage.getBatch();
		
	}
	
	public MySkin getSkin() {
		return skin;
	}
	
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	public void render() {
		super.render();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
	
	public void setStage(Stage newStage) {
		Stage oldStage = this.stage;
		Gdx.input.setInputProcessor(newStage);
		this.stage = newStage;
		this.render();
		if(oldStage != null && oldStage != newStage)
		oldStage.dispose();
	}
	
	public void act(String action) {
		System.out.println("Got action "+action+"");
		switch(action) {
		case "CONNECTION_ANONYMOUS":
			setStage(new ModeSelectionStage(this));
			break;
		case "CONNECTION_LOCAL":
			//setStage(new Stage(this));
			break;
		case "CONNECTION_TWITTER":
			//setStage(new Stage(this));
			break;
		default:
			System.out.println("Action "+action+" not implemented");
		}
		
	}
}