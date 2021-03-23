package jarcassonne.model;

import jarcassonne.viewController.ViewControllerForModel;

import java.awt.Point;
import java.util.LinkedList;



public class Model implements IModel{
	//-----------------------------------------------------------------
	// STATIC COSTANT
	//-----------------------------------------------------------------
	
	public final static int DEFAULT_NUM_ROWS_COLUMNS = 100;
	public final static int DEFAULT_NUM_FOLLOWERS = 7;
	public final static int DEFAULT_NUM_TILE = 71;
	
	public final static int P1=1;
	public final static int P2=2;
	public final static int P3=3;
	public final static int P4=4;
	//-----------------------------------------------------------------
	// STATIC FIELDS
	//-----------------------------------------------------------------
	private static Model instance = null;
	//-----------------------------------------------------------------
	// INSTANCE ATTRIBUTES
	//-----------------------------------------------------------------
	private int numPlayer;
	private int p1Score;
	private int p2Score;
	private int p3Score;
	private int p4Score;
	private String p1Name;
	private String p2Name;
	private String p3Name;
	private String p4Name;
	private int currentPlayer;
	
	private int tilePlaced;
	private Tile[][] arrayTilePlaced; 
	private int[][] rotations;
	private int xUltimoPezzo;
	private int yUltimoPezzo;
	private int firstTileX;
	private int firstTileY;
	
	private boolean[][] arrayPossibleCell;
	private Follower[][] arrayFollowerPlaced;

	private LinkedList<Tile> tileList;
	private LinkedList<Town> townList;
	private LinkedList<Road> roadList;
	private LinkedList<Church> churchList;
	
	private LinkedList<Point> tilePlacedCoordinates;
	private LinkedList<Point> expantionMapCoordinates;
	
	private boolean annul;
	private boolean firstStep;
	
	private LinkedList<Follower> blueList;
	private LinkedList<Follower> redList;
	private LinkedList<Follower> greenList;
	private LinkedList<Follower> yellowList;
	
	private Model(){
		this.initGame();
		this.annul=false;
		initLists();	
	}

	//-----------------------------------------------------------------
	// STATIC METHODS
	//-----------------------------------------------------------------
	
	public static IModel getInstance(){
		if (instance == null)
			instance = new Model();
		return instance;
	}
	
	//-----------------------------------------------------------------
	// PUBLIC INSTANCE METHODS
	//-----------------------------------------------------------------
	
	public void initGame(){
		//-----lista tessere--
		this.tileList= new LinkedList<Tile>();
		Tile.initTileList(this.tileList);
		Tile.mixList(this.tileList);
		this.townList= new LinkedList<Town>();
		this.roadList= new LinkedList<Road>();
		this.churchList= new LinkedList<Church>();
		this.numPlayer=2;
		this.arrayTilePlaced = new Tile[DEFAULT_NUM_ROWS_COLUMNS][DEFAULT_NUM_ROWS_COLUMNS];
		this.rotations = new int[DEFAULT_NUM_ROWS_COLUMNS][DEFAULT_NUM_ROWS_COLUMNS];
		this.arrayPossibleCell = new boolean[DEFAULT_NUM_ROWS_COLUMNS][DEFAULT_NUM_ROWS_COLUMNS];
		this.arrayFollowerPlaced = new Follower[DEFAULT_NUM_ROWS_COLUMNS][DEFAULT_NUM_ROWS_COLUMNS];
		this.tilePlaced=1;
		this.firstTileX=54;
		this.firstTileY=56;
		this.xUltimoPezzo=this.firstTileX;
		this.yUltimoPezzo=this.firstTileY;
		this.p1Score=0;
		this.p2Score=0;
		this.p3Score=0;
		this.p4Score=0;	
		this.currentPlayer=P1;		
		this.blueList=new LinkedList<Follower>();
		this.redList=new LinkedList<Follower>();
		this.greenList=new LinkedList<Follower>();
		this.yellowList=new LinkedList<Follower>();	
		this.tilePlacedCoordinates = new LinkedList<Point>();
		this.expantionMapCoordinates = new LinkedList<Point>();
		//inizializzazione prima tessera
		initFristTile();		
	}
	
