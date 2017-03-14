package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
	
	public BitmapFont getFont(String name, int size, Color internal) {
		return this.getFont(name, size, internal, 0, null);
	}
	
	public BitmapFont getFont(String name, int size, Color color, int border, Color borderColor) {
		if(borderColor == null) {
			borderColor = Color.BLACK;
		}
		String internalName = name+Integer.toString(size)+color.toString()+Integer.toString(border)+borderColor.toString();
		BitmapFont font;
		
		try {
			font = skin.getFont(internalName);
		} catch(GdxRuntimeException e) {
			// font not found in skin
			font = null;
		}
		
		if(font == null) {
			FileHandle file = Gdx.files.internal("fonts/"+name+".ttf");
			if(file == null || !file.exists()) {
				Gdx.app.error("MyFonts.getFont", "Font "+name+"does not exists");
				return null;
			}
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(file);
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
