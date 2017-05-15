package be.ac.umons.sgl.lazer.g06.game;
/**
 * A switch represent a move executed by user. It contains an old position and a new position.
 */
public class Switch {
	private Position oldPos;
	private Position newPos;
	/**
	 * Required for JSON class to create this object
	 */
	@SuppressWarnings(value = { "unused" })
	private Switch() {}
	/**
	 * Create switch.
	 * @param oldPos Old position.
	 * @param newPos New position.
	 */
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
	/**
	 * String representation of this switch.
	 */
	public String toString() {
		return "oldPos="+oldPos.toString()+", newPos="+newPos.toString();
	}

}
