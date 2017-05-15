package be.ac.umons.sgl.lazer.g06;

import static org.junit.Assert.*;

import org.junit.Test;

import be.ac.umons.sgl.lazer.g06.game.Position;
import be.ac.umons.sgl.lazer.g06.game.orientations.StandardOrientation;

public class OrientationUPTests {
	StandardOrientation o = StandardOrientation.UP;
	@Test
	public void reverseTest() {
		assertEquals(StandardOrientation.DOWN, o.reverse());
	}
	@Test
	public void nextTest() {
		assertEquals(StandardOrientation.RIGHT, o.next());
	}
	@Test
	public void prevTest() {
		assertEquals(StandardOrientation.LEFT, o.prev());
	}
	@Test
	public void angleTest() {
		assertEquals(0, o.getAngle());
	}
	@Test
	public void rotateUPTest() {
		assertEquals(StandardOrientation.UP, o.rotateBy(StandardOrientation.UP));
	}
	@Test
	public void rotateRIGHTTest() {
		assertEquals(StandardOrientation.RIGHT, o.rotateBy(StandardOrientation.RIGHT));
	}
	@Test
	public void rotateDOWNTest() {
		assertEquals(StandardOrientation.DOWN, o.rotateBy(StandardOrientation.DOWN));
	}
	@Test
	public void rotateLEFTTest() {
		assertEquals(StandardOrientation.LEFT, o.rotateBy(StandardOrientation.LEFT));
	}
	@Test
	public void unRotateUPTest() {
		assertEquals(StandardOrientation.UP, o.unRotateBy(StandardOrientation.UP));
	}
	@Test
	public void unRotateRIGHTTest() {
		assertEquals(StandardOrientation.LEFT, o.unRotateBy(StandardOrientation.RIGHT));
	}
	@Test
	public void unRotateDOWNTest() {
		assertEquals(StandardOrientation.DOWN, o.unRotateBy(StandardOrientation.DOWN));
	}
	@Test
	public void unRotateLEFTTest() {
		assertEquals(StandardOrientation.RIGHT, o.unRotateBy(StandardOrientation.LEFT));
	}
	@Test
	public void nextPositionTest() {
		Position p = new Position(1, 1);
		assertEquals(new Position(1, 2), o.nextPosition(p));
	}
}
