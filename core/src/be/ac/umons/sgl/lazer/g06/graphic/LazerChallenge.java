package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import be.ac.umons.sgl.lazer.g06.listeners.ButtonListener;

public class LazerChallenge extends Game {
	SpriteBatch batch;
	public static int WIDTH=1000;
	public static int HEIGHT=720;
	Stage stage;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		//this.setScreen(new MainMenu(this));
		//*
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		MyButton.init();
		MyButton btn = new MyButton("Coucou petite perruche !");
		ButtonListener bl = new ButtonListener("MENU");
		btn.addListener(bl);
		stage.addActor(btn);
		//stage.add(batch);
		//stage.addActor(actor);
		//*/
	}
	
	public void render(){
		super.render();
		stage.draw();
	}
}