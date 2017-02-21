package be.ac.umons.sgl.lazer.g06;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenu implements Screen {
	
	Texture img;
	LazerChallenge game;
	
	public MainMenu(LazerChallenge game){
		this.game=game;
	}
	
	public void dispose () {
		game.batch.dispose();
		img.dispose();
	}

	@Override
	public void resize(int width, int height) {
		//camera.setToOrtho(false,width,height);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void show() {
		img = new Texture("badlogic.jpg");
		
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(img, 0, 0);
		game.batch.end();
		
	}
}