	public void initLists(){
			for(int i=0;i<7;i++)
				this.blueList.add(Follower.getFollowerArray(i));
			for(int i=7;i<14;i++)
				this.redList.add(Follower.getFollowerArray(i));	
			for(int i=14;i<21;i++)
				this.greenList.add(Follower.getFollowerArray(i));
			for(int i=21;i<28;i++)
				this.yellowList.add(Follower.getFollowerArray(i));
	}
	
	public void initFristTile(){
		Point p=new Point(this.firstTileX,this.firstTileY);
		Point tde=new Point(this.firstTileX-1,this.firstTileY);
		Point rde1=new Point(this.firstTileX,this.firstTileY-1);
		Point rde2=new Point(this.firstTileX,this.firstTileY+1);
		Point fieldExpantion=new Point(this.firstTileX+1,this.firstTileY);
		arrayTilePlaced[this.firstTileX][this.firstTileY]=tileList.getFirst();
		Town nt = new Town();
		nt.scoreTmp=1;
		nt.getTDE().add(tde);
		nt.getTTP().add(p);
		this.townList.add(nt);
		Road nr = new Road();
		nr.getRTP().add(p);
		nr.getRDE().add(rde1);
		nr.getRDE().add(rde2);
		nr.scoreTmp=1;
		this.roadList.add(nr);
		this.tilePlacedCoordinates.add(p);
		this.expantionMapCoordinates.add(tde);
		this.expantionMapCoordinates.add(rde1);
		this.expantionMapCoordinates.add(rde2);
		this.expantionMapCoordinates.add(fieldExpantion);
	}
	
	//---------------------------------------------------------------------------------------------
	public int getNumPlayer(){
		return this.numPlayer;
	}
	
	//----------------------------------
	//metodi che lavorano sulla lista
	//----------------------------------	
	
	public Tile getCurrentTile(){
		if(this.tileList.size()>tilePlaced)
		return tileList.get(tilePlaced);
		else
		return null;
	}
	
	@Override
	public void setCurrentTile(Tile t) {
		// TODO Auto-generated method stub
		
	}
	
	public Tile getPreviousTile(){
		if(this.tileList.size()>tilePlaced)
		return tileList.get(tilePlaced-1);
		else
			return tileList.get(tileList.size()-1);
	}
	
	public int getSizeOfTileList(){
		return this.tileList.size();
	}

	public void nextTurn(){
		possibleCell(tileList.get(tilePlaced));	
	}
	
	@Override
	public LinkedList<Tile> getTileList() {
		return this.tileList;
	}

	//----------------------------------
	//metodi annulla
	//----------------------------------	
	
	public void annul(){
		this.annul=true;
	}
	
	public boolean getAnnul(){
		return this.annul;
	}
	
	public void resetAnnul(){
		this.annul=false;
	}
	//----------------------------------
	//fasi turno
	//----------------------------------		
	
	public void firstStepEnd(){
		this.firstStep=false;
	}
	
	public void firstStepStart(){
		this.firstStep=true;
	}
	
	public boolean getFirstStep(){
		return this.firstStep;
	}

	
	//----------------------------------
	//metodi sul tilePlaced
	//----------------------------------	

	public int getTilePlaced(){
		return this.tilePlaced;
	}
	
	public void TilePlacedPlus(){
		this.tilePlaced++;
	}
	
	public void TilePlacedMinus(){
		this.tilePlaced--;
	}
	
	public int getXUltimoPezzo(){
		return this.xUltimoPezzo;
	}
	
	public int getYUltimoPezzo(){
		return this.yUltimoPezzo;
	}
	
	public void setXUltimoPezzo(int x){
		this.xUltimoPezzo=x;
	}
	
	public void setYUltimoPezzo(int y){
		this.yUltimoPezzo=y;
	}
	
	public boolean getArrayPossibleCell(int indexI,int indexJ){
		return this.arrayPossibleCell[indexI][indexJ];
	}
	
	public void setFalseInArrayPossibleCell(int x, int y){
		this.arrayPossibleCell[x][y]=false;
	}

