package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.graphic.MySkin;
import be.ac.umons.sgl.lazer.g06.graphic.stages.AbstractStage;
import be.ac.umons.sgl.lazer.g06.listeners.ActionListener;
import be.ac.umons.sgl.lazer.g06.listeners.MyActionListener;
import be.ac.umons.sgl.lazer.g06.users.User;

/**
 * Controller of the game.
 * Contains skin, current displayed stage, current connected user, current mode and current selected level.
 */
public class LazerChallenge extends Game {
	// default to fullHD resolution
	public static int WIDTH = 1920;
	public static int HEIGHT = 1080;
	// singleton pattern instance
	private static LazerChallenge instance;
	
	// perform actions from UI
	private ActionListener al;
	// contains drawables
	private MySkin skin;
	// state management
	private AbstractStage stage;
	private User user;
	private Level.Mode mode;
	private Level level;
	/**
	 * Check single instance and save it statically.
	 */
	public LazerChallenge() {
		if(instance != null) {
			throw new GdxRuntimeException("Singleton pattern does not allow multiple intances.");
		}
		LazerChallenge.instance = this;
	}
	/**
	 * @return Singleton pattern instance.
	 */
	public static LazerChallenge getInstance() {
		if(instance == null) {
			instance = new LazerChallenge();
		}
		return instance;
	}
	/**
	 * Set log level, create application skin, set action listener, load LevelTypes, Levels and display login menu.
	 */
	public void create () {
		/*
		Gdx.app.setLogLevel(Application.LOG_NONE);
		//*/Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// know where you are
		Gdx.app.debug("LOCAL_PATH", Gdx.files.getLocalStoragePath());
		
		skin = new MySkin("josefin_sans_bold");
		al = new MyActionListener();
		
		// load files from disk
		act("ACTION_LOAD_LEVEL_TYPES");
		act("ACTION_LOAD_LEVELS");
		
		// display login menu
		act("MENU_LOGINS");
	}
	/**
	 * @return MySkin instance created at the beginning.
	 */
	public MySkin getSkin() {
		return skin;
	}
	/**
	 * @return Currently displayed stage.
	 */
	public AbstractStage getStage() {
		return stage;
	}
	/**
	 * @return Current user.
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @return Mode selected by user.
	 */
	public Level.Mode getMode() {
		return mode;
	}
	/**
	 * Save mode here because mode is selected before level is created.
	 * @param mode The mode within which the player want to play.
	 */
	public void setMode(Level.Mode mode) {
		this.mode = mode;
	}
	/**
	 * @return Current level being played.
	 */
	public Level getLevel() {
		return level;
	}
	/**
	 * Set new user and log out old one if not null.
	 * @param user New user
	 */
	public void setUser(User user) {
		if(this.user != null) {
			this.user.logout();
		}
		
		this.user = user;
	}
	/**
	 * Set new level and stop old one if not null.
	 * @param level New level
	 */
	public void setLevel(Level level) {
		if(this.level != null) {
			this.level.stop();
		}
		
		this.level = level;
	}
	/**
	 * Propagates resize to stage viewport.
	 */
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	/**
	 * Clear screen and draw stage.
	 */
	public void render() {
		super.render();
		Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	/**
	 * Free resources
	 */
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
	/**
	 * Set new stage to be displayed and dispose old one if not null.
	 * @param newStage New Stage
	 */
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
	/**
	 * Called by UI elements to make action
	 * @param action String representation of an action.
	 */
	public void act(String action) {
		al.act(action);
	}
	
}