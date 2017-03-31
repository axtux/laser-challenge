package be.ac.umons.sgl.lazer.g06.users;

import java.io.IOException;
import java.io.StringWriter;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;

import be.ac.umons.sgl.lazer.g06.Files;
import be.ac.umons.sgl.lazer.g06.Security;

import com.badlogic.gdx.utils.XmlWriter;

public class LocalUser extends User {
	/**
	 * @param create if true, user will be created
	 * 		   if false, username and password will be used to login user
	 */
	public LocalUser(String username, String password, boolean create) throws LoginException {
		super(username, "noimage");
		if(username == null || username.isEmpty()) {
			throw new LoginException("Le nom d'utilisateur ne peut être vide.");
		}
		
		boolean valid = Pattern.matches("[A-Za-z][A-Za-z0-9_]*", username);
		if(!valid) {
			throw new LoginException("Le nom d'utilisateur doit commencer par une lettre\net ne peut contenir que des lettres, chiffres et _.");
		}
		
		setUsername(username);
		
		if(password == null || password.isEmpty()) {
			throw new LoginException("Le mot de passe ne peut être vide.");
		}
		
		// user signup
		if(create) {
			if(exists()) {
				throw new LoginException("Le nom d'utilisateur existe déjà.");
			}
			if(!saveToFile(password)) {
				throw new LoginException("Erreur lors de la création de l'utilisateur.");
			}
		// user login
		} else if(!exists()){
			throw new LoginException("Le nom d'utilisateur n'existe pas.");
		} else if(!loadFromFile(password)) {
			throw new LoginException("Le mot de passe est incorrect.");
		}
		
	}
	
	/**
	 * Define the path for the user's file
	 * @return the path
	 */
	protected String userPath() {
		return "users/local/"+getUsername();
	}
	
	/**
	 * Define the path of user's files XML
	 * @return the path
	 */
	private String xmlPath() {
		return userPath()+"/user.xml";
	}
	
	/**
	 * Check if the local user exist
	 * @return result
	 */
	private boolean exists() {
		return Files.exists(xmlPath());
	}
	
	/**
	 * Try to create a new local user
	 * @param password the password that want the user
	 * @throws IOException if the file have not been created
	 * @return result
	 */
	private boolean saveToFile(String password) {
		StringWriter writer = new StringWriter();
		XmlWriter xml = new XmlWriter(writer);
		password = Security.md5(password);
		try {
			xml
				.element("user")
					.element("password").attribute("hash", password).attribute("type", "md5")
					.pop()
				.pop();
			xml.close();
		} catch (IOException e) {
			return false;
		}
		
		return Files.putContent(xmlPath(), writer.toString());
	}
	
	/**
	 * Try to get back the data of user
	 * @param password the user's password
	 * @return false if the file does not exist, the password is missing, or the hash type is not md5. Otherwise, return true
	 */
	public boolean loadFromFile(String password) {
		Element user = Files.parseXML(xmlPath());
		if(user == null) {
			Gdx.app.error("LocalUser.loadFromFile", "Error parsing file "+xmlPath());
			return false;
		}
		
		Element p = user.getChildByName("password");
		if(p == null) {
			Gdx.app.error("LocalUser.loadFromFile", "No password element inside user element.");
			return false;
		}
		
		String hash = p.get("hash", "");
		String type = p.get("type", "md5");
		
		if(!passwordMatch(password, type, hash)) {
			return false;
		}
		
		setImage("");
		return true;
	}
	/**
	 * Test if the password is hashed in md5
	 * @param password the user's password
	 * @param hashType the type of hash 
	 * @param hash 
	 * @return result
	 */
	private boolean passwordMatch(String password, String hashType, String hash) {
		switch(hashType) {
		case "md5":
			if(hash.equals(Security.md5(password))) {
				return true;
			} else {
				return false;
			}
		default:
			Gdx.app.error("UNKNOWN_HASH_TYPE", hashType);
			return false;
		}
	}
	
	public class LoginException extends Exception {
		
		/**
		 * this class is called as soon as an error is detected in logging, or when an user try to register
		 */
		private static final long serialVersionUID = 1L;
		public LoginException(String message) {
			super(message);
		}
	}
	
}
