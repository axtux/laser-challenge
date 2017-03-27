package be.ac.umons.sgl.lazer.g06.game;

public class Switch {
	private Position oldPos;
	private Position newPos;
	
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

}
