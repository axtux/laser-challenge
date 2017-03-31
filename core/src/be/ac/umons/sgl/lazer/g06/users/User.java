package be.ac.umons.sgl.lazer.g06.users;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import be.ac.umons.sgl.lazer.g06.Files;
import be.ac.umons.sgl.lazer.g06.game.Switch;
/**
 * create a new user
 */
public class User {
	private String username;
	private String image;
	private Json json;
	
	public User(String username, String image) {
		setUsername(username);
		setImage(image);
		json = new Json();
	}
	
	/**
	 * define a name for the user
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * define a picture for the user
	 * @param image the picture that will be blinded to the user
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * Called when user is logged out
	 */
	public void logout() {}
	
	/**
	 * @return the user's name
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @return the picture of the user
	 */
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
	/**
	 * Default path to save files.
	 * @param levelName
	 * @return a path with the name of level
	 */
	private String levelPath(String levelName) {
		return userPath()+"/"+levelName;
	}
	/**
	 * Create a file score
	 * @param levelName the level that contains the score to record
	 * @return a path with the file created
	 */
	private String scorePath(String levelName) {
		return levelPath(levelName)+"/score.txt";
	}
	/**
	 * create a XML file history
	 * @param levelName the level that contains the history to record
	 * @return a path with the XML history
	 */
	private String historyPath(String levelName) {
		return levelPath(levelName)+"/history.xml";
	}
	/**
	 * try to save score in file
	 * @param levelName the level associated to the score that will be recorded
	 * @param score the score to record
	 * @return result 
	 */
	public boolean saveScore(String levelName, int score){
		String str_score = String.valueOf(score);
		return Files.putContent(scorePath(levelName), str_score);
	}
	/**
	 * Load score recorded in file score
	 * @param levelName the level associated to the score recorded
	 * @throws NumberFormatException if the file contain no score
	 * @return the score 
	 */
	public int loadScore(String levelName){
		String str_score = Files.getContent(scorePath(levelName));
		try {
			return Integer.parseInt(str_score);
		} catch(NumberFormatException e) {
			return 0;
		}
	}
	/**
	 * try to save history in XML file
	 * @param levelName the level associated to the history that will be recorded
	 * @param history the history to record
	 * @return result
	 */
	public boolean saveHistory ( String levelName , Array<Switch> history){
		String json_history = json.toJson(history);
		//Gdx.app.debug("User.saveHistory", json_history);
		return Files.putContent(historyPath(levelName), json_history);
	}
	/**
	 * Load history recorded in XML file
	 * @param levelName the level associated to the history that will be loaded
	 * @return null if the history does not exist, or the history if it exists
	 */
	@SuppressWarnings("unchecked")
	public Array<Switch> loadHistory(String levelName){
		String json_history = Files.getContent(historyPath(levelName));
		if(json_history == null) {
			//Gdx.app.debug("User.loadHistory", "no file "+historyPath(levelName));
			return null;
		}
		
		return json.fromJson(Array.class, Switch.class, json_history);
	}
}
