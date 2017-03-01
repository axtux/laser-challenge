package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class ModeMenu implements Screen {
		
	LazerChallenge game;
	Texture deconnexionButtonOff,arcadeButtonOff,entrainementButtonOff,deconnexionButtonOn,arcadeButtonOn,entrainementButtonOn,logo;
	
	public ModeMenu(LazerChallenge game ){
		this.game=game;
	}

	@Override
	public void show() {
		deconnexionButtonOff = new Texture("ModeMenu/deconnexion_button_off.png");
		deconnexionButtonOn = new Texture("ModeMenu/deconnexion_button_on.png");
		arcadeButtonOff = new Texture("ModeMenu/arcade_button_off.png");
		arcadeButtonOn = new Texture("ModeMenu/arcade_button_on.png");
		entrainementButtonOff = new Texture("ModeMenu/entrainement_button_off.png");
		entrainementButtonOn = new Texture("ModeMenu/entrainement_button_on.png");
		logo = new Texture("MainMenu/LazerChallengeLogo.png");
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.getProjectionMatrix().setToOrtho2D(0, 0,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
		game.batch.draw(logo,Gdx.graphics.getWidth()/2-190,Gdx.graphics.getHeight()/2+250,400,100);
		//game.batch.draw(deconnexion,Gdx.graphics.getWidth()/2+390,Gdx.graphics.getHeight()/2+170,200,100);
		
		//Gestion du bouton Menu Arcade
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+90) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-110)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)-50 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-150){
			
			game.batch.draw(arcadeButtonOn,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2+50,250,100);
			if ( Gdx.input.justTouched()){
				this.dispose();
				game.setScreen(new ChoiceLevel(this.game));
			}
		}
			
		else 
			game.batch.draw(arcadeButtonOff,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2+50,250,100);
		
		
		//Gestion du bouton Mode Entrainement
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+90) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-110)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)+90 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)
				) {
			
			game.batch.draw(entrainementButtonOn,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2-170,250,150);
			
			if ( Gdx.input.justTouched()){
				this.dispose();
				game.setScreen(new ChoiceLevel(game));
				}
			}
			
		else 
			game.batch.draw(entrainementButtonOff,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2-170,250,150);
		
		//Gestion du bouton Deconnexion
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+360) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)+210)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)-140 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-220){
			
			game.batch.draw(deconnexionButtonOn,Gdx.graphics.getWidth()/2+210,Gdx.graphics.getHeight()/2+150,150,80);
			
			if ( Gdx.input.justTouched()){
				this.dispose();
				game.setScreen(new MainMenu(this.game));
				}
			}
			
		else 
			game.batch.draw(deconnexionButtonOff,Gdx.graphics.getWidth()/2+210,Gdx.graphics.getHeight()/2+150,150,80);
		
		
		game.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
	public void dispose() {
		game.dispose();
		
	}

}
