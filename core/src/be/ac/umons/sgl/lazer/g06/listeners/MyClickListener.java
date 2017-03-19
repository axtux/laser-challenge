package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

public class MyClickListener extends ClickListener {
	LazerChallenge game;
	String action;
	
	public MyClickListener(int mouseButton, String action) {
		super(mouseButton);
		this.game = LazerChallenge.getInstance();
		this.action = action;
	}
	
	public void clicked(InputEvent event, float x, float y) {
		game.act(action);
	}
}
