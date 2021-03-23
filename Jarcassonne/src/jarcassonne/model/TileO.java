package jarcassonne.model;

import jarcassonne.viewController.NewGameWindow;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class TileO extends Tile {

	TileO() throws URISyntaxException{
		this.tileName = "TileO";
		this.fileName = "bg011.png";
		this.side = new int[4];
		this.numTownInCurrentTile=1;		
		this.side[0]=TOWN;
		this.side[1]=ROAD;
		this.side[2]=ROAD;
		this.side[3]=TOWN;		
		this.townIsContinue=true;
		this.roadIsContinue=true;
		this.isShield=true;
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
