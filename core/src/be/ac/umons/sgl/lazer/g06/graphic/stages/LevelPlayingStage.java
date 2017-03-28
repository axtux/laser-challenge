package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Array;

import be.ac.umons.sgl.lazer.g06.Time;
import be.ac.umons.sgl.lazer.g06.game.Block;
import be.ac.umons.sgl.lazer.g06.game.LevelType;
import be.ac.umons.sgl.lazer.g06.game.LevelType.BlockType;
import be.ac.umons.sgl.lazer.g06.game.Map;
import be.ac.umons.sgl.lazer.g06.graphic.MapTable;

public class LevelPlayingStage extends AbstractLevelStage implements Observer {
	Table mapTable;
	Table leftTable;
	Table rightTable;
	
	Label scoreLabel;
	Label timeLabel;
	Label blockLabel;
	Table selectionContent;
	
	TextButton moveButton;
	TextButton rotateButton;
	
	public LevelPlayingStage() {
		super();
		setTitle(game.getMode().toString()+" : "+level.getName());
		
		addHeaderButton("Retour", "MENU_LEVEL_INFOS");
		
		content.row();
		
		leftTable = new Table();
		content.add(new ScrollPane(leftTable, skin, "gray")).pad(10).fill().uniform();
		addLegend(leftTable);
		
		mapTable = new Table();
		content.add(mapTable).grow();
		//addInventory(mapTable, level.getInventory());
		addMapTable(mapTable, level.getMap());
		addLaserButton(mapTable, false);
		
		rightTable = new Table();
		content.add(new ScrollPane(rightTable, skin)).pad(10).fill().uniform();
		addInfos(rightTable);
		addInventory(rightTable, level.getInventory());
		addSelection(rightTable);
		
		// get updated
		level.addObserver(this);
	}
	
	private void addLegend(Table container) {
		container.row();
		Label label = new Label("Légende", skin, "label");
		container.add(label).colspan(2).pad(20);
		
		LevelType levelType = level.getType();
		Array<String> blockTypes = levelType.getBlockTypes();
		BlockType bt;
		for(String blockType : blockTypes) {
			bt = levelType.getBlockType(blockType);
			addLegendEntry(container, bt);
		}
	}
	
	private void addLegendEntry(Table container, BlockType bt) {
		container.row();
		
		Image image = new Image(bt.getTextureRegion());
		container.add(image).pad(10);
		
		Label label = new Label(bt.getLabel(), skin, "small-label");
		container.add(label).pad(10).left();
	}
	
	private void addMapTable(Table container, Map map) {
		Table mapTable = new MapTable(map);
		container.row();
		container.add(mapTable).grow().pad(20);
	}
	
	private Table addBox(Table container, String title) {
		Table box = new Table();
		box.background(skin.getColor(Color.LIGHT_GRAY));
		container.row();
		container.add(box).growX().padBottom(10);
		
		if(title != null) {
			Label label = new Label(title, skin, "label");
			box.row();
			box.add(label).pad(20);
		}
		
		Table content = new Table();
		content.background(skin.getColor(Color.WHITE));
		box.row();
		if(title != null) {
			box.add(content).pad(20).padTop(0);
		} else {
			box.add(content).pad(20);
		}
		
		return content;
	}
	
	private void addInfos(Table container) {
		if(!game.getMode().hasScore()) return;
		
		Table content = addBox(container, "Informations");
		timeLabel = addDoubleLabel(content, "Temps", "", "small-label");
		scoreLabel = addDoubleLabel(content, "Score", "", "small-label");
	}
	
	private void addInventory(Table container, Map inv) {
		Table content = addBox(container, "Inventaire");
		Table mapTable = new MapTable(inv);
		content.row();
		content.add(mapTable).pad(5);
	}
	
	private void addSelection(Table container) {
		Table content = addBox(container, "Sélection");
		
		selectionContent = new Table();
		content.row();
		content.add(selectionContent);
		blockLabel = addDoubleLabel(selectionContent, "Block", "", "small-label");
		// TODO add information about empty case
		
		Table buttons = new Table();
		content.row();
		content.add(buttons);
		moveButton = getButton("Déplacer", "ACTION_LEVEL_MOVE", "small-menu");
		rotateButton = getButton("Pivoter", "ACTION_LEVEL_ROTATE", "small-menu");
		buttons.add(moveButton).pad(5);
		buttons.add(rotateButton).pad(5);
	}
	
	private void addLaserButton(Table container, boolean box) {
		// TODO continuous laser in training mode
		//if(!game.getMode().hasScore()) return;
		
		if(box) {
			container = addBox(container, null);
		}
		container.row();
		addButton(container, "Lancer le lazer", "ACTION_LEVEL_LASER", "small-menu").pad(box ? 5 : 10);
	}
	
	public void update(Observable o, Object arg) {
		timeLabel.setText(Time.prettyTime(level.getRemainingTime(), false, false));
		scoreLabel.setText(Integer.toString(level.getScore()));
		
		Block block = level.getSelectedBlock();
		blockLabel.setText(block == null ? "none" : block.getType().getLabel());
		
		boolean move = block != null && block.canMove();
		boolean rotate = block != null && block.canRotate();
		moveButton.setStyle(skin.get(move ? "small-menu" : "disabled-small-menu", TextButtonStyle.class));
		rotateButton.setStyle(skin.get(rotate ? "small-menu" : "disabled-small-menu", TextButtonStyle.class));
	}
	
}
