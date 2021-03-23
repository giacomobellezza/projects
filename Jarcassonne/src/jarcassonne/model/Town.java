package jarcassonne.model;


import java.util.LinkedList;
import java.awt.Point;

public class Town{
	public int scoreTmp;
	public int numShield;
	public LinkedList<Follower> playerConqueror;
	public LinkedList<Point> townTilePlaced;
	public LinkedList<Point> townDirectionExpandable;

	public Town(){
	this.scoreTmp=0;
	this.numShield=0;
	this.playerConqueror= new LinkedList<Follower>();
	this.townTilePlaced=new LinkedList<Point>();
	this.townDirectionExpandable=new LinkedList<Point>();
	}
		
	public LinkedList<Point> getTDE(){
		return this.townDirectionExpandable;
	}
	
	public LinkedList<Point> getTTP(){
		return this.townTilePlaced;
	}
	
	public LinkedList<Follower> getPlayerConqueror(){
		return this.playerConqueror;
	}
	
	public int getScoreTmp(){
		return this.scoreTmp;
	}
	
	public int getNumShield(){
		return this.numShield;
	}
	
	public int totalScoreCompleteTown(){
		return 2*(this.scoreTmp+this.numShield);
	}
	
	public int totalScoreIncompleteTown(){
		return (this.scoreTmp+this.numShield);
	}
	
	public LinkedList<Integer> maiorPlayersConqueror(){
		LinkedList<Integer> pl = new LinkedList<Integer>();
		int blue=0;
		int red=0;				
		int green=0;
		int yellow=0;
		
		for (int i =0; i<this.playerConqueror.size();i++){
			switch (this.playerConqueror.get(i).getFollowerColor()) {
			case 1:
				blue++;
				break;
			case 2:
				red++;
				break;
			case 3:
				green++;
				break;
			case 4:
				yellow++;
				break;
			default:
				break;
			}
		}
		if(blue==Math.max(Math.max(blue, red), Math.max(green, yellow)))
			pl.add(1);
		if(red==Math.max(Math.max(blue, red), Math.max(green, yellow)))
			pl.add(2);
		if(green==Math.max(Math.max(blue, red), Math.max(green, yellow)))
			pl.add(3);
		if(yellow==Math.max(Math.max(blue, red), Math.max(green, yellow)))
			pl.add(4);
		
		return pl;
	}
	
	public void scoreTmpPlus(){
		this.scoreTmp++;
	}
	
	public void numShieldPlus(){
		this.numShield++;
	}
	
	public void setScoreTmp(int score){
		this.scoreTmp += score;
	}
	
	public void setNumShield(int shield){
		this.numShield += shield;
	}

}
