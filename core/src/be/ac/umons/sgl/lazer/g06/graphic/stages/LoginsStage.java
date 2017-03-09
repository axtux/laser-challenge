package be.ac.umons.sgl.lazer.g06.graphic.stages;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

public class LoginsStage extends AbstractStage {
	
	public LoginsStage(LazerChallenge game) {
		super(game, "Connexion");
		
		this.addMenuButton("Anonyme", "ACTTION_LOGIN_ANONYMOUS");
		this.addMenuButton("Compte local", "MENU_LOGIN_LOCAL");
		this.addMenuButton("Compte Twitter", "ACTTION_LOGIN_TWITTER");
	}
	
}