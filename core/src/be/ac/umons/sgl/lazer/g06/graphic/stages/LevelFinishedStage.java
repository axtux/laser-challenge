package be.ac.umons.sgl.lazer.g06.graphic.stages;

public class LevelFinishedStage extends AbstractLevelStage {
	public LevelFinishedStage() {
		super("Niveau terminé");
		
		addDoubleLabel("Niveau", level.getName());
		if(game.getMode().hasScore()) {
			// TODO load score
			addDoubleLabel("Score", "");
		}
		
		content.row();
		addButton(content, "Partager sur twitter", "ACTION_SHARE_TWITTER").colspan(2).pad(30);
		
		addDoubleButton("Choix niveau", "MENU_LEVELS", "Niveau suivant", "ACTION_LEVEL_NEXT");
	}
	
}
