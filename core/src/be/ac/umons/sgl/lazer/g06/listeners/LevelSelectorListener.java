package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

import be.ac.umons.sgl.lazer.g06.game.Level;
import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

public class LevelSelectorListener extends MyClickListener {
	Level level;

	public LevelSelectorListener(LazerChallenge game, int mouseButton, String action, Level level) {
		super(game, mouseButton, action);
		this.level = level;
	}
	
	public void clicked(InputEvent event, float x, float y) {
		game.setLevel(level);
		super.clicked(event, x, y);
	}
}
