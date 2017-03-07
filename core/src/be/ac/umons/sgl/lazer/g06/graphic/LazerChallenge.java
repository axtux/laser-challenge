package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import be.ac.umons.sgl.lazer.g06.graphic.stages.ConnexionStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelSelectionStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.ModeSelectionStage;
import be.ac.umons.sgl.lazer.g06.users.User;

public class LazerChallenge extends Game {
	public static int WIDTH=1000;
	public static int HEIGHT=720;
	Stage stage;
	MySkin skin;
	User user;
	String mode = "";
	
	public void create () {
		skin = new MySkin();
		act("MENU_CONNECTION");
		/*
		Gdx.app.setLogLevel(Application.LOG_NONE);
		//*/Gdx.app.setLogLevel(Application.LOG_DEBUG);
	}
	
	public MySkin getSkin() {
		return skin;
	}

	public User getUser() {
		return user;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
		render();
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
		if(newStage == stage) {
			return;
		}
		
		Stage oldStage = stage;
		
		Gdx.input.setInputProcessor(newStage);
		this.stage = newStage;
		this.render();
		
		if(oldStage != null) {
			oldStage.dispose();
		}
	}
	
	public void act(String action) {
		Gdx.app.debug("ACTION", action);
		switch(action) {
		
		case "EXIT":
			Gdx.app.exit();
		case "LOGOUT":
			logout();
		case "MENU_CONNECTION":
			setStage(new ConnexionStage(this));
			break;
		
		// from connection menu
		case "CONNECTION_ANONYMOUS":
			setUser(new User());
			act("MENU_MODE_SELECTION");
			break;
		case "CONNECTION_LOCAL":
			//setStage(new Stage(this));
			break;
		case "CONNECTION_TWITTER":
			//setStage(new Stage(this));
			break;
		
		case "MENU_MODE_SELECTION":
			setStage(new ModeSelectionStage(this));
			break;
		
		// from mode selection
		case "MODE_ARCADE":
			mode = "ARCADE";
			act("MENU_LEVEL_SELECTION");
			break;
		case "MODE_TRAINING":
			mode = "TRAINING";
			act("MENU_LEVEL_SELECTION");
			break;
		
		case "MENU_LEVEL_SELECTION":
			setStage(new LevelSelectionStage(this));
			break;
		
		default:
			Gdx.app.error("ACTION_NOT_IMPLEMENTED", action);
		}
		
	}
	
	private void logout() {
		if(user == null) {
			return;
		}
		
		user.logout();
		user = null;
	}
}