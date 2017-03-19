package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

public class LevelInfosStage extends AbstractStage {
	boolean displayScore;
	
	public LevelInfosStage(LazerChallenge game) {
		super(game, "Informations sur le niveau");
		
		if(game.getLevel() == null) {
			throw new GdxRuntimeException("game level cannot be null.");
		}
		if(game.getMode() == null) {
			throw new GdxRuntimeException("game mode cannot be null.");
		}
		displayScore = game.getMode().equals("ARCADE");
		
		addHeaderButton("Retour", "MENU_LEVELS");
		
		addDoubleLabel("Nom", game.getLevel().getName());
		if(displayScore) {
			// TODO load score
			addDoubleLabel("Score", "");
		}
		
		addDoubleButton("Charger", "ACTION_LEVEL_LOAD", "Lancer", "ACTION_LEVEL_LAUNCH");
	}
	
}
