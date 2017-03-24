package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import be.ac.umons.sgl.lazer.g06.game.Position;
/**
 * Listener to keep track of tile clicks.
 */
public class MapClickListener extends MyClickListener {
	Position position;
	static Button previous;
	/**
	 * Create listener.
	 * @param mouseButton Button from @Input.Buttons
	 * @param position Position of tile.
	 * @param action Action to send to game when mouseButton is clicked.
	 */
	public MapClickListener(int mouseButton, Position position, String action) {
		super(mouseButton, action);
		this.position = position;
	}
	
	public void clicked(InputEvent event, float x, float y) {
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
				game.getLevel().select(position);
				game.getLevel().moving(false);
			} else {
				Gdx.app.debug("MapClickListener.clicked", "Selecting position");
				game.getLevel().select(position);
			}
			break;
		case "ACTION_LEVEL_ROTATE":
			Gdx.app.debug("MapClickListener.clicked", "Rotating position");
			game.getLevel().rotate(position);
			break;
		}
	}
}
