package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.listeners.MyClickListener;

public class LevelsStage extends AbstractStage {
	
	String mode;
	boolean scores;
	
	public LevelsStage(LazerChallenge game) {
		super(game, "Mode "+game.getMode()+" : Choix du niveau", "MENU_MODES");
		setMode(game.getMode());
		
		addLevelsBlock("Niveaux standards");
		addLevelsBlock("Niveaux avancés");
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
	
	private void addLevelsBlock(String name) {
		Table block = new Table();
		
		Label title = new Label(name+" : ", skin, "label");
		block.row();
		block.add(title).pad(20).padLeft(100).left();
		
		Table levelsContainer = new Table();
		for(int i = 1; i <= 10; ++i) {
			addLevelButton(levelsContainer, i, false);
		}
		
		block.row();
		block.add(levelsContainer).expand().top();
		
		content.row();
		content.add(block).expandX().fillX().top().left();
	}
	
	protected void addLevelButton(Table container, int number, boolean locked) {
		String str_number = Integer.toString(number);
		Table levelContainer = new Table();
		
		TextButton btn = new TextButton(str_number, skin, locked ? "disabled-menu" : "menu");
		btn.getLabelCell().pad(10);
		if(!locked) {
			btn.addListener(new MyClickListener(game, Input.Buttons.LEFT, "ACTION_LEVEL_"+mode+"_"+str_number));
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
		
		container.add(levelContainer).pad(20);
		levelContainer.debug();
	}

}
