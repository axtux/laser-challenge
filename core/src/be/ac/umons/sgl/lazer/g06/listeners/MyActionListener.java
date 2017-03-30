package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.Gdx;

import be.ac.umons.sgl.lazer.g06.game.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.game.Level;
import be.ac.umons.sgl.lazer.g06.game.LevelType;
import be.ac.umons.sgl.lazer.g06.game.Levels;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelFinishedStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelInfosStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelPlayingStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelsStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LocalLoginStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LoginsStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.ModesStage;
import be.ac.umons.sgl.lazer.g06.users.LocalUser;
import be.ac.umons.sgl.lazer.g06.users.User;
import be.ac.umons.sgl.lazer.g06.users.LocalUser.LoginException;
/**
 * Manage actions from UI
 */
public class MyActionListener implements ActionListener {
	LazerChallenge game;
	
	public MyActionListener() {
		this.game = LazerChallenge.getInstance();
	}
	/**
	 * Called by LaserChallenge.act(String)
	 */
	public void act(String action) {
		Gdx.app.debug("ACTION", action);
		
		switch(action) {
		
		// ACTIONs to LOAD resources
		case "ACTION_LOAD_LEVELS":
			Levels.getLevels(true);
			break;
		case "ACTION_LOAD_LEVEL_TYPES":
			LevelType.getLevelTypes(true);
			break;
		
		// ACTIONs from headers
		case "ACTION_EXIT":
			Gdx.app.exit();
			break; // required
		case "ACTION_LOGOUT":
			game.setUser(null);
			act("MENU_LOGINS");
			break;
		
		case "MENU_LOGINS":
			game.setStage(new LoginsStage());
			break;
		// ACTIONs from MENU_LOGINS
		case "ACTTION_LOGIN_ANONYMOUS":
			game.setUser(new User());
			act("MENU_MODES");
			break;
		case "ACTTION_LOGIN_LOCAL":
			act("MENU_LOGIN_LOCAL");
			break;
		case "ACTTION_LOGIN_TWITTER":
			//setStage(new Stage(this));
			break;
		
		case "MENU_LOGIN_LOCAL":
			game.setStage(new LocalLoginStage());
			break;
		// ACTIONs from LOGIN_LOCAL
		case "ACTION_LOCAL_LOGIN":
			localUser(false);
			break;
		case "ACTION_LOCAL_SIGNUP":
			localUser(true);
			break;
			
		case "MENU_MODES":
			game.setStage(new ModesStage());
			break;
		// ACTIONs from MENU_MODES
		case "ACTION_MODE_ARCADE":
			game.setMode(Level.Mode.ARCADE);
			act("MENU_LEVELS");
			break;
		case "ACTION_MODE_TRAINING":
			game.setMode(Level.Mode.TRAINING);
			act("MENU_LEVELS");
			break;
		
		case "MENU_LEVELS":
			game.setStage(new LevelsStage());
			break;
		
		// ACTIONs from MENU_LEVELS
		case "MENU_LEVEL_INFOS":
			game.setStage(new LevelInfosStage());
			break;
		case "MENU_LEVEL_PLAY":
			game.setStage(new LevelPlayingStage());
			break;
		
		// ACTIONs from MENU_LEVEL_INFOS
		case "MENU_LEVEL_LOAD":
			act("MENU_LEVEL_PLAY");
			game.getLevel().start(true);
			break;
		case "ACTION_LEVEL_START":
			act("MENU_LEVEL_PLAY");
			game.getLevel().start(false);
			break;
		case "ACTION_LEVEL_STOP":
			game.getLevel().stop();
			act("ACTION_LEVEL_INFOS");
			break;
		
		case "MENU_LEVEL_FINISHED":
			game.setStage(new LevelFinishedStage());
			break;
		
		case "ACTION_LEVEL_UNDO":
			game.getLevel().undo();
			break;
		case "ACTION_LEVEL_MOVE":
			game.getLevel().moving(true);
			break;
		case "ACTION_LEVEL_ROTATE":
			game.getLevel().rotateSelected();
			break;
		case "ACTION_LEVEL_LASER":
			game.getLevel().startLasers();
			break;
		
		default:
			Gdx.app.error("ACTION_NOT_IMPLEMENTED", action);
		}
		
	}
	/**
	 * Manage local user connection.
	 * @param create If true, user will be created. If false, user will be logged in.
	 */
	private void localUser(boolean create) {
		String username = game.getStage().getFieldValue("USERNAME");
		String password = game.getStage().getFieldValue("PASSWORD");
		
		try {
			User user = new LocalUser(username, password, create);
			game.setUser(user);
			game.setStage(new ModesStage());
		} catch (LoginException e) {
			// keep username, password and display error message
			game.setStage(new LocalLoginStage(username, password, e.getMessage()));
		}
		
	}

}
