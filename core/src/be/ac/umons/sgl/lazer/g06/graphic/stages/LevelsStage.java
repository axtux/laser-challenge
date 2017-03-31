package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.game.Level;
import be.ac.umons.sgl.lazer.g06.game.LevelType;
import be.ac.umons.sgl.lazer.g06.game.LevelTypes;
import be.ac.umons.sgl.lazer.g06.game.Levels;
import be.ac.umons.sgl.lazer.g06.listeners.LevelSelectorListener;

public class LevelsStage extends AbstractStage {
	boolean center;
	
	public LevelsStage() {
		this(true);
	}
	
	public LevelsStage(boolean center) {
		super();
		
		if(game.getMode() == null) {
			throw new GdxRuntimeException("game mode cannot be null.");
		}
		setTitle(game.getMode().toString()+" : Choix du niveau");
		
		this.center = center;
		
		addHeaderButton("Retour", "MENU_MODES");
		
		Array<String> types = LevelTypes.getLevelTypes();
		for(String type : types) {
			addLevelsBlock(LevelTypes.getLevelType(type));
		}
		
		content.row();
		
		if(!center) {
			addExpandingBlock(content);
		}
	}
	/**
	 * Add level block
	 * @param lt
	 */
	private void addLevelsBlock(LevelType lt) {
		Table block = new Table();
		
		Label title = new Label(lt.getLabel()+" : ", skin, "label");
		block.row();
		block.add(title).pad(20).padLeft(100).left();
		
		Table levelsContainer = new Table();
		
		int i = 1;
		boolean unlocked = true;
		for(Level level : Levels.getLevels(lt)) {
			unlocked &= addLevelButton(levelsContainer, level, i++, unlocked);
		}
		
		block.row();
		if(center) {
			block.add(levelsContainer).expand().top();
		} else {
			block.add(levelsContainer).expand().top().left();
		}
		
		content.row();
		content.add(block).growX();
	}
	/**
	 * Add level button
	 * @param container
	 * @param level
	 * @param number
	 * @param unlocked
	 * @return Whether score equals zero.
	 */
	protected boolean addLevelButton(Table container, Level level, int number, boolean unlocked) {
		String str_number = Integer.toString(number);
		Table levelContainer = new Table();
		unlocked = unlocked || !game.getMode().hasScore();
		
		TextButton btn = new TextButton(str_number, skin, unlocked ? "menu" : "disabled-menu");
		btn.getLabelCell().pad(10);
		if(unlocked) {
			btn.addListener(new LevelSelectorListener(Input.Buttons.LEFT, "MENU_LEVEL_INFOS", level));
			
		}
		levelContainer.add(btn).minSize(80, 80).pad(10);
		levelContainer.row();
		
		Label subtitle = new Label(level.getName(), skin, "small-label");
		levelContainer.add(subtitle);
		levelContainer.row();
		
		int score = 0;
		if(game.getMode().hasScore()) {
			score = game.getUser().loadScore(level.getName());
			Label label = new Label("Score : "+Integer.toString(score), skin, "small-label");
			levelContainer.add(label);
		}
		
		container.add(levelContainer).pad(20).left();
		return score != 0;
	}

}
