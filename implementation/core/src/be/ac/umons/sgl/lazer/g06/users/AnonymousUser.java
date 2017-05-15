package be.ac.umons.sgl.lazer.g06.users;

import be.ac.umons.sgl.lazer.g06.Files;
/**
 * Anonymous user as browser anonymous sessions. Everything is saved during session but everything is deleted on logout.
 */
public class AnonymousUser extends User {
	/**
	 * Create anonymous user with name "Anonyme" and picture "anonymous.png"
	 */
	public AnonymousUser() {
		super("Anonyme", "anonymous.png");
	}
	/**
	 * Delete all user files on logout
	 */
	public void logout() {
		Files.delete(userPath());
	}
	
}
