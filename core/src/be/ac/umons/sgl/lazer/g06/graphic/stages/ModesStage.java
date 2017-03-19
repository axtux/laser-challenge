package be.ac.umons.sgl.lazer.g06.graphic.stages;

public class ModesStage extends AbstractStage {
	
	public ModesStage() {
		super("Mode de jeu");
		
		this.addMenuButton("Arcade", "ACTION_MODE_ARCADE");
		this.addMenuButton("Entrainement", "ACTION_MODE_TRAINING");
	}

}
