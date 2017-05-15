package be.ac.umons.sgl.lazer.g06;

import static org.junit.Assert.*;

import org.junit.Test;

import be.ac.umons.sgl.lazer.g06.game.Position;
import be.ac.umons.sgl.lazer.g06.game.orientations.StandardOrientation;

public class OrientationRIGHTTests {
	StandardOrientation o = StandardOrientation.RIGHT;
	@Test
	public void reverseTest() {
		assertEquals(StandardOrientation.LEFT, o.reverse());
	}
	@Test
	public void nextTest() {
		assertEquals(StandardOrientation.DOWN, o.next());
	}
	@Test
	public void prevTest() {
		assertEquals(StandardOrientation.UP, o.prev());
	}
	@Test
	public void angleTest() {
		assertEquals(90, o.getAngle());
	}
	@Test
	public void rotateUPTest() {
		assertEquals(StandardOrientation.RIGHT, o.rotateBy(StandardOrientation.UP));
	}
	@Test
	public void rotateRIGHTTest() {
		assertEquals(StandardOrientation.DOWN, o.rotateBy(StandardOrientation.RIGHT));
	}
	@Test
	public void rotateDOWNTest() {
		assertEquals(StandardOrientation.LEFT, o.rotateBy(StandardOrientation.DOWN));
	}
	@Test
	public void rotateLEFTTest() {
		assertEquals(StandardOrientation.UP, o.rotateBy(StandardOrientation.LEFT));
	}
	@Test
	public void unRotateUPTest() {
		assertEquals(StandardOrientation.RIGHT, o.unRotateBy(StandardOrientation.UP));
	}
	@Test
	public void unRotateRIGHTTest() {
		assertEquals(StandardOrientation.UP, o.unRotateBy(StandardOrientation.RIGHT));
	}
	@Test
	public void unRotateDOWNTest() {
		assertEquals(StandardOrientation.LEFT, o.unRotateBy(StandardOrientation.DOWN));
	}
	@Test
	public void unRotateLEFTTest() {
		assertEquals(StandardOrientation.DOWN, o.unRotateBy(StandardOrientation.LEFT));
	}
	@Test
	public void nextPositionTest() {
		Position p = new Position(1, 1);
		assertEquals(new Position(2, 1), o.nextPosition(p));
	}
}
