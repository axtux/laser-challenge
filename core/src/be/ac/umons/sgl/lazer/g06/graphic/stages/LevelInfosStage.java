package be.ac.umons.sgl.lazer.g06.graphic.stages;

import be.ac.umons.sgl.lazer.g06.Time;
/**
 * create screen with description of level
 *
 */
public class LevelInfosStage extends AbstractLevelStage {
	public LevelInfosStage() {
		super("Informations sur le niveau");
		
		addHeaderButton("Retour", "MENU_LEVELS");
		
		addDoubleLabel("Nom", level.getLabel());
		
		int width = level.getMap().getWidth();
		int height = level.getMap().getHeight();
		addDoubleLabel("Taille", Integer.toString(width)+"x"+Integer.toString(height));
		addDoubleLabel("Difficulté", level.getDifficulty().toString());
		
		addDoubleLabel("Mode", game.getMode().toString());
		if(game.getMode().hasScore()) {
			int score = game.getUser().loadScore(level.getLabel());
			addDoubleLabel("Temps", Time.prettyTime(level.getTime(), false, false));
			addDoubleLabel("Score", Integer.toString(score));
		}
		
		if(level.canLoad()) {
			addDoubleButton("Charger", "ACTION_LEVEL_LOAD", "menu", "Lancer", "ACTION_LEVEL_START", "menu");
		} else {
			addMenuButton("Lancer", "ACTION_LEVEL_START").colspan(2);
		}
	}
	
}
