package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.game.Level;
import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.graphic.MyMap;

public class LevelPlayingStage extends AbstractStage {
	boolean displayScore;
	
	Table map;

	Level level;
	MyMap mapActor;
	
	public LevelPlayingStage() {
		super(LazerChallenge.getInstance().getMode().toString()+" : "+LazerChallenge.getInstance().getLevel().getName());
		
		if(game.getMode() == null) {
			throw new GdxRuntimeException("game mode cannot be null.");
		}
		displayScore = game.getMode().equals(Level.Mode.ARCADE);
		
		addHeaderButton("Retour", "MENU_LEVEL_INFOS");
		
		if(game.getLevel() == null) {
			throw new GdxRuntimeException("game level cannot be null.");
		}
		level = game.getLevel();
		
		map = new Table();
		content.row();
		content.add(map).expand().fill();
		
		mapActor = new MyMap(level.getMap().getTiledMap());
		map.add(mapActor).pad(20);
		
		content.row();
		addMenuButton("Lancer le lazer", "ACTION_GAME_LAZER");
		
	}
	

}
