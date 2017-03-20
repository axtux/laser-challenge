package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import be.ac.umons.sgl.lazer.g06.graphic.MapTable;

public class LevelPlayingStage extends AbstractLevelStage {
	Table map;
	MapTable mapActor;
	
	public LevelPlayingStage() {
		super();
		setTitle(game.getMode().toString()+" : "+level.getName());
		
		addHeaderButton("Retour", "MENU_LEVEL_INFOS");
		
		map = new Table();
		content.row();
		content.add(map).grow();
		
		mapActor = new MapTable(level.getMap());
		map.add(mapActor).pad(20);
		
		content.row();
		addDoubleButton("Swap", "ACTION_LEVEL_SWAP", "Lancer le lazer", "ACTION_LEVEL_LASER");
	}
	
}
