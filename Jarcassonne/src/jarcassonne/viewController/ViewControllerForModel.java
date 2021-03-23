package jarcassonne.viewController;

import jarcassonne.model.Church;
import jarcassonne.model.Follower;
import jarcassonne.model.Model;
import jarcassonne.model.Road;
import jarcassonne.model.Tile;
import jarcassonne.model.Town;
import jarcassonne.viewController.MainGUI.MapPanel;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.LinkedList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class ViewControllerForModel implements IViewControllerForModel {

    private static ViewControllerForModel instance=null;
    private StartWindow startWindow = null;
    private NewGameWindow newGameWindow = null;
    private MainGUI mainGUI = null;
    private TutorialWindow tutorialWindow = null;
    private EndGame endgame = null;
    private JFileChooser jfileChooser = null;
    private boolean impossibleTilePlaced=false;

    
    private final String endLocation = "/jarcassonne/sound/final.wav";
    private final String fileLocation = "/jarcassonne/sound/bard.wav";
    private final String fileLocation2 = "/jarcassonne/sound/trumpet.wav";

  
    
    private ViewControllerForModel(){
    	
    }
    
    //-----------------------------------------------------------------------------
  	//                METODI GESTIONE MOUSE
  	//-----------------------------------------------------------------------------
  		
	    public void mousePressed(MouseEvent e, int dx, int dy, int driftVectorX, int driftVectorY, MapPanel mapPanel, Cursor cursore, Cursor defaultCursor){
	    	if(mapPanel.endGame == false){
	    		if (Model.getInstance().getFirstStep()==true){
						if (e.getButton()==1){
							if (mainGUI.pezzoPiazzato==true){
								if(mouseClickedRight(mapPanel.getColumnIndex(e.getX())-dx+driftVectorX, mapPanel.getRowIndex(e.getY())-dy+driftVectorY)==true
										&& Model.getInstance().getAnnul()==false){
									Tile.createPreviewTile(Model.getInstance().getPreviousTile().getName()).paintTile(mapPanel.getGraphics(), (mapPanel.getRowIndex(e.getY())-1)*100+10, (mapPanel.getColumnIndex(e.getX())-1)*100+10);
									mapPanel.paintTileOutOfPanel(e.getY(), e.getX());
								}
							}
							if(mainGUI.pezzoPiazzato==false)
								if(mouseClickedLeft(mapPanel.getColumnIndex(e.getX())-dx +driftVectorX, mapPanel.getRowIndex(e.getY())-dy +driftVectorY)==true){
						
									Tile.createPreviewTile(Model.getInstance().getCurrentTile().getName()).paintTile(mapPanel.getGraphics(), ((mapPanel.getRowIndex(e.getY())-1))*100+10, ((mapPanel.getColumnIndex(e.getX())-1))*100+10);
									mapPanel.paintTileOutOfPanel(e.getY(), e.getX());
									Model.getInstance().TilePlacedPlus();
									mainGUI.pezzoPiazzato=true;
									mainGUI.paintWhiteOnPreview(mainGUI.getPreviewPanel().getGraphics());
									Model.getInstance().resetAnnul();
							}
						}
					//---------------------------------------------------------------------------------------------------------------
						//----------passa al seguace, non puoi piu fare annulla
						if (e.getButton()==3 && mainGUI.pezzoPiazzato==true && mainGUI.seguacePiazzato==false && mainGUI.released==false){
							if(Model.getInstance().getTilePlaced()!=0){		
									mainGUI.pezzoPiazzato=false;
									Model.getInstance().refreshMapList();
									Model.getInstance().setFalseInArrayPossibleCell(Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo());
									Model.getInstance().firstStepEnd();
									mapPanel.setCursor(cursore);		
									addTownOrNewTown();
									addRoadOrNewRoad();
								}
							//---------------------monasteri
							newChurch();
							addTileInChurchDomine();
							//----------------------end monasteri
						}
					//---------------------------------------------------------------------------------------------------------------
						if (e.getButton()==2){
							if(mainGUI.pezzoPiazzato==true)	
								if (Model.getInstance().getAnnul()==false){
									Model.getInstance().setTileInArray(null,Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo());		
									Model.getInstance().TilePlacedMinus();
									Model.getInstance().annul();
									mainGUI.refreshPreviewTile();
									mapPanel.paintPossibleCell();
									mainGUI.pezzoPiazzato=false;
									Model.getInstance().setXUltimoPezzo( Model.getInstance().getTilePlacedCoordinates().getLast().x);
									Model.getInstance().setYUltimoPezzo( Model.getInstance().getTilePlacedCoordinates().getLast().y);
								}
						}
					}
				if(Model.getInstance().getFirstStep()==false){
						if (e.getButton()==1 && Model.getInstance().getYUltimoPezzo()==mapPanel.getColumnIndex(e.getX())-dx +driftVectorX && Model.getInstance().getXUltimoPezzo()==mapPanel.getRowIndex(e.getY())-dy +driftVectorY){
							int side = followerPositionDecition(Model.getInstance().getCurrentFollower(), e.getY(), e.getX());
							if(side==Follower.NORTH && checkNorthSide()){
								Model.getInstance().getCurrentFollower().paintFollower(mapPanel.getGraphics(),((mapPanel.getRowIndex(e.getY())-1))*100+10, ((mapPanel.getColumnIndex(e.getX())-1))*100+40);
								mainGUI.seguacePiazzato=true;
							}
							if(side==Follower.EAST && checkEastSide()){
								Model.getInstance().getCurrentFollower().paintFollower(mapPanel.getGraphics(),((mapPanel.getRowIndex(e.getY())-1))*100+40, ((mapPanel.getColumnIndex(e.getX())-1))*100+70);
								mainGUI.seguacePiazzato=true;
							}
							if(side==Follower.SOUTH && checkSouthSide()){
								Model.getInstance().getCurrentFollower().paintFollower(mapPanel.getGraphics(),((mapPanel.getRowIndex(e.getY())-1))*100+70, ((mapPanel.getColumnIndex(e.getX())-1))*100+40);
								mainGUI.seguacePiazzato=true;
							}
							if(side==Follower.WEST && checkWestSide()){
								Model.getInstance().getCurrentFollower().paintFollower(mapPanel.getGraphics(),((mapPanel.getRowIndex(e.getY())-1))*100+40, ((mapPanel.getColumnIndex(e.getX())-1))*100+10);
								mainGUI.seguacePiazzato=true;
							}
							if(side==Follower.CENTRE){
								clickedCenter();
								Model.getInstance().getCurrentFollower().paintFollower(mapPanel.getGraphics(),((mapPanel.getRowIndex(e.getY())-1))*100+40, ((mapPanel.getColumnIndex(e.getX())-1))*100+40);
								mainGUI.seguacePiazzato=true;
							}
							if(mainGUI.seguacePiazzato==true){
								Model.getInstance().followerPlacedAndRemove(mapPanel.getColumnIndex(e.getX())-dx +driftVectorX, mapPanel.getRowIndex(e.getY())-dy +driftVectorY);
								mainGUI.repaintFollowerLabel();
							}
							//fa la stessa funzione di salta----------------------------------------------------
							if(mainGUI.seguacePiazzato==true){
								if(Model.getInstance().getTilePlaced()<Model.getInstance().getSizeOfTileList()){
									mainGUI.refreshPreviewTile();
									mapPanel.paintPossibleCell();
										if(getImpossibleTilePlaced()==true){
											mainGUI.refreshPreviewTile();
											setImpossibleTilePlaced(false);
										}
									Model.getInstance().firstStepStart();
									mapPanel.setCursor(defaultCursor);
									mainGUI.seguacePiazzato=false;
									mainGUI.released=false;
									mainGUI.scrollArrow();
									checkCompleteTown();
									checkCompleteRoad();
									checkCompleteChurch();
									mainGUI.setTitle("Unplaced Tile : "+ (71-Model.getInstance().getTilePlaced()));
									mainGUI.systemOut();
								}else{
									mapPanel.setCursor(defaultCursor);
									mainGUI.seguacePiazzato=true;
									ViewControllerForModel.getInstance().endGame();
									mapPanel.endGame=true;
								}
							}
						}
				
						if (e.getButton()==3 && mainGUI.released== true)
							if( Model.getInstance().getTilePlaced()<Model.getInstance().getSizeOfTileList()){
								//comportamento del salta								
								mainGUI.refreshPreviewTile();
								mapPanel.paintPossibleCell();
									if(getImpossibleTilePlaced()==true){
										mainGUI.refreshPreviewTile();
										setImpossibleTilePlaced(false);
									}
								Model.getInstance().firstStepStart();								
								mapPanel.setCursor(defaultCursor);
								mainGUI.released=false;
								mainGUI.scrollArrow();
								checkCompleteTown();
								checkCompleteRoad();
								checkCompleteChurch();
								mainGUI.setTitle("Unplaced Tile : "+ (71-Model.getInstance().getTilePlaced()));
								mainGUI.systemOut();
							}else{
								mapPanel.setCursor(defaultCursor);
								mainGUI.seguacePiazzato=true;
								ViewControllerForModel.getInstance().endGame();
								mapPanel.endGame=true;
							}
					}
	    		}
	    	}//-----------------------------------------------------END METHODS----------------------------------------------------------------------
    
	    public void mouseReleased(MapPanel mappanel,MouseEvent e){
	    	if (mappanel.dragged==true && e.getButton()==1){
				if(Model.getInstance().getTilePlaced()<Model.getInstance().getSizeOfTileList()){	
					mappanel.deltaX=(e.getX()-10)/100;
					mappanel.deltaY=(e.getY()-10)/100;
					mainGUI.dx=mappanel.deltaX-mappanel.clickX;
					mainGUI.dy=mappanel.deltaY-mappanel.clickY;
					if(mainGUI.mouseClickX!=mappanel.deltaX || mainGUI.mouseClickY!=mappanel.deltaY)
						mappanel.repaintMap();		
					mappanel.dragged=false;
				}else{
					mappanel.deltaX=(e.getX()-10)/100;
					mappanel.deltaY=(e.getY()-10)/100;
					mainGUI.dx=mappanel.deltaX-mappanel.clickX;
					mainGUI.dy=mappanel.deltaY-mappanel.clickY;
					if(mainGUI.mouseClickX!=mappanel.deltaX || mainGUI.mouseClickY!=mappanel.deltaY){
						mappanel.paintWhiteAll();
							if(mainGUI.pezzoPiazzato==false)
								for(int i=0;i<Model.getInstance().getTilePlacedCoordinates().size();i++){
									int x=Model.getInstance().getTilePlacedCoordinates().get(i).x;
									int y=Model.getInstance().getTilePlacedCoordinates().get(i).y;
											Model.getInstance().getTileFromArray(x, y).resetRotation();
											Model.getInstance().getTileFromArray(x, y).rotate(Model.getInstance().getRotations(x, y));					
											Model.getInstance().getTileFromArray(x, y).paintTile(mappanel.getGraphics(), (x-1+mainGUI.dy-mainGUI.driftVectorY)*100+10, (y-1+mainGUI.dx-mainGUI.driftVectorX)*100+10);	
									}
						int x=Model.getInstance().getXUltimoPezzo();
						int y=Model.getInstance().getYUltimoPezzo();
						Model.getInstance().getTileFromArray(x, y).resetRotation();
						Model.getInstance().getTileFromArray(x, y).rotate(Model.getInstance().getRotations(x, y));					
						Model.getInstance().getTileFromArray(x, y).paintTile(mappanel.getGraphics(), (x-1+mainGUI.dy-mainGUI.driftVectorY)*100+10, (y-1+mainGUI.dx-mainGUI.driftVectorX)*100+10);		
						mappanel.repaintFollower();
						mappanel.dragged=false;
					}
				}
	    	}
			if(e.getButton()==3 && Model.getInstance().getFirstStep()==false)
				mainGUI.released=true;
	    }
   
	    private boolean mouseClickedLeft(int X, int Y){
			//click sinistro: posizionamento,salvataggio su array, tiene memoria delle cordinate dell'ultimo pezzo piazzato
	    	boolean click = false;
			if(Y>0 && X>0){
				Tile attuale = Model.getInstance().getCurrentTile();
				Tile currentTile=Tile.createPreviewTile(attuale.getName());  //controllare la correttezza, valutare se creare un attributo
				if(Model.getInstance().getTileFromArray(Y, X)== null  && Model.getInstance().tileIsCompatible(X, Y, currentTile)==true){
					Model.getInstance().setYUltimoPezzo(X); 
					Model.getInstance().setXUltimoPezzo(Y); 
					Model.getInstance().setTileInArray(attuale, Y, X);
					Model.getInstance().setRotations(currentTile.getNumRotations(), Y, X);
					click = true;
					}	
				}
				return click;
		}

	    private boolean mouseClickedRight(int X, int Y){	
			//tasto destro: ruota solo se puntatore è nella cella interessata
			boolean click = false;
			if(X==Model.getInstance().getYUltimoPezzo() && Y==Model.getInstance().getXUltimoPezzo()){
				Tile attuale = Model.getInstance().getPreviousTile();
				Tile currentTile=Tile.createPreviewTile(attuale.getName());
				currentTile.rotate(); 
				Model.getInstance().checkRotate(currentTile,X,Y);			
				Model.getInstance().setRotations(currentTile.getNumRotations(), Y, X);
				click=true;		
			}
			return click;
		}
	    
	//-----------------------------------------------------------------------------
	//                METODI SEGUACI
	//-----------------------------------------------------------------------------
		
		public int followerPositionDecition(Follower f, int Xpixel, int Ypixel){
			int restoX = (Xpixel-10) % 100;
			int restoY = (Ypixel-10) % 100;
			int position = 0;
			if(f!=null){
			int numRotation=f.getNumRotation();
			Tile tile= Model.getInstance().getTileFromArray(Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo());
			tile.resetRotation();
			tile.rotate(Model.getInstance().getRotations(Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo()));
				//North
				if (restoX < restoY && restoY < 100 - restoX && (tile.getNSide()!=0)){
					position= Follower.NORTH;
					f.resetRotation(numRotation);
					numRotation=2;
					f.rotate();
					f.rotate();		
				}
				//East
				if (restoX < restoY && restoY > 100 - restoX && (tile.getESide()!=0)){
					position= Follower.EAST;
					f.resetRotation(numRotation);
					numRotation=3;
					f.rotate();
					f.rotate();
					f.rotate();
				}
				//South
				if (restoX > restoY && restoY > 100 - restoX && (tile.getSSide()!=0)){
					position= Follower.SOUTH;
					f.resetRotation(numRotation);
				}
				//West
				if (restoX > restoY && restoY < 100 - restoX && (tile.getWSide()!=0)){	
					position= Follower.WEST;
					f.resetRotation(numRotation);
					numRotation=1;
					f.rotate();
				}
				//Church
				if(tile.getChurch()==true)
					if(restoX>30 && restoX<70 && restoY>30 && restoY<70)
						position=Follower.CENTRE;	
				f.setFollowerOnSide(position);
			}
			return position;
		}
		
		public boolean checkNorthSide(){
			boolean N=false;
			Tile tile= Model.getInstance().getTileFromArray(Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo());
			tile.resetRotation();
			tile.rotate(Model.getInstance().getRotations(Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo()));
			int north= tile.getNSide();
			Point p = new Point(Model.getInstance().getXUltimoPezzo(),
								Model.getInstance().getYUltimoPezzo());
			Point pNear = new Point(Model.getInstance().getXUltimoPezzo()-1,
									Model.getInstance().getYUltimoPezzo());
			int index =getTTPList(p);
			int indexRoad =getRTPList(p,pNear);
			
			//città
			if(north==Tile.TOWN){
				if(tile.getTownIsContinue()==true){
					if(Model.getInstance().getTownList().get(index).getPlayerConqueror().isEmpty()){
						Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
						N=true;
					}	
				}
				if(tile.getTownIsContinue()==false){
					if(Model.getInstance().getTileFromArray(pNear.x, pNear.y)==null){
						if(Model.getInstance().getTownList().get(index).getTDE().contains(pNear) &&
								Model.getInstance().getTownList().get(index).getTTP().size()==1){
							    Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
						}else{
								Model.getInstance().getTownList().addFirst(Model.getInstance().getTownList().get(index));
								Model.getInstance().getTownList().remove(index+1);
								index= getTTPList(p);
								Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
						}
								N=true;
					}else{
						if(Model.getInstance().getTileFromArray(pNear.x, pNear.y).isTown()){
							if(Model.getInstance().getTownList().get(index).getTTP().contains(pNear) &&
									Model.getInstance().getTownList().get(index).getTTP().contains(p)){
									if(Model.getInstance().getTownList().get(index).getPlayerConqueror().isEmpty()){
										Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
										N=true;
									}
							}else{
								Model.getInstance().getTownList().addFirst(Model.getInstance().getTownList().get(index));
								Model.getInstance().getTownList().remove(index+1);
								index= getTTPList(p);
								if(Model.getInstance().getTownList().get(index).getPlayerConqueror().isEmpty()){
										Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
										N=true;
								}
							}
						}
					}
				}
			}
			//strade
			if(north==Tile.ROAD){
				if(!Model.getInstance().getTileFromArray(p.x , p.y).getRoadIsContinue()){
					if(Model.getInstance().getTileFromArray(pNear.x , pNear.y)==null || 
							Model.getInstance().getTileFromArray(pNear.x , pNear.y).getRoadIsContinue()){
								if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty() ||
							      Model.getInstance().getTileFromArray(pNear.x , pNear.y)==null){
									Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
									N=true;
								}
					}else{
						if(Model.getInstance().getFollowerFromArray(pNear.x, pNear.y)==null ||
							Model.getInstance().getFollowerFromArray(pNear.x, pNear.y).getFollowerOnSide()!=Follower.SOUTH){
								Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));	
								N=true;
						}
					}
				}else{
					if(tile.getESide()==Tile.ROAD){
						if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty()){
							Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							N=true;
						}							
					}
					if(tile.getSSide()==Tile.ROAD){
						if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty()){
							Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							N=true;
						}
					}
					if(tile.getWSide()==Tile.ROAD){
						if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty()){
							Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							N=true;
						}
					}
				}
			}
			return N;
		}
		
		public boolean checkEastSide(){
			boolean E=false;
			Tile tile= Model.getInstance().getTileFromArray(Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo());
			tile.resetRotation();
			tile.rotate(Model.getInstance().getRotations(Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo()));
			int east= tile.getESide();
			Point p = new Point(Model.getInstance().getXUltimoPezzo(),
								Model.getInstance().getYUltimoPezzo());
			Point pNear = new Point(Model.getInstance().getXUltimoPezzo(),
									Model.getInstance().getYUltimoPezzo()+1);
			int index =getTTPList(p);
			int indexRoad=getRTPList(p,pNear);
			
			//città
			if(east==Tile.TOWN){
				if(tile.getTownIsContinue()==true){
					if(Model.getInstance().getTownList().get(index).getPlayerConqueror().isEmpty()){
						Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
						E=true;
					}	
				}
				if(tile.getTownIsContinue()==false){
					if(Model.getInstance().getTileFromArray(pNear.x, pNear.y)==null){
						if(Model.getInstance().getTownList().get(index).getTDE().contains(pNear) &&
						   Model.getInstance().getTownList().get(index).getTTP().size()==1){
							Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							}else{
								Model.getInstance().getTownList().addFirst(Model.getInstance().getTownList().get(index));
								Model.getInstance().getTownList().remove(index+1);
								index= getTTPList(p);
								Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							}
						E=true;
					}else{
						if(Model.getInstance().getTileFromArray(pNear.x, pNear.y).isTown()){
							if(Model.getInstance().getTownList().get(index).getTTP().contains(pNear) &&
									Model.getInstance().getTownList().get(index).getTTP().contains(p)){
									if(Model.getInstance().getTownList().get(index).getPlayerConqueror().isEmpty()){
										Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
										E=true;
									}
							}else{
								Model.getInstance().getTownList().addFirst(Model.getInstance().getTownList().get(index));
								Model.getInstance().getTownList().remove(index+1);
								index= getTTPList(p);
									if(Model.getInstance().getTownList().get(index).getPlayerConqueror().isEmpty()){
										Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
										E=true;
									}
							}
							
						}
					}
				}
			}
			//strade
			if(east==Tile.ROAD){
				if(!Model.getInstance().getTileFromArray(p.x , p.y).getRoadIsContinue()){
					if(Model.getInstance().getTileFromArray(pNear.x , pNear.y)==null || 
				    	Model.getInstance().getTileFromArray(pNear.x , pNear.y).getRoadIsContinue()){
							if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty() ||
							   Model.getInstance().getTileFromArray(pNear.x , pNear.y)==null){
								Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
								E=true;
							}
					}else{
						if(Model.getInstance().getFollowerFromArray(pNear.x, pNear.y)==null ||
								Model.getInstance().getFollowerFromArray(pNear.x, pNear.y).getFollowerOnSide()!=Follower.WEST){
								Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
								E=true;
						}
					}
				}else{
					if(tile.getNSide()==Tile.ROAD){
						if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty()){
								Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
								E=true;
						}							
					}
					if(tile.getSSide()==Tile.ROAD){
						if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty()){
								Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
								E=true;
						}
					}
					if(tile.getWSide()==Tile.ROAD){
						if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty()){
								Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
								E=true;
						}
					}
				}
			}
			return E;
		}
		
		public boolean checkSouthSide(){
			boolean S=false;
			Tile tile= Model.getInstance().getTileFromArray(Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo());
			tile.resetRotation();
			tile.rotate(Model.getInstance().getRotations(Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo()));
			int south= tile.getSSide();
			Point p = new Point(Model.getInstance().getXUltimoPezzo(),
								Model.getInstance().getYUltimoPezzo());
			Point pNear = new Point(Model.getInstance().getXUltimoPezzo()+1,
									Model.getInstance().getYUltimoPezzo());
			int index =getTTPList(p);
			int indexRoad=getRTPList(p,pNear);
			
			//città
			if(south==Tile.TOWN){
				if(tile.getTownIsContinue()==true){
					if(Model.getInstance().getTownList().get(index).getPlayerConqueror().isEmpty()){
						Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
						S=true;
					}	
				}
				if(tile.getTownIsContinue()==false){
					if(Model.getInstance().getTileFromArray(pNear.x, pNear.y)==null){
					if(Model.getInstance().getTownList().get(index).getTDE().contains(pNear) &&
						Model.getInstance().getTownList().get(index).getTTP().size()==1){
						Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
						}else{
								Model.getInstance().getTownList().addFirst(Model.getInstance().getTownList().get(index));
								Model.getInstance().getTownList().remove(index+1);
								index= getTTPList(p);
								Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
						}
						S=true;
					}else{
						if(Model.getInstance().getTileFromArray(pNear.x, pNear.y).isTown()){
							if(Model.getInstance().getTownList().get(index).getTTP().contains(pNear) &&
									Model.getInstance().getTownList().get(index).getTTP().contains(p)){
									if(Model.getInstance().getTownList().get(index).getPlayerConqueror().isEmpty()){
										Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
										S=true;
									}
							}else{
								Model.getInstance().getTownList().addFirst(Model.getInstance().getTownList().get(index));
								Model.getInstance().getTownList().remove(index+1);
								index= getTTPList(p);
								if(Model.getInstance().getTownList().get(index).getPlayerConqueror().isEmpty()){
									Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
									S=true;
								}
							}
							
						}
					}
				}
			}
			//strade
			if(south==Tile.ROAD){
				if(!Model.getInstance().getTileFromArray(p.x , p.y).getRoadIsContinue()){
					if(Model.getInstance().getTileFromArray(pNear.x , pNear.y)==null || 
						Model.getInstance().getTileFromArray(pNear.x , pNear.y).getRoadIsContinue()){
						if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty() ||
							Model.getInstance().getTileFromArray(pNear.x , pNear.y)==null){
							Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							S=true;
						}
					}else{
						if(Model.getInstance().getFollowerFromArray(pNear.x, pNear.y)==null ||
							Model.getInstance().getFollowerFromArray(pNear.x, pNear.y).getFollowerOnSide()!=Follower.NORTH){
							Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							S=true;
						}
					}
					}else{
					if(tile.getNSide()==Tile.ROAD){
						if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty()){
							Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							S=true;
						}							
					}
					if(tile.getESide()==Tile.ROAD){
						if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty()){
							Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							S=true;
						}
					}
					if(tile.getWSide()==Tile.ROAD){
						if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty()){
							Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							S=true;
						}
					}
				}
			}
			return S;
		}
		
		public boolean checkWestSide(){
			boolean W=false;
			Tile tile= Model.getInstance().getTileFromArray(Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo());
			tile.resetRotation();
			tile.rotate(Model.getInstance().getRotations(Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo()));
			int west= tile.getWSide();
			Point p = new Point(Model.getInstance().getXUltimoPezzo(),
								Model.getInstance().getYUltimoPezzo());
			Point pNear = new Point(Model.getInstance().getXUltimoPezzo(),
									Model.getInstance().getYUltimoPezzo()-1);
			int index =getTTPList(p);
			int indexRoad=getRTPList(p,pNear);
			//città
			if(west==Tile.TOWN){
				if(tile.getTownIsContinue()==true){
					if(Model.getInstance().getTownList().get(index).getPlayerConqueror().isEmpty()){
						Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
						W=true;
					}	
				}
				if(tile.getTownIsContinue()==false){
					if(Model.getInstance().getTileFromArray(pNear.x, pNear.y)==null){
						if(Model.getInstance().getTownList().get(index).getTDE().contains(pNear) &&
					    	Model.getInstance().getTownList().get(index).getTTP().size()==1){
							Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							}else{
							Model.getInstance().getTownList().addFirst(Model.getInstance().getTownList().get(index));
							Model.getInstance().getTownList().remove(index+1);
							index= getTTPList(p);
							Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							}
						W=true;
					}else{
						if(Model.getInstance().getTileFromArray(pNear.x, pNear.y).isTown()){
							if(Model.getInstance().getTownList().get(index).getTTP().contains(pNear) &&
								Model.getInstance().getTownList().get(index).getTTP().contains(p)){
									if(Model.getInstance().getTownList().get(index).getPlayerConqueror().isEmpty()){
										Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
										W=true;
									}
							}else{
								Model.getInstance().getTownList().addFirst(Model.getInstance().getTownList().get(index));
								Model.getInstance().getTownList().remove(index+1);
								index= getTTPList(p);
									if(Model.getInstance().getTownList().get(index).getPlayerConqueror().isEmpty()){
										Model.getInstance().getTownList().get(index).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
										W=true;
									}
							}
						}
					}
				}
			}
			//strade
			if(west==Tile.ROAD){
				if(!Model.getInstance().getTileFromArray(p.x , p.y).getRoadIsContinue()){
					if(Model.getInstance().getTileFromArray(pNear.x , pNear.y)==null || 
						Model.getInstance().getTileFromArray(pNear.x , pNear.y).getRoadIsContinue()){
						if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty() ||
						 Model.getInstance().getTileFromArray(pNear.x , pNear.y)==null){
							Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							W=true;
						}
					}else{
						if(Model.getInstance().getFollowerFromArray(pNear.x, pNear.y)==null ||
						 Model.getInstance().getFollowerFromArray(pNear.x, pNear.y).getFollowerOnSide()!=Follower.EAST){
							Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							W=true;
						}
					}
				}else{
					if(tile.getESide()==Tile.ROAD){
						if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty()){
						 Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							W=true;
						}							
					}
					if(tile.getSSide()==Tile.ROAD){
						if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty()){
						 Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							W=true;
						}
											
					}
					if(tile.getNSide()==Tile.ROAD){
						if(Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().isEmpty()){
							Model.getInstance().getRoadList().get(indexRoad).getPlayerConqueror().add(Model.getInstance().returnList().get(0));
							W=true;
						}
					}
				}
			}
			return W;
		}
		
		public void clickedCenter(){
			if(!Model.getInstance().getChurchList().isEmpty())
			Model.getInstance().getChurchList().getFirst().getPlayerConqueror().add(Model.getInstance().getCurrentFollower());
		}
	
	//-----------------------------------------------------------------------------
	//                METODI CITTÀ
	//-----------------------------------------------------------------------------
		
		public void addTownOrNewTown(){
			Point p = new Point();
			p.x= Model.getInstance().getXUltimoPezzo();
			p.y= Model.getInstance().getYUltimoPezzo();
			int indexPoint=-1;
			int counter=0;
			int contatore=0;
			
			for (int i =0; i<Model.getInstance().getTownList().size();i++){
				if(Model.getInstance().getTownList().get(i).getTDE().contains(p))
					contatore++;
			}	
			if(Model.getInstance().getTileFromArray(p.x, p.y).isTown()){
			//-----------------verifica unione città------------------
				if (contatore >1 && Model.getInstance().getTileFromArray(p.x, p.y).getTownIsContinue()){
					combineTownLists(p);
				}
			  
			if(Model.getInstance().getTileFromArray(p.x, p.y).getTownIsContinue()){
				//-----------------------------------------------------------------------------------------
				//-----------------città continua esistente: aggiunge pezzo	
				for (int i =0; i<Model.getInstance().getTownList().size();i++){
					if(Model.getInstance().getTownList().get(i).getTDE().indexOf(p)!=-1){
						indexPoint = Model.getInstance().getTownList().get(i).getTDE().indexOf(p);
						Model.getInstance().getTownList().get(i).getTTP().add(p);
						Model.getInstance().getTownList().get(i).scoreTmpPlus();
							if(Model.getInstance().getTileFromArray(p.x, p.y).getShield()){
								Model.getInstance().getTownList().get(i).numShieldPlus();
							}
						Model.getInstance().getTownList().get(i).getTDE().remove(indexPoint);
						refreshTDE(Model.getInstance().getTownList().get(i).getTDE());
					}
					while(Model.getInstance().getTownList().get(i).getTDE().indexOf(p)!=-1){
						indexPoint = Model.getInstance().getTownList().get(i).getTDE().indexOf(p);
						Model.getInstance().getTownList().get(i).getTDE().remove(indexPoint);
					}
				}
				//-----------------------------------------------------------------------------------------
				//-------------------------crea una nuova città, tessera città continua
				if (indexPoint==-1){
					if(Model.getInstance().getTileFromArray(p.x, p.y).getTownIsContinue()){
					Town nt = new Town();
					Model.getInstance().getTownList().add(nt);
					//inizializza parametri nuova città
					nt.scoreTmp=1;
					if(Model.getInstance().getTileFromArray(p.x, p.y).getShield()==true){
						nt.numShield++;
					}
					nt.townTilePlaced.add(p);
					refreshTDE(nt.getTDE());
					}
				}
			}
				//valutiamo pezzi con due città
			if(Model.getInstance().getTileFromArray(p.x, p.y).getTownIsContinue()==false){
				for (int i =0; i<Model.getInstance().getTownList().size();i++){
					if(Model.getInstance().getTownList().get(i).getTDE().contains(p))
						counter++;
				}	
						switch (counter) {
						case 0://-----------------------------------------------------------------------------------------
							//-----------------aprire 2 nuove città, tessera città discontinua
							if(Model.getInstance().getTileFromArray(p.x, p.y).getNumTown()==2){
								Town nt1 = new Town();
								Town nt2 = new Town();
								Model.getInstance().getTownList().add(nt1);
								Model.getInstance().getTownList().add(nt2);
								//inizializza parametri nuova città
								nt1.scoreTmp=1;
								nt2.scoreTmp=1;
								if(Model.getInstance().getTileFromArray(p.x, p.y).getShield()==true){
									nt1.numShield++;
									nt2.numShield++;
								}
								nt1.townTilePlaced.add(p);
								nt2.townTilePlaced.add(p);
								refreshTDE(nt1.getTDE());
								refreshTDE(nt2.getTDE());
								nt1.getTDE().removeFirst();
								nt2.getTDE().removeLast();
							}
							//-----------------aprire 1 nuova città, tessera città discontinua
							if(Model.getInstance().getTileFromArray(p.x, p.y).getNumTown()==1){
								Town nt = new Town();
								Model.getInstance().getTownList().add(nt);
								//inizializza parametri nuova città
								nt.scoreTmp=1;
								if(Model.getInstance().getTileFromArray(p.x, p.y).getShield()==true){
									nt.numShield++;
								}
								nt.townTilePlaced.add(p);
								refreshTDE(nt.getTDE());
							}
							break;
							
						case 1://-----------------------------------------------------------------------------------------
							//------------------aprire una nuova città e non fare il refresh su quella gia aperta
							//------------------tessera città discontinua
							if(Model.getInstance().getTileFromArray(p.x, p.y).getNumTown()==2){
								for (int i =0; i<Model.getInstance().getTownList().size();i++){
									if(Model.getInstance().getTownList().get(i).getTDE().indexOf(p)!=-1){
										indexPoint = Model.getInstance().getTownList().get(i).getTDE().indexOf(p);
										Model.getInstance().getTownList().get(i).getTTP().add(p);
										Model.getInstance().getTownList().get(i).scoreTmpPlus();
										if(Model.getInstance().getTileFromArray(p.x, p.y).getShield()){
											Model.getInstance().getTownList().get(i).numShieldPlus();
										}
										Model.getInstance().getTownList().get(i).getTDE().remove(indexPoint);
									}
									while(Model.getInstance().getTownList().get(i).getTDE().indexOf(p)!=-1){
										indexPoint = Model.getInstance().getTownList().get(i).getTDE().indexOf(p);
										Model.getInstance().getTownList().get(i).getTDE().remove(indexPoint);
									}
								}
							       //add new town
								Town nt = new Town();
								Model.getInstance().getTownList().add(nt);
								//inizializza parametri nuova città
								nt.scoreTmp=1;
								if(Model.getInstance().getTileFromArray(p.x, p.y).getShield()==true){
									nt.numShield++;
								}
								nt.townTilePlaced.add(p);
								refreshTDE(nt.getTDE());
							}
							//------------------non aprire una nuova città e gestire il pezzo messo, tessera città discontinua
							if(Model.getInstance().getTileFromArray(p.x, p.y).getNumTown()==1){
								for (int i =0; i<Model.getInstance().getTownList().size();i++){
									if(Model.getInstance().getTownList().get(i).getTDE().indexOf(p)!=-1){
										indexPoint = Model.getInstance().getTownList().get(i).getTDE().indexOf(p);
										Model.getInstance().getTownList().get(i).getTTP().add(p);
										Model.getInstance().getTownList().get(i).scoreTmpPlus();
										if(Model.getInstance().getTileFromArray(p.x, p.y).getShield()){
										Model.getInstance().getTownList().get(i).numShieldPlus();
										}
										Model.getInstance().getTownList().get(i).getTDE().remove(indexPoint);
										refreshTDE(Model.getInstance().getTownList().get(i).getTDE());
									}
									while(Model.getInstance().getTownList().get(i).getTDE().indexOf(p)!=-1){
										indexPoint = Model.getInstance().getTownList().get(i).getTDE().indexOf(p);
										Model.getInstance().getTownList().get(i).getTDE().remove(indexPoint);
									}
								}
							}
							break;
						default://-----------------------------------------------------------------------------------------
							//-----------------non aprire nuove città, tessera città discontinua
							for (int i =0; i<Model.getInstance().getTownList().size();i++){
								if(Model.getInstance().getTownList().get(i).getTDE().indexOf(p)!=-1){
									indexPoint = Model.getInstance().getTownList().get(i).getTDE().indexOf(p);
									Model.getInstance().getTownList().get(i).getTTP().add(p);
									Model.getInstance().getTownList().get(i).scoreTmpPlus();
									if(Model.getInstance().getTileFromArray(p.x, p.y).getShield()){
									Model.getInstance().getTownList().get(i).numShieldPlus();
									}
									Model.getInstance().getTownList().get(i).getTDE().remove(indexPoint);
								}
								while(Model.getInstance().getTownList().get(i).getTDE().indexOf(p)!=-1){
									indexPoint = Model.getInstance().getTownList().get(i).getTDE().indexOf(p);
									Model.getInstance().getTownList().get(i).getTDE().remove(indexPoint);
								}
							}
							break;
						}
				}
			}
		}//------------------------------------------END METHOD: addTownOrNewTown------------------------------------------
		
		private void refreshTDE(LinkedList<Point> TDE){
			Point p = new Point();
			Point n = new Point();
			Point w = new Point();
			Point s = new Point();
			Point e = new Point();
			p.x= Model.getInstance().getXUltimoPezzo();
			p.y= Model.getInstance().getYUltimoPezzo();
			int x = p.x;
			int y= p.y;
			Model.getInstance().getTileFromArray(p.x, p.y).resetRotation();
			Model.getInstance().getTileFromArray(p.x, p.y).rotate(Model.getInstance().getRotations(p.x,p.y));
			if(!Model.getInstance().getTileFromArray(p.x, p.y).getTownIsContinue() || Model.getInstance().getTilePlaced()!=0){
				if (Model.getInstance().getTileFromArray(p.x, p.y).getNSide()== Tile.TOWN && 
					 Model.getInstance().getTileFromArray(x-1, y)==null){
					 n.setLocation(x-1, y);
					if(TDE.contains(n)==false)
					TDE.add(n);
				}
				if (Model.getInstance().getTileFromArray(p.x, p.y).getWSide()== Tile.TOWN && 
					 Model.getInstance().getTileFromArray(x, y-1)==null){
					 w.setLocation(x, y-1);
					if(TDE.contains(w)==false)
					TDE.add(w);
				}
				if (Model.getInstance().getTileFromArray(p.x, p.y).getSSide()== Tile.TOWN && 
					 Model.getInstance().getTileFromArray(x+1, y)==null){
					 s.setLocation(x+1, y);
					if(TDE.contains(s)==false)
					TDE.add(s);
				}
				if (Model.getInstance().getTileFromArray(p.x, p.y).getESide()== Tile.TOWN && 
					 Model.getInstance().getTileFromArray(x, y+1)==null){
					 e.setLocation(x, y+1);
					if(TDE.contains(e)==false)
					TDE.add(e);
				}
			}
		}
		
		public void checkCompleteTown(){
			for (int i =0; i<Model.getInstance().getTownList().size();i++){
				if(Model.getInstance().getTownList().get(i).getTDE().isEmpty()){
					if(!Model.getInstance().getTownList().get(i).getPlayerConqueror().isEmpty()){
						Model.getInstance().setScore(
								Model.getInstance().getTownList().get(i).maiorPlayersConqueror(),
								Model.getInstance().getTownList().get(i).totalScoreCompleteTown());
						//restituire seguaci ai giocatori
						for(int k =0;k<Model.getInstance().getTownList().get(i).getPlayerConqueror().size();k++){
							switch (Model.getInstance().getTownList().get(i).getPlayerConqueror().get(k).getFollowerColor()) {
							case Model.P1:
								Model.getInstance().getTownList().get(i).getPlayerConqueror().get(k).resetRotation(Model.getInstance().getTownList().get(i).getPlayerConqueror().get(k).getNumRotation());
								Model.getInstance().getBlueList().add(Model.getInstance().getTownList().get(i).getPlayerConqueror().get(k));
								break;
							case Model.P2:
								Model.getInstance().getTownList().get(i).getPlayerConqueror().get(k).resetRotation(Model.getInstance().getTownList().get(i).getPlayerConqueror().get(k).getNumRotation());
								Model.getInstance().getRedList().add(Model.getInstance().getTownList().get(i).getPlayerConqueror().get(k));
								break;
							case Model.P3:
								Model.getInstance().getTownList().get(i).getPlayerConqueror().get(k).resetRotation(Model.getInstance().getTownList().get(i).getPlayerConqueror().get(k).getNumRotation());
								Model.getInstance().getGreenList().add(Model.getInstance().getTownList().get(i).getPlayerConqueror().get(k));
								break;
							case Model.P4:								
								Model.getInstance().getTownList().get(i).getPlayerConqueror().get(k).resetRotation(Model.getInstance().getTownList().get(i).getPlayerConqueror().get(k).getNumRotation());
								Model.getInstance().getYellowList().add(Model.getInstance().getTownList().get(i).getPlayerConqueror().get(k));
								break;
							}
						}
						new Thread(new Runnable() {
							@Override
							public void run() {
								runTrack();
							}
					    }).start();
						//aggiorna punteggio e n.seguaci a video
						for(int k=0;k<Model.getInstance().getTownList().get(i).getPlayerConqueror().size();k++)
							switch(Model.getInstance().getTownList().get(i).getPlayerConqueror().get(k).getFollowerColor()){	
							case Model.P1:
								MainGUI.jlabPlayer1.setText(Model.getInstance().getNamePlayer1()+": "+ Model.getInstance().getScore(Model.P1));
								MainGUI.jlabFollower1.setText("Followers  :  "+Model.getInstance().getBlueList().size());
								break;
							case Model.P2:
								MainGUI.jlabPlayer2.setText(Model.getInstance().getNamePlayer2()+": "+ Model.getInstance().getScore(Model.P2));
								MainGUI.jlabFollower2.setText("Followers  :  "+Model.getInstance().getRedList().size());
								break;
							case Model.P3:
								MainGUI.jlabPlayer3.setText(Model.getInstance().getNamePlayer3()+": "+ Model.getInstance().getScore(Model.P3));
								MainGUI.jlabFollower3.setText("Followers  :  "+Model.getInstance().getGreenList().size());
								break;
							case Model.P4:								
								MainGUI.jlabPlayer4.setText(Model.getInstance().getNamePlayer4()+": "+ Model.getInstance().getScore(Model.P4));
								MainGUI.jlabFollower4.setText("Followers  :  "+Model.getInstance().getYellowList().size());
								break;		
							}			
						for(int l=0;l<Model.getInstance().getTownList().get(i).getPlayerConqueror().size();l++){
						  Model.getInstance().resetArrayFollowerPlaced(
								  Model.getInstance().getTownList().get(i).getPlayerConqueror().get(l).getCoordinates().x,
								  Model.getInstance().getTownList().get(i).getPlayerConqueror().get(l).getCoordinates().y);
						  mainGUI.repaintTile(Model.getInstance().getTownList().get(i).getPlayerConqueror().get(l).getCoordinates().y,
								  			  Model.getInstance().getTownList().get(i).getPlayerConqueror().get(l).getCoordinates().x);
						}
						Model.getInstance().getTownList().remove(i);
					}else{
						if(Model.getInstance().getTownList().get(i).getTDE().isEmpty()){
								Model.getInstance().getTownList().remove(i);
						}
					}
				}
			}
		}
		
		public void combineTownLists(Point p){
		int indexFirstList=-1;
		boolean found=false;
			for (int i =0; i<Model.getInstance().getTownList().size();){
				if(Model.getInstance().getTownList().get(i).getTDE().indexOf(p)!=-1 && found==false){
					indexFirstList=i;
					found=true;
				}else{
				if(Model.getInstance().getTownList().get(i).getTDE().indexOf(p)!=-1 && found==true){
					for(int j=0;j<Model.getInstance().getTownList().get(i).getTDE().size();j++){
						if(!Model.getInstance().getTownList().get(indexFirstList).getTDE().contains(Model.getInstance().getTownList().get(i).getTDE().get(j)))
						Model.getInstance().getTownList().get(indexFirstList).getTDE().add(
																Model.getInstance().getTownList().get(i).getTDE().get(j));
						}
					Model.getInstance().getTownList().get(indexFirstList).getTTP().addAll(
							Model.getInstance().getTownList().get(i).getTTP());
					Model.getInstance().getTownList().get(indexFirstList).setScoreTmp(
															Model.getInstance().getTownList().get(i).getScoreTmp());
					Model.getInstance().getTownList().get(indexFirstList).setNumShield(
															Model.getInstance().getTownList().get(i).getNumShield());
					Model.getInstance().getTownList().get(indexFirstList).getPlayerConqueror().addAll(
															Model.getInstance().getTownList().get(i).getPlayerConqueror());
					Model.getInstance().getTownList().remove(i);
					i--;			
					}
				}
				i++;
			}
		}
	
		private int getTTPList(Point p){//Restituisce l'indice dell'ultima città che incontra che contiene il punto p passato come parametro
			int index=-1;
		
			for (int i =0; i<Model.getInstance().getTownList().size();i++){
				if(Model.getInstance().getTownList().get(i).getTTP().indexOf(p)!=-1){
					index=i;
				}
			}
			return index;
		}
		
		
	//-----------------------------------------------------------------------------
	//                METODI STRADE
	//-----------------------------------------------------------------------------
		public void addRoadOrNewRoad(){
			int indexPoint = -1;
			int contatore=0;
			Point p = new Point( Model.getInstance().getXUltimoPezzo(),Model.getInstance().getYUltimoPezzo());
			for(int i =0;i < Model.getInstance().getRoadList().size();i++){
				if(Model.getInstance().getRoadList().get(i).getRDE().contains(p)){
					contatore++;
					indexPoint=i;
				}
			}	
			if(Model.getInstance().getTileFromArray(p.x, p.y).isRoad()){
				if(Model.getInstance().getTileFromArray(p.x, p.y).getRoadIsContinue()){//--è continua la strada del pezzo p
					if(indexPoint!=-1){//---esiste almeno una RDE che contiene p
						if(contatore==1){//---è una sola RDE
							Model.getInstance().getRoadList().get(indexPoint).getRTP().add(p);
							Model.getInstance().getRoadList().get(indexPoint).getRDE().remove(p);
							Model.getInstance().getRoadList().get(indexPoint).scoreTmpPlus();
							refreshRDE(Model.getInstance().getRoadList().get(indexPoint).getRDE());
						}
						if (contatore>1){//--- sono più di una
							combineRoadLists(p);
						}
					}else{//--nessuna RDE contiene p
						newRoads();
					}
				}else{//--non è continua la strada del pezzo p
					if(indexPoint!=-1){//--esiste almeno una RDE che contiene p
						for(int i=0;i<Model.getInstance().getRoadList().size();i++){
							if(Model.getInstance().getRoadList().get(i).getRDE().contains(p)){
								Model.getInstance().getRoadList().get(i).getRTP().add(p);
								Model.getInstance().getRoadList().get(i).getRDE().remove(p);
								Model.getInstance().getRoadList().get(i).scoreTmpPlus();
							}
						}
						newRoads();
					}else{//--non c'è nessuna RDE che contiene p
						newRoads();
					}
				}
			}
		}
		
		private void newRoads(){
			Point p = new Point( Model.getInstance().getXUltimoPezzo(),Model.getInstance().getYUltimoPezzo());
			Point n = new Point(Model.getInstance().getXUltimoPezzo()-1,Model.getInstance().getYUltimoPezzo());
			Point w = new Point(Model.getInstance().getXUltimoPezzo(),Model.getInstance().getYUltimoPezzo()-1);
			Point s = new Point(Model.getInstance().getXUltimoPezzo()+1,Model.getInstance().getYUltimoPezzo());
			Point e = new Point(Model.getInstance().getXUltimoPezzo(),Model.getInstance().getYUltimoPezzo()+1);
			if(Model.getInstance().getTileFromArray(p.x, p.y).getRoadIsContinue()){	
				initRoad();
				refreshRDE(Model.getInstance().getRoadList().getFirst().getRDE());
			}else{
				Model.getInstance().getTileFromArray(p.x, p.y).resetRotation();
				Model.getInstance().getTileFromArray(p.x, p.y).rotate(Model.getInstance().getRotations(p.x, p.y));
				
				if(Model.getInstance().getTileFromArray(p.x-1, p.y)==null && Model.getInstance().getTileFromArray(p.x, p.y).getNSide()==Tile.ROAD){
					initRoad();
					Model.getInstance().getRoadList().getFirst().getRDE().add(n);
				}
				if(Model.getInstance().getTileFromArray(p.x, p.y-1)==null && Model.getInstance().getTileFromArray(p.x, p.y).getWSide()==Tile.ROAD){
					initRoad();
					Model.getInstance().getRoadList().getFirst().getRDE().add(w);
				}
				if(Model.getInstance().getTileFromArray(p.x+1, p.y)==null && Model.getInstance().getTileFromArray(p.x, p.y).getSSide()==Tile.ROAD){
					initRoad();
					Model.getInstance().getRoadList().getFirst().getRDE().add(s);
				}
				if(Model.getInstance().getTileFromArray(p.x, p.y+1)==null && Model.getInstance().getTileFromArray(p.x, p.y).getESide()==Tile.ROAD){
					initRoad();
					Model.getInstance().getRoadList().getFirst().getRDE().add(e);
				}
			}
		}
		
		private void initRoad(){
			Point p = new Point( Model.getInstance().getXUltimoPezzo(),Model.getInstance().getYUltimoPezzo());
			Road nr = new Road();
			Model.getInstance().getRoadList().addFirst(nr);
			nr.getRTP().add(p);
			nr.scoreTmpPlus();
		}
		
		private void refreshRDE(LinkedList<Point> RDE){
			Point p = new Point( Model.getInstance().getXUltimoPezzo(),Model.getInstance().getYUltimoPezzo());
			Point n = new Point(Model.getInstance().getXUltimoPezzo()-1,Model.getInstance().getYUltimoPezzo());
			Point w = new Point(Model.getInstance().getXUltimoPezzo(),Model.getInstance().getYUltimoPezzo()-1);
			Point s = new Point(Model.getInstance().getXUltimoPezzo()+1,Model.getInstance().getYUltimoPezzo());
			Point e = new Point(Model.getInstance().getXUltimoPezzo(),Model.getInstance().getYUltimoPezzo()+1);
			Model.getInstance().getTileFromArray(p.x, p.y).resetRotation();
			Model.getInstance().getTileFromArray(p.x, p.y).rotate(Model.getInstance().getRotations(p.x,p.y));
			if(Model.getInstance().getTilePlaced()!=0){
				if(Model.getInstance().getTileFromArray(p.x, p.y).getRoadIsContinue()==true){
					if (Model.getInstance().getTileFromArray(p.x, p.y).getNSide()== Tile.ROAD && 
						Model.getInstance().getTileFromArray(n.x, n.y)==null){
						 if(RDE.contains(n)==false)
						 RDE.add(n);
					}
					if (Model.getInstance().getTileFromArray(p.x, p.y).getWSide()== Tile.ROAD && 
						Model.getInstance().getTileFromArray(w.x, w.y)==null){
						 if(RDE.contains(w)==false)
						 RDE.add(w);
					}
					if (Model.getInstance().getTileFromArray(p.x, p.y).getSSide()== Tile.ROAD && 
						Model.getInstance().getTileFromArray(s.x, s.y)==null){
						 if(RDE.contains(s)==false)
						 RDE.add(s);
					}
					if (Model.getInstance().getTileFromArray(p.x, p.y).getESide()== Tile.ROAD && 
					  	Model.getInstance().getTileFromArray(e.x, e.y)==null){
						 if(RDE.contains(e)==false)
						 RDE.add(e);
					}
				}
			}
		}
		
		public void checkCompleteRoad(){
			for(int i=0;i< Model.getInstance().getRoadList().size();i++){
				if(Model.getInstance().getRoadList().get(i).getRDE().isEmpty()){
					if(!Model.getInstance().getRoadList().get(i).getPlayerConqueror().isEmpty()){
						Model.getInstance().setScore(
								Model.getInstance().getRoadList().get(i).maiorPlayersConqueror(),
								Model.getInstance().getRoadList().get(i).getScore());
						//restituire seguaci ai giocatori
						for(int k =0;k<Model.getInstance().getRoadList().get(i).getPlayerConqueror().size();k++){
							switch (Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(k).getFollowerColor()) {
							case Model.P1:
								Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(k).resetRotation(Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(k).getNumRotation());
								Model.getInstance().getBlueList().add(Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(k));
								break;
							case Model.P2:
								Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(k).resetRotation(Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(k).getNumRotation());
								Model.getInstance().getRedList().add(Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(k));
								break;
							case Model.P3:
								Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(k).resetRotation(Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(k).getNumRotation());
								Model.getInstance().getGreenList().add(Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(k));
								break;
							case Model.P4:								
								Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(k).resetRotation(Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(k).getNumRotation());
								Model.getInstance().getYellowList().add(Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(k));
								break;
							}
						}
						//aggiornare punteggio a video
						for(int k=0;k<Model.getInstance().getRoadList().get(i).getPlayerConqueror().size();k++)
							switch(Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(k).getFollowerColor()){	
							case Model.P1:
								MainGUI.jlabPlayer1.setText(Model.getInstance().getNamePlayer1()+": "+ Model.getInstance().getScore(Model.P1));
								MainGUI.jlabFollower1.setText("Followers  :  "+Model.getInstance().getBlueList().size());
								break;
							case Model.P2:
								MainGUI.jlabPlayer2.setText(Model.getInstance().getNamePlayer2()+": "+ Model.getInstance().getScore(Model.P2));
								MainGUI.jlabFollower2.setText("Followers  :  "+Model.getInstance().getRedList().size());
								break;
							case Model.P3:
								MainGUI.jlabPlayer3.setText(Model.getInstance().getNamePlayer3()+": "+ Model.getInstance().getScore(Model.P3));
								MainGUI.jlabFollower3.setText("Followers  :  "+Model.getInstance().getGreenList().size());
								break;
							case Model.P4:								
								MainGUI.jlabPlayer4.setText(Model.getInstance().getNamePlayer4()+": "+ Model.getInstance().getScore(Model.P4));
								MainGUI.jlabFollower4.setText("Followers  :  "+Model.getInstance().getYellowList().size());
								break;		
							}
						new Thread(new Runnable() {	
							@Override
							public void run() {
								runTrack();
							}
					    }).start();
							for(int f=0;f<Model.getInstance().getRoadList().get(i).getPlayerConqueror().size();f++){
								  Model.getInstance().resetArrayFollowerPlaced(
										  Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(f).getCoordinates().x,
										  Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(f).getCoordinates().y);
								  mainGUI.repaintTile(Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(f).getCoordinates().y,
													  Model.getInstance().getRoadList().get(i).getPlayerConqueror().get(f).getCoordinates().x);
							}
								Model.getInstance().getRoadList().remove(i);
						}else{
							if(Model.getInstance().getRoadList().get(i).getRDE().isEmpty()){
									Model.getInstance().getRoadList().remove(i);
						}
					}
				}
			}
		}
		
		public void combineRoadLists(Point p){
			int indexFirstList=-1;
			boolean found=false;
			for (int i =0; i<Model.getInstance().getRoadList().size();i++){
				if(Model.getInstance().getRoadList().get(i).getRDE().indexOf(p)!=-1 && found==false){
					indexFirstList=i;
					found=true;
				}else{
				if(Model.getInstance().getRoadList().get(i).getRDE().indexOf(p)!=-1 && found==true){
					for(int j=0;j<Model.getInstance().getRoadList().get(i).getRDE().size();j++){
						if(!Model.getInstance().getRoadList().get(indexFirstList).getRDE().contains(Model.getInstance().getRoadList().get(i).getRDE().get(j))){
							Model.getInstance().getRoadList().get(indexFirstList).getRDE().add(Model.getInstance().getRoadList().get(i).getRDE().get(j));
						}
					}
					Model.getInstance().getRoadList().get(indexFirstList).getRTP().addAll(
							Model.getInstance().getRoadList().get(i).getRTP());
					Model.getInstance().getRoadList().get(indexFirstList).setScoreTmp(
							Model.getInstance().getRoadList().get(i).getScore());
					Model.getInstance().getRoadList().get(indexFirstList).getPlayerConqueror().addAll(
							Model.getInstance().getRoadList().get(i).getPlayerConqueror());
					Model.getInstance().getRoadList().get(indexFirstList).getRTP().add(p);
					Model.getInstance().getRoadList().get(indexFirstList).scoreTmpPlus();
					Model.getInstance().getRoadList().remove(i);
					}
				}
			}
			while (Model.getInstance().getRoadList().get(indexFirstList).getRDE().contains(p)){
				Model.getInstance().getRoadList().get(indexFirstList).getRDE().remove(p);
			}
		}
		
		private int getRTPList(Point p, Point pNear){ // restituisce l'indice dell'ultima lista incontrata che contiene p
			int index=-1;
			for (int i =0; i<Model.getInstance().getRoadList().size();i++){
				if(Model.getInstance().getRoadList().get(i).getRTP().contains(p)	){
					index=i;
				}
			}
			for (int i =0; i<Model.getInstance().getRoadList().size();i++){
				if(Model.getInstance().getRoadList().get(i).getRTP().contains(p) &&
						Model.getInstance().getRoadList().get(i).getRDE().contains(pNear)	){
					index=i;
				}
			}
			return index;
		}
		
		
	//-----------------------------------------------------------------------------
	//                METODI MONASTERI
	//-----------------------------------------------------------------------------
		public void newChurch(){
			if(Model.getInstance().getPreviousTile().getChurch()==true){
				Model.getInstance().getChurchList().addFirst(new Church());
				Point church = new Point(Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo());
					for(int i= church.x-1;i<= church.x+1;i++){
						for(int j= church.y-1;j<= church.y+1;j++){
							if(Model.getInstance().getTileFromArray(i, j)!=null){
								Model.getInstance().getChurchList().getFirst().getCTP().add(new Point(i, j));
								Model.getInstance().getChurchList().getFirst().scoreTmpPlus();
							}else{
								Model.getInstance().getChurchList().getFirst().getCDE().add(new Point(i, j));
							}
						}
					}
			}
		}
	
		public void addTileInChurchDomine(){
			Point lastTilePlaced= new Point(Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo());
			if(!Model.getInstance().getChurchList().isEmpty()){ // se la lista monasteri non è vuota
				for(int i =0; i<Model.getInstance().getChurchList().size();i++){
					for(int j =0; j<Model.getInstance().getChurchList().get(i).getCDE().size();j++){
						if(Model.getInstance().getChurchList().get(i).getCDE().get(j).equals(lastTilePlaced)){
							Model.getInstance().getChurchList().get(i).getCTP().add(lastTilePlaced);
							Model.getInstance().getChurchList().get(i).scoreTmpPlus();
							Model.getInstance().getChurchList().get(i).getCDE().remove(lastTilePlaced);
						}
					}
				}
			}
		}
		
		public void checkCompleteChurch(){
			for(int i =0; i<Model.getInstance().getChurchList().size();i++){
				if(Model.getInstance().getChurchList().get(i).getCDE().isEmpty()){
					if(Model.getInstance().getChurchList().get(i).getPlayerConqueror().isEmpty()==false){
						Model.getInstance().setScore(Model.getInstance().getChurchList().get(i).getPlayersConqueror(), 9);
						//restituire seguaci ai giocatori
						switch (Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst().getFollowerColor()) {
						case Model.P1:
							Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst().resetRotation(Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst().getNumRotation());
							Model.getInstance().getBlueList().add(Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst());
							break;
						case Model.P2:
							Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst().resetRotation(Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst().getNumRotation());
							Model.getInstance().getRedList().add(Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst());
							break;
						case Model.P3:
							Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst().resetRotation(Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst().getNumRotation());
							Model.getInstance().getGreenList().add(Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst());
							break;
						case Model.P4:								
							Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst().resetRotation(Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst().getNumRotation());
							Model.getInstance().getYellowList().add(Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst());
							break;
						}
						//aggiorna punteggio a video
						for(int l=0;l<Model.getInstance().getChurchList().size();l++)
							switch(Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst().getFollowerColor()){	
							case Model.P1:
								MainGUI.jlabPlayer1.setText(Model.getInstance().getNamePlayer1()+": "+ Model.getInstance().getScore(Model.P1));
								MainGUI.jlabFollower1.setText("Followers  :  "+Model.getInstance().getBlueList().size());
								break;
							case Model.P2:
								MainGUI.jlabPlayer2.setText(Model.getInstance().getNamePlayer2()+": "+ Model.getInstance().getScore(Model.P2));
								MainGUI.jlabFollower2.setText("Followers : "+Model.getInstance().getRedList().size());
								break;
							case Model.P3:
								MainGUI.jlabPlayer3.setText(Model.getInstance().getNamePlayer3()+": "+ Model.getInstance().getScore(Model.P3));
								MainGUI.jlabFollower3.setText("Followers  :  "+Model.getInstance().getGreenList().size());
								break;
							case Model.P4:								
								MainGUI.jlabPlayer4.setText(Model.getInstance().getNamePlayer4()+": "+ Model.getInstance().getScore(Model.P4));
								MainGUI.jlabFollower4.setText("Followers  :  "+Model.getInstance().getYellowList().size());
								break;
							}
						new Thread(new Runnable() {
							@Override
							public void run() {
								runTrack();
							}
					    }).start();
						 Model.getInstance().resetArrayFollowerPlaced(
									  Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst().getCoordinates().x,
									  Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst().getCoordinates().y);
						mainGUI.repaintTile(Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst().getCoordinates().y,
											Model.getInstance().getChurchList().get(i).getPlayerConqueror().getFirst().getCoordinates().x);
						Model.getInstance().getChurchList().remove(i);
					}else{
						Model.getInstance().getChurchList().remove(i);
					}
				}
			}
			
		}
		
	//-----------------------------------------------------------------------------
	//                METODI END GAME
	//-----------------------------------------------------------------------------
		private void residualTownPoints(){
			for (int i =0; i<Model.getInstance().getTownList().size();i++){
					if(!Model.getInstance().getTownList().get(i).getPlayerConqueror().isEmpty()){
						Model.getInstance().setScore(
								Model.getInstance().getTownList().get(i).maiorPlayersConqueror(),
								Model.getInstance().getTownList().get(i).totalScoreIncompleteTown());
					}
			}
		}
		
		private void residualRoadPoints(){
			for(int i=0;i< Model.getInstance().getRoadList().size();i++){
				if(!Model.getInstance().getRoadList().get(i).getPlayerConqueror().isEmpty()){
						Model.getInstance().setScore(
								Model.getInstance().getRoadList().get(i).maiorPlayersConqueror(),
								Model.getInstance().getRoadList().get(i).getScore());
				}
			}
		}
			
		private void residualChurchPoints(){
			for(int i =0; i<Model.getInstance().getChurchList().size();i++){
				if(Model.getInstance().getChurchList().get(i).getPlayerConqueror().isEmpty()==false){
					Model.getInstance().setScore(Model.getInstance().getChurchList().get(i).getPlayersConqueror(), 9);
				}
			}
		}
			
		public String winnerOrDraw(){
			String winner = "";
			switch (Model.getInstance().getNumPlayer()) {
			case 2:
				if(Model.getInstance().getScore(Model.P1)!=Model.getInstance().getScore(Model.P2)){
					if (Model.getInstance().getScore(Model.P1)<Model.getInstance().getScore(Model.P2))
						winner = Model.getInstance().getNamePlayer2()+" wins!";
					else 
						winner = Model.getInstance().getNamePlayer1()+" wins!";
				}
				else 
						winner = "Tie ! ";
				break;
			case 3:
				winner =  "Tie ! ";
				if(Model.getInstance().getScore(Model.P1)!=Model.getInstance().getScore(Model.P2)
						|| Model.getInstance().getScore(Model.P1)!=Model.getInstance().getScore(Model.P3) 
						|| Model.getInstance().getScore(Model.P2)!=Model.getInstance().getScore(Model.P3)){
					if(Model.getInstance().getScore(Model.P1) > Math.max(Model.getInstance().getScore(Model.P2), Model.getInstance().getScore(Model.P3)))
						winner =  Model.getInstance().getNamePlayer1()+" wins!";
					else if (Model.getInstance().getScore(Model.P2) > Math.max(Model.getInstance().getScore(Model.P1), Model.getInstance().getScore(Model.P3)))
						winner =  Model.getInstance().getNamePlayer2()+" wins!";
					else if (Model.getInstance().getScore(Model.P3) > Math.max(Model.getInstance().getScore(Model.P1), Model.getInstance().getScore(Model.P2)))
						winner =  Model.getInstance().getNamePlayer3()+" wins!";
					break;
				}
			case 4:
				winner = "Tie ! ";
				if(Model.getInstance().getScore(Model.P1)!=Model.getInstance().getScore(Model.P2)
				|| Model.getInstance().getScore(Model.P1)!=Model.getInstance().getScore(Model.P3) 
				|| Model.getInstance().getScore(Model.P1)!=Model.getInstance().getScore(Model.P4)
				|| Model.getInstance().getScore(Model.P2)!=Model.getInstance().getScore(Model.P3) 
				|| Model.getInstance().getScore(Model.P2)!=Model.getInstance().getScore(Model.P4)
				|| Model.getInstance().getScore(Model.P3)!=Model.getInstance().getScore(Model.P4)){
					if(Model.getInstance().getScore(Model.P1) > Math.max(Model.getInstance().getScore(Model.P2), Model.getInstance().getScore(Model.P3))
							&& Model.getInstance().getScore(Model.P1) > Math.max(Model.getInstance().getScore(Model.P3), Model.getInstance().getScore(Model.P4)))
						winner =  Model.getInstance().getNamePlayer1()+" wins!";
					else if(Model.getInstance().getScore(Model.P2) > Math.max(Model.getInstance().getScore(Model.P1), Model.getInstance().getScore(Model.P3))
							&& Model.getInstance().getScore(Model.P2) > Math.max(Model.getInstance().getScore(Model.P1), Model.getInstance().getScore(Model.P4)))
						winner =  Model.getInstance().getNamePlayer2()+" wins!";
					else if(Model.getInstance().getScore(Model.P3) > Math.max(Model.getInstance().getScore(Model.P1), Model.getInstance().getScore(Model.P2))
							&& Model.getInstance().getScore(Model.P3) > Math.max(Model.getInstance().getScore(Model.P1), Model.getInstance().getScore(Model.P4)))
						winner =  Model.getInstance().getNamePlayer3()+" wins!";
					else if(Model.getInstance().getScore(Model.P4) > Math.max(Model.getInstance().getScore(Model.P1), Model.getInstance().getScore(Model.P2))
							&& Model.getInstance().getScore(Model.P4) > Math.max(Model.getInstance().getScore(Model.P1), Model.getInstance().getScore(Model.P3)))
						winner =  Model.getInstance().getNamePlayer4()+" wins!";
				}	
				break;
			}
			return winner;	
		}
		
		public void endGame(){
			if(Model.getInstance().getSizeOfTileList()==Model.getInstance().getTilePlaced()){
				residualChurchPoints();
				residualRoadPoints();
				residualTownPoints();
				ViewControllerForModel.getInstance().openEndGame();
			}
		}
		

	//-----------------------------------------------------------------------------
	//                METODI ECCEZIONI
	//-----------------------------------------------------------------------------
		 
		public void setImpossibleTilePlaced(boolean Boolean){
			this.impossibleTilePlaced=Boolean;
		}
				
		public boolean getImpossibleTilePlaced(){
			return this.impossibleTilePlaced;
		}
		
	//-------------------------------------------------------------------------
	// OPEN-CLOSE METHODS
	//-------------------------------------------------------------------------	

		public void openStartWindow() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (startWindow == null)
					startWindow = new StartWindow();
				startWindow.setVisible(true);
			}
		});
	}

	@Override
		public void closeStartWindow() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (startWindow != null)
					startWindow.setVisible(false);
			}
		});
		
		
	}
	
		public void openTutorial() {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (tutorialWindow == null)
						tutorialWindow = new TutorialWindow();
					tutorialWindow.setVisible(true);
				}
			});
		}

	@Override
		public void closeTutorial() {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (tutorialWindow != null)
						tutorialWindow.setVisible(false);
				}
			});
		}

	@Override
		public void openNewGameWindow() {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (newGameWindow == null)
						newGameWindow = new NewGameWindow();
					newGameWindow.setVisible(true);
				}
			});
		}

	@Override
		public void closeNewGameWindow() {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (newGameWindow != null)
						newGameWindow.setVisible(false);
				}
			});
		}

	@Override
		public void openMainGUI() {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (mainGUI == null)
						mainGUI = new MainGUI();
					mainGUI.setVisible(true);				
					new Thread(new Runnable() {
						@Override
						public void run() {
							runTrack();	
						}
				    }).start();
				}
			});	
		}
		
		@Override
		public void closeMainGUI() {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (mainGUI != null)
						System.exit(0);
												
				}
			});
		}
	
		public void openEndGame() {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (endgame == null)
						endgame = new EndGame();
					endgame.setVisible(true);
					new Thread(new Runnable() {
						@Override
						public void run() {
							runTrack();
						}
				    }).start();
				}
			});
		}
	
	//-------------------------------------------------------------------------
	// MESSAGE METHODS
	//-------------------------------------------------------------------------
	    
		public String openFile(){
			String file = null;
			if(this.jfileChooser==null)
				this.jfileChooser=new JFileChooser();
			int returnVal=jfileChooser.showOpenDialog(startWindow);
			if(returnVal==JFileChooser.APPROVE_OPTION)
				file=jfileChooser.getSelectedFile().getAbsolutePath();
			return file;
		}
		
		public String saveFile() {
			String file = null;
			if (this.jfileChooser == null)
				this.jfileChooser = new JFileChooser();
			int returnVal = jfileChooser.showSaveDialog(startWindow);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (jfileChooser.getSelectedFile().exists()) {
					Object[] options = {"Yes", "No"};
					int n = JOptionPane.showOptionDialog(jfileChooser,
								"The selected file already exists.\n" +
								"Do you want to overwrite it?",
								"Warning",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								options,
								options[1]);
					if (n == JOptionPane.YES_OPTION)
						file = jfileChooser.getSelectedFile().getAbsolutePath();
				}
				else
					file = jfileChooser.getSelectedFile().getAbsolutePath();
			}
			return file;
		}	
		
		public void showExitMessage() {
			int n = JOptionPane.showConfirmDialog(this.mainGUI,
					"Would you like to save this match?",
					"Confirm Dialog",
					JOptionPane.YES_OPTION);
			if (n == JOptionPane.YES_OPTION)
				saveGame();
			else if (n == JOptionPane.NO_OPTION)
				closeMainGUI();		
				
		}
	
		public void showCannotFindFileMessage(String file) {
			JOptionPane.showMessageDialog(startWindow,
				"Cannot find file: " + file,
				"Input/Output Error",
				JOptionPane.ERROR_MESSAGE);
		}
	
		public void showCannotReadFileMessage(String file) {
			JOptionPane.showMessageDialog(startWindow,
				"Cannot open file: " + file,
				"Input/Output Error",
				JOptionPane.ERROR_MESSAGE);
		}
	
		public void showCannotWriteFileMessage(String file) {
			JOptionPane.showMessageDialog(startWindow,
				"Cannot write file: " + file,
				"Input/Output Error",
				JOptionPane.ERROR_MESSAGE);
		}
	
		public void saveGame() {
			String file = null;
			file = saveFile();
			if (file != null) {
				try {
					Model.getInstance().saveModelToFile(file);
					System.exit(0);
				}
				catch(IOException ioe) {
					showCannotWriteFileMessage(file);
				}
			}		
		}
	
	//-------------------------------------------------------------------------
	// AUDIO METHODS
	//-------------------------------------------------------------------------
		    
		private void runTrack(){
			 if(Model.getInstance().getTilePlaced()==1)
		        playSound(fileLocation);
			 else if (Model.getInstance().getTilePlaced() == 72)
				 playSound(endLocation);
			 else
				 playSound(fileLocation2);
		    }

	    private void playSound(String fileName){

	    	java.net.URL url = getClass().getResource(fileName);
	        AudioInputStream audioInputStream = null;
	        try 
	        {
	            audioInputStream = AudioSystem.getAudioInputStream(url);
	        } 
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        AudioFormat audioFormat = audioInputStream.getFormat();
	        SourceDataLine line = null;
	        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
	        try 
	        {
	            line = (SourceDataLine) AudioSystem.getLine(info);
	            line.open(audioFormat);
	        } 
	        catch (LineUnavailableException e) 
	        {
	            e.printStackTrace();
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	        line.start();
	        int nBytesRead = 0;
	        byte[] abData = new byte[128000];
	        while (nBytesRead != -1) 
	        {
	            try 
	            {
	                nBytesRead = audioInputStream.read(abData, 0, abData.length);
	            } 
	            catch (IOException e) 
	            {
	                e.printStackTrace();
	            }
	            if (nBytesRead >= 0) 
	            {
	            	@SuppressWarnings("unused")
	                int nBytesWritten = line.write(abData, 0, nBytesRead);
	            }
	        }
	        line.drain();
	        line.close();
	    }

    //-------------------------------------------------------------------------
    // STATIC METHODS
    //-------------------------------------------------------------------------
    
		public static IViewControllerForModel getInstance(){
    	if (instance == null)
    		instance = new ViewControllerForModel();
    	return instance;
		}
} // end class
	
