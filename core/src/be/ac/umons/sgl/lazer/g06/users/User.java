package be.ac.umons.sgl.lazer.g06.users;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import be.ac.umons.sgl.lazer.g06.Files;
import be.ac.umons.sgl.lazer.g06.game.Switch;

public class User {
	private String username;
	private String image;
	private Json json;
	
	public User(String username, String image) {
		setUsername(username);
		setImage(image);
		json = new Json();
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * Called when user is logged out
	 */
	public void logout() {}
	
	public String getUsername() {
		return username;
	}
	
	public String getImage() {
		return image;
	}
	/**
	 * Default path to save files. Overwrite method to change path in subclasses.
	 * @return
	 */
	protected String userPath() {
		return "users/"+getUsername();
	}
	
	private String levelPath(String levelName) {
		return userPath()+"/"+levelName;
	}
	
	private String scorePath(String levelName) {
		return levelPath(levelName)+"/score.txt";
	}
	
	private String historyPath(String levelName) {
		return levelPath(levelName)+"/history.xml";
	}
	
	public boolean saveScore(String levelName, int score){
		String str_score = String.valueOf(score);
		return Files.putContent(scorePath(levelName), str_score);
	}
	
	public int loadScore(String levelName){
		String str_score = Files.getContent(scorePath(levelName));
		try {
			return Integer.parseInt(str_score);
		} catch(NumberFormatException e) {
			return 0;
		}
	}
	
	public boolean saveHistory ( String levelName , Array<Switch> history){
		String json_history = json.toJson(history);
		Gdx.app.debug("User.saveHistory", json_history);
		return Files.putContent(historyPath(levelName), json_history);
	}
	
	public Array<Switch> loadHistory(String levelName){
		String json_history = Files.getContent(historyPath(levelName));
		if(json_history == null) {
			Gdx.app.debug("User.loadHistory", "no file "+historyPath(levelName));
			return null;
		}
		
		@SuppressWarnings("unchecked")
		Array<Switch> history = json.fromJson(Array.class, Switch.class, json_history);
		
		Gdx.app.debug("User.loadHistory", "size : "+Integer.toString(history.size));
		for(Switch s : history) {
			Gdx.app.debug("User.loadHistory", s.toString());
		}
		
		return history;
	}
}
