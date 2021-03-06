package be.ac.umons.sgl.lazer.g06.desktop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import be.ac.umons.sgl.lazer.g06.game.LazerChallenge;

public class DesktopLauncher {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Lazer Challenge";
		config.width = LazerChallenge.WIDTH;
		config.height = LazerChallenge.HEIGHT;
		//*
		config.fullscreen = false;
		//*/config.fullscreen = true;
		new LwjglApplication(LazerChallenge.getInstance(), config);
		
		if(!config.fullscreen) {
			// maximize window
			config.height = Gdx.graphics.getHeight();
			config.width = Gdx.graphics.getWidth();
		}
	}
}
