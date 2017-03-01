package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/* Textures from packfile
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//*/
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MyButton {
	TextButton button;
	TextButtonStyle textButtonStyle;
	
	public MyButton(String text) {
		// default 15pt Arial
		BitmapFont font = new BitmapFont();

		/* Textures from packfile
		TextureAtlas buttonAtlas = new TextureAtlas("relative/from/assets/to/file.pack");
		Skin skin.addRegions(buttonAtlas);
		//*/
		//* Individual textures
		Texture tex = new Texture("images/MainMenu/connexion_anonyme_button_on.png");
		TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(tex));
		//*/
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;

		/* Textures from packfile
		textButtonStyle.up = skin.getDrawable("up-button");
		textButtonStyle.down = skin.getDrawable("down-button");
		textButtonStyle.checked = skin.getDrawable("checked-button");
		//*/
		//* Individual textures
		textButtonStyle.up = drawable;
		textButtonStyle.down = drawable;
		textButtonStyle.checked = drawable;
		//*/
		
		button = new TextButton(text, textButtonStyle);
	}
	
	public TextButton getButton() {
		return button;
	}
}
