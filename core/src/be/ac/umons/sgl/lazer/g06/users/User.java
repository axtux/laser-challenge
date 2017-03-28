package be.ac.umons.sgl.lazer.g06.users;

import com.badlogic.gdx.utils.Array;

import be.ac.umons.sgl.lazer.g06.game.Switch;

public class User {
	private String username;
	private String image;

	public User() {
		this("Anonyme", "anonymous.png");
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
	
	public void saveScore (String levelname, float score ){
		
	}
	
	public void saveHistory ( String levelname , Array<Switch> history){
		
	}
	
	/*public float loadScore(String levelname){
		
	}
	public Array<Switch> loadHistory(String levelname){
		
	} */
}
