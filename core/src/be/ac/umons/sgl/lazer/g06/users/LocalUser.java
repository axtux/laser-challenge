package be.ac.umons.sgl.lazer.g06.users;

import java.io.IOException;
import java.io.StringWriter;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import be.ac.umons.sgl.lazer.g06.Files;
import be.ac.umons.sgl.lazer.g06.Security;

import com.badlogic.gdx.utils.XmlWriter;

public class LocalUser extends User {
	/*
	 * @create if true, user will be created
	 * 		   if false, username and password will be used to login user
	 */
	public LocalUser(String username, String password, boolean create) throws LoginException {
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
	
	private String filePath() {
		return "users/local/"+getUsername()+"/user.xml";
	}
	
	private boolean exists() {
		return Files.exists(filePath());
	}
	
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
		
		return Files.putContent(filePath(), writer.toString());
	}
	
	public boolean loadFromFile(String password) {
		String xml = Files.getContent(filePath());
		if(xml == null) {
			Gdx.app.error("LocalUser.loadFromFile", "Reading file failed");
			return false;
		}
		
		XmlReader reader = new XmlReader();
		Element user = reader.parse(xml);
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
	
	public class UserData {
		String passwordHash;
		String passwordHashType;
		
	}
	
	public class LoginException extends Exception {
		private static final long serialVersionUID = 1L;
		
		public LoginException(String message) {
			super(message);
		}
	}
	
}
