package be.ac.umons.sgl.lazer.g06.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import be.ac.umons.sgl.lazer.g06.game.BlockType;

public class BlockTypeTest {
	BlockType testA;
	
	@Test
	public void testA() throws IOException{
		testA=new BlockType(Gdx.files.internal(("/levels/standard/block.xml")));
	}

}