	public boolean tileIsCompatible(int X, int Y, Tile t){
		boolean compatible=false;
		boolean N=false;
		boolean E=false;
		boolean S=false;
		boolean W=false;
		int i = 0;
		while (compatible==false && i<4){
			if(
				( arrayTilePlaced[Y-1][X]==null) &&
				( arrayTilePlaced[Y][X+1]==null) &&
				( arrayTilePlaced[Y+1][X]==null) &&
				( arrayTilePlaced[Y][X-1]==null)){
				if(tilePlaced==0){
					compatible=true;
				}else{
					i=4;
				}	
				}else{
						if (arrayTilePlaced[Y-1][X]!=null){
							arrayTilePlaced[Y-1][X].resetRotation();
							arrayTilePlaced[Y-1][X].rotate(rotations[Y-1][X]);
						}
						if (arrayTilePlaced[Y-1][X]==null || t.getNSide()==arrayTilePlaced[Y-1][X].getSSide())
							N=true;				
						if (arrayTilePlaced[Y][X+1]!=null){
							 arrayTilePlaced[Y][X+1].resetRotation();
							 arrayTilePlaced[Y][X+1].rotate(rotations[Y][X+1]);
						}					
						if (arrayTilePlaced[Y][X+1]==null || t.getESide()==arrayTilePlaced[Y][X+1].getWSide())
								E=true;						
						if ( arrayTilePlaced[Y+1][X]!=null){
								arrayTilePlaced[Y+1][X].resetRotation();
								arrayTilePlaced[Y+1][X].rotate(rotations[Y+1][X]);
						}
						if ( arrayTilePlaced[Y+1][X]==null || t.getSSide()==arrayTilePlaced[Y+1][X].getNSide())
								S=true;						
						if ( arrayTilePlaced[Y][X-1]!=null){
								 arrayTilePlaced[Y][X-1].resetRotation();
								 arrayTilePlaced[Y][X-1].rotate(rotations[Y][X-1]);						 	
						}					
						if ( arrayTilePlaced[Y][X-1]==null || t.getWSide()==arrayTilePlaced[Y][X-1].getESide())
								W=true;							
						if (N==true && E==true && S==true && W==true){
								compatible=true;
						}else{
								N=false;
								E=false;
								S=false;
								W=false;
								t.rotate();
								i++;
						}
					}
		}
		return compatible;
	}
	
	public void possibleCell(Tile t) {
		int numGray=0;
		int x= 0;
		int y= 0;
		for(int j=0; j<this.expantionMapCoordinates.size();j++){
			x=this.expantionMapCoordinates.get(j).x;
			y=this.expantionMapCoordinates.get(j).y;
			arrayPossibleCell[x][y]=tileIsCompatible(y,x,t);
			if(tileIsCompatible(y,x,t)==true)
				numGray++;
		}
		if(numGray==0){
			this.tileList.addLast(tileList.get(tilePlaced));
			this.tileList.remove(tilePlaced);
			ViewControllerForModel.getInstance().setImpossibleTilePlaced(true);
			}else{
				numGray=0;
			}
		}
	
	@Override
	public void checkRotate(Tile t, int X, int Y){
		tileIsCompatible(X,Y,t);		
	}
	
	public Tile getTileFromArray(int indexI,int indexJ){
		return this.arrayTilePlaced[indexI][indexJ];
	}
	
	public void setTileInArray(Tile pezzoPiazzato,int indexI,int indexJ){
		arrayTilePlaced[indexI][indexJ]=pezzoPiazzato;
	}
	
	public int getRotations(int indexI,int indexJ){
		return this.rotations[indexI][indexJ];
	}
	
	public void setRotations(int numRot,int indexI,int indexJ){
		rotations[indexI][indexJ]=numRot;
	}
	
	public LinkedList<Point> getTilePlacedCoordinates(){
		return this.tilePlacedCoordinates;
	}
	
	public LinkedList<Point> getExpantionMapCoordinates(){
		return this.expantionMapCoordinates;
	}
	
