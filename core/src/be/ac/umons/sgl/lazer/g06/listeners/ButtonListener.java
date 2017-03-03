package be.ac.umons.sgl.lazer.g06.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class ButtonListener extends ChangeListener {
	String action;
	
	public ButtonListener(String action) {
		this.action = action;
	}
	/*
	public boolean handle(Event event) {
		Actor actor = event.getTarget();
		if(! (actor instanceof MyButton) ) {
			System.out.println("event triggered but actor not MyButton ("+actor.getClass().getName()+")");
			return false;
		}
		
		if(action.equals("MENU")) {
			System.out.println("launching menu");
			return true;
		}
		
		return false;
	}
	//*/
	public void changed(ChangeEvent event, Actor actor) {
		System.out.println("doing action "+this.action);
		
	}

}
