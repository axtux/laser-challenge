package be.ac.umons.sgl.lazer.g06.graphic.stages;

import be.ac.umons.sgl.lazer.g06.Time;

public class LevelInfosStage extends AbstractLevelStage {
	public LevelInfosStage() {
		super("Informations sur le niveau");
		
		addHeaderButton("Retour", "MENU_LEVELS");
		
		addDoubleLabel("Nom", level.getName());
		
		int width = level.getMap().getWidth();
		int height = level.getMap().getHeight();
		addDoubleLabel("Taille", Integer.toString(width)+"x"+Integer.toString(height));
		
		addDoubleLabel("Mode", game.getMode().toString());
		if(game.getMode().hasScore()) {
			addDoubleLabel("Temps", Time.prettyTime(level.getTime(), false, false));
			// TODO load score
			addDoubleLabel("Score", "");
		}
		
		addDoubleButton("Charger", "ACTION_LEVEL_LOAD", "Lancer", "ACTION_LEVEL_LAUNCH");
	}
	
}
