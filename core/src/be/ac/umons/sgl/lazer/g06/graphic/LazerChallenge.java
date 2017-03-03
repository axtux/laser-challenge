package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import be.ac.umons.sgl.lazer.g06.listeners.ButtonListener;

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
		Gdx.input.setInputProcessor(newStage);
		this.stage = newStage;
		this.render();
	}
}