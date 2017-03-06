package be.ac.umons.sgl.lazer.g06.graphic.stages;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

public class LevelSelectionStage extends AbstractStage {

	public LevelSelectionStage(LazerChallenge game) {
		super(game, "Mode "+game.getMode()+" : Choix du niveau", "MENU_MODE_SELECTION");
		
		addMenuButton("Level 1", "LEVEL_1");
		addMenuButton("Level 2", "LEVEL_2");
		addMenuButton("Level 3", "LEVEL_3");
	}

}
