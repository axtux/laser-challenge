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

public class MyButton extends TextButton {
	static TextButtonStyle defaultStyle;
	
	public static void init() {
		defaultStyle = new TextButtonStyle();
		defaultStyle.font = new BitmapFont();
	}
	
	public MyButton(String text) {
		// call to constructor
		super(text, defaultStyle);
		
		TextButtonStyle myButtonStyle = new TextButtonStyle();
		// default 15pt Arial
		myButtonStyle.font = new BitmapFont();
		
		/* Textures from packfile
		TextureAtlas buttonAtlas = new TextureAtlas("relative/from/assets/to/file.pack");
		Skin skin.addRegions(buttonAtlas);
		//*/
		//* Individual textures
		Texture tex = new Texture("images/MainMenu/connexion_anonyme_button_on.png");
		TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(tex));
		//*/
		
		/* Textures from packfile
		myButtonStyle.up = skin.getDrawable("up-button");
		myButtonStyle.down = skin.getDrawable("down-button");
		myButtonStyle.checked = skin.getDrawable("checked-button");
		//*/
		//* Individual textures
		myButtonStyle.up = drawable;
		myButtonStyle.down = drawable;
		myButtonStyle.checked = drawable;
		//*/
		super.setStyle(myButtonStyle);
	}
	
}
