package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

public class TileClickListener extends MyClickListener {
	int tileX, tileY;
	
	public TileClickListener(int mouseButton, int tileX, int tileY, String action) {
		super(null, mouseButton, action);
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
