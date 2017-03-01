package be.ac.umons.sgl.lazer.g06.Graphic;
//import be.ac.umons.sgl.lazer.g06.desktop.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;


public class ChoiceLevel implements Screen {
	LazerChallenge game;
	Texture deconnexionButtonOff,deconnexionButtonOn,niveauStandard,niveauAvances,modeDeJeuOff,modeDeJeuOn,
	niveau1_On,niveau1_Off,niveau2_On,niveau2_Off,niveau3_On,niveau3_Off,niveau4_On,niveau4_Off,niveau5_On,niveau5_Off,
	niveau6_On,niveau6_Off,niveau7_On,niveau7_Off,niveau8_On,niveau8_Off,niveau9_On,niveau9_Off,niveau10_On,niveau10_Off;
	
	public ChoiceLevel(LazerChallenge game){
		this.game=game;
	}
	public void show() {
		deconnexionButtonOff = new Texture("ModeMenu/deconnexion_button_off.png");
		deconnexionButtonOn = new Texture("ModeMenu/deconnexion_button_on.png");
		niveauStandard = new Texture("ChoiceLevel/niveauxstandard.png");
		niveauAvances = new Texture("ChoiceLevel/niveauxavances.png");
		modeDeJeuOff= new Texture("ChoiceLevel/mode_de_jeu.png");
		modeDeJeuOn= new Texture("ChoiceLevel/mode_de_jeu_on.png");
		niveau1_Off= new Texture("ChoiceLevel/niveau1_off.png");
		niveau1_On= new Texture("ChoiceLevel/niveau1_on.png");
		niveau2_On= new Texture("ChoiceLevel/niveau2_on.png");
		niveau2_Off= new Texture("ChoiceLevel/niveau2_off.png");
		niveau3_On= new Texture("ChoiceLevel/niveau3_on.png");
		niveau3_Off= new Texture("ChoiceLevel/niveau3_off.png");
		niveau4_On= new Texture("ChoiceLevel/niveau4_on.png");
		niveau4_Off= new Texture("ChoiceLevel/niveau4_off.png");
		niveau5_On= new Texture("ChoiceLevel/niveau5_on.png");
		niveau5_Off= new Texture("ChoiceLevel/niveau5_off.png");
		niveau6_On= new Texture("ChoiceLevel/niveau6_on.png");
		niveau6_Off= new Texture("ChoiceLevel/niveau6_off.png");
		niveau7_On= new Texture("ChoiceLevel/niveau7_on.png");
		niveau7_Off= new Texture("ChoiceLevel/niveau7_off.png");
		niveau8_On= new Texture("ChoiceLevel/niveau8_on.png");
		niveau8_Off= new Texture("ChoiceLevel/niveau8_off.png");
		niveau9_On= new Texture("ChoiceLevel/niveau9_on.png");
		niveau9_Off= new Texture("ChoiceLevel/niveau9_off.png");
		niveau10_On= new Texture("ChoiceLevel/niveau10_on.png");
		niveau10_Off= new Texture("ChoiceLevel/niveau10_off.png");
		
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.getProjectionMatrix().setToOrtho2D(0, 0,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
		game.batch.draw(niveauStandard,Gdx.graphics.getWidth()/2-390,Gdx.graphics.getHeight()/2+170,230,100);
		game.batch.draw(niveauAvances,Gdx.graphics.getWidth()/2-390,Gdx.graphics.getHeight()/2-170,230,100);
		
		//Gestion du bouton Deconnexion
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+360) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)+210)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)-240 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-320){
					
			game.batch.draw(deconnexionButtonOn,Gdx.graphics.getWidth()/2+210,Gdx.graphics.getHeight()/2+250,150,80);
					
			if ( Gdx.input.isTouched()){
				this.dispose();
				game.setScreen(new MainMenu(this.game));
					}
			}
					
			else 
				game.batch.draw(deconnexionButtonOff,Gdx.graphics.getWidth()/2+210,Gdx.graphics.getHeight()/2+250,150,80);
		
		//gestion du bouton Mode De Jeu
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+162) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)+12)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)-240 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-320){
					
			game.batch.draw(modeDeJeuOn,Gdx.graphics.getWidth()/2+12,Gdx.graphics.getHeight()/2+250,150,80);
					
			if ( Gdx.input.justTouched()){
				this.dispose();
				game.setScreen(new ModeMenu(this.game));
					}
			}
					
			else 
				game.batch.draw(modeDeJeuOff,Gdx.graphics.getWidth()/2+12,Gdx.graphics.getHeight()/2+250,150,80);
		
		//Gestion bouton niveau1
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)-210) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-360)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)-120 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-200){
					
			game.batch.draw(niveau1_On,Gdx.graphics.getWidth()/2-360,Gdx.graphics.getHeight()/2+100,150,80);
					
			if ( Gdx.input.justTouched()){
				this.dispose();
				//game.setScreen(new ModeMenu(this.game));
					}
			}
					
			else 
				game.batch.draw(niveau1_Off,Gdx.graphics.getWidth()/2-360,Gdx.graphics.getHeight()/2+100,150,80);
		
		
		
		//Gestion bouton niveau3
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+110) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-60)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)-120 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-200){
					
			game.batch.draw(niveau3_On,Gdx.graphics.getWidth()/2-60,Gdx.graphics.getHeight()/2+100,150,80);
					
			if ( Gdx.input.justTouched()){
				this.dispose();
				//game.setScreen(new ModeMenu(this.game));
					}
			}
					
			else 
				game.batch.draw(niveau3_Off,Gdx.graphics.getWidth()/2-60,Gdx.graphics.getHeight()/2+100,150,80);
		

		//Gestion bouton niveau4
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+240) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)+90)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)-120 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-200){
					
			game.batch.draw(niveau4_On,Gdx.graphics.getWidth()/2+90,Gdx.graphics.getHeight()/2+100,150,80);
					
			if ( Gdx.input.justTouched()){
				this.dispose();
				//game.setScreen(new ModeMenu(this.game));
					}
			}
					
			else 
				game.batch.draw(niveau4_Off,Gdx.graphics.getWidth()/2+90,Gdx.graphics.getHeight()/2+100,150,80);
		

		//Gestion bouton niveau2
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)-110) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-210)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)-120 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-200){
					
			game.batch.draw(niveau2_On,Gdx.graphics.getWidth()/2-210,Gdx.graphics.getHeight()/2+100,150,80);
					
			if ( Gdx.input.justTouched()){
				this.dispose();
				//game.setScreen(new ModeMenu(this.game));
					}
			}
					
			else 
				game.batch.draw(niveau2_Off,Gdx.graphics.getWidth()/2-210,Gdx.graphics.getHeight()/2+100,150,80);
		

		//Gestion bouton niveau5
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+390) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)+240)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2)-120 && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-200){
					
			game.batch.draw(niveau5_On,Gdx.graphics.getWidth()/2+240,Gdx.graphics.getHeight()/2+100,150,80);
					
			if ( Gdx.input.justTouched()){
				this.dispose();
				//game.setScreen(new ModeMenu(this.game));
					}
			}
					
			else 
				game.batch.draw(niveau5_Off,Gdx.graphics.getWidth()/2+240,Gdx.graphics.getHeight()/2+100,150,80);
		

		//Gestion bouton niveau6
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)-210) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-360)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2) && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-60){
					
			game.batch.draw(niveau6_On,Gdx.graphics.getWidth()/2-360,Gdx.graphics.getHeight()/2-20,150,80);
					
			if ( Gdx.input.justTouched()){
				this.dispose();
				//game.setScreen(new ModeMenu(this.game));
					}
			}
					
			else 
				game.batch.draw(niveau6_Off,Gdx.graphics.getWidth()/2-360,Gdx.graphics.getHeight()/2-20,150,80);
		

		//Gestion bouton niveau7
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)-110) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-210)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2) && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-60){
					
			game.batch.draw(niveau7_On,Gdx.graphics.getWidth()/2-210,Gdx.graphics.getHeight()/2-20,150,80);
					
			if ( Gdx.input.justTouched()){
				this.dispose();
				//game.setScreen(new ModeMenu(this.game));
					}
			}
					
			else 
				game.batch.draw(niveau7_Off,Gdx.graphics.getWidth()/2-210,Gdx.graphics.getHeight()/2-20,150,80);
		
		
		//Gestion bouton niveau8
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+110) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)-60)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2) && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-60){
					
			game.batch.draw(niveau8_On,Gdx.graphics.getWidth()/2-60,Gdx.graphics.getHeight()/2-20,150,80);
					
			if ( Gdx.input.justTouched()){
				this.dispose();
				//game.setScreen(new ModeMenu(this.game));
					}
			}
					
			else 
				game.batch.draw(niveau8_Off,Gdx.graphics.getWidth()/2-60,Gdx.graphics.getHeight()/2-20,150,80);
		

		//Gestion bouton niveau9
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+240) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)+90)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2) && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-60){
					
			game.batch.draw(niveau9_On,Gdx.graphics.getWidth()/2+90,Gdx.graphics.getHeight()/2-20,150,80);
					
			if ( Gdx.input.justTouched()){
				this.dispose();
				//game.setScreen(new ModeMenu(this.game));
					}
			}
					
			else 
				game.batch.draw(niveau9_Off,Gdx.graphics.getWidth()/2+90,Gdx.graphics.getHeight()/2-20,150,80);
		

		//Gestion bouton niveau10
		if (Gdx.input.getX() < ((Gdx.graphics.getWidth()/2)+390) && Gdx.input.getX()> ((Gdx.graphics.getWidth()/2)+240)
				&& Gdx.input.getY() < (Gdx.graphics.getHeight()/2) && Gdx.input.getY() > (Gdx.graphics.getHeight()/2)-60){
					
			game.batch.draw(niveau10_On,Gdx.graphics.getWidth()/2+240,Gdx.graphics.getHeight()/2-20,150,80);
					
			if ( Gdx.input.justTouched()){
				this.dispose();
				//game.setScreen(new ModeMenu(this.game));
					}
			}
					
			else 
				game.batch.draw(niveau10_Off,Gdx.graphics.getWidth()/2+240,Gdx.graphics.getHeight()/2-20,150,80);
				
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
		// TODO Auto-generated method stub
		game.dispose();
	}

}
