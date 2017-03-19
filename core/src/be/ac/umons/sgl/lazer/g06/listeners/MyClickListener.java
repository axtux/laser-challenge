package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;
/**
 * Add this listener to scene2d object to make it do actions
 */
public class MyClickListener extends ClickListener {
	LazerChallenge game;
	String action;
	/**
	 * Create listener.
	 * @param mouseButton Button from @Input.Buttons
	 * @param action Action to send to game when mouseButton is clicked.
	 */
	public MyClickListener(int mouseButton, String action) {
		super(mouseButton);
		this.game = LazerChallenge.getInstance();
		this.action = action;
	}
	
	public void clicked(InputEvent event, float x, float y) {
		game.act(action);
	}
}
