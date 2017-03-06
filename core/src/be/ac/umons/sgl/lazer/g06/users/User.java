package be.ac.umons.sgl.lazer.g06.users;

public class User {
	private String username;
	private String image;

	public User() {
		this("Anonymous", "anonymous.png");
	}
	
	public User(String username, String image) {
		login(username, image);
	}
	
	public void login(String username, String image) {
		this.username = username;
		this.image = image;
	}
	
	public void logout() {
		
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getImage() {
		return image;
	}
	
}
