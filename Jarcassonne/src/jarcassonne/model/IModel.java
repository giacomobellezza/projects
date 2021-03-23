package jarcassonne.model;

import java.awt.Point;
import java.util.LinkedList;

public interface IModel {

	
	// tutti i metodi che servono per agire sulle tessere,plancia,funzioni di gioco
	
	
	//-----------------------------------------------------------------------------
	//                METODI GIOCATORE
	//-----------------------------------------------------------------------------

	
	public void initGame();
	
	public int getNumPlayer();	
	public void setNumPlayer(int n);
	
	public void setNamePlayer1(String name);
	public void setNamePlayer2(String name);
	public void setNamePlayer3(String name);
	public void setNamePlayer4(String name);	
	
	public String getNamePlayer1();
	public String getNamePlayer2();
	public String getNamePlayer3();
	public String getNamePlayer4();
	
	public int getCurrentPlayer();
	public void setCurrentPlayer(int currentPlayer);

	
	public void setTilePlaced(int n);
	
	
		
	//-----------------------------------------------------------------------------
	//                METODI CONTROLLO REGOLE
	//-----------------------------------------------------------------------------
	public void possibleCell(Tile t);
	
	public boolean tileIsCompatible(int X, int Y, Tile t);
	
	public void checkRotate(Tile t, int X, int Y);
	
	

	
	//-----------------------------------------------------------------------------
	//                METODI SVOLGIMENTO TURNO
	//-----------------------------------------------------------------------------
	public int getSizeOfTileList();
	
	public void nextTurn();
		
	public void setScore(LinkedList<Integer> player, int score);
	
	public void setScore( int player, int score);
	
	public int getScore(int player);
		
	public Tile getCurrentTile();
	
	public void setCurrentTile(Tile t);
	
	public Tile getPreviousTile();
	
	
	public void annul();	
	public boolean getAnnul();	
	public void resetAnnul();
		
	public void firstStepEnd();
	public void firstStepStart();	
	public boolean getFirstStep();

	
	
	public int getTilePlaced();
	
	public void TilePlacedPlus();
	
	public void TilePlacedMinus();
	
	
	public int getXUltimoPezzo();
	
	public int getYUltimoPezzo();
	
	public void setXUltimoPezzo(int x);
	
	public void setYUltimoPezzo(int y);
	
	
	public boolean getArrayPossibleCell(int indexI,int indexJ);
	
	public void setFalseInArrayPossibleCell(int x, int y);
	
	public LinkedList<Point> getTilePlacedCoordinates();
	
	public LinkedList<Point> getExpantionMapCoordinates();
	
	public void refreshMapList();

	public Tile getTileFromArray(int indexI,int indexJ);
	
	public void setTileInArray(Tile pezzoPiazzato,int indexI,int indexJ);
	
	public int getRotations(int indexI,int indexJ);
	
	public void setRotations(int numRot,int indexI,int indexJ);

	//-----------------------------------------------------------------------------
	//                METODI SEGUACE
	//-----------------------------------------------------------------------------
	
	public LinkedList<Follower> returnList();
	
	public Follower getCurrentFollower();
	
	public void followerPlacedAndRemove(int ArrayX, int ArrayY);
	
	public void resetArrayFollowerPlaced(int i , int k);
	
	public void setArrayFollowerPlaced(Follower f,int i , int k);
	
	public Follower getFollowerFromArray(int i, int k);
	
	public LinkedList<Follower> getBlueList();
	
	public LinkedList<Follower> getRedList();
	
	public LinkedList<Follower> getGreenList();
	
	public LinkedList<Follower> getYellowList();
	
	
	//-----------------------------------------------------------------------------
	//                METODI CITTÀ
	//-----------------------------------------------------------------------------
	
		
	public LinkedList<Town> getTownList();
	

	//-----------------------------------------------------
	//                METODI STRADE
	//-----------------------------------------------------

	
	public LinkedList<Road> getRoadList();


	//-----------------------------------------------------
	//                METODI MONASTERI
	//-----------------------------------------------------
	
	
	public LinkedList<Church> getChurchList();
	
	
	//-----------------------------------------------------------------------------
	//                METODI SALVATAGGIO
	//-----------------------------------------------------------------------------
	
	public String toString();
	
	public void saveModelToFile(String file) throws java.io.IOException;
	
	public void loadModelFromFile(String file) throws java.io.FileNotFoundException, java.io.IOException;

	public void loadTileListFromFile(String tile, int i);
	
	public LinkedList<Tile> getTileList();
	
	
}
