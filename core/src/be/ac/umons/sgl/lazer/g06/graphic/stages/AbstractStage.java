package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.graphic.MySkin;
import be.ac.umons.sgl.lazer.g06.listeners.MyClickListener;

public abstract class AbstractStage extends Stage {
	LazerChallenge game;
	MySkin skin;
	Table container;
	Table content;
	
	public AbstractStage(LazerChallenge game, String title) {
		this(game, title, null);
	}
	
	public AbstractStage(LazerChallenge game, String title, String backAction) {
		super();
		
		this.game = game;
		this.skin = game.getSkin();
		createStyles();
		
		container = new Table();
		this.addActor(container);
		container.setFillParent(true);
		container.setBackground(skin.getColor(Color.WHITE));
		
		addHeader(title, backAction);
		
		container.row();
		content = new Table();
		container.add(content).expand();
		content.pad(20);
	}
	
	protected void addHeader(String title, String backAction) {
		container.row().top().fillX();
		
		Table header = new Table();
		container.add(header).fillX();
		header.setBackground(skin.getColor(Color.ROYAL));
		header.pad(20);
		
		Label label = new Label("Lazer Challenge", skin, "title");
		header.add(label);
		header.row();
		
		Table header2 = new Table();
		container.row().top().fillX();
		container.add(header2).fillX();
		header2.setBackground(skin.getColor(Color.SKY));
		//header2.pad(10);
		
		Table leftHeader = new Table();
		header2.add(leftHeader).prefWidth(400);
		// exit button
		TextButton exit = new TextButton("Quitter", skin, "menu");
		exit.getLabelCell().pad(10);
		exit.addListener(new MyClickListener(game, Input.Buttons.LEFT, "EXIT"));
		leftHeader.add(exit).pad(10);
		// back button, default disabled
		TextButton back;
		if(backAction != null && backAction != "") {
			back = new TextButton("Retour", skin, "menu");
			back.addListener(new MyClickListener(game, Input.Buttons.LEFT, backAction));
		} else {
			back = new TextButton("Retour", skin, "disabled-menu");
		}
		back.getLabelCell().pad(10);
		leftHeader.add(back);
		
		
		label = new Label(title, skin, "subtitle");
		header2.add(label).expand();
		
		Table rightHeader = new Table();
		header2.add(rightHeader).prefWidth(400);
		// user
		if(game.getUser() != null) {
			Table user = new Table();
			user.setBackground(skin.getColor(Color.SLATE));
			rightHeader.add(user);
			
			// TODO add image
			
			Label username = new Label(game.getUser().getUsername(), skin, "small-title");
			user.add(username).pad(5);
			user.row();
			
			TextButton logout = new TextButton("Logout", skin, "small-menu");
			logout.addListener(new MyClickListener(game, Input.Buttons.LEFT, "LOGOUT"));
			logout.getLabelCell().pad(5);
			user.add(logout).right();
		}
		/*
		header.debug();
		header2.debug();//*/
	}
	
	protected void addMenuButton(String text, String action) {
		TextButton btn = new TextButton(text, skin, "menu");
		btn.getLabelCell().pad(10);
		btn.addListener(new MyClickListener(game, Input.Buttons.LEFT, action));
		content.add(btn).minSize(800, 80).space(50);
		content.row();
	}
	
	protected void createStyles() {
		// Title label
		LabelStyle ls = new LabelStyle();
		ls.font = skin.getFont(80, Color.WHITE, 2, Color.BLACK);
		skin.add("title", ls);

		// Subitle label
		ls = new LabelStyle();
		ls.font = skin.getFont(40, Color.WHITE, 2, Color.BLACK);
		skin.add("subtitle", ls);
		
		// Subitle label
		ls = new LabelStyle();
		ls.font = skin.getFont(20, Color.WHITE, 2, Color.BLACK);
		skin.add("small-title", ls);

		// Menu button
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = skin.getColor(Color.DARK_GRAY);
		tbs.over = skin.getColor(Color.GRAY);
		tbs.down = skin.getColor(Color.LIGHT_GRAY);
		tbs.font = skin.getFont(40, Color.WHITE, 2, Color.BLACK);
		skin.add("menu", tbs);
		// Menu button (disabled)
		tbs = new TextButtonStyle();
		tbs.up = skin.getColor(Color.DARK_GRAY);
		tbs.over = skin.getColor(Color.DARK_GRAY);
		tbs.down = skin.getColor(Color.DARK_GRAY);
		tbs.font = skin.getFont(40, Color.DARK_GRAY, 2, Color.BLACK);
		skin.add("disabled-menu", tbs);
		
		tbs = new TextButtonStyle();
		tbs.up = skin.getColor(Color.DARK_GRAY);
		tbs.over = skin.getColor(Color.GRAY);
		tbs.down = skin.getColor(Color.LIGHT_GRAY);
		tbs.font = skin.getFont(20, Color.WHITE, 2, Color.BLACK);
		skin.add("small-menu", tbs);
	}
}
