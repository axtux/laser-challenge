package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import be.ac.umons.sgl.lazer.g06.graphic.stages.LoginsStage;
import be.ac.umons.sgl.lazer.g06.game.Level;
import be.ac.umons.sgl.lazer.g06.graphic.stages.AbstractStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelFinishedStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelInfosStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelPausedStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelPlayingStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelsStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LocalLoginStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.ModesStage;
import be.ac.umons.sgl.lazer.g06.users.LocalUser;
import be.ac.umons.sgl.lazer.g06.users.LocalUser.LoginException;
import be.ac.umons.sgl.lazer.g06.users.User;

public class LazerChallenge extends Game {
	// default to fullHD resolution
	public static int WIDTH = 1920;
	public static int HEIGHT = 1080;
	
	AbstractStage stage;
	MySkin skin;
	User user;
	String mode = "";
	
	Level level;
	
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
	
	public void levelInfo(String mode, String type, int number, boolean launch) {
		AbstractStage s;
		if(launch) {
			// TODO launch game
			s = null;
		} else {
			s = new LevelInfosStage(this, type, number);
		}
		setStage(s);
	}
	
	public void act(String action) {
		Gdx.app.debug("ACTION", action);
		if(action.startsWith("ACTION_LEVEL_")) {
			
		}
		
		switch(action) {
		
		// ACTIONs from headers
		case "ACTION_EXIT":
			Gdx.app.exit();
		case "ACTION_LOGOUT":
			setUser(null);
			act("MENU_LOGINS");
			break;
		
		case "MENU_LOGINS":
			setStage(new LoginsStage(this));
			break;
		// ACTIONs from MENU_LOGINS
		case "ACTTION_LOGIN_ANONYMOUS":
			setUser(new User());
			act("MENU_MODES");
			break;
		case "ACTTION_LOGIN_LOCAL":
			act("MENU_LOGIN_LOCAL");
			break;
		case "ACTTION_LOGIN_TWITTER":
			//setStage(new Stage(this));
			break;
		
		case "MENU_LOGIN_LOCAL":
			setStage(new LocalLoginStage(this));
			break;
		// ACTIONs from LOGIN_LOCAL
		case "ACTION_LOCAL_LOGIN":
			local_user(false);
			break;
		case "ACTION_LOCAL_SIGNUP":
			local_user(true);
			break;
			
		case "MENU_MODES":
			setStage(new ModesStage(this));
			break;
		// ACTIONs from MENU_MODES
		case "ACTION_MODE_ARCADE":
			mode = "ARCADE";
			act("MENU_LEVELS");
			break;
		case "ACTION_MODE_TRAINING":
			mode = "TRAINING";
			act("MENU_LEVELS");
			break;

		case "MENU_LEVELS":
			setStage(new LevelsStage(this));
			break;

		case "MENU_LEVEL_PAUSE":
			setStage(new LevelPausedStage(this, "ARCADE", 10));
			break;

		case "MENU_LEVEL_FINISHED":
			setStage(new LevelFinishedStage(this, "ARCADE", 10));
			break;
		
		case "ACTION_LEVEL_PLAY":
			setLevel(new Level("levels/standard/level2/g6_A.xml"));
			setStage(new LevelPlayingStage(this, "ARCADE"));
			break;
		
		default:
			Gdx.app.error("ACTION_NOT_IMPLEMENTED", action);
		}
		
	}
	
	private void local_user(boolean create) {
		String username = stage.getFieldValue("USERNAME");
		String password = stage.getFieldValue("PASSWORD");
		
		try {
			User user = new LocalUser(username, password, create);
			setUser(user);
			setStage(new ModesStage(this));
		} catch (LoginException e) {
			setStage(new LocalLoginStage(this, username, password, e.getMessage()));
			
			//e.printStackTrace();
		}
		
	}
	
}