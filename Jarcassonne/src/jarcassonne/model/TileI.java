package jarcassonne.model;

import jarcassonne.viewController.NewGameWindow;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class TileI extends Tile {

	TileI() throws URISyntaxException{
		this.tileName = "TileI";
		this.fileName = "bg014.png";
		this.side = new int[4];
		this.numTownInCurrentTile=2;
		
		this.side[0]=TOWN;
		this.side[1]=FIELD;
		this.side[2]=FIELD;
		this.side[3]=TOWN;
		
		this.townIsContinue=false;
		this.roadIsContinue=false;
		this.isShield=false;
		this.isChurch=false;
		
		try{
			//metodo che trasforma un file in una bufferedImage
			img = ImageIO.read(NewGameWindow.class.getResourceAsStream("/jarcassonne/img/Tessere_gioco/"+this.fileName));
		}
		catch(IOException e){
		}
	}
}