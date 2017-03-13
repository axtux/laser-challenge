package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.listeners.LevelInfosListener;
import be.ac.umons.sgl.lazer.g06.listeners.MyClickListener;

public class LevelsStage extends AbstractStage {
	
	String mode;
	boolean scores;
	boolean center;

	public LevelsStage(LazerChallenge game) {
		this(game, true);
	}
	
	public LevelsStage(LazerChallenge game, boolean center) {
		super(game, "Mode "+game.getMode()+" : Choix du niveau");
		setMode(game.getMode());
		this.center = center;
		
		addHeaderButton("Retour", "MENU_MODES");
		
		int[] standardLevels = new int[10];
		int[] advancedLevels = new int[9];
		for(int i = 0; i < standardLevels.length; ++i) {
			standardLevels[i] = i+1;
		}
		for(int i = 0; i < advancedLevels.length; ++i) {
			advancedLevels[i] = i+1;
		}
		
		addLevelsBlock("Niveaux standards", "STANDARD", standardLevels);
		addLevelsBlock("Niveaux avancés", "ADVANCED", advancedLevels);
		
		content.row();
		
		if(!center) {
			addExpandingBlock(content);
		}
	}
	
	private boolean setMode(String mode) {
		scores = false;
		
		switch(mode) {
		case "ARCADE":
			scores = true;
		case "TRAINING":
			this.mode = mode;
			return true;
		default:
			Gdx.app.error("LevelsStage.setMode", "mode "+mode+" not implemented");
			return false;
		}
	}

	private void addLevelsBlock(String name, String type, int[] levels) {
		Table block = new Table();
		
		Label title = new Label(name+" : ", skin, "label");
		block.row();
		block.add(title).pad(20).padLeft(100).left();
		
		Table levelsContainer = new Table();
		for(int i : levels) {
			addLevelButton(levelsContainer, type, i, false);
		}
		
		block.row();
		if(center) {
			block.add(levelsContainer).expand().top();
		} else {
			block.add(levelsContainer).expand().top().left();
		}
		
		content.row();
		content.add(block).expandX().fillX();
	}
	
	protected void addLevelButton(Table container, String type, int number, boolean locked) {
		String str_number = Integer.toString(number);
		Table levelContainer = new Table();
		
		TextButton btn = new TextButton(str_number, skin, locked ? "disabled-menu" : "menu");
		btn.getLabelCell().pad(10);
		if(!locked) {
			//String action = "ACTION_LEVEL_"+mode+"_"+type+"_"+str_number;
			//btn.addListener(new MyClickListener(game, Input.Buttons.LEFT, action));
			btn.addListener(new LevelInfosListener(game, Input.Buttons.LEFT, mode, type, number, false));
			
		}
		levelContainer.add(btn).minSize(80, 80).pad(10);
		levelContainer.row();
		
		Label subtitle = new Label("Niveau "+str_number, skin, "small-label");
		levelContainer.add(subtitle);
		levelContainer.row();
		
		if(scores) {
			Label score = new Label("Score : 0", skin, "small-label");
			levelContainer.add(score);
		}
		
		container.add(levelContainer).pad(20).left();
	}

}
