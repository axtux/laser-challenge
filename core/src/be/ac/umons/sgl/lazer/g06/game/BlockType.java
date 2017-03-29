package be.ac.umons.sgl.lazer.g06.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader.Element;

public class BlockType {
	private final TextureRegion tr;
	private final String name;
	private final String label;
	private final HashMap<Orientation, Array<Orientation>> inputs;
	
	public BlockType(String spritesPath, Element block) {
		this.name = block.get("name");
		this.label = block.get("label");
		
		String filename = spritesPath+"/"+name+".png";
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
		Array<Orientation> orientations = inputs.get(orientation);
		if(orientations == null) {
			orientations = new Array<Orientation>(0);
		}
		return orientations;
	}
}