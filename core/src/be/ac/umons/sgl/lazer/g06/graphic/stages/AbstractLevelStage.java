package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.game.Level;
/**
 * To be used by UI stages that needs a level
 */
public class AbstractLevelStage extends AbstractStage {
	protected Level level;
	/**
	 * Empty constructor
	 */
	protected AbstractLevelStage() {
		this("");
	}
	/**
	 * Create stage with title.
	 * @param title Title to display on stage.
	 */
	protected AbstractLevelStage(String title) {
		super(title);
		if(game.getMode() == null) {
			throw new GdxRuntimeException("game mode cannot be null.");
		}
		
		level = game.getLevel();
		if(level == null) {
			throw new GdxRuntimeException("game level cannot be null.");
		}
	}

}
