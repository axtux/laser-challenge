package be.ac.umons.sgl.lazer.g06.graphic.stages;

public class LevelInfosStage extends AbstractLevelStage {
	public LevelInfosStage() {
		super("Informations sur le niveau");
		
		addHeaderButton("Retour", "MENU_LEVELS");
		
		addDoubleLabel("Nom", level.getName());
		
		int width = level.getMap().getIntProp("width");
		int height = level.getMap().getIntProp("height");
		addDoubleLabel("Taille", Integer.toString(width)+"x"+Integer.toString(height));
		
		addDoubleLabel("Mode", game.getMode().toString());
		if(game.getMode().score()) {
			// TODO load score
			addDoubleLabel("Score", "");
		}
		
		addDoubleButton("Charger", "ACTION_LEVEL_LOAD", "Lancer", "ACTION_LEVEL_LAUNCH");
	}
	
}
