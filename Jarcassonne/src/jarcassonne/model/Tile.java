package jarcassonne.model;


import java.awt.geom.AffineTransform;
import java.awt.Graphics;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.URISyntaxException;
import java.util.LinkedList;

public class Tile  {
	//creo array di tutte le diverse tessere
	protected final static Tile[] TILE_ARRAY = new Tile[24];	
	private final static Tile[] TILE_ARRAY_PREVIEW = new Tile[24];
	public final static int FIELD = 0;
	public final static int ROAD = 1;
	public final static int TOWN = 2;
	
	//inizializzazione pezzi
	  static { 
		  
		  try {
		  TILE_ARRAY[0] = new TileA();		
		  TILE_ARRAY[1] = new TileB();
		  TILE_ARRAY[2] = new TileC();
		  TILE_ARRAY[3] = new TileD();
		  TILE_ARRAY[4] = new TileE();
		  TILE_ARRAY[5] = new TileF();
		  TILE_ARRAY[6] = new TileG();
		  TILE_ARRAY[7] = new TileH();
		  TILE_ARRAY[8] = new TileI();
		  TILE_ARRAY[9] = new TileJ();
		  TILE_ARRAY[10] = new TileK();
		  TILE_ARRAY[11] = new TileL();
		  TILE_ARRAY[12] = new TileM();
		  TILE_ARRAY[13] = new TileN();
		  TILE_ARRAY[14] = new TileO();
		  TILE_ARRAY[15] = new TileP();
		  TILE_ARRAY[16] = new TileQ();
		  TILE_ARRAY[17] = new TileR();
		  TILE_ARRAY[18] = new TileS();
		  TILE_ARRAY[19] = new TileT();
		  TILE_ARRAY[20] = new TileU();
		  TILE_ARRAY[21] = new TileV();
		  TILE_ARRAY[22] = new TileW();
		  TILE_ARRAY[23] = new TileX();
		  
		  TILE_ARRAY_PREVIEW[0] = new TileA();		
		  TILE_ARRAY_PREVIEW[1] = new TileB();
		  TILE_ARRAY_PREVIEW[2] = new TileC();
		  TILE_ARRAY_PREVIEW[3] = new TileD();
		  TILE_ARRAY_PREVIEW[4] = new TileE();
		  TILE_ARRAY_PREVIEW[5] = new TileF();
		  TILE_ARRAY_PREVIEW[6] = new TileG();
		  TILE_ARRAY_PREVIEW[7] = new TileH();
		  TILE_ARRAY_PREVIEW[8] = new TileI();
		  TILE_ARRAY_PREVIEW[9] = new TileJ();
		  TILE_ARRAY_PREVIEW[10] = new TileK();
		  TILE_ARRAY_PREVIEW[11] = new TileL();
		  TILE_ARRAY_PREVIEW[12] = new TileM();
		  TILE_ARRAY_PREVIEW[13] = new TileN();
		  TILE_ARRAY_PREVIEW[14] = new TileO();
		  TILE_ARRAY_PREVIEW[15] = new TileP();
		  TILE_ARRAY_PREVIEW[16] = new TileQ();
		  TILE_ARRAY_PREVIEW[17] = new TileR();
		  TILE_ARRAY_PREVIEW[18] = new TileS();
		  TILE_ARRAY_PREVIEW[19] = new TileT();
		  TILE_ARRAY_PREVIEW[20] = new TileU();
		  TILE_ARRAY_PREVIEW[21] = new TileV();
		  TILE_ARRAY_PREVIEW[22] = new TileW();
		  TILE_ARRAY_PREVIEW[23] = new TileX();
	
		  } catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
	  }

	//attributi pezzo
	protected String tileName;
	protected String fileName;
	protected int[] side;
	protected boolean townIsContinue;
	protected boolean roadIsContinue;
	protected boolean isShield;   
	protected boolean isChurch;
	protected int numRotations;
	protected int numTownInCurrentTile;	
	protected BufferedImage img; 
	
	//costruttore tessera
	public Tile() {
		this.numRotations=0;
		this.numTownInCurrentTile=0;
		
	}
	public void paint(Graphics g){
		g.drawImage(img,0,0,null);
	}

	//metodo fondamentale per l'acquisizione che usa il metodo drawImage
	public void paintTile(Graphics g,int x,int y){
		g.drawImage(img,y,x,null);
	}
	
	public void rotate(){
		BufferedImage bi= this.img;	
		if(this.img != null){
		int w=50;
		int h=50;
		AffineTransform at = new AffineTransform();
		at.rotate(Math.toRadians(90),w,h);
		AffineTransformOp op=new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
		bi=op.filter(bi,null);
		
		this.img=bi;
		
		//rotazione lati
		int tmp = this.side[0];
		this.side[0]=this.side[3];
		this.side[3]=this.side[2];
		this.side[2]=this.side[1];
		this.side[1]=tmp;
		
		numRotations++;
		numRotations=numRotations%4;
		}			
	}
	
