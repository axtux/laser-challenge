package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.Gdx;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelFinishedStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelInfosStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelPausedStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelPlayingStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LevelsStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LocalLoginStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.LoginsStage;
import be.ac.umons.sgl.lazer.g06.graphic.stages.ModesStage;
import be.ac.umons.sgl.lazer.g06.users.LocalUser;
import be.ac.umons.sgl.lazer.g06.users.User;
import be.ac.umons.sgl.lazer.g06.users.LocalUser.LoginException;

public class MyActionListener implements ActionListener {
	LazerChallenge game;
	
	public MyActionListener(LazerChallenge game) {
		this.game = game;
	}
	
	public void act(String action) {
		Gdx.app.debug("ACTION", action);
		
		switch(action) {
		
		// ACTIONs from headers
		case "ACTION_EXIT":
			Gdx.app.exit();
			break; // required
		case "ACTION_LOGOUT":
			game.setUser(null);
			act("MENU_LOGINS");
			break;
		
		case "MENU_LOGINS":
			game.setStage(new LoginsStage(game));
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
			game.setStage(new LocalLoginStage(game));
			break;
		// ACTIONs from LOGIN_LOCAL
		case "ACTION_LOCAL_LOGIN":
			local_user(false);
			break;
		case "ACTION_LOCAL_SIGNUP":
			local_user(true);
			break;
			
		case "MENU_MODES":
			game.setStage(new ModesStage(game));
			break;
		// ACTIONs from MENU_MODES
		case "ACTION_MODE_ARCADE":
			game.setMode("ARCADE");
			act("MENU_LEVELS");
			break;
		case "ACTION_MODE_TRAINING":
			game.setMode("TRAINING");
			act("MENU_LEVELS");
			break;
		
		case "MENU_LEVELS":
			game.setStage(new LevelsStage(game));
			break;
		
		// ACTIONs from MENU_LEVELS
		case "MENU_LEVEL_INFOS":
			game.setStage(new LevelInfosStage(game));
			break;
		
		// ACTIONs from MENU_LEVEL_INFOS
		case "MENU_LEVEL_LOAD":
			// TODO load level
			break;
		case "ACTION_LEVEL_LAUNCH":
			game.setStage(new LevelPlayingStage(game));
			break;

		case "MENU_LEVEL_PAUSE": // TODO remove parameters
			game.setStage(new LevelPausedStage(game, "ARCADE", 10));
			break;

		case "MENU_LEVEL_FINISHED":
			game.setStage(new LevelFinishedStage(game, "ARCADE", 10));
			break;
		
		default:
			Gdx.app.error("ACTION_NOT_IMPLEMENTED", action);
		}
		
	}
	
	private void local_user(boolean create) {
		String username = game.getStage().getFieldValue("USERNAME");
		String password = game.getStage().getFieldValue("PASSWORD");
		
		try {
			User user = new LocalUser(username, password, create);
			game.setUser(user);
			game.setStage(new ModesStage(game));
		} catch (LoginException e) {
			game.setStage(new LocalLoginStage(game, username, password, e.getMessage()));
			
			//e.printStackTrace();
		}
		
	}

}
