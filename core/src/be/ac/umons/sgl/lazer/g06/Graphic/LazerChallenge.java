package be.ac.umons.sgl.lazer.g06.Graphic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LazerChallenge extends Game {
	SpriteBatch batch;
	public static int WIDTH=1000;
	public static int HEIGHT=720;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenu(this));
	}
	
	public void render(){
		super.render();
	}
}