package be.ac.umons.sgl.lazer.g06.graphic.stages;

public class LevelPausedStage extends AbstractLevelStage {
	public LevelPausedStage() {
		super("Niveau en pause");
		
		addDoubleLabel("Niveau", level.getName());
		if(level.getMode().score()) {
			// TODO load score
			addDoubleLabel("Score", "");
		}
		
		addDoubleButton("Recommencer", "ACTION_RESET", "Reprendre", "ACTION_UNPAUSE");
	}
	
}
