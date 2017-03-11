package be.ac.umons.sgl.lazer.g06.graphic.stages;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.graphic.MySkin;
import be.ac.umons.sgl.lazer.g06.listeners.MyClickListener;

public abstract class AbstractStage extends Stage {
	LazerChallenge game;
	MySkin skin;
	Table container;
	Table content;
	Hashtable<String, TextField> fields;
	
	public AbstractStage(LazerChallenge game, String title) {
		this(game, title, null);
	}
	
	public AbstractStage(LazerChallenge game, String title, String backAction) {
		super();
		
		this.game = game;
		this.skin = game.getSkin();
		
		container = new Table();
		this.addActor(container);
		container.setFillParent(true);
		container.setBackground(skin.getColor(Color.WHITE));
		
		addHeader(title, backAction);
		
		container.row();
		content = new Table();
		container.add(content).expand().fill();
		//content.pad(20);
		
		this.fields = new Hashtable<String, TextField>();
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
		exit.addListener(new MyClickListener(game, Input.Buttons.LEFT, "ACTION_EXIT"));
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
			
			TextButton logout = new TextButton("Déconnexion", skin, "small-menu");
			logout.addListener(new MyClickListener(game, Input.Buttons.LEFT, "ACTION_LOGOUT"));
			logout.getLabelCell().pad(5);
			user.add(logout).right();
		}
		/*
		header.debug();
		header2.debug();//*/
	}

	protected void addMenuButton(String text, String action) {
		addButton(text, action).minSize(800, 80).space(50);
		content.row();
	}
	
	protected Cell<TextButton> addButton(String text, String action) {
		return addButton(text, action, "menu");
	}
	
	protected Cell<TextButton> addButton(String text, String action, String styleName) {
		TextButton btn = new TextButton(text, skin, styleName);
		btn.getLabelCell().pad(10);
		btn.addListener(new MyClickListener(game, Input.Buttons.LEFT, action));
		return content.add(btn);
	}
	
	protected TextField addTextField(String label, String fieldname) {
		return addTextField(label, fieldname, "");
	}
	protected TextField addTextField(String label, String fieldname, String defaultValue) {
		content.row().fillX();
		
		Label l = new Label(label+" : ", skin, "label");
		Table lContainer = new Table();
		lContainer.add(l).pad(10).expandX().right();
		content.add(lContainer).uniform();
		
		TextField tf = new TextField(defaultValue, skin, "field");
		Table tfContainer = new Table();
		tfContainer.background(skin.getColor(Color.DARK_GRAY));
		tfContainer.add(tf).pad(5).fillX().expandX();
		content.add(tfContainer).pad(5).fillX().uniform();
		
		fields.put(fieldname, tf);
		tf.setPasswordMode(true);
		
		return tf;
	}
	
	public String getFieldValue(String fieldname) {
		TextField tf = fields.get(fieldname);
		if(tf == null) {
			Gdx.app.error("ERROR", "field "+fieldname+" was not found");
			return null;
		}
		
		return tf.getText();
	}
	
}
