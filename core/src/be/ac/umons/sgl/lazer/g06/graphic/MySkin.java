package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class MySkin extends Skin {
	
	MyFonts fonts;
	String defaultFont;
	
	public MySkin(String defaultFont) {
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
		this.defaultFont = defaultFont;
		if(getFont(1, Color.WHITE) == null) {
			throw new GdxRuntimeException("Font "+defaultFont+" not avaialble");
		}
		
		// custom styles
		createStyles();
	}
	
	public Drawable getColor(Color c) {
		return this.newDrawable("white", c);
	}
	
	public BitmapFont getFont(int size, Color internal) {
		return this.getFont(size, internal, 0, null);
	}
	
	public BitmapFont getFont(int size, Color color, int border, Color borderColor) {
		return fonts.getFont(defaultFont, size, color, border, borderColor);
	}
	

	private void createStyles() {
		int defaultBorder = 2;
		// Title label
		LabelStyle ls = new LabelStyle();
		ls.font = getFont(80, Color.WHITE, defaultBorder, Color.BLACK);
		add("title", ls);

		// Subtitle label
		ls = new LabelStyle();
		ls.font = getFont(40, Color.WHITE, defaultBorder, Color.BLACK);
		add("subtitle", ls);
		
		// error label
		ls = new LabelStyle();
		ls.font = getFont(20, Color.RED);
		add("error", ls);
		
		// Small subtitle label
		ls = new LabelStyle();
		ls.font = getFont(20, Color.WHITE, defaultBorder, Color.BLACK);
		add("small-title", ls);

		// Field label
		ls = new LabelStyle();
		ls.font = getFont(40, Color.BLACK);
		add("label", ls);
		// Field small label
		ls = new LabelStyle();
		ls.font = getFont(20, Color.BLACK);
		add("small-label", ls);
		
		// Field
		TextFieldStyle tfs = new TextFieldStyle();
		tfs.background = getColor(Color.WHITE);
		tfs.focusedBackground = getColor(Color.LIGHT_GRAY);
		tfs.font = ls.font;
		tfs.fontColor = Color.BLACK;
		tfs.cursor = getColor(Color.BLACK);
		tfs.selection = getColor(Color.GRAY);
		add("field", tfs);
		
		// Menu button
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = getColor(Color.DARK_GRAY);
		tbs.over = getColor(Color.GRAY);
		tbs.down = getColor(Color.LIGHT_GRAY);
		tbs.font = getFont(40, Color.WHITE, defaultBorder, Color.BLACK);
		add("menu", tbs);
		// Menu button (disabled)
		tbs = new TextButtonStyle();
		tbs.up = getColor(Color.DARK_GRAY);
		tbs.over = getColor(Color.DARK_GRAY);
		tbs.down = getColor(Color.DARK_GRAY);
		tbs.font = getFont(40, Color.DARK_GRAY, defaultBorder, Color.BLACK);
		add("disabled-menu", tbs);
		
		tbs = new TextButtonStyle();
		tbs.up = getColor(Color.DARK_GRAY);
		tbs.over = getColor(Color.GRAY);
		tbs.down = getColor(Color.LIGHT_GRAY);
		tbs.font = getFont(20, Color.WHITE, defaultBorder, Color.BLACK);
		add("small-menu", tbs);
	}
}
