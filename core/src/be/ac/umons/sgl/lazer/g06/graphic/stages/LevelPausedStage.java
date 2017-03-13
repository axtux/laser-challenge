package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.listeners.MyClickListener;

public class LevelPausedStage extends AbstractStage {
	String mode;
	boolean score;
	
	public LevelPausedStage(LazerChallenge game, String type, int level) {
		super(game, "Niveau");
		setMode(game.getMode());
		
		addDoubleLabel("Niveau", Integer.toString(level));
		if(score) {
			addDoubleLabel("Score", Integer.toString(level));
		}
		
		addDoubleLabel("Other info", "TEST_OK");
		
		addDoubleButton("Recommencer", "ACTION_RESET", "Reprendre", "ACTION_UNPAUSE");
	}
	
	private boolean setMode(String mode) {
		score = false;
		
		switch(mode) {
		case "ARCADE":
			score = true;
		case "TRAINING":
			this.mode = mode;
			return true;
		default:
			Gdx.app.error("LevelsStage.setMode", "mode "+mode+" not implemented");
			return false;
		}
	}
	

}
