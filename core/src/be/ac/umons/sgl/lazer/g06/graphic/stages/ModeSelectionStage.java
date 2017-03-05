package be.ac.umons.sgl.lazer.g06.graphic;

public class ModeSelectionStage extends AbstractStage {

	public ModeSelectionStage(LazerChallenge game) {
		super(game);

		this.addButton("Arcade", "MODE_ARCADE");
		this.addButton("Entrainement", "MODE_TRAINING");
	}

}
