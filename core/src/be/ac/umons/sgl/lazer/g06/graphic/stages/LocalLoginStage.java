package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LocalLoginStage extends AbstractStage {

	public LocalLoginStage() {
		this("", "", "");
	}
	
	public LocalLoginStage(String usernameFieldValue, String passwordFieldValue, String errorMessage) {
		super("Connexion locale");
		addHeaderButton("Retour", "MENU_LOGINS");
		
		addTextField("Nom d'utilisateur", "USERNAME", usernameFieldValue);
		// TODO find why password mode does not make characters displayed as *
		addTextField("Mot de passe", "PASSWORD", passwordFieldValue).setPasswordMode(true);
		
		content.row();
		addButton(content, "Créer ce compte", "ACTION_LOCAL_SIGNUP", "small-menu").space(50);
		addButton(content, "Connexion", "ACTION_LOCAL_LOGIN").space(50);
		
		Label error = new Label(errorMessage, skin, "error");
		content.row().pad(50);
		content.add(error).colspan(2);
	}
	
}
