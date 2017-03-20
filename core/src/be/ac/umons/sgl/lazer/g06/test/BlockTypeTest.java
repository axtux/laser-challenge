package be.ac.umons.sgl.lazer.g06.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import be.ac.umons.sgl.lazer.g06.game.BlockType;

public class BlockTypeTest {
	BlockType test;
	BlockType testA;
	BlockType testB;
	BlockType testC;
	
	@Test
	public void test() throws IOException{
		test=new BlockType(new FileHandle("/home/mehdi/Bureau/projetGL/pgl1617-pgl6/levels/standard/blocks.xml"));
		assertEquals(test.size(),12);
		assertEquals(test.getBlocks().size(),12);
	}
	@Test
	public void testA() throws IOException{
		testA=new BlockType(new FileHandle("/home/mehdi/Bureau/projetGL/pgl1617-pgl6/core/assets/testUnit/blockTest/testA.xml"));
		assertEquals(testA.size(),2);
	}
	@Test
	public void testB() throws IOException{
		testB=new BlockType(new FileHandle("/home/mehdi/Bureau/projetGL/pgl1617-pgl6/core/assets/testUnit/blockTest/testB.xml"));
		assertEquals(testB.size(),2);
	}
	
	@Test
	public void testC() throws IOException{
		testC=new BlockType(new FileHandle("/home/mehdi/Bureau/projetGL/pgl1617-pgl6/core/assets/testUnit/blockTest/testC.xml"));
		assertEquals(testC.size(),0);
	}

}
