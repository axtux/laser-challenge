package be.ac.umons.sgl.lazer.g06.game;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * 
 *
 */
public class BlockType {
	
	private HashMap<String,String> blocks = new HashMap<String,String>();
	
	/**
	 * @param xml take a xml file in parametre
	 * @throws IOException if the xml file doesn't exist
	 */
	public BlockType(FileHandle xml) throws IOException{
		XmlReader reader= new XmlReader();
		Element root = reader.parse(xml);
		Array<Element> allBlocks = root.getChildrenByName("block");
		for (Element child : allBlocks){
			blocks.put(child.getChildByName("name").getText(),child.getChildByName("name").getText());
			}
	}
	
	/**
	 * 
	 * @param block
	 * @param orientation
	 */
	public void output(String block, String orientation){
		
	}
	
	/**
	 * 
	 * @return the attribut blocks
	 */
	public HashMap<String,String> getBlocks(){
		return this.blocks;
	}
	
	public int size(){
		return this.blocks.size();
	}
	
}
