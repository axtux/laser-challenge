package be.ac.umons.sgl.lazer.g06.test;

import static org.junit.Assert.*;

import java.io.IOException;

import be.ac.umons.sgl.lazer.g06.game.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class OrientationsTest {
		
	Orientation test;
	Orientation testA;
	Orientation testB;
	Orientation testC;
	
	@Before
	public void init() throws IOException{
		
	}
	
	@Test
	public void test() throws IOException {
		test = new Orientation(Gdx.files.classpath("/levels/standard/orientations.xml"));
		// test if the xml has correctly been parsed
		assertEquals(test.size(),4);
		// test sizes of attributs are ok 
		assertEquals(test.getOrientationToIndice().size(),test.getIndiceToOrientation().length);
	}
	@Test
	public void testA() throws IOException{
		testA = new Orientation(Gdx.files.classpath("/testUnit/testA.xml"));
		// test when the xml file has no orientation
		assertEquals(testA.size(),0);
		// test sizes of attributs are ok 
		assertEquals(test.getOrientationToIndice().size(),test.getIndiceToOrientation().length);
	}
	
	@Test
	public void TestB() throws IOException{
		testB = new Orientation(Gdx.files.classpath("/testUnit/testB.xml"));
		// test when the xml file has others balises and it has correctly been parsed
		assertEquals(testB.size(),2);
		// test sizes of attributs are ok 
		assertEquals(testB.getOrientationToIndice().size(),testB.getIndiceToOrientation().length);
	}
	@Test
	public void TestC() throws IOException{
		testC = new Orientation(Gdx.files.classpath("/testUnit/testC.xml"));
		//test when a balise contains 2 same orientations
		assertEquals(testC.size(),2);
		assertEquals(testC.getOrientationToIndice().size(),testC.getIndiceToOrientation().length);
		assertTrue(testC.getIndiceToOrientation()[0].equals("UP"));
	}
}
