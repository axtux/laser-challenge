package be.ac.umons.sgl.lazer.g06.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import be.ac.umons.sgl.lazer.g06.Files;
/**
 * Types of block available for class Block
 */
public class LevelType {
	static final String LEVEL_TYPES_PATH = "level_types";
	static final String defaultType = "standard";
	/**
	 * Keep all instances of LevelType to avoid too many disk access.
	 */
	private static OrderedMap<String, LevelType> levelTypes;
	
	private OrderedMap<String, BlockType> blocks;
	String name;
	String label;
	
	TextureRegion input;
	TextureRegion output;
	/**
	 * Parse XML blocks file of levelType and create associated BlockType objects.
	 * @param levelType LevelType name.
	 */
	public LevelType(String name) {
		this.name = name;
		
		label = Files.getContent(dirPath()+"name.txt");
		if(label == null) {
			throw new GdxRuntimeException("File "+dirPath()+"name.txt cannot be read.");
		}
		label = label.replaceAll("[\\r\\n]", "");
		
		String blocksXml = dirPath()+"blocks.xml";
		String xml = Files.getContent(blocksXml);
		if(xml == null) {
			throw new GdxRuntimeException("File "+blocksXml+" cannot be read.");
		}
		
		XmlReader reader = new XmlReader();
		Element blocksElement = reader.parse(xml);
		Array<Element> blockElements = blocksElement.getChildrenByName("block");
		blocks = new OrderedMap<String, BlockType>(blockElements.size);
		
		BlockType block;
		for(Element blockElement : blockElements) {
			block = new BlockType(name, blockElement);
			blocks.put(block.getName(), block);
		}
		
		String filename = dirPath()+"/sprites/laserInput.png";
		FileHandle fh = Gdx.files.local(filename);
		if(fh == null) {
			throw new GdxRuntimeException("404 File not found "+filename);
		}
		input = new TextureRegion(new Texture(fh));
		
		filename = dirPath()+"/sprites/laserOutput.png";
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
		return LEVEL_TYPES_PATH+"/"+name+"/";
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
	/**
	 * Refresh levelTypes from disk. LevelType directory is scanned and all valid levels are loaded into static array.
	 */
	private static void refresh() {
		Array<String> names = Files.listDirs(LEVEL_TYPES_PATH);
		if(names == null) {
			throw new GdxRuntimeException("Got null while listing directory "+LEVEL_TYPES_PATH);
		}
		Gdx.app.debug("LevelType.refresh", "Found types "+String.join("|", names));
		
		// set max size to avoid useless copies when increasing size
		levelTypes = new OrderedMap<String, LevelType>();
		LevelType lt;
		
		for(String name : names) {
			try {
				lt = new LevelType(name);
			} catch (GdxRuntimeException e) {
				Gdx.app.error("LevelType.refresh", "Unable to create LevelType "+name+" : "+e.getMessage());
				continue;
			}
			levelTypes.put(name, lt);
		}
		
	}
	/**
	 * Get level types from disk.
	 * @return Level types.
	 */
	public static Array<String> getLevelTypes() {
		return getLevelTypes(false);
	}
	/**
	 * Get level types from disk.
	 * @return Level types.
	 */
	public static Array<String> getLevelTypes(boolean refresh) {
		if(refresh || levelTypes == null) {
			refresh();
		}
		return levelTypes.orderedKeys();
	}
	/**
	 * Get level types from disk.
	 * @return Level types.
	 */
	public static LevelType getLevelType(String name) {
		return getLevelType(name, false);
	}
	/**
	 * Get level types from disk.
	 * @return Level types.
	 */
	public static LevelType getLevelType(String name, boolean refresh) {
		getLevelTypes(refresh);
		if(name == null || name.isEmpty()) {
			name = defaultType;
		}
		return levelTypes.get(name);
	}
	
	public class BlockType {
		private final TextureRegion tr;
		private final String name;
		private final String label;
		private final HashMap<Orientation, Array<Orientation>> inputs;
		
		public BlockType(String levelType, Element block) {
			this.name = block.get("name");
			this.label = block.get("label");
			
			String filename = LEVEL_TYPES_PATH+"/"+levelType+"/sprites/"+name+".png";
			FileHandle fh = Gdx.files.local(filename);
			if(fh == null) {
				throw new GdxRuntimeException("404 File not found "+filename);
			}
			tr = new TextureRegion(new Texture(fh));
			
			
			Element inputsElement = block.getChildByName("inputs");
			Array<Element> inputElements = inputsElement.getChildrenByName("input");
			inputs = new HashMap<Orientation, Array<Orientation>>(inputElements.size);
			// for each intputElement
			Array<Element> outputElements;
			Array<Orientation> outputs;
			// for each intputElement and outputElement
			String orientationStr;
			Orientation orientation;
			
			for(Element inputElement : inputElements) {
				outputElements = inputElement.getChildrenByName("output");
				outputs = new Array<Orientation>(outputElements.size);
				for(Element outputElement : outputElements) {
					orientationStr = outputElement.getAttribute("orientation");
					orientation = Orientation.fromString(orientationStr);
					if(orientation == null) {
						throw new GdxRuntimeException("No orientation "+orientationStr);
					}
					
					outputs.add(orientation);
				}
				
				orientationStr = inputElement.getAttribute("orientation", "");
				orientation = Orientation.fromString(orientationStr);
				inputs.put(orientation, outputs);
			}
		}
		
		public TextureRegion getTextureRegion() {
			return tr;
		}
		
		public String getName() {
			return name;
		}
		public String getLabel() {
			return label;
		}
		
		public Array<Orientation> input(Orientation orientation) {
			return inputs.get(orientation);
		}
	}
	
	public enum Orientation {
		UP(0),
		RIGHT(1),
		DOWN(2),
		LEFT(3);
		
		int indice;
		
		private Orientation(int indice) {
			this.indice = indice;
		}
		
		public Orientation next() {
			switch(this) {
			case UP:
				return Orientation.RIGHT;
			case RIGHT:
				return Orientation.DOWN;
			case DOWN:
				return Orientation.LEFT;
			case LEFT:
				return Orientation.UP;
			default:
				return null;
			}
		}
		
		public Orientation prev() {
			return this.next().next().next();
		}
		
		public int getAngle() {
			switch(this) {
			case UP:
				return 0;
			case RIGHT:
				return 90;
			case DOWN:
				return 180;
			case LEFT:
				return 270;
			default:
				return 0;
			}
		}
		/**
		 * Get orientation from string
		 * @param str String representation of orientation
		 * @return null if orientation does not exists instead of throwing an exception
		 */
		public static Orientation fromString(String str) {
			try {
				return Orientation.valueOf(str);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
	}

}
