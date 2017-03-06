package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
/* Textures from packfile
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//*/
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
/* Individual textures
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
//*/

public class MyButton extends TextButton {
	
	public MyButton(MySkin skin, String text) {
		// call to super constructor with minimum style
		super(text, new TextButtonStyle(null, null, null, new BitmapFont()));
		// TODO remove this class, kept only for texture example
		/* Textures from packfile
		TextureAtlas buttonAtlas = new TextureAtlas("relative/from/assets/to/file.pack");
		Skin skin.addRegions(buttonAtlas);
		//*/
		/* Individual textures
		Texture tex = new Texture("images/button.png");
		TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(tex));
		//*/
		
		/* Textures from packfile
		myButtonStyle.up = skin.getDrawable("up-button");
		myButtonStyle.down = skin.getDrawable("down-button");
		myButtonStyle.checked = skin.getDrawable("checked-button");
		//*/
		//* Individual textures
		
		this.getStyle().up = skin.getColor(Color.DARK_GRAY);
		this.getStyle().over = skin.getColor(Color.GRAY);
		this.getStyle().down = skin.getColor(Color.LIGHT_GRAY);
		
		this.getStyle().font = skin.getFont(40, Color.WHITE, 2, Color.BLACK);
		
		//*/
		// force style update
		super.setStyle(this.getStyle());
	}
	
}
