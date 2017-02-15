package be.ac.umons.sgl.lazer.g06;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LazerChallenge extends Game {
	
	@Override
	public void create () {
		setScreen(new Window());
		 
	}
	

	@Override
	public void render () {
		super.render();
		/*Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Load Assets
		img =new Texture(Gdx.files.internal("noir.png"));
		region = new TextureRegion(img, 0, 0, 700, 500);
		//batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(region, 0, 0);
		batch.end();*/
	}
	
	@Override
	public void dispose () {
		super.dispose();
		//batch.dispose();
		//img.dispose();
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}
}
