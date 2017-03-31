package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import be.ac.umons.sgl.lazer.g06.game.LazerChallenge;
/**
 * Add this listener to a scene2d object to make it run actions on mouse click
 */
public class MyClickListener extends ClickListener {
	protected LazerChallenge game;
	protected String action;
	/**
	 * Create listener of mouseButton to perform action.
	 * @param mouseButton Mouse button that has to be pressed for this listener to be called, from {@link com.badlogic.gdx.Input.Buttons}
	 * @param action Action to run.
	 */
	public MyClickListener(int mouseButton, String action) {
		super(mouseButton);
		this.game = LazerChallenge.getInstance();
		this.action = action;
	}
	/**
	 * Run action.
	 */
	public void clicked(InputEvent event, float x, float y) {
		game.act(action);
	}
}
