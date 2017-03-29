package be.ac.umons.sgl.lazer.g06;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import be.ac.umons.sgl.lazer.g06.game.BlockType;
import be.ac.umons.sgl.lazer.g06.game.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.game.LevelType;
import be.ac.umons.sgl.lazer.g06.game.Orientation;

public class StandardBlockTypeTest {
	/**
	 * Create application before any test
	 */
	LwjglApplication app;
	/**
	 * save level type for all tests to use it
	 */
	LevelType standard;
	/**
	 * Initialize Gdx application before using any feature.
	 */
	@Before
	public void before() {
		// init app only once
		if(app != null) {
			return;
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Lazer Challenge";
		config.width = 1;
		config.height = 1;
		app = new LwjglApplication(LazerChallenge.getInstance(), config);
		// initialize level type
		standard = LevelType.getLevelType("standard");
		Gdx.app.debug("StandardBlockTypeTest.before", String.join("|", standard.getBlockTypes()));
	}
	
	@Test(expected=GdxRuntimeException.class)
	public void InvalidNameTest() {
		standard.getBlockType("invalid");
	}
	
	@Test
	public void GateTest() {
		// initialization
		BlockType gate = standard.getBlockType("gate");
		Array<Orientation> outputs;
		// from UP
		outputs = gate.input(Orientation.UP);
		assertEquals("From UP size", 1, outputs.size);
		assertEquals("From UP Orientation", Orientation.DOWN, outputs.get(0));
		// from DOWN
		outputs = gate.input(Orientation.DOWN);
		assertEquals("From DOWN size", 1, outputs.size);
		assertEquals("From DOWN Orientation", Orientation.UP, outputs.get(0));
		// FROM RIGHT
		outputs = gate.input(Orientation.RIGHT);
		assertEquals("From RIGHT size", 0, outputs.size);
		// FROM LEFT
		outputs = gate.input(Orientation.LEFT);
		assertEquals("From LEFT size", 0, outputs.size);
	}
}
