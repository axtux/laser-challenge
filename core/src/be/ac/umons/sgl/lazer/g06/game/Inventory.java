package be.ac.umons.sgl.lazer.g06.game;

import java.util.Observable;

import com.badlogic.gdx.utils.Array;

public class Inventory extends Observable {
	Array<Block> blocks;
	
	public Inventory() {
		blocks = new Array<Block>();
	}
	
	public void addBlock(Block block) {
		blocks.add(block);
	}
	
	public Block getBlock(int indice) {
		return blocks.get(indice);
	}
}
