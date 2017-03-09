package be.ac.umons.sgl.lazer.g06.graphic.stages;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

public class ModesStage extends AbstractStage {
	
	public ModesStage(LazerChallenge game) {
		super(game, "Mode de jeu");
		
		this.addMenuButton("Arcade", "ACTION_MODE_ARCADE");
		this.addMenuButton("Entrainement", "ACTION_MODE_TRAINING");
	}

}
