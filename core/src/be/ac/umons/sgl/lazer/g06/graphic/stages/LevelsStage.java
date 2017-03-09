package be.ac.umons.sgl.lazer.g06.graphic.stages;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

public class LevelsStage extends AbstractStage {
	
	public LevelsStage(LazerChallenge game) {
		super(game, "Mode "+game.getMode()+" : Choix du niveau", "MENU_MODES");
		
		addMenuButton("Level 1", "ACTION_LEVEL_1");
		addMenuButton("Level 2", "ACTION_LEVEL_2");
		addMenuButton("Level 3", "ACTION_LEVEL_3");
	}

}
