package be.ac.umons.sgl.lazer.g06.graphic.stages;

public class LevelFinishedStage extends AbstractLevelStage {
	/**
	 * Display a screen that contains informations when the level is finished
	 */
	public LevelFinishedStage() {
		super("Niveau terminé");
		
		addDoubleLabel("Niveau", level.getName());
		if(game.getMode().hasScore()) {
			addDoubleLabel("Status", level.isWon() ? "Gagné" : "Perdu");
			addDoubleLabel("Score", level.isWon() ? Integer.toString(level.getScore()) : "0");
		}
		
		content.row();
		//addButton(content, "Partager sur twitter", "ACTION_SHARE_TWITTER").colspan(2).pad(30);
		
		addMenuButton("Changer de niveau", "MENU_LEVELS").colspan(2);
	}
	
}
