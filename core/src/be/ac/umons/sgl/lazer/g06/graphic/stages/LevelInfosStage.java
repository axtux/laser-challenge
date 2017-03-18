package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.listeners.MyClickListener;

public class LevelInfosStage extends AbstractStage {
	String mode;
	boolean score;
	
	public LevelInfosStage(LazerChallenge game, String type, int level) {
		super(game, "Niveau");
		setMode(game.getMode());
		
		addHeaderButton("Retour", "MENU_LEVELS");
		
		addDoubleLabel("Niveau", Integer.toString(level));
		if(score) {
			addDoubleLabel("Score", Integer.toString(level));
		}
		
		addDoubleLabel("Autre info", "TEST_OK");
		
		addDoubleButton("Charger", "ACTION_LOAD", "Lancer", "ACTION_LEVEL_PLAY");
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
