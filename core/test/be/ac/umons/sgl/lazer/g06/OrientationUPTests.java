package be.ac.umons.sgl.lazer.g06;

import static org.junit.Assert.*;

import org.junit.Test;

import be.ac.umons.sgl.lazer.g06.game.Position;
import be.ac.umons.sgl.lazer.g06.game.orientations.Orientation;

public class OrientationUPTests {
	Orientation o = Orientation.UP;
	@Test
	public void reverseTest() {
		assertEquals(Orientation.DOWN, o.reverse());
	}
	@Test
	public void nextTest() {
		assertEquals(Orientation.RIGHT, o.next());
	}
	@Test
	public void prevTest() {
		assertEquals(Orientation.LEFT, o.prev());
	}
	@Test
	public void angleTest() {
		assertEquals(0, o.getAngle());
	}
	@Test
	public void rotateUPTest() {
		assertEquals(Orientation.UP, o.rotateBy(Orientation.UP));
	}
	@Test
	public void rotateRIGHTTest() {
		assertEquals(Orientation.RIGHT, o.rotateBy(Orientation.RIGHT));
	}
	@Test
	public void rotateDOWNTest() {
		assertEquals(Orientation.DOWN, o.rotateBy(Orientation.DOWN));
	}
	@Test
	public void rotateLEFTTest() {
		assertEquals(Orientation.LEFT, o.rotateBy(Orientation.LEFT));
	}
	@Test
	public void unRotateUPTest() {
		assertEquals(Orientation.UP, o.unRotateBy(Orientation.UP));
	}
	@Test
	public void unRotateRIGHTTest() {
		assertEquals(Orientation.LEFT, o.unRotateBy(Orientation.RIGHT));
	}
	@Test
	public void unRotateDOWNTest() {
		assertEquals(Orientation.DOWN, o.unRotateBy(Orientation.DOWN));
	}
	@Test
	public void unRotateLEFTTest() {
		assertEquals(Orientation.RIGHT, o.unRotateBy(Orientation.LEFT));
	}
	@Test
	public void nextPositionTest() {
		Position p = new Position(1, 1);
		assertEquals(new Position(1, 2), o.nextPosition(p));
	}
}
