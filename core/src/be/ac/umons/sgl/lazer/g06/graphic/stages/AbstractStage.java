package be.ac.umons.sgl.lazer.g06.graphic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import be.ac.umons.sgl.lazer.g06.listeners.ButtonListener;

public abstract class AbstractStage extends Stage {
	LazerChallenge game;
	MySkin skin;
	Table table;
	
	public AbstractStage(LazerChallenge game) {
		super();
		
		this.game = game;
		this.skin = game.getSkin();
		
		table = new Table();
		this.addActor(table);
		table.setFillParent(true);
		table.setBackground(skin.getColor(Color.WHITE));
	}
	
	protected void addButton(String text, String action) {
		MyButton btn = new MyButton(skin, text);
		btn.addListener(new ButtonListener(game, action));
		table.add(btn).minSize(800, 80).pad(50);
		table.row();
	}
}
