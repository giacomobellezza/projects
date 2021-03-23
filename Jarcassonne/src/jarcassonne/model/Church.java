package jarcassonne.model;

import java.awt.Point;
import java.util.LinkedList;

public class Church {
	protected int scoreTmp;
	protected LinkedList<Follower> playerConqueror;
	protected LinkedList<Point> churchTilePlaced;
	protected LinkedList<Point> churchDirectionExpandable;

	public Church(){
	this.scoreTmp=0;
	this.playerConqueror= new LinkedList<Follower>();
	this.churchTilePlaced=new LinkedList<Point>();
	this.churchDirectionExpandable=new LinkedList<Point>();
	}
	
	
	public LinkedList<Point> getCDE(){
		return this.churchDirectionExpandable;
	}
	
	public LinkedList<Point> getCTP(){
		return this.churchTilePlaced;
	}
	
	public LinkedList<Integer> getPlayersConqueror(){
		LinkedList<Integer> pl = new LinkedList<Integer>();
		if(this.playerConqueror.getFirst().color==Model.P1)
			pl.add(Model.P1);
		if(this.playerConqueror.getFirst().color==Model.P2)
			pl.add(Model.P2);
		if(this.playerConqueror.getFirst().color==Model.P3)
			pl.add(Model.P3);
		if(this.playerConqueror.getFirst().color==Model.P4)
			pl.add(Model.P4);
		return pl;
	}
	
	public LinkedList<Follower> getPlayerConqueror(){
		return this.playerConqueror;
	}
	
	public int getScore(){
		return this.scoreTmp;
	}
	
	public void scoreTmpPlus(){
		this.scoreTmp++;
	}
	
	public void setScoreTmp(int score){
		this.scoreTmp += score;
	}

}
