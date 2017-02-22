package be.ac.umons.sgl.lazer.g06;

import java.awt.Desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class MainMenu implements Screen {
	
	LazerChallenge game;
	Texture playButtonOn;
	Texture playButtonOff, exitButtonOn, exitButtonOff, logo,compteLocalOff,
			compteLocalOn,compteFacebookOff;
	
	public MainMenu(LazerChallenge game){
		this.game=game;
	}
	
	public void show() {
		// Mise en place de l image de fond
		//img = new Texture("badlogic.jpg");
		
		//creation des images des boutons
		playButtonOn=new Texture("connexion_anonyme_button_on.png");
		playButtonOff=new Texture("connexion_anonyme_button.png");
		compteLocalOff= new Texture("compte_local_off.png");
		compteLocalOn= new Texture("compte_local_on.png");
		compteFacebookOff = new Texture("connexion_facebook_button_off.png");
		exitButtonOn=new Texture("exit_button_active.png");
		exitButtonOff= new Texture("exit_button_inactive.png");
		logo = new Texture("LazerChallengeLogo.png");
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//On commence le batch qui va traiter l image ou les images
		game.batch.begin();
		game.batch.draw(logo,Gdx.graphics.getWidth()/2-190,Gdx.graphics.getHeight()/2+250,400,100);
		game.batch.getProjectionMatrix().setToOrtho2D(0, 0,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+140) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-90)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)+1 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-170) {
			
			game.batch.draw(playButtonOn,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2+50,250,100);
			
			if ( Gdx.input.isTouched()){
				this.dispose();
				game.setScreen(new Window());
				}
			}
			
		else 
			game.batch.draw(playButtonOff,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2+50,250,100);
		
		
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+140) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-90)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)-99 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-170) {
			
			game.batch.draw(compteLocalOn,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2-100,250,100);
			
			if ( Gdx.input.isTouched()){
				this.dispose();
				game.setScreen(new Window());
				}
			}
			
		else 
			game.batch.draw(compteLocalOff,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2-100,250,100);
		
		
		
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+140) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-90)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)+1 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-170) {
			
			game.batch.draw(compteFacebookOff,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2-280,250,130);
			
			if ( Gdx.input.isTouched()){
				this.dispose();
				game.setScreen(new Window());
				}
			}
			
		else 
			game.batch.draw(compteFacebookOff,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2-280,250,130);
		
		/*if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+50) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-90)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)+300 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)+170) {
				
			game.batch.draw(exitButtonOn,Gdx.graphics.getWidth()/2-90,Gdx.graphics.getHeight()/2-300,140,170);
			if ( Gdx.input.isTouched())
				Gdx.app.exit();
			}
		else
			game.batch.draw(exitButtonOff,Gdx.graphics.getWidth()/2-90,Gdx.graphics.getHeight()/2-300,140,170);
*/
		game.batch.end();
		
	}
	public void dispose () {
		game.batch.dispose();
		playButtonOn.dispose();
		playButtonOff.dispose();
	}

	public void resize(int width, int height) {
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
}
