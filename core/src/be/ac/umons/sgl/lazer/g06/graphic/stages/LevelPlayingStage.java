package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
		content.add(new ScrollPane(leftTable, skin)).pad(10).fill().uniform();
		addLegend(leftTable);
		
		mapTable = new Table();
		content.add(mapTable).grow();
		addMapTable(mapTable, level.getMap());
		
		rightTable = new Table();
		content.add(new ScrollPane(rightTable, skin)).pad(10).fill().uniform();
		addInventory(rightTable, level.getInventory());
		addInfos(rightTable);
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
		container.add(mapTable).grow().pad(20);
	}
	
	private void addInventory(Table container, Map inv) {
		container.row();
		Table mapTable = new MapTable(inv);
		container.add(mapTable).grow().pad(10);
	}
	
	private void addInfos(Table container) {
		container.row();
		addButton(container, "Swap", "ACTION_LEVEL_SWAP", "small-menu").pad(20);
		container.row();
		addButton(container, "Lancer le lazer", "ACTION_LEVEL_LASER", "small-menu").pad(20);
	}
	
}
