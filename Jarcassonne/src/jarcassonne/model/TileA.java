package jarcassonne.model;


import jarcassonne.viewController.NewGameWindow;
import java.io.IOException;
import java.net.URISyntaxException;




import javax.imageio.ImageIO;



public class TileA extends Tile {
	
	TileA() throws URISyntaxException{
		this.tileName = "TileA";
		this.fileName = "bg002.png";	
		this.side = new int[4];		
		this.side[0]=FIELD;
		this.side[1]=FIELD;
		this.side[2]=ROAD;
		this.side[3]=FIELD;
		this.townIsContinue=false;
		this.roadIsContinue=false;
		this.isShield=false;
		this.isChurch=true;
		
		
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
