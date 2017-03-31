package be.ac.umons.sgl.lazer.g06.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader.Element;

import be.ac.umons.sgl.lazer.g06.game.orientations.Orientation;
/**
 * BlockType defines displayed sprite, and input management of the associated block
 * The design chosen is to have BlockType read from XML files and not defined programmatically.
 * Informations about XML format can be found into {@link LevelType}.
 */
public class BlockType {
	private final TextureRegion tr;
	private final String name;
	private final String label;
	private final HashMap<Orientation, Array<Orientation>> inputs;
	private final Array<Orientation> requiredInputs;
	private final LevelType levelType;
	/**
	 * Create BlockType.
	 * @param levelType LevelType associated to this BlockType. Used for sprite path and for orientation instance.
	 * @param block XML Element from which this BlockType has to be created. Informations about XML format can be found into {@link LevelType}.
	 * @throws GdxRuntimeException if sprite cannot be accessed,
	 * if block Element format is not valid or if an orientation used does not exists.
	 */
	public BlockType(LevelType levelType, Element block) {
		this.levelType = levelType;
		this.name = block.get("name");
		this.label = block.get("label");
		
		String filename = levelType.spritesPath()+"/"+name+".png";
		FileHandle fh = Gdx.files.local(filename);
		if(fh == null) {
			throw new GdxRuntimeException("404 File not found "+filename);
		}
		tr = new TextureRegion(new Texture(fh));
		
		inputs = parseInputs(block.getChildByName("inputs"));
		requiredInputs = parseRequiredInputs(block.getChildByName("required-inputs"));
	}
	/**
	 * Parsing method.
	 * @param inputsElement XML Element
	 * @return Parsed inputs in required format to be added as attribute.
	 */
	private HashMap<Orientation, Array<Orientation>> parseInputs(Element inputsElement) {
		if(inputsElement == null) {
			return new HashMap<Orientation, Array<Orientation>>(0);
		}
		
		Array<Element> inputElements = inputsElement.getChildrenByName("input");
		HashMap<Orientation, Array<Orientation>> inputs = new HashMap<Orientation, Array<Orientation>>(inputElements.size);
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
				orientation = levelType.getOrientation().fromString(orientationStr);
				if(orientation == null) {
					throw new GdxRuntimeException("No orientation "+orientationStr);
				}
				
				outputs.add(orientation);
			}
			
			orientationStr = inputElement.getAttribute("orientation", "");
			orientation = levelType.getOrientation().fromString(orientationStr);
			inputs.put(orientation, outputs);
		}
		
		return inputs;
	}
	/**
	 * Parsing method.
	 * @param inputsElement XML Element
	 * @return Parsed inputs in required format to be added as attribute.
	 */
	private Array<Orientation> parseRequiredInputs(Element inputsElement) {
		if(inputsElement == null) {
			return new Array<Orientation>(0);
		}
		
		Array<Element> inputElements = inputsElement.getChildrenByName("input");
		Array<Orientation> inputs = new Array<Orientation>(inputElements.size);
		// for each intputElement
		String orientationStr;
		Orientation orientation;
		
		for(Element inputElement : inputElements) {
			orientationStr = inputElement.getAttribute("orientation");
			orientation = levelType.getOrientation().fromString(orientationStr);
			if(orientation == null) {
				throw new GdxRuntimeException("No orientation "+orientationStr);
			}
			
			inputs.add(orientation);
		}
		
		return inputs;
	}
	/**
	 * @return TextureRegion representing the sprite of block
	 */
	public TextureRegion getTextureRegion() {
		return tr;
	}
	
	/**
	 * @return the name of block
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the label of block
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * Simulate laser input into this type of block.
	 * @param orientation Laser input orientation.
	 * @return Laser output orientations going out of this block because of input.
	 */
	public Array<Orientation> input(Orientation orientation) {
		Array<Orientation> orientations = inputs.get(orientation);
		if(orientations == null) {
			orientations = new Array<Orientation>(0);
		}
		return orientations;
	}
	/**
	 * Required inputs must be satisfied for a level to be won.
	 * @return Required input orientations.
	 */
	public Array<Orientation> getRequiredInputs() {
		return requiredInputs;
	}
}