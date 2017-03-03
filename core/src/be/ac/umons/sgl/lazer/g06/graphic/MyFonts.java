package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class MyFonts {
	
	Skin skin;
	
	public MyFonts(Skin skin) {
		this.skin = skin;
	}
	
	public BitmapFont getFont(int size, Color internal) {
		return this.getFont(size, internal, 0, null);
	}
	
	public BitmapFont getFont(int size, Color color, int border, Color borderColor) {
		if(borderColor == null) {
			borderColor = Color.BLACK;
		}
		String name = Integer.toString(size)+color.toString()+Integer.toString(border)+borderColor.toString();
		BitmapFont font;
		
		try {
			font = skin.getFont(name);
		} catch(GdxRuntimeException e) {
			// font not found in skin
			font = null;
		}
		
		if(font == null) {
			String fontFile = "fonts/opificio/opificio_regular.ttf";
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontFile));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = size;
			parameter.color = color;
			parameter.borderWidth = border;
			parameter.borderColor = borderColor;
			
			font = generator.generateFont(parameter);
			generator.dispose();
			
			skin.add(name, font);
		}
		
		return font;
	}
}
