package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.Array;

import be.ac.umons.sgl.lazer.g06.game.orientations.Orientation;

public class Lasers extends Cell {
	Array<Orientation> inputs;
	Array<Orientation> outputs;
	
	public Lasers() {
		clear();
	}
	
	public void clear() {
		inputs = new Array<Orientation>();
		outputs = new Array<Orientation>();
	}
	
	public void addInput(Orientation input) {
		inputs.add(input);
	}
	public void addOutput(Orientation output) {
		outputs.add(output);
	}
	
	public Array<Orientation> getInputs() {
		return inputs;
	}
	public Array<Orientation> getOutputs() {
		return outputs;
	}
}
