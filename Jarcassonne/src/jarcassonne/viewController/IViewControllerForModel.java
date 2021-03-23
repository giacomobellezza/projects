package jarcassonne.viewController;

import java.awt.Cursor;
import java.awt.event.MouseEvent;

import jarcassonne.model.Follower;
import jarcassonne.viewController.MainGUI.MapPanel;



public interface IViewControllerForModel {
	
	//metodi per aprire e chiudere tutte le finestre che creiamo.
	
	public void openStartWindow();
	
	public void closeStartWindow();
	
	public void openNewGameWindow();
	
	public void closeNewGameWindow();
	
	public void openMainGUI();
	
	public void closeMainGUI();
	
	public void openTutorial();
	
	public void closeTutorial();
	
	public void openEndGame();
	
	public void mousePressed(MouseEvent e, int dx, int dy, int driftVectorX, int driftVectorY, MapPanel mapPanel,  Cursor cursore, Cursor defaultCursor);
    
	public void mouseReleased(MapPanel mappanel,MouseEvent e);
    
    public int followerPositionDecition(Follower f, int Xpixel, int Ypixel);
    
    public boolean checkNorthSide();
    
    public boolean checkEastSide();
    
    public boolean checkSouthSide();
    
    public boolean checkWestSide();
    
    public void addTownOrNewTown();
    
    public void checkCompleteTown();
    
	public void addRoadOrNewRoad();
	
	public void checkCompleteRoad();
	
	public void newChurch();
	
	public void addTileInChurchDomine();
	
	public void checkCompleteChurch();
	
	public void clickedCenter();
	
	public void showExitMessage();
	
	public String winnerOrDraw();
	
	public void endGame();
	
	public void saveGame();

	public void setImpossibleTilePlaced(boolean Boolean);
	
	public boolean getImpossibleTilePlaced();
	
	public String openFile();
	
	public void showCannotFindFileMessage(String file);

	public void showCannotReadFileMessage(String file);


	
    
 
	
    
    

}
