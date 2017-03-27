package be.ac.umons.sgl.lazer.g06;

import static org.junit.Assert.*;

import java.io.IOException;

import be.ac.umons.sgl.lazer.g06.game.OldOrientation;
import be.ac.umons.sgl.lazer.g06.graphic.LazerChallenge;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;

public class OrientationsTest {
	OldOrientation test;
	OldOrientation testA;
	OldOrientation testB;
	OldOrientation testC;
	/**
	 * Initialize Gdx application before using any feature.
	 */
	@Before
	public void before() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Lazer Challenge";
		config.width = 1;
		config.height = 1;
		new LwjglApplication(new LazerChallenge(), config);
	}
	@Test
	public void test() throws IOException {
		FileHandle file = Gdx.files.local("level_types/standard/orientations.xml");
		assertNotNull("404 File not found", file);
		test = new OldOrientation(file);
		// test if the xml has correctly been parsed
		assertEquals(test.size(),4);
		// test sizes of attributs are ok 
		assertEquals(test.getOrientationToIndice().size(),test.getIndiceToOrientation().length);
	}
	@Test
	public void testA() throws IOException{
		testA = new OldOrientation(Gdx.files.classpath("/testUnit/orientationTest/testA.xml"));
		// test when the xml file has no orientation
		assertEquals(testA.size(),0);
		// test sizes of attributs are ok 
		assertEquals(test.getOrientationToIndice().size(),test.getIndiceToOrientation().length);
	}
	
	@Test
	public void TestB() throws IOException{
		testB = new OldOrientation(Gdx.files.classpath("/testUnit/orientationTest/testB.xml"));
		// test when the xml file has others balises and it has correctly been parsed
		assertEquals(testB.size(),2);
		// test sizes of attributs are ok 
		assertEquals(testB.getOrientationToIndice().size(),testB.getIndiceToOrientation().length);
	}
	@Test
	public void TestC() throws IOException{
		testC = new OldOrientation(Gdx.files.classpath("/orientationTest/testUnit/testC.xml"));
		//test when a balise contains 2 same orientations
		assertEquals(testC.size(),2);
		assertEquals(testC.getOrientationToIndice().size(),testC.getIndiceToOrientation().length);
		assertTrue(testC.getIndiceToOrientation()[0].equals("UP"));
	}
}
