package be.ac.umons.sgl.lazer.g06.game;

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
	
	public Position getOldPos(){
		return oldPos;
	}
	
	public Position getNewPos(){
		return newPos;
	}
	
	public String toString() {
		return "oldPos="+oldPos.toString()+", newPos="+newPos.toString();
	}

}
