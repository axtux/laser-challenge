package be.ac.umons.sgl.lazer.g06.game;

/**
 * 
 *To keep an history of positions in memory
 */

public class Switch {
	private Position oldPos;
	private Position newPos;
	/**
	 * Required for JSON class to create this object
	 */
	@SuppressWarnings(value = { "unused" })
	private Switch() {}
	
	public Switch(Position oldPos, Position newPos){
		this.oldPos=oldPos;
		this.newPos=newPos;
	}
	/**
	 * @return the old position
	 */
	public Position getOldPos(){
		return oldPos;
	}
	/**
	 * @return the new position
	 */
	public Position getNewPos(){
		return newPos;
	}
	
	public String toString() {
		return "oldPos="+oldPos.toString()+", newPos="+newPos.toString();
	}

}
