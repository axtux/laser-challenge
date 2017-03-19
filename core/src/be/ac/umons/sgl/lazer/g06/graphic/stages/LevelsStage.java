package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.game.Level;
import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.listeners.LevelSelectorListener;

public class LevelsStage extends AbstractStage {
	boolean displayScore;
	boolean center;
	
	public LevelsStage(LazerChallenge game) {
		this(game, true);
	}
	
	public LevelsStage(LazerChallenge game, boolean center) {
		super(game, game.getMode()+" : Choix du niveau");
		
		if(game.getMode() == null) {
			throw new GdxRuntimeException("game mode cannot be null.");
		}
		displayScore = game.getMode().equals("ARCADE");
		
		this.center = center;
		
		addHeaderButton("Retour", "MENU_MODES");
		
		addLevelsBlock("Niveaux standards", "STANDARD");
		addLevelsBlock("Niveaux avancés", "ADVANCED");
		
		content.row();
		
		if(!center) {
			addExpandingBlock(content);
		}
	}
	
	private void addLevelsBlock(String name, String type) {
		Table block = new Table();
		
		Label title = new Label(name+" : ", skin, "label");
		block.row();
		block.add(title).pad(20).padLeft(100).left();
		
		Table levelsContainer = new Table();
		
		int i = 1;
		for(Level level : Level.getLevels(type)) {
			addLevelButton(levelsContainer, level, i++, false);
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
	
	protected void addLevelButton(Table container, Level level, int number, boolean locked) {
		String str_number = Integer.toString(number);
		Table levelContainer = new Table();
		
		TextButton btn = new TextButton(str_number, skin, locked ? "disabled-menu" : "menu");
		btn.getLabelCell().pad(10);
		if(!locked) {
			btn.addListener(new LevelSelectorListener(game, Input.Buttons.LEFT, "MENU_LEVEL_INFOS", level));
			
		}
		levelContainer.add(btn).minSize(80, 80).pad(10);
		levelContainer.row();
		
		Label subtitle = new Label(level.getName(), skin, "small-label");
		levelContainer.add(subtitle);
		levelContainer.row();
		
		if(displayScore) {
			Label score = new Label("Score : 0", skin, "small-label");
			levelContainer.add(score);
		}
		
		container.add(levelContainer).pad(20).left();
	}

}
