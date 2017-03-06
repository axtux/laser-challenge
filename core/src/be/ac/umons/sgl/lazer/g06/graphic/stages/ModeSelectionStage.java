package be.ac.umons.sgl.lazer.g06.graphic.stages;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

public class ModeSelectionStage extends AbstractStage {

	public ModeSelectionStage(LazerChallenge game) {
		super(game, "Mode de jeu", "MENU_CONNECTION");
		
		this.addMenuButton("Arcade", "MODE_ARCADE");
		this.addMenuButton("Entrainement", "MODE_TRAINING");
	}

}
