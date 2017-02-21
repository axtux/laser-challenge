package be.ac.umons.sgl.lazer.g06.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import be.ac.umons.sgl.lazer.g06.LazerChallenge;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Lazer Challenge";
		config.useGL30=true;
		config.width=LazerChallenge.WIDTH;
		config.height=LazerChallenge.HEIGHT;
		
		new LwjglApplication(new LazerChallenge(), config);
	}
}
