package jarcassonne.model;

import jarcassonne.viewController.NewGameWindow;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class TileR extends Tile {

	TileR() throws URISyntaxException{
		this.tileName = "TileR";
		this.fileName = "bg004.png";
		this.side = new int[4];
		this.numTownInCurrentTile=1;	
		this.side[0]=TOWN;
		this.side[1]=TOWN;
		this.side[2]=FIELD;
		this.side[3]=TOWN;
		this.townIsContinue=true;
		this.roadIsContinue=false;
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