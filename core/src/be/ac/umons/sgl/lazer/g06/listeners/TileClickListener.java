package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
/**
 * Listener to keep track of tile clicks.
 */
public class TileClickListener extends MyClickListener {
	int tileX, tileY;
	/**
	 * Create listener.
	 * @param mouseButton Button from @Input.Buttons
	 * @param tileX X coordinate
	 * @param tileY Y coordinate
	 * @param action Action to send to game when mouseButton is clicked.
	 */
	public TileClickListener(int mouseButton, int tileX, int tileY, String action) {
		super(mouseButton, action);
		this.tileX = tileX;
		this.tileY = tileY;
	}
	
	public void clicked(InputEvent event, float x, float y) {
		String tile = Integer.toString(tileX)+"x"+Integer.toString(tileY);
		Gdx.app.debug("TileClickListener from "+tile, "ACTION : "+action);
		
		Actor actor = event.getListenerActor();
		
		switch(action) {
		case "ACTION_ROTATE":
			actor.rotateBy(90);
			break;
		}
	}
}
