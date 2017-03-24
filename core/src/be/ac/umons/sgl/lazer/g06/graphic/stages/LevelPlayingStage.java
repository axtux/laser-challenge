package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Array;

import be.ac.umons.sgl.lazer.g06.game.LevelType;
import be.ac.umons.sgl.lazer.g06.game.LevelType.BlockType;
import be.ac.umons.sgl.lazer.g06.game.Map;
import be.ac.umons.sgl.lazer.g06.graphic.MapTable;

public class LevelPlayingStage extends AbstractLevelStage {
	Table mapTable;
	Table leftTable;
	Table rightTable;
	
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
		
		rightTable = new Table();
		content.add(new ScrollPane(rightTable, skin)).pad(10).fill().uniform();
		addInfos(rightTable);
		addInventory(rightTable, level.getInventory());
		addSelection(rightTable);
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
		
		Label label = new Label(title, skin, "label");
		box.row();
		box.add(label).pad(20);
		
		Table content = new Table();
		content.background(skin.getColor(Color.WHITE));
		box.row();
		box.add(content).pad(20).padTop(0);
		
		return content;
	}
	
	private void addInfos(Table container) {
		Table content = addBox(container, "Informations");
		addDoubleLabel(content, "Temps", "XX:XX", "small-label");
		addDoubleLabel(content, "Score", "XXX", "small-label");
	}
	
	private void addInventory(Table container, Map inv) {
		Table content = addBox(container, "Inventaire");
		Table mapTable = new MapTable(inv);
		content.row();
		content.add(mapTable).pad(5);
	}
	
	private void addSelection(Table container) {
		Table content = addBox(container, "Sélection");
		addDoubleLabel(content, "Block", "source", "small-label");
		
		addDoubleLabel(content, "Déplacer", "autorisé", "small-label");
		addDoubleLabel(content, "Orienter", "autorisé", "small-label");
		
		content.row();
		addButton(content, "Swap", "ACTION_LEVEL_SWAP", "small-menu").pad(20).colspan(2);
		
		container.row();
		addButton(container, "Lancer le lazer", "ACTION_LEVEL_LASER", "small-menu").pad(20);
	}
	
}
