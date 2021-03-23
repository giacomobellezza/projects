package jarcassonne.model;

import jarcassonne.viewController.NewGameWindow;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class TileU extends Tile {

	TileU() throws URISyntaxException{
		this.tileName = "TileU";
		this.fileName = "bg023.png";
		this.side = new int[4];		
		this.side[0]=ROAD;
		this.side[1]=FIELD;
		this.side[2]=ROAD;
		this.side[3]=FIELD;		
		this.townIsContinue=false;
		this.roadIsContinue=true;
		this.isShield=false;
		this.isChurch=false;
		
		//-------------------ACQUISIZIONE FILE IMMAGINE----------------------------------------
				try{
					//metodo che trasforma un file in una bufferedImage
						img = ImageIO.read(NewGameWindow.class.getResourceAsStream("/jarcassonne/img/Tessere_gioco/"+this.fileName));
					//img=ImageIO.read(new File("/home/giacomo/jarcassonne/Jarcassonne/src/jarcassonne/Tessere_gioco/"+fileName));
					}
						catch(IOException e){
							System.out.println("error:file TILE path"); 				
						}	
	}
}