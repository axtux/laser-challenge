package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.XmlReader.Element;

import be.ac.umons.sgl.lazer.g06.Files;
import be.ac.umons.sgl.lazer.g06.game.orientations.AdvancedOrientation;
import be.ac.umons.sgl.lazer.g06.game.orientations.Orientation;
import be.ac.umons.sgl.lazer.g06.game.orientations.StandardOrientation;
/**
 * Types of block available for class Block
 */
public class LevelType {
	static final String defaultType = "standard";
	
	Orientation orientationClass;
	private OrderedMap<String, BlockType> blocks;
	String name;
	String label;
	
	TextureRegion input;
	TextureRegion output;
	/**
	 * Parse XML blocks file of levelType and create associated BlockType objects.
	 * @param name LevelType name.
	 */
	public LevelType(String name) {
		this.name = name;
		
		label = Files.getContent(dirPath()+"/label.txt");
		if(label == null) {
			throw new GdxRuntimeException("File "+dirPath()+"/label.txt cannot be read.");
		}
		label = label.replaceAll("[\\r\\n]", "");
		
		String orientation = Files.getContent(dirPath()+"/orientations.txt");
		if(orientation != null && orientation.trim().equals("advanced")) {
			orientationClass = AdvancedOrientation.staticFirst();
		} else {
			orientationClass = StandardOrientation.staticFirst();
		}
		
		Element blocksElement = Files.parseXML(dirPath()+"/blocks.xml");
		if(blocksElement == null) {
			throw new GdxRuntimeException("Error parsing "+dirPath()+"/blocks.xml");
		}
		
		Array<Element> blockElements = blocksElement.getChildrenByName("block");
		blocks = new OrderedMap<String, BlockType>(blockElements.size);
		
		BlockType block;
		for(Element blockElement : blockElements) {
			block = new BlockType(this, blockElement);
			blocks.put(block.getName(), block);
		}
		
		String filename = spritesPath()+"/laserInput.png";
		FileHandle fh = Gdx.files.local(filename);
		if(fh == null) {
			throw new GdxRuntimeException("404 File not found "+filename);
		}
		input = new TextureRegion(new Texture(fh));
		
		filename = spritesPath()+"/laserOutput.png";
		fh = Gdx.files.local(filename);
		if(fh == null) {
			throw new GdxRuntimeException("404 File not found "+filename);
		}
		output = new TextureRegion(new Texture(fh));
	}
	/**
	 * @return Directory path relative to application root.
	 */
	private String dirPath() {
		return LevelTypes.LEVEL_TYPES_PATH+"/"+name+"/";
	}
	/**
	 * @return Directory path relative to application root.
	 */
	public String spritesPath() {
		return dirPath()+"/sprites";
	}
	
	public Orientation getOrientation() {
		return orientationClass;
	}
	/**
	 * @return Name.
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @return Raw name.
	 */
	public String getName() {
		return name;
	}
	
	public TextureRegion getInput() {
		return input;
	}
	public TextureRegion getOutput() {
		return output;
	}
	/**
	 * Get block types.
	 * @return Valid types of block for this LevelType.
	 */
	public Array<String> getBlockTypes() {
		return blocks.orderedKeys();
	}
	/**
	 * Get {@link BlockType} from BlockType name.
	 * @param blockType Name of BlockType.
	 * @return BlockType corresponding to given name.
	 */
	public BlockType getBlockType(String blockType) {
		BlockType block = blocks.get(blockType);
		if(block == null) {
			throw new GdxRuntimeException("Invalid blockType "+blockType+" for levelType "+name);
		}
		return block;
	}
}
