package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
/**
 * Stage that allows user to log in/sign up containing fields USERNAME and PASSWORD
 */
public class LocalLoginStage extends AbstractStage {
	/**
	 * Create stage with all values set to empty strings
	 */
	public LocalLoginStage() {
		this("", "", "");
	}
	/**
	 * Create stage with predefined values.
	 * @param usernameFieldValue Value to set on user name field.
	 * @param passwordFieldValue Value to set on password field.
	 * @param errorMessage Error message to be displayed in red at the bottom of the stage.
	 */
	public LocalLoginStage(String usernameFieldValue, String passwordFieldValue, String errorMessage) {
		super("Connexion locale");
		addHeaderButton("Retour", "MENU_LOGINS");
		
		addTextField("Nom d'utilisateur", "USERNAME", usernameFieldValue);
		TextField password = addTextField("Mot de passe", "PASSWORD", passwordFieldValue);
		// replace characters by *
		password.setPasswordCharacter('*');
		password.setPasswordMode(true);
		
		content.row();
		addButton(content, "Créer ce compte", "ACTION_LOCAL_SIGNUP", "small-menu").space(50);
		addButton(content, "Connexion", "ACTION_LOCAL_LOGIN").space(50);
		
		Label error = new Label(errorMessage, skin, "error");
		content.row().pad(50);
		content.add(error).colspan(2);
	}
	
}