	public void refreshMapList(){
		//aggiorna lista cordinate ed espansione mappa.
		int xcoord=this.getXUltimoPezzo();
		int ycoord=this.getYUltimoPezzo();
		this.getTilePlacedCoordinates().add(new Point(xcoord,ycoord));
		this.getExpantionMapCoordinates().remove(new Point(xcoord,ycoord));
		if(this.getTileFromArray(xcoord-1, ycoord)==null && !this.getExpantionMapCoordinates().contains(new Point(xcoord-1,ycoord)))
			this.getExpantionMapCoordinates().add(new Point(xcoord-1,ycoord));
		if(this.getTileFromArray(xcoord+1, ycoord)==null && !this.getExpantionMapCoordinates().contains(new Point(xcoord+1,ycoord)))
			this.getExpantionMapCoordinates().add(new Point(xcoord+1,ycoord));
		if(this.getTileFromArray(xcoord, ycoord-1)==null && !this.getExpantionMapCoordinates().contains(new Point(xcoord,ycoord-1)))
			this.getExpantionMapCoordinates().add(new Point(xcoord,ycoord-1));
		if(this.getTileFromArray(xcoord, ycoord+1)==null && !this.getExpantionMapCoordinates().contains(new Point(xcoord,ycoord+1)))
			this.getExpantionMapCoordinates().add(new Point(xcoord,ycoord+1));
		//------------fine aggiornamento
	}
	//----------------------------------
	//metodi giocatore
	//----------------------------------	

	@Override
	public void setNamePlayer1(String name) {
		this.p1Name=name;	
	}
	
	@Override
	public void setNamePlayer2(String name) {
		this.p2Name=name;	
	}

	@Override
	public void setNamePlayer3(String name) {
		this.p3Name=name;	
	}

	@Override
	public void setNamePlayer4(String name) {
		this.p4Name=name;	
	}

	@Override
	public String getNamePlayer1() {
			return this.p1Name ;
	}

	@Override
	public String getNamePlayer2() {
			return this.p2Name ;
	}

	@Override
	public String getNamePlayer3() {
			return this.p3Name ;
	}

	@Override
	public String getNamePlayer4() {
			return this.p4Name ;
	}
	
	public int getCurrentPlayer(){
		int tmp = ((tilePlaced-1)%numPlayer);
		if (tmp==0){
			switch(getNumPlayer()){
			case 2:
				tmp=2;
				break;
			case 3:
				tmp=3;
				break;
			case 4:
				tmp=4;
				break;	
			}
		}
		return tmp;
	}

	@Override
	public void setScore(LinkedList<Integer> player, int score) {
		for(int i=0;i<player.size();i++)
			switch (player.get(i)) {
			case 1:
				p1Score+= score;
				break;
			case 2:
				p2Score+= score;
				break;
			case 3:
				p3Score+= score;
				break;
			case 4:
				p4Score+= score;
				break;
			}
	}
	
	public int getScore(int player){
		int score=0;
		switch (player) {
		case 1:
			score= this.p1Score;
			break;
		case 2:
			score= this.p2Score;
			break;
		case 3:
			score= this.p3Score;
			break;
		case 4:
			score= this.p4Score;
			break;
		}
		return score;
	}

	@Override
	public void setScore(int player, int score) {
		switch (player) {
		case 1:
			this.p1Score=score;
			break;
		case 2:
			this.p2Score=score;
			break;
		case 3:
			this.p3Score=score;
			break;
		case 4:
			this.p4Score=score;
			break;
		}	
	}

	@Override
	public void setNumPlayer(int n) {
		this.numPlayer=n;
	}

