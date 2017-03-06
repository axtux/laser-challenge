package be.ac.umons.sgl.lazer.g06.graphic.stages;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

public class ConnexionStage extends AbstractStage {
	
	public ConnexionStage(LazerChallenge game) {
		super(game, "Connexion");
		
		this.addMenuButton("Anonyme", "CONNECTION_ANONYMOUS");
		this.addMenuButton("Compte local", "CONNECTION_LOCAL");
		this.addMenuButton("Compte Twitter", "CONNECTION_TWITTER");
	}
	
}
