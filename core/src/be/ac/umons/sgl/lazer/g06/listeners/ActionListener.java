package be.ac.umons.sgl.lazer.g06.listeners;
/**
 * To manage actions from UI
 */
public interface ActionListener {
	/**
	 * Called by LaserChallenge.act(String)
	 * @param action Action to perform.
	 */
	public void act(String action);
}