	//ruota n volte
	public void rotate(int n){
		for(int i=0;i<n;i++)
			rotate();
	}
	
	// il pezzo torna in posizione iniziale
	public void resetRotation(){
		rotate(4-numRotations);
		//numRotations--;
	}
	
	public String getName(){
		return this.tileName;
	}
	
	public String getFileName(){
		return this.fileName;
	}
	
	//direction array
	public int[] getSide(){
		return this.side;				
	}
	
	//north
	public int getNSide(){
		return this.side[0];
	}
	//east
	public int getESide(){
		return this.side[1];
	}
	//south
	public int getSSide(){
		return this.side[2];
	}
	//west
	public int getWSide(){
		return this.side[3];
	}
  
	public boolean getChurch(){
		return this.isChurch;
	}
	public boolean getShield(){
		return this.isShield;
	}

	public boolean getTownIsContinue(){
		return this.townIsContinue;
	}

	public boolean getRoadIsContinue(){
		return this.roadIsContinue;
	}

	public BufferedImage getTileImage(){
		return this.img;				
	}
	
	public int getNumRotations() {
		return this.numRotations;
	}
	
	public String getInfo(){
		return ""+this.tileName;
	}
	
	public boolean isTown(){
		boolean isTown=false;
			if( this.getNSide()==TOWN ||
				this.getESide()==TOWN ||
				this.getSSide()==TOWN ||
				this.getWSide()==TOWN )
				isTown=true;									
		return isTown;
	}
	
	public boolean isRoad(){
		boolean isTown=false;
			if( this.getNSide()==ROAD ||
				this.getESide()==ROAD ||
				this.getSSide()==ROAD ||
				this.getWSide()==ROAD )
				isTown=true;									
		return isTown;
	}
	
	public int getNumTown(){
		return this.numTownInCurrentTile;
	}
	
		//-----------------------------------------------------------------
		// STATIC METHODS
		//-----------------------------------------------------------------
	
	public static LinkedList<Tile> initTileList(LinkedList<Tile> tileList){
		//LinkedList<Tile> tileList = new LinkedList<Tile>();
				tileList.add(TILE_ARRAY[2]);
				tileList.add(TILE_ARRAY[6]);
				tileList.add(TILE_ARRAY[16]);
				tileList.add(TILE_ARRAY[19]);
				tileList.add(TILE_ARRAY[23]);
			for(int i=0;i<2;i++){
				tileList.add(TILE_ARRAY[0]);
				tileList.add(TILE_ARRAY[5]);
				tileList.add(TILE_ARRAY[8]);
				tileList.add(TILE_ARRAY[12]);
				tileList.add(TILE_ARRAY[14]);
				tileList.add(TILE_ARRAY[18]);
				}
			for(int i=0;i<3;i++){
				tileList.add(TILE_ARRAY[3]);  //tolto il pezzo iniziale
				tileList.add(TILE_ARRAY[7]);
				tileList.add(TILE_ARRAY[9]);
				tileList.add(TILE_ARRAY[10]);
				tileList.add(TILE_ARRAY[11]);
				tileList.add(TILE_ARRAY[13]);
				tileList.add(TILE_ARRAY[15]);
				tileList.add(TILE_ARRAY[17]);
				}
			for(int i=0;i<4;i++){
				tileList.add(TILE_ARRAY[1]);
				tileList.add(TILE_ARRAY[22]);
				}
			for(int i=0;i<5;i++){
				tileList.add(TILE_ARRAY[4]);
				}
			for(int i=0;i<8;i++){
				tileList.add(TILE_ARRAY[20]);
				}
			for(int i=0;i<9;i++){
				tileList.add(TILE_ARRAY[21]);
				}
			return tileList;
		
	}
	
	public static void mixList(LinkedList<Tile> list){
		Tile tmp=null;
		int index=0;
		for(int i=0;i<80;i++){
			index=(int)(Math.random()*70);
			tmp= list.get(index);
			list.remove(index);
			list.add(tmp);
		}
		for(int i=0;i<30;i++){
			index=(int)(Math.random()*10);
			tmp= list.get(index);
			list.remove(index);
			list.add(tmp);
		}
		list.addFirst(TILE_ARRAY[3]);
	}
	
	public static Tile createPreviewTile(String nameTile){
		Tile t=null;
		boolean find=false;
		while(find==false)
			for(int i=0;i<24;i++){
				if (nameTile == TILE_ARRAY_PREVIEW[i].getName()){
					find=true;
					t=TILE_ARRAY_PREVIEW[i];
				}
			}
		return t;
	}
}

