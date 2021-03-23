package jarcassonne.model;

import jarcassonne.viewController.NewGameWindow;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class TileW extends Tile {

	TileW() throws URISyntaxException{
		this.tileName = "TileW";
		this.fileName = "bg027.png";
		this.side = new int[4];
		this.side[0]=FIELD;
		this.side[1]=ROAD;
		this.side[2]=ROAD;
		this.side[3]=ROAD;
		this.townIsContinue=false;
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