package be.ac.umons.sgl.lazer.g06.users;

public class User {
	private String username;
	private String image;

	public User() {
		this("Anonymous", "anonymous.png");
	}
	
	public User(String username, String image) {
		setUsername(username);
		setImage(image);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setImage(String image) {
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
