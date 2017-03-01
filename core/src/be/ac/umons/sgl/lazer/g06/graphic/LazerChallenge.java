package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

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
		
		MyButton btn = new MyButton("Coucou");
		stage.addActor(btn.getButton());
		//stage.add(batch);
		//stage.addActor(actor);
		//*/
	}
	
	public void render(){
		super.render();
		stage.draw();
	}
}