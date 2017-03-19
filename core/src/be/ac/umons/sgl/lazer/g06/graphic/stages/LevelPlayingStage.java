package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import be.ac.umons.sgl.lazer.g06.graphic.MyMap;

public class LevelPlayingStage extends AbstractLevelStage {
	Table map;
	MyMap mapActor;
	
	public LevelPlayingStage() {
		super();
		setTitle(game.getMode().toString()+" : "+level.getName());
		
		addHeaderButton("Retour", "MENU_LEVEL_INFOS");
		
		map = new Table();
		content.row();
		content.add(map).grow();
		
		mapActor = new MyMap(level.getMap().getTiledMap());
		map.add(mapActor).pad(20);
		
		content.row();
		addMenuButton("Lancer le lazer", "ACTION_GAME_LAZER");
	}
	
}
