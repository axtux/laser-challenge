package be.ac.umons.sgl.lazer.g06.game;

import java.util.HashMap;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import be.ac.umons.sgl.lazer.g06.Files;
/**
 * Types of block available for class Block
 */
public class LevelType {
	private HashMap<String, BlockType> blocks;
	String rawName;
	String name;
	/**
	 * Parse XML blocks file of levelType and create associated BlockType objects.
	 * @param levelType LevelType name.
	 */
	public LevelType(String rawName) {
		this.rawName = rawName;
		
		name = Files.getContent(dirPath()+"name.txt");
		if(name == null) {
			throw new GdxRuntimeException("File "+dirPath()+"name.txt cannot be read.");
		}
		name = name.replaceAll("[\\r\\n]", "");
		
		String blocksXml = dirPath()+"blocks.xml";
		String xml = Files.getContent(blocksXml);
		if(xml == null) {
			throw new GdxRuntimeException("File "+blocksXml+" cannot be read.");
		}
		
		XmlReader reader = new XmlReader();
		Element blocksElement = reader.parse(xml);
		Array<Element> blockElements = blocksElement.getChildrenByName("block");
		blocks = new HashMap<String, BlockType>(blockElements.size);
		
		BlockType block;
		for(Element blockElement : blockElements) {
			block = new BlockType(blockElement);
			blocks.put(block.getName(), block);
		}
	}
	/**
	 * @return Directory path relative to application root.
	 */
	private String dirPath() {
		return "levels/"+rawName+"/";
	}
	/**
	 * @return Name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return Raw name.
	 */
	public String getRawName() {
		return rawName;
	}
	/**
	 * Get {@link BlockType} from BlockType name.
	 * @param blockType Name of BlockType.
	 * @return BlockType corresponding to given name.
	 */
	public BlockType getBlockType(String blockType) {
		BlockType block = blocks.get(blockType);
		if(block == null) {
			throw new GdxRuntimeException("Invalid blockType "+blockType+" for levelType "+rawName);
		}
		return block;
	}
	/**
	 * Get level types from disk.
	 * @return Level types.
	 */
	public static Array<String> getLevelTypes() {
		Array<String> types = Files.listDirs("levels");
		if(types == null) {
			throw new GdxRuntimeException("Unable to get levels types from filesystem.");
		}
		return types;
	}
	
	class BlockType {
		private final String image;
		private final String name;
		private final HashMap<String, Array<String>> inputs;
		
		public BlockType(Element block) {
			this.name = block.get("name");
			this.image = block.get("image");
			
			Element inputsElement = block.getChildByName("inputs");
			Array<Element> inputElements = inputsElement.getChildrenByName("input");
			inputs = new HashMap<String, Array<String>>(inputElements.size);
			for(Element inputElement : inputElements) {
				Array<Element> outputElements = inputElement.getChildrenByName("output");
				Array<String> outputs = new Array<String>(outputElements.size);
				for(Element outputElement : outputElements) {
					outputs.add(outputElement.getAttribute("orientation"));
				}
				inputs.put(inputElement.getAttribute("orientation", "NONE"), outputs);
			}
		}
		
		public String getName() {
			return name;
		}
		
		public String getImage() {
			return image;
		}
		
		public Array<String> input(String orientation) {
			if(orientation == null) {
				orientation = "NONE";
			}
			Array<String> array = inputs.get(orientation);
			if(array == null) {
				array = new Array<String>(0);
			}
			return array;
		}
	}

}
