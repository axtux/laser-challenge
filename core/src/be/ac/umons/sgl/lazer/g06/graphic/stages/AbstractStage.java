package be.ac.umons.sgl.lazer.g06.graphic.stages;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import be.ac.umons.sgl.lazer.g06.game.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.graphic.MySkin;
import be.ac.umons.sgl.lazer.g06.listeners.MyClickListener;

public abstract class AbstractStage extends Stage {
	LazerChallenge game;
	MySkin skin;
	Hashtable<String, TextField> fields;
	
	Label title;
	Table container;
	Table leftHeader;
	Table content;

	public AbstractStage() {
		this("");
	}
	
	public AbstractStage(String title) {
		// use ScreenViewport instead of ScalingViewport for elements to keep their size
		super(new ScreenViewport());
		
		this.game = LazerChallenge.getInstance();
		this.skin = game.getSkin();
		this.fields = new Hashtable<String, TextField>();
		
		initContainer();
		addHeader();
		addSubHeader(title);
		initContent();
	}
	
	protected void setTitle(String title) {
		this.title.setText(title);
	}
	
	protected void initContainer() {
		container = new Table();
		this.addActor(container);
		container.setFillParent(true);
		container.setBackground(skin.getColor(Color.WHITE));
	}
	
	protected void initContent() {
		container.row();
		content = new Table();
		container.add(content).expand().fill();
		//content.pad(20);
	}
	
	protected void addHeader() {
		container.row().top().fillX();
		
		Table header = new Table();
		container.add(header).fillX();
		header.setBackground(skin.getColor(Color.ROYAL));
		header.pad(20);
		
		Label label = new Label("Lazer Challenge", skin, "title");
		header.add(label);
		header.row();
	}
	
	protected void addSubHeader(String title) {
		Table header = new Table();
		container.row().top().fillX();
		container.add(header).fillX();
		header.setBackground(skin.getColor(Color.SKY));
		
		leftHeader = new Table();
		header.add(leftHeader).uniform();
		// exit button
		addHeaderButton("Quitter", "ACTION_EXIT").pad(10);
		
		Table centerHeader = new Table();
		Label label = this.title = new Label(title, skin, "subtitle");
		centerHeader.add(label).center().pad(10);
		header.add(centerHeader).expand().center();
		
		Table rightHeader = new Table();
		header.add(rightHeader).uniform().fill();
		// user
		if(game.getUser() != null) {
			Table user = new Table();
			user.setBackground(skin.getColor(Color.SLATE));
			rightHeader.add(user).expand().right().pad(10);
			
			// TODO add image
			
			Label username = new Label(game.getUser().getUsername(), skin, "small-title");
			user.add(username).pad(5);
			user.row();
			
			TextButton logout = new TextButton("Déconnexion", skin, "small-menu");
			logout.addListener(new MyClickListener(Input.Buttons.LEFT, "ACTION_LOGOUT"));
			logout.getLabelCell().pad(5);
			user.add(logout).right();
		}
		
	}
	
	protected Cell<TextButton> addHeaderButton(String title, String action) {
		return addButton(leftHeader, title, action).pad(10).padLeft(0);
	}
	
	protected void addExpandingBlock(Table container) {
		Table block = new Table();
		container.add(block).expand().fill();
	}
	
	protected void addMenuButton(String text, String action) {
		addButton(content, text, action).minSize(800, 80).pad(30);
		content.row();
	}
	
	protected Cell<TextButton> addButton(Table container, String text, String action) {
		return addButton(container, text, action, "menu");
	}
	
	protected Cell<TextButton> addButton(Table container, String text, String action, String styleName) {
		return container.add(getButton(text, action, styleName));
	}
	
	protected TextButton getButton(String text, String action, String styleName) {
		TextButton btn = new TextButton(text, skin, styleName);
		btn.getLabelCell().pad(10);
		btn.addListener(new MyClickListener(Input.Buttons.LEFT, action));
		return btn;
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
		return tf;
	}

	protected Label addDoubleLabel(String label1, String label2) {
		return addDoubleLabel(content, label1, label2, "label");
	}
	
	protected Label addDoubleLabel(Table container, String label1, String label2, String skinName) {
		container.row().fillX();
		
		Label l1 = new Label(label1+" :", skin, skinName);
		Table l1Container = new Table();
		l1Container.add(l1).pad(10).padRight(0).expandX().right();
		container.add(l1Container).uniform();
		
		Label l2 = new Label(" "+label2, skin, skinName);
		Table l2Container = new Table();
		l2Container.add(l2).pad(10).padLeft(0).expandX().left();
		container.add(l2Container).uniform();
		
		return l2;
	}
	
	protected void addDoubleButton(String label1, String action1, String label2, String action2) {
		content.row().fillX();

		TextButton btn1 = new TextButton(label1, skin, "menu");
		btn1.getLabelCell().pad(10);
		btn1.addListener(new MyClickListener(Input.Buttons.LEFT, action1));
		Table container1 = new Table();
		container1.add(btn1).pad(10).expandX();
		content.add(container1).uniform();
		
		TextButton btn2 = new TextButton(label2, skin, "menu");
		btn2.getLabelCell().pad(10);
		btn2.addListener(new MyClickListener(Input.Buttons.LEFT, action2));
		Table container2 = new Table();
		container2.add(btn2).pad(10).expandX();
		content.add(container2).uniform();
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
