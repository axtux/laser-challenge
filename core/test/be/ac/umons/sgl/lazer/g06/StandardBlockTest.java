package be.ac.umons.sgl.lazer.g06;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.utils.Array;

import be.ac.umons.sgl.lazer.g06.game.Block;
import be.ac.umons.sgl.lazer.g06.game.BlockType;
import be.ac.umons.sgl.lazer.g06.game.LazerChallenge;
import be.ac.umons.sgl.lazer.g06.game.LevelType;
import be.ac.umons.sgl.lazer.g06.game.orientations.Orientation;

@RunWith(GdxTestRunner.class)
public class StandardBlockTest {
	/**
	 * save level type for all tests to use it
	 */
	LevelType standard;
	/**
	 * Initialize Gdx application before using any feature.
	 */
	@Before
	public void before() {
		// initialize LazerChallenge
		LazerChallenge.getInstance();
		// initialize level type
		standard = LevelType.getLevelType("standard");
	}
	
	@Test
	public void BlockerTest() {
		// initialization
		BlockType blockType = standard.getBlockType("blocker");
		Block block = new Block(blockType, Orientation.UP);
		Array<Orientation> outputs;
		// check type
		assertEquals(blockType, block.getType());
		// from null
		outputs = block.input(null);
		assertEquals("From null size", 0, outputs.size);
		// from UP
		outputs = block.input(Orientation.UP);
		assertEquals("From UP size", 0, outputs.size);
		// from DOWN
		outputs = block.input(Orientation.DOWN);
		assertEquals("From DOWN size", 0, outputs.size);
		// FROM RIGHT
		outputs = block.input(Orientation.RIGHT);
		assertEquals("From RIGHT size", 0, outputs.size);
		// FROM LEFT
		outputs = block.input(Orientation.LEFT);
		assertEquals("From LEFT size", 0, outputs.size);
	}
	
	@Test
	public void GateTest() {
		// initialization
		BlockType blockType = standard.getBlockType("gate");
		Block block = new Block(blockType, Orientation.UP);
		Array<Orientation> outputs;
		// check type
		assertEquals(blockType, block.getType());
		// from null
		outputs = block.input(null);
		assertEquals("From null size", 0, outputs.size);
		// from UP
		outputs = block.input(Orientation.UP);
		assertEquals("From UP size", 1, outputs.size);
		assertEquals("From UP Orientation", Orientation.DOWN, outputs.get(0));
		// from DOWN
		outputs = block.input(Orientation.DOWN);
		assertEquals("From DOWN size", 1, outputs.size);
		assertEquals("From DOWN Orientation", Orientation.UP, outputs.get(0));
		// FROM RIGHT
		outputs = block.input(Orientation.RIGHT);
		assertEquals("From RIGHT size", 0, outputs.size);
		// FROM LEFT
		outputs = block.input(Orientation.LEFT);
		assertEquals("From LEFT size", 0, outputs.size);
	}
	
	@Test
	public void OneWayMirrorTest() {
		// initialization
		BlockType blockType = standard.getBlockType("one-way_mirror");
		Block block = new Block(blockType, Orientation.UP);
		Array<Orientation> outputs;
		// check type
		assertEquals(blockType, block.getType());
		// from null
		outputs = block.input(null);
		assertEquals("From null size", 0, outputs.size);
		// from UP
		outputs = block.input(Orientation.UP);
		assertEquals("From UP size", 1, outputs.size);
		assertEquals("From UP Orientation", Orientation.RIGHT, outputs.get(0));
		// FROM RIGHT
		outputs = block.input(Orientation.RIGHT);
		assertEquals("From RIGHT size", 1, outputs.size);
		assertEquals("From RIGHT Orientation", Orientation.UP, outputs.get(0));
		// from DOWN
		outputs = block.input(Orientation.DOWN);
		assertEquals("From DOWN size", 0, outputs.size);
		// FROM LEFT
		outputs = block.input(Orientation.LEFT);
		assertEquals("From LEFT size", 0, outputs.size);
	}
}
