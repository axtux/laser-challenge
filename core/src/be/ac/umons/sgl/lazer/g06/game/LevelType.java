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
	
	private Orientation orientationClass;
	private OrderedMap<String, BlockType> blocks;
	private String name;
	private String label;
	
	private TextureRegion input;
	private TextureRegion output;
	/**
	 * Create LevelType from directory name, located into level_types. At least 2 files are required :
	 * - blocks.xml that describes blocks allowed for this LevelType. A DTD file describing XML format for blocks.xml is located at core/assets/XML_format/blocks.dtd
	 * - label.txt that contains the name under which this type has to be displayed
	 * A third file orientations.txt can be created with content "advanced" to get 8-directions orientations instead of 4-directions ones.
	 * For each block, an image of the same name has to be located into subdirectory sprites with extension ".png". This image will be used to draw the block.
	 * Laser input and output are respectively represented by laserInput.png and laserOutput.png, inside sprites directory.
	 * @param name Directory name from which to load LevelType.
	 * @throws GdxRuntimeException if any of the above requirements are not met.
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
	/**
	 * @return An instance of Orientation class used for this LevelType.
	 */
	public Orientation getOrientation() {
		return orientationClass;
	}
	/**
	 * @return Label is intended to be displayed to the used.
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @return Raw name (directory name).
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return TextureRegion representing laser input from first Orientation.
	 */
	public TextureRegion getInput() {
		return input;
	}
	/**
	 * @return TextureRegion representing laser output from first Orientation.
	 */
	public TextureRegion getOutput() {
		return output;
	}
	/**
	 * @return All possible block types for this LevelType.
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
