package be.ac.umons.sgl.lazer.g06.graphic;

public class ConnexionStage extends AbstractStage {
	
	public ConnexionStage(LazerChallenge game) {
		super(game);
		
		this.addButton("Anonyme", "CONNECTION_ANONYMOUS");
		this.addButton("Compte local", "CONNECTION_LOCAL");
		this.addButton("Compte Twitter", "CONNECTION_TWITTER");
	}
	
}
