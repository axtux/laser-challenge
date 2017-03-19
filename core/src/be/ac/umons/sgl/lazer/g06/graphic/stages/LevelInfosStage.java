package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.game.Level;

public class LevelInfosStage extends AbstractStage {
	boolean displayScore;
	
	public LevelInfosStage() {
		super("Informations sur le niveau");
		
		Level level = game.getLevel();
		if(level == null) {
			throw new GdxRuntimeException("game level cannot be null.");
		}
		if(game.getMode() == null) {
			throw new GdxRuntimeException("game mode cannot be null.");
		}
		displayScore = game.getMode().equals("ARCADE");
		
		addHeaderButton("Retour", "MENU_LEVELS");
		
		addDoubleLabel("Nom", level.getName());
		int width = level.getMap().getIntProp("width");
		int height = level.getMap().getIntProp("height");
		addDoubleLabel("Taille", Integer.toString(width)+"x"+Integer.toString(height));
		addDoubleLabel("Mode", game.getMode().toString());
		if(displayScore) {
			// TODO load score
			addDoubleLabel("Score", "");
		}
		
		addDoubleButton("Charger", "ACTION_LEVEL_LOAD", "Lancer", "ACTION_LEVEL_LAUNCH");
	}
	
}
