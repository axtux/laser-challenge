package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.Array;

import be.ac.umons.sgl.lazer.g06.game.orientations.Orientation;
/**
 * Lasers to be inserted on the map. Extends Cell to use already existing LibGDX map classes.
 * Can contain multiple input/output orientations.
 */
public class Lasers extends Cell {
	private Array<Orientation> inputs;
	private Array<Orientation> outputs;
	/**
	 * Default constructor initialize inputs and outputs Arrays.
	 */
	public Lasers() {
		clear();
	}
	/**
	 * Clear inputs and outputs Arrays.
	 */
	public void clear() {
		inputs = new Array<Orientation>();
		outputs = new Array<Orientation>();
	}
	/**
	 * Add an input.
	 * @param input Input Orientation.
	 */
	public void addInput(Orientation input) {
		inputs.add(input);
	}
	/**
	 * Add an output.
	 * @param output Output Orientation.
	 */
	public void addOutput(Orientation output) {
		outputs.add(output);
	}
	/**
	 * Get all inputs.
	 * @return Input orientations.
	 */
	public Array<Orientation> getInputs() {
		return inputs;
	}
	/**
	 * Get all outputs.
	 * @return Output orientations.
	 */
	public Array<Orientation> getOutputs() {
		return outputs;
	}
}
