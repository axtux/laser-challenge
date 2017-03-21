package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import be.ac.umons.sgl.lazer.g06.game.Position;
/**
 * Listener to keep track of tile clicks.
 */
public class TileClickListener extends MyClickListener {
	Position position;
	/**
	 * Create listener.
	 * @param mouseButton Button from @Input.Buttons
	 * @param position Position of tile.
	 * @param action Action to send to game when mouseButton is clicked.
	 */
	public TileClickListener(int mouseButton, Position position, String action) {
		super(mouseButton, action);
		this.position = position;
	}
	
	public void clicked(InputEvent event, float x, float y) {
		String pos = position.getLocation().toString()+" : "+Integer.toString(position.getX())+"x"+Integer.toString(position.getY());
		Gdx.app.debug("TileClickListener from "+pos, "ACTION : "+action);
		
		Actor actor = event.getListenerActor();
		
		switch(action) {
		case "ACTION_LEVEL_TILE_ROTATE":
			Gdx.app.debug("TileClickListener.clicked", "Rotating tile");
			actor.rotateBy(90);
			break;
		}
	}
}
