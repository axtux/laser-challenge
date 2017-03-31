package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import be.ac.umons.sgl.lazer.g06.game.Position;
/**
 * Listener to keep track of tile clicks.
 */
public class MapClickListener extends MyClickListener {
	/**
	 * Keep track of last pressed button to check it out
	 */
	static Button previous;
	/**
	 * Position of the button to send to application.
	 */
	private Position position;
	/**
	 * Create listener of mouseButton to perform action with position.
	 * @param mouseButton Mouse button that has to be pressed for this listener to be called, from {@link com.badlogic.gdx.Input.Buttons}
	 * @param position Position to run action on.
	 * @param action Action to run with position.
	 */
	public MapClickListener(int mouseButton, Position position, String action) {
		super(mouseButton, action);
		this.position = position;
	}
	/**
	 * Run action on position.
	 */
	public void clicked(InputEvent event, float x, float y) {
		// check out previously checked button
		if(previous != null) {
			previous.setChecked(false);
		}
		previous = (Button) event.getTarget();
		
		Gdx.app.debug("MapClickListener from "+position.toString(), "ACTION : "+action);
		
		switch(action) {
		case "ACTION_LEVEL_SELECT":
			if(game.getLevel().moving()) {
				Gdx.app.debug("MapClickListener.clicked", "Moving to position");
				game.getLevel().moveSelectedTo(position);
			} else {
				Gdx.app.debug("MapClickListener.clicked", "Selecting position");
				game.getLevel().select(position);
			}
			break;
		case "ACTION_LEVEL_ROTATE":
			Gdx.app.debug("MapClickListener.clicked", "Rotating position");
			game.getLevel().select(position);
			game.getLevel().rotateSelected();
			break;
		}
	}
}
