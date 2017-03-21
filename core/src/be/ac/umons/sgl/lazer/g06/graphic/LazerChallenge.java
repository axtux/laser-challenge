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

/**
 * Controller of the game
 */
public class LazerChallenge extends Game {
	// default to fullHD resolution
	public static int WIDTH = 1920;
	public static int HEIGHT = 1080;
	// singleton pattern instance
	private static LazerChallenge instance;
	// perform actions from UI, pattern strategy
	ActionListener al;
	
	MySkin skin;
	AbstractStage stage;
	User user;
	Level.Mode mode;
	Level level;
	/**
	 * Check single instance and sage it statically.
	 * Initialize action listener.
	 */
	public LazerChallenge() {
		if(instance != null) {
			throw new GdxRuntimeException("Singleton pattern does not allow multiple intances.");
		}
		LazerChallenge.instance = this;
		al = new MyActionListener();
	}
	/**
	 * @return Singleton pattern instance.
	 */
	public static LazerChallenge getInstance() {
		return instance;
	}
	/**
	 * Set debug values, create application skin and display login menu.
	 */
	public void create () {
		/*
		Gdx.app.setLogLevel(Application.LOG_NONE);
		//*/Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		Gdx.app.debug("LOCAL_PATH", Gdx.files.getLocalStoragePath());
		
		skin = new MySkin("josefin_sans_bold");
		// change UI
		act("MENU_LOGINS");
		//act("ACTION_LEVEL_PLAY");
		// load files from disk
		act("ACTION_LOAD_LEVELS");
		act("ACTION_LOAD_LEVEL_TYPES");
		
	}
	/**
	 * @return Skin created by constructor and containing all UI @Drawable
	 */
	public MySkin getSkin() {
		return skin;
	}
	/**
	 * @return Current stage displayed on screen.
	 */
	public AbstractStage getStage() {
		return stage;
	}
	/**
	 * @return Current user playing game.
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
	 * Save mode here because mode is selected before level is created
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
	 * Log out old user (if it exists) and set new user.
	 * @param user New user
	 */
	public void setUser(User user) {
		if(this.user != null) {
			this.user.logout();
		}
		
		this.user = user;
	}
	/**
	 * Change current level
	 * @param level New level
	 */
	public void setLevel(Level level) {
		this.level = level;
	}
	/**
	 * Called when window is resized. Ask stage viewport to resize.
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
	 * Free resources of old stage (if exists) and set new stage.
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