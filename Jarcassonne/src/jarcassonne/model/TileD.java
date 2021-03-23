package jarcassonne.model;

import jarcassonne.viewController.NewGameWindow;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class TileD extends Tile {
	
	//TESSERA INIZIALE

	TileD() throws URISyntaxException{
		this.tileName = "TileD";
		this.fileName = "bg021.png";
		this.side = new int[4];
		this.numTownInCurrentTile=1;		
		this.side[0]=TOWN;
		this.side[1]=ROAD;
		this.side[2]=FIELD;
		this.side[3]=ROAD;		
		this.townIsContinue=false;
		this.roadIsContinue=true;
		this.isShield=false;
		this.isChurch=false;
		
		//-------------------ACQUISIZIONE FILE IMMAGINE----------------------------------------
				try{
					//metodo che trasforma un file in una bufferedImage
						img = ImageIO.read(NewGameWindow.class.getResourceAsStream("/jarcassonne/img/Tessere_gioco/"+this.fileName));
					}
						catch(IOException e){
							System.out.println("error:file TILE path"); 				
					}	
	}
}
