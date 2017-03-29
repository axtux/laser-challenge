package be.ac.umons.sgl.lazer.g06;

import static org.junit.Assert.*;

import org.junit.Test;

import be.ac.umons.sgl.lazer.g06.game.Orientation;
import be.ac.umons.sgl.lazer.g06.game.Position;

public class OrientationRIGHTTests {
	Orientation o = Orientation.RIGHT;
	@Test
	public void reverseTest() {
		assertEquals(Orientation.LEFT, o.reverse());
	}
	@Test
	public void nextTest() {
		assertEquals(Orientation.DOWN, o.next());
	}
	@Test
	public void prevTest() {
		assertEquals(Orientation.UP, o.prev());
	}
	@Test
	public void angleTest() {
		assertEquals(90, o.getAngle());
	}
	@Test
	public void rotateUPTest() {
		assertEquals(Orientation.RIGHT, o.rotateBy(Orientation.UP));
	}
	@Test
	public void rotateRIGHTTest() {
		assertEquals(Orientation.DOWN, o.rotateBy(Orientation.RIGHT));
	}
	@Test
	public void rotateDOWNTest() {
		assertEquals(Orientation.LEFT, o.rotateBy(Orientation.DOWN));
	}
	@Test
	public void rotateLEFTTest() {
		assertEquals(Orientation.UP, o.rotateBy(Orientation.LEFT));
	}
	@Test
	public void unRotateUPTest() {
		assertEquals(Orientation.RIGHT, o.unRotateBy(Orientation.UP));
	}
	@Test
	public void unRotateRIGHTTest() {
		assertEquals(Orientation.UP, o.unRotateBy(Orientation.RIGHT));
	}
	@Test
	public void unRotateDOWNTest() {
		assertEquals(Orientation.LEFT, o.unRotateBy(Orientation.DOWN));
	}
	@Test
	public void unRotateLEFTTest() {
		assertEquals(Orientation.DOWN, o.unRotateBy(Orientation.LEFT));
	}
	@Test
	public void nextPositionTest() {
		Position p = new Position(1, 1);
		assertEquals(new Position(2, 1), o.nextPosition(p));
	}
}