	@Override
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer=currentPlayer;
	}
	
	@Override
	public void setTilePlaced(int n) {
		this.tilePlaced=n;	
	}
	
	//----------------------------------
	//metodi seguace
	//----------------------------------	
	public LinkedList<Follower> returnList(){
		LinkedList<Follower> lf= new LinkedList<Follower>();
			if (getCurrentPlayer()==P1)
				lf= blueList;
			if (getCurrentPlayer()==P2)
				lf= redList;
			if (getCurrentPlayer()==P3)
				lf= greenList;
			if (getCurrentPlayer()==P4)
				lf= yellowList;
		return lf;
	}
	
	public LinkedList<Follower> getBlueList(){
		return this.blueList;
	}
	
	public LinkedList<Follower> getRedList(){
		return this.redList;
	}
	
	public LinkedList<Follower> getGreenList(){
		return this.greenList;
	}
	
	public LinkedList<Follower> getYellowList(){
		return this.yellowList;
	}
	
	public Follower getCurrentFollower(){
		if (!returnList().isEmpty())
		return returnList().get(0);
		else return null;
	}

	public void followerPlacedAndRemove(int indexX, int indexY){
		returnList().get(0).setCoordinates(indexX, indexY);
		this.arrayFollowerPlaced[indexX][indexY]=returnList().get(0);
		returnList().remove(0);	
	}
	
	public Follower getFollowerFromArray(int i, int k){
		return this.arrayFollowerPlaced[i][k];
	}
	
	public void resetArrayFollowerPlaced(int i , int k){
		this.arrayFollowerPlaced[i][k]=null;
	}
	
	public void setArrayFollowerPlaced(Follower f,int i , int k){
		this.arrayFollowerPlaced[i][k]=f;
	}


	//-----------------------------------------------------
	//metodi città	
	//-----------------------------------------------------

	public LinkedList<Town> getTownList(){
		return this.townList;
	}
	
	//-----------------------------------------------------
	//metodi strade
	//-----------------------------------------------------

	public LinkedList<Road> getRoadList(){
		return this.roadList;
	}
	
	//-----------------------------------------------------
	//metodi monasteri
	//-----------------------------------------------------
	
	public LinkedList<Church> getChurchList(){
		return this.churchList;
	}
	
	//-----------------------------------------------------
	//metodi salvataggio
	//-----------------------------------------------------

	public String toString() {
		StringBuilder strBuild = new StringBuilder();
		strBuild.append("#Jarcassonne saving file\r\n");
		
		strBuild.append("Placed Tile Number = "+ getTilePlaced() + "\r\n");

		strBuild.append("\r\n#Players' names\r\n");
		strBuild.append("Num Players = "+ getNumPlayer() +"\r\n");
		strBuild.append("p1Name = " + getNamePlayer1() + "\r\n");
		strBuild.append("p2Name = " + getNamePlayer2() + "\r\n");
		strBuild.append("p3Name = " + getNamePlayer3() + "\r\n");
		strBuild.append("p4Name = " + getNamePlayer4() + "\r\n");
		
		strBuild.append("\r\n#Scores\r\n");
		strBuild.append("p1Score = " + getScore(P1) + "\r\n");
		strBuild.append("p2Score = " + getScore(P2) + "\r\n");
		strBuild.append("p3Score = " + getScore(P3) + "\r\n");
		strBuild.append("p4Score = " + getScore(P4) + "\r\n");
		
		strBuild.append("\r\n#Informations about Turn\r\n");
		strBuild.append("Placed Tile Number = "+ getTilePlaced() + "\r\n");
		strBuild.append("getXUltimoPezzo = " + getXUltimoPezzo() + "\r\n");
		strBuild.append("getYUltimoPezzo = " + getYUltimoPezzo() + "\r\n");
		strBuild.append("currentPlayer = " + currentPlayer + "\r\n");
		strBuild.append("annull = " + getAnnul() + "\r\n");
		strBuild.append("first step = " + firstStep + "\r\n");
		
		strBuild.append("\r\n#Tile List\r\n");
		strBuild.append("\r\ntilelist = \r\n");
		for(int i=0;i<tileList.size();i++){
			strBuild.append(""+i+"Tile = " + tileList.get(i).getInfo()+ "\r\n");
		}
		
		strBuild.append("\r\n#Tile Information\r\n");
			for(int i=0; i<this.tilePlacedCoordinates.size();i++){
			strBuild.append(i+"TCP.x = "+this.tilePlacedCoordinates.get(i).x+"\r\n");
			strBuild.append(i+"TCP.y = "+this.tilePlacedCoordinates.get(i).y+"\r\n");
			strBuild.append(i+"Tile = "+this.arrayTilePlaced[this.tilePlacedCoordinates.get(i).x][this.tilePlacedCoordinates.get(i).y].getInfo()+"\r\n");
			strBuild.append(i+"Rotations = "+this.rotations[this.tilePlacedCoordinates.get(i).x][this.tilePlacedCoordinates.get(i).y]+"\r\n");
			if(this.arrayFollowerPlaced[this.tilePlacedCoordinates.get(i).y][this.tilePlacedCoordinates.get(i).x]!=null){
				strBuild.append(i+"color = "+this.arrayFollowerPlaced[this.tilePlacedCoordinates.get(i).y][this.tilePlacedCoordinates.get(i).x].getFollowerColor()+"\r\n");
				strBuild.append(i+"followerOnSide = "+this.arrayFollowerPlaced[this.tilePlacedCoordinates.get(i).y][this.tilePlacedCoordinates.get(i).x].getFollowerOnSide()+"\r\n");
			}
		}
		
		for(int i=0; i<this.expantionMapCoordinates.size();i++){
			strBuild.append(i+"EMC.x = "+this.expantionMapCoordinates.get(i).x+"\r\n");
			strBuild.append(i+"EMC.y = "+this.expantionMapCoordinates.get(i).y+"\r\n");
			strBuild.append(i+"possibleCell = "+this.arrayPossibleCell[this.expantionMapCoordinates.get(i).x][this.expantionMapCoordinates.get(i).y]+"\r\n");
		}
			
		strBuild.append("\r\n#Lists\r\n");
		
		for (int i =0; i<getTownList().size();i++){
			for (int j=0; j<getTownList().get(i).getTTP().size();j++){
				strBuild.append("citta"+i+"TTP.x"+ j + " = "+getTownList().get(i).getTTP().get(j).x+ "\r\n");
				strBuild.append("citta"+i+"TTP.y"+ j + " = "+getTownList().get(i).getTTP().get(j).y+ "\r\n");
			}
		
			for (int k=0; k<getTownList().get(i).getTDE().size();k++){
				strBuild.append("citta"+i+"TDE.x"+ k + " = "+getTownList().get(i).getTDE().get(k).x+ "\r\n");
				strBuild.append("citta"+i+"TDE.y"+ k + " = "+getTownList().get(i).getTDE().get(k).y+ "\r\n");
			}
		
			for (int l=0; l<getTownList().get(i).getPlayerConqueror().size();l++){
			strBuild.append("citta"+i+"playerConqueror.x"+l+" = "+getTownList().get(i).getPlayerConqueror().get(l).getCoordinates().x+ "\r\n");
			strBuild.append("citta"+i+"playerConqueror.y"+l+" = "+getTownList().get(i).getPlayerConqueror().get(l).getCoordinates().y+ "\r\n");
			strBuild.append("citta"+i+"playerConqueror"+l+" = "+getTownList().get(i).getPlayerConqueror().get(l).color+ "\r\n");
			}
		
			strBuild.append("citta"+i+"numShield"+" = "+getTownList().get(i).getNumShield()+ "\r\n");		
			strBuild.append("citta"+i+"scoreTmp"+" = "+getTownList().get(i).getScoreTmp()+ "\r\n");
			
		}

		strBuild.append("\r\n");
					
		for (int i =0; i<getRoadList().size();i++){
			for (int j=0; j<getRoadList().get(i).getRTP().size();j++){
				strBuild.append("strada"+i+"RTP.x" + j + " = "+getRoadList().get(i).getRTP().get(j).x+ "\r\n");
				strBuild.append("strada"+i+"RTP.y" + j + " = "+getRoadList().get(i).getRTP().get(j).y+ "\r\n");
			}
		
			for (int j=0; j<getRoadList().get(i).getRDE().size();j++){
				strBuild.append("strada"+i+"RDE.x" + j + " = "+getRoadList().get(i).getRDE().get(j).x+ "\r\n");
				strBuild.append("strada"+i+"RDE.y" + j + " = "+getRoadList().get(i).getRDE().get(j).y+ "\r\n");
			}
			for (int l=0; l<getRoadList().get(i).getPlayerConqueror().size();l++){
				strBuild.append("strada"+i+"playerConqueror.x"+l+" = "+getRoadList().get(i).getPlayerConqueror().get(l).getCoordinates().x+ "\r\n");
				strBuild.append("strada"+i+"playerConqueror.y"+l+" = "+getRoadList().get(i).getPlayerConqueror().get(l).getCoordinates().y+ "\r\n");
				strBuild.append("strada"+i+"playerConqueror"+l+" = "+getRoadList().get(i).getPlayerConqueror().get(l).color+ "\r\n");
			}
	
				strBuild.append("strada"+i+"score"+" = "+getRoadList().get(i).getScore()+ "\r\n");		
		}

		for (int i =0; i<getChurchList().size();i++){
			for (int k=0;k<getChurchList().get(i).getCTP().size();k++){
				strBuild.append("monastero"+i+"CTP.x"+k+" = "+getChurchList().get(i).getCTP().get(k).x+ "\r\n");
				strBuild.append("monastero"+i+"CTP.y"+k+" = "+getChurchList().get(i).getCTP().get(k).y+ "\r\n");
			}
			for (int k=0;k<getChurchList().get(i).getCDE().size();k++){
				strBuild.append("monastero"+i+"CDE.x"+k+" = "+getChurchList().get(i).getCDE().get(k).x+ "\r\n");
				strBuild.append("monastero"+i+"CDE.y"+k+" = "+getChurchList().get(i).getCDE().get(k).y+ "\r\n");
			}
			if(!getChurchList().get(i).getPlayerConqueror().isEmpty()){
			strBuild.append("monastero"+i+"playerConqueror.x"+" = "+getChurchList().get(i).getPlayerConqueror().getFirst().getCoordinates().x+ "\r\n");
			strBuild.append("monastero"+i+"playerConqueror.y"+" = "+getChurchList().get(i).getPlayerConqueror().getFirst().getCoordinates().y+ "\r\n");
			strBuild.append("monastero"+i+"playerConqueror"+" = "+getChurchList().get(i).getPlayerConqueror().getFirst().color+ "\r\n");
			}
			strBuild.append("monastero"+i+"score"+" = "+getChurchList().get(i).getScore()+ "\r\n");		
		}

		strBuild.append("\r\n#Followers'Lists\r\n");
			strBuild.append(getBlueList().size()+ "\r\n");
			strBuild.append(getRedList().size()+ "\r\n");
			strBuild.append(getGreenList().size()+ "\r\n");
			strBuild.append(getYellowList().size()+ "\r\n");
			
		return strBuild.toString();
	} 

	public void saveModelToFile(String file) throws java.io.IOException {
		InputOutput.saveModelToFile(file, this);
	}
	
	public void loadModelFromFile(String file) throws java.io.FileNotFoundException, java.io.IOException{
		InputOutput.loadModelFromFile(file, this);
	}

	@Override
	public void loadTileListFromFile(String tile, int i) {
		if (tile.equals("tilea")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[0]);	
		}
		if (tile.equals("tileb")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[1]);		
		}
		if (tile.equals("tilec")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[2]);		
		}
		if (tile.equals("tiled")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[3]);
		}
		if (tile.equals("tilee")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[4]);	
		}
		if (tile.equals("tilef")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[5]);
		}
		if (tile.equals("tileg")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[6]);	
		}
		if (tile.equals("tileh")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[7]);	
		}
		if (tile.equals("tilei")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[8]);			
		}
		if (tile.equals("tilej")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[9]);			
		}
		if (tile.equals("tilek")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[10]);			
		}
		if (tile.equals("tilel")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[11]);			
		}
		if (tile.equals("tilem")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[12]);			
		}
		if (tile.equals("tilen")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[13]);			
		}
		if (tile.equals("tileo")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[14]);			
		}
		if (tile.equals("tilep")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[15]);			
		}
		if (tile.equals("tileq")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[16]);			
		}
		if (tile.equals("tiler")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[17]);			
		}
		if (tile.equals("tiles")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[18]);			
		}
		if (tile.equals("tilet")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[19]);			
		}
		if (tile.equals("tileu")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[20]);			
		}
		if (tile.equals("tilev")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[21]);			
		}
		if (tile.equals("tilew")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[22]);			
		}
		if (tile.equals("tilex")){
			tileList.remove(i);
			tileList.add(i, Tile.TILE_ARRAY[23]);			
		}
		
	}	
}
