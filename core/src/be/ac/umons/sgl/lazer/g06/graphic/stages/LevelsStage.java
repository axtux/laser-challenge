package be.ac.umons.sgl.lazer.g06.graphic.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.game.Level;
import be.ac.umons.sgl.lazer.g06.game.LevelType;
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
		
		Array<String> types = LevelType.getLevelTypes();
		Gdx.app.debug("LevelsStage.LevelsStage", "Found types "+String.join("|", types));
		LevelType lt;
		for(String type : types) {
			try {
				lt = new LevelType(type);
			} catch (GdxRuntimeException e) {
				Gdx.app.error("LevelsStage.LevelsStage", "Not able to create LevelType from type "+type+" : "+e.getMessage());
				continue;
			}
			addLevelsBlock(lt);
		}
		
		content.row();
		
		if(!center) {
			addExpandingBlock(content);
		}
	}
	
	private void addLevelsBlock(LevelType lt) {
		Table block = new Table();
		
		Label title = new Label(lt.getName()+" : ", skin, "label");
		block.row();
		block.add(title).pad(20).padLeft(100).left();
		
		Table levelsContainer = new Table();
		
		int i = 1;
		for(Level level : Level.getLevels(lt.getRawName())) {
			addLevelButton(levelsContainer, level, i++, false);
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
	
	protected void addLevelButton(Table container, Level level, int number, boolean locked) {
		String str_number = Integer.toString(number);
		Table levelContainer = new Table();
		
		TextButton btn = new TextButton(str_number, skin, locked ? "disabled-menu" : "menu");
		btn.getLabelCell().pad(10);
		if(!locked) {
			btn.addListener(new LevelSelectorListener(Input.Buttons.LEFT, "MENU_LEVEL_INFOS", level));
			
		}
		levelContainer.add(btn).minSize(80, 80).pad(10);
		levelContainer.row();
		
		Label subtitle = new Label(level.getName(), skin, "small-label");
		levelContainer.add(subtitle);
		levelContainer.row();
		
		if(game.getMode().hasScore()) {
			Label score = new Label("Score : 0", skin, "small-label");
			levelContainer.add(score);
		}
		
		container.add(levelContainer).pad(20).left();
	}

}
