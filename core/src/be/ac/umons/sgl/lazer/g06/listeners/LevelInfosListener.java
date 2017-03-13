package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

public class LevelInfosListener extends ClickListener {
	LazerChallenge game;
	String mode;
	String type;
	int number;
	boolean launch;
	
	public LevelInfosListener(LazerChallenge game, int mouseButton, String mode, String type, int number, boolean launch) {
		super(mouseButton);
		this.game = game;
		this.mode = mode;
		this.type = type;
		this.number = number;
		this.launch = launch;
	}
	
	public void clicked(InputEvent event, float x, float y) {
		game.levelInfo(mode, type, number, launch);
	}
}
