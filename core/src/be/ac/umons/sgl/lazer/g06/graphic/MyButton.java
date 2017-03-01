package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MyButton {
 TextButton button;
 TextButtonStyle textButtonStyle;
 BitmapFont font;
 Skin skin;
 TextureAtlas buttonAtlas;

 public MyButton(String text) {
	 font = new BitmapFont();
	/*
	AssetManager am = new AssetManager();
	am.load("buttons.pack", TextureAtlas.class);
	am.finishLoading();
	buttonAtlas = am.get("buttons.pack");
	//*/
	 //buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
	 //skin.addRegions(buttonAtlas);
	 Texture tex = new Texture("images/MainMenu/connexion_anonyme_button_on.png");
	 TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(tex));
	 
	 textButtonStyle = new TextButtonStyle();
	 textButtonStyle.font = font;
	 //*
	 textButtonStyle.up = drawable;
	 textButtonStyle.down = drawable;
	 textButtonStyle.checked = drawable;
	 //*/
	 /*
	 textButtonStyle.up = skin.getDrawable("up-button");
	 textButtonStyle.down = skin.getDrawable("down-button");
	 textButtonStyle.checked = skin.getDrawable("checked-button");
	 //*/
	 button = new TextButton(text, textButtonStyle);
 }
 
 public TextButton getButton() {
	 return button;
 }
}
