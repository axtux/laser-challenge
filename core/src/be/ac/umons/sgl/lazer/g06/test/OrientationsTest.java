package be.ac.umons.sgl.lazer.g06.test;

import static org.junit.Assert.*;

import java.io.IOException;

import be.ac.umons.sgl.lazer.g06.game.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.files.FileHandle;

public class OrientationsTest {
		
	Orientation test;
	Orientation testA;
	Orientation testB;
	Orientation testC;
	
	@Before
	public void init() throws IOException{
		test = new Orientation(new FileHandle("/home/mehdi/Bureau/projetGL/pgl1617-pgl6/levels/standard/orientations.xml"));
		testA = new Orientation(new FileHandle("/home/mehdi/Bureau/projetGL/pgl1617-pgl6/core/assets/testUnit/testA.xml"));
		testB = new Orientation(new FileHandle("/home/mehdi/Bureau/projetGL/pgl1617-pgl6/core/assets/testUnit/testB.xml"));
		testC = new Orientation(new FileHandle("/home/mehdi/Bureau/projetGL/pgl1617-pgl6/core/assets/testUnit/testC.xml"));
		
	}
	@Test
	public void testParseur() {
		// test if the xml has correctly been parsed
		assertEquals(test.size(),4);
		// test when the xml file has no orientation
		assertEquals(testA.size(),0);
		// test when the xml file has others balises and it has correctly been parsed
		assertEquals(testB.size(),2);
		//test when a balise contains 2 same orientations
		assertEquals(testC.size(),2);
	}
	
	@Test
	public void testAttribut(){
		// test sizes of attributs are ok 
		assertEquals(test.getOrientationToIndice().size(),test.getIndiceToOrientation().length);
		assertEquals(testC.getOrientationToIndice().size(),testC.getIndiceToOrientation().length);
		assertTrue(testC.getIndiceToOrientation()[0].equals("UP"));
		
	}

}
