package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class MySkin extends Skin {
	
	MyFonts fonts;
	
	public MySkin() {
		super();
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		this.add("white", new Texture(pixmap));
		pixmap.dispose();
		
		// default 15pt Arial
		this.add("default", new BitmapFont());
		
		fonts = new MyFonts(this);
	}
	
	public Drawable getColor(Color c) {
		return this.newDrawable("white", c);
	}
	
	public BitmapFont getFont(int size, Color internal) {
		return this.getFont(size, internal, 0, null);
	}
	
	public BitmapFont getFont(int size, Color color, int border, Color borderColor) {
		return fonts.getFont(size, color, border, borderColor);
	}
}
