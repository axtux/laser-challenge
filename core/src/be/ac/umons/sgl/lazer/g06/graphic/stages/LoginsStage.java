package be.ac.umons.sgl.lazer.g06.graphic.stages;

/**
 * Display a menu that contains all modes of connections
 *
 */
public class LoginsStage extends AbstractStage {
	
	public LoginsStage() {
		super("Connexion");
		
		this.addMenuButton("Anonyme", "ACTTION_LOGIN_ANONYMOUS");
		this.addMenuButton("Compte local", "MENU_LOGIN_LOCAL");
		this.addMenuButton("Compte Twitter", "ACTTION_LOGIN_TWITTER");
	}
	
}
