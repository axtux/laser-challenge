package be.ac.umons.sgl.lazer.g06.users;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import be.ac.umons.sgl.lazer.g06.game.Switch;

public class User {
	private String username;
	private String image;
	private XMLEncoder encoder; 

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
	
	//public void saveScore (String levelname, float score ){
		//if (Gdx.files.classpath())
	//}
	
	public void saveHistory ( String levelname , Array<Switch> history){
		try {
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("score"+levelname+".xml")));
			encoder.writeObject(history);
			encoder.flush();
		} catch (final java.io.IOException e) {
			e.printStackTrace();
			} 
		finally {
			if (encoder != null) {
				encoder.close();
			}
		}
	}
	
	/*public float loadScore(String levelname){
		
	}
	public Array<Switch> loadHistory(String levelname){
		
	} */
}
