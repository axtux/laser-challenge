package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.graphic.ModeSelectionStage;

public class ButtonListener extends ChangeListener {
	LazerChallenge game;
	String action;
	
	public ButtonListener(LazerChallenge game, String action) {
		this.game = game;
		this.action = action;
	}
	
	public void changed(ChangeEvent event, Actor actor) {
		// switch String available since Java 1.7
		System.out.println("acting "+this.action);
		switch(this.action) {
		case "CONNECTION_ANONYMOUS":
			game.setStage(new ModeSelectionStage(game));
			break;
		case "CONNECTION_LOCAL":
			//game.setStage(new Stage(game));
			break;
		case "CONNECTION_TWITTER":
			//game.setStage(new Stage(game));
			break;
		}
		
	}
	
}
