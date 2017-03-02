package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenu implements Screen {
	
	LazerChallenge game;
	Texture playButtonOn;
	Texture playButtonOff, exitButtonOn, exitButtonOff, logo,compteLocalOff,
			compteLocalOn,compteFacebookOff,compteFacebookOn,connexion;
	
	static MainMenu instance;
	
	public MainMenu(LazerChallenge game){
		this.game=game;
	}
	
	public static MainMenu getInstance(LazerChallenge game) {
		if(MainMenu.instance == null) {
			MainMenu.instance = new MainMenu(game);
		}
		
		return MainMenu.instance;
	}
	
	public void show() {
		// Mise en place de l image de fond
		//img = new Texture("badlogic.jpg");
		
		//creation des images des boutons
		playButtonOn=new Texture("MainMenu/connexion_anonyme_button_on.png");
		playButtonOff=new Texture("MainMenu/connexion_anonyme_button.png");
		compteLocalOff= new Texture("MainMenu/compte_local_off.png");
		compteLocalOn= new Texture("MainMenu/compte_local_on.png");
		compteFacebookOff = new Texture("MainMenu/connexion_facebook_button_off.png");
		compteFacebookOn = new Texture("MainMenu/connexion_facebook_button_on.png");
		logo = new Texture("MainMenu/LazerChallengeLogo.png");
		connexion = new Texture("MainMenu/connexion.png");
	}
	
	public void render(float delta) {
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		TextButton btn = new TextButton("Coucou petite perruche !", textButtonStyle);
		BitmapFont font = new BitmapFont();
		textButtonStyle.font = font;
		
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//On commence le batch qui va traiter l image ou les images
		game.batch.begin();
		game.batch.draw(logo,Gdx.graphics.getWidth()/2-190,Gdx.graphics.getHeight()/2+250,400,100);
		game.batch.draw(connexion,Gdx.graphics.getWidth()/2-390,Gdx.graphics.getHeight()/2+170,200,100);
		game.stage.addActor(btn);
		game.batch.getProjectionMatrix().setToOrtho2D(0, 0,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
		
		//Gestion du bouton Anonyme
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+90) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-110)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)-50 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-150){
			
			game.batch.draw(playButtonOn,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2+50,250,100);
			
			if ( Gdx.input.justTouched()){
				this.dispose();
				game.setScreen(new ModeMenu(game));
				}
			}
			
		else 
			game.batch.draw(playButtonOff,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2+50,250,100);
		
		
		//Gestion du bouton Compte local
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+90) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-110)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)+90 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)
				) {
			
			game.batch.draw(compteLocalOn,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2-100,250,100);
			
			if ( Gdx.input.justTouched()){
				this.dispose();
				game.setScreen(new ModeMenu(game));
				}
			}
			
		else 
			game.batch.draw(compteLocalOff,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2-100,250,100);
		
		
		//Gestion du bouton Compte facebook
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+130) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-110)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)+230 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)+160
				){
			
			game.batch.draw(compteFacebookOn,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2-280,250,130);
			
			if ( Gdx.input.justTouched()){
				this.dispose();
				game.setScreen(new Window());
				}
			}
			
		else 
			game.batch.draw(compteFacebookOff,Gdx.graphics.getWidth()/2-110,Gdx.graphics.getHeight()/2-280,250,130);
		
		game.batch.end();
		
	}
	public void dispose () {
		game.dispose();
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
