package jarcassonne.model;

import jarcassonne.viewController.NewGameWindow;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Follower {

	public final static Follower[] FOLLOWER_ARRAY = new Follower[4*Model.DEFAULT_NUM_FOLLOWERS];
	public final static int NORTH = 1;	
	public final static int EAST = 2;	
	public final static int SOUTH = 3;
	public final static int WEST = 4;	
	public final static int CENTRE = 5;
	
	static {
		  for(int i = 0; i <4*Model.DEFAULT_NUM_FOLLOWERS;i++){
		  FOLLOWER_ARRAY[i] = new Follower();

			  try {
				
				  if(i<7){
					  FOLLOWER_ARRAY[i].loadImage(1);
					  FOLLOWER_ARRAY[i].initColor(1);
				  }
				  if(i>=7 && i<14){
					  FOLLOWER_ARRAY[i].loadImage(2);
					  FOLLOWER_ARRAY[i].initColor(2);
				  }
				  if(i>=14 && i<21){
					  FOLLOWER_ARRAY[i].loadImage(3);
					  FOLLOWER_ARRAY[i].initColor(3);
				  }
				  if(i>=21 && i<28){
					  FOLLOWER_ARRAY[i].loadImage(4);
					  FOLLOWER_ARRAY[i].initColor(4);
				  }
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
	}
	
	protected final int BLUE = 1;	
	protected final int RED = 2;	
	protected final int GREEN = 3;	
	protected final int YELLOW = 4;
	
	protected final int NULL = 0;	

	protected BufferedImage img; 
	protected int followerOnSide;
	protected int color;
	protected int numRotation;
	protected Point followerCoordinates;
	
	//costruttore tessera
	public Follower() {
		this.img=null;
		this.followerOnSide = NULL;
		this.numRotation=0;
		this.followerCoordinates= new Point();
		
	}
	
	public void loadImage(int i) throws URISyntaxException{
		try{
			//metodo che trasforma un file in una bufferedImage
                                img = ImageIO.read(NewGameWindow.class.getResourceAsStream("/jarcassonne/img/Seguaci/"+i+".png"));
			//img=ImageIO.read(new File("/home/giacomo/jarcassonne/Jarcassonne/src/jarcassonne/Tessere_gioco/"+fileName));
		} catch(IOException e){
					System.out.println("error:file FOLLOWER path");		
		}
	}
	
	public void paint(Graphics g){
		g.drawImage(img,0,0,null);
	}

	//metodo fondamentale per l'acquisizione che usa il metodo drawImage
	public void paintFollower(Graphics g,int x,int y){
		g.drawImage(img,y,x,null);
	}
	
	public void rotate(){
		BufferedImage bi= this.img;	
		AffineTransform at = new AffineTransform();
		at.rotate(Math.toRadians(90),bi.getWidth()/2,bi.getHeight()/2);
		AffineTransformOp op=new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
		bi=op.filter(bi,null);
		this.img=bi;	
		this.numRotation=(this.numRotation+1)%4;
	}
	
	public void rotate(int n){
		for(int i =0;i<n;i++)
			rotate();
	}
	
	public void resetRotation(int numRotation){
		rotate(4-(numRotation));
	}
	
	public int getNumRotation(){
		return this.numRotation;
	}
	
	public int getFollowerColor(){
		return this.color;
	}
	public void initColor(int color){
		this.color=color;		
	}
	
	public int getFollowerOnSide(){
		return this.followerOnSide;
	}
	public void setFollowerOnSide(int sidePosition){
		this.followerOnSide=sidePosition;		
	}
	
	public Point getCoordinates(){
		return this.followerCoordinates;
	}
	
	public void setCoordinates(int x, int y){
		this.followerCoordinates.x=x;
		this.followerCoordinates.y=y;
	}
	
	//-----------------------------------------------------------------
	// STATIC METHODS
	//-----------------------------------------------------------------
	public static LinkedList<Follower> initFollowerList(){
		LinkedList<Follower> ll = new LinkedList<Follower>();
		for (int i =0; i<FOLLOWER_ARRAY.length;i++)
			ll.add(FOLLOWER_ARRAY[i]);
		return ll;
	}
	
	public static Follower[] getFollowerArray(){
		return FOLLOWER_ARRAY;
	}
	
	public static Follower getFollowerArray(int i){
		Follower f = null;
		if (i<FOLLOWER_ARRAY.length)
			f = FOLLOWER_ARRAY[i];
		 return f;
	}
}
