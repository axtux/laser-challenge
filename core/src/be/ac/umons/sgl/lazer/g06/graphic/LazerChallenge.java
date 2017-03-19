package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.game.Level;
import be.ac.umons.sgl.lazer.g06.graphic.stages.AbstractStage;
import be.ac.umons.sgl.lazer.g06.listeners.ActionListener;
import be.ac.umons.sgl.lazer.g06.listeners.MyActionListener;
import be.ac.umons.sgl.lazer.g06.users.User;

public class LazerChallenge extends Game {
	// default to fullHD resolution
	public static int WIDTH = 1920;
	public static int HEIGHT = 1080;
	// singleton pattern instance
	private static LazerChallenge instance;
	// perform actions from UI, pattern strategy
	ActionListener al;
	
	AbstractStage stage;
	MySkin skin;
	User user;
	String mode = "";
	Level level;
	
	public LazerChallenge() {
		LazerChallenge.instance = this;
		al = new MyActionListener(this);
	}
	
	public static LazerChallenge getInstance() {
		return instance;
	}
	
	public void create () {
		/*
		Gdx.app.setLogLevel(Application.LOG_NONE);
		//*/Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		Gdx.app.debug("LOCAL_PATH", Gdx.files.getLocalStoragePath());
		
		skin = new MySkin("josefin_sans_bold");
		act("MENU_LOGINS");
		//act("ACTION_LEVEL_PLAY");
		
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
	
	public void setMode(String mode) {
		mode = mode.toUpperCase();
		switch(mode) {
		case "TRAINING":
		case "ARCADE":
			this.mode = mode;
			break;
		default:
			throw new GdxRuntimeException("Invalid mode "+mode+". Should be TRAINING or ARCADE.");
		}
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void setUser(User user) {
		if(this.user != null) {
			this.user.logout();
		}
		
		this.user = user;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	public void render() {
		super.render();
		Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
	}
	
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
	
	public void setStage(AbstractStage newStage) {
		if(newStage == stage) {
			return;
		}
		
		Stage oldStage = stage;
		stage = newStage;
		
		Gdx.input.setInputProcessor(stage);
		stage.draw();
		
		if(oldStage != null) {
			oldStage.dispose();
		}
		
	}
	
	public AbstractStage getStage() {
		return stage;
	}
	
	public void act(String action) {
		al.act(action);
	}
	
}