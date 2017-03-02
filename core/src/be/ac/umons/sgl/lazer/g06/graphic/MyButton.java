package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/* Textures from packfile
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//*/
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MyButton extends TextButton {
	
	public MyButton(String text) {
		// call to super constructor with minimum style
		super(text, new TextButtonStyle(null, null, null, new BitmapFont()));
		
		TextButtonStyle myButtonStyle = new TextButtonStyle();
		// default 15pt Arial
		myButtonStyle.font = new BitmapFont();
		
		/* Textures from packfile
		TextureAtlas buttonAtlas = new TextureAtlas("relative/from/assets/to/file.pack");
		Skin skin.addRegions(buttonAtlas);
		//*/
		//* Individual textures
		Texture tex = new Texture("images/button.png");
		Texture tex2 = new Texture("images/button_over.png");
		TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(tex));
		TextureRegionDrawable drawable2 = new TextureRegionDrawable(new TextureRegion(tex2));
		//*/
		
		/* Textures from packfile
		myButtonStyle.up = skin.getDrawable("up-button");
		myButtonStyle.down = skin.getDrawable("down-button");
		myButtonStyle.checked = skin.getDrawable("checked-button");
		//*/
		//* Individual textures
		myButtonStyle.up = drawable;
		myButtonStyle.down = drawable2;
		myButtonStyle.checked = drawable2;
		//*/
		super.setStyle(myButtonStyle);
	}
	
}
