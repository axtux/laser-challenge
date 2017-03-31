package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

import be.ac.umons.sgl.lazer.g06.game.Level;
/**
 * Select level and run action.
 */
public class LevelSelectorListener extends MyClickListener {
	private Level level;
	/**
	 * Create listener of mouseButton to perform action after selecting level.
	 * @param mouseButton Mouse button that has to be pressed for this listener to be called, from {@link com.badlogic.gdx.Input.Buttons}
	 * @param action Action to run after selecting level.
	 * @param level Level to select.
	 */
	public LevelSelectorListener(int mouseButton, String action, Level level) {
		super(mouseButton, action);
		this.level = level;
	}
	/**
	 * Select level and run action.
	 */
	public void clicked(InputEvent event, float x, float y) {
		game.setLevel(level);
		super.clicked(event, x, y);
	}
}
