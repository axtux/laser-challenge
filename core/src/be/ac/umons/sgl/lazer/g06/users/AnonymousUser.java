package be.ac.umons.sgl.lazer.g06.users;

import be.ac.umons.sgl.lazer.g06.Files;
/**
 * create an AnonymousUser
 */
public class AnonymousUser extends User {
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
