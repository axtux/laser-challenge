package be.ac.umons.sgl.lazer.g06.graphic.stages;
/**
 * Stage containing informations when the level is finished
 */
public class LevelFinishedStage extends AbstractLevelStage {
	public LevelFinishedStage() {
		super("Niveau terminé");
		
		addDoubleLabel("Niveau", level.getLabel());
		if(game.getMode().hasScore()) {
			addDoubleLabel("Statut", level.isWon() ? "Gagné" : "Perdu");
			addDoubleLabel("Score", level.isWon() ? Integer.toString(level.getScore()) : "0");
		}
		/* Not implemented
		content.row();
		addButton(content, "Partager sur twitter", "ACTION_SHARE_TWITTER").colspan(2).pad(30);
		//*/
		addMenuButton("Changer de niveau", "MENU_LEVELS").colspan(2);
	}
	
}
