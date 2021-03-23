package jarcassonne.model;

import java.awt.Point;
import java.util.LinkedList;

public class Road {
	protected int scoreTmp;
	protected LinkedList<Follower> playerConqueror;
	protected LinkedList<Point> roadTilePlaced;
	protected LinkedList<Point> roadDirectionExpandable;

	public Road(){
	this.scoreTmp=0;
	this.playerConqueror= new LinkedList<Follower>();
	this.roadTilePlaced=new LinkedList<Point>();
	this.roadDirectionExpandable=new LinkedList<Point>();
	}
	
	public LinkedList<Point> getRDE(){
		return this.roadDirectionExpandable;
	}
	
	public LinkedList<Point> getRTP(){
		return this.roadTilePlaced;
	}
	
	public LinkedList<Follower> getPlayerConqueror(){
		return this.playerConqueror;
	}
	
	public int getScore(){
		return this.scoreTmp;
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
	
	public void setScoreTmp(int score){
		this.scoreTmp += score;
	}
}
