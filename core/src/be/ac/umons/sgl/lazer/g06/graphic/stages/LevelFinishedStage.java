package be.ac.umons.sgl.lazer.g06.graphic.stages;

public class LevelFinishedStage extends AbstractLevelStage {
	public LevelFinishedStage() {
		super("Niveau terminé");
		
		addDoubleLabel("Niveau", level.getName());
		if(game.getMode().hasScore()) {
			addDoubleLabel("Status", level.isWon() ? "Gagné" : "Perdu");
			addDoubleLabel("Score", level.isWon() ? Integer.toString(level.getScore()) : "0");
		}
		
		content.row();
		//addButton(content, "Partager sur twitter", "ACTION_SHARE_TWITTER").colspan(2).pad(30);
		
		addDoubleButton("Changer niveau", "MENU_LEVELS", "Niveau suivant", "ACTION_LEVEL_NEXT");
	}
	
}
