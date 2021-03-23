package jarcassonne.model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import jarcassonne.model.Model;

public class InputOutput {
	private static int index=0;
	private static int pointer=0;
	private static int indexExpation=0;
	private static int indexTown=0;
	private static int indexRoad=0;
	private static int indexChurch=0;
	private static int placedDimention=0;
	private static int expantionDimention=0;
	private static int followerDimention=0;
	private static int x=0;
	private static int y=0;

	

		public InputOutput(){}

			public static void stampa(){
				try{

					FileWriter fileout = new FileWriter("FileStampato.txt");
					BufferedWriter filebuf = new BufferedWriter(fileout);
					PrintWriter printout = new PrintWriter(filebuf);
			
					printout.println(Model.getInstance().toString());
					printout.close();
				}
				catch (Exception e){
					System.out.println("Errore :"+e);
				}
			}
			
			public static void saveModelToFile(String file, IModel model) throws IOException {
                    try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "ISO-8859-1")))) {
                        printWriter.print(model.toString());
                        printWriter.flush();
                    }
			}
			
			
			public static void loadModelFromFile(String file, IModel model) throws IOException{
				BufferedReader buffRead = new BufferedReader(new InputStreamReader(new FileInputStream(file),"ISO-8859-1"));
				String s =null;
				while((s=buffRead.readLine())!=null){
					if(!s.isEmpty() && !s.startsWith("#") && (s.indexOf("=")>0 ))
						parseLineAndUpdateModel(s.split("="),model);
				}
				buffRead.close();
			}

			private static void parseLineAndUpdateModel(String[] sSplit,IModel model) {
				System.out.println("split");
				
				
				if(sSplit[0].trim().toLowerCase().equals("num players"))
					model.setNumPlayer(Integer.parseInt(sSplit[1].trim()));
				//----nomi giocatori
				else if(sSplit[0].trim().toLowerCase().equals("p1name"))
					model.setNamePlayer1(sSplit[1].trim());
				else if(sSplit[0].trim().toLowerCase().equals("p2name"))
					model.setNamePlayer2(sSplit[1].trim());
				else if(sSplit[0].trim().toLowerCase().equals("p3name"))
					model.setNamePlayer3(sSplit[1].trim());
				else if(sSplit[0].trim().toLowerCase().equals("p4name"))
					model.setNamePlayer4(sSplit[1].trim());
				
				//----punteggi
				else if(sSplit[0].trim().toLowerCase().equals("p1score"))
					model.setScore(1, Integer.parseInt(sSplit[1].trim()));
				else if(sSplit[0].trim().toLowerCase().equals("p2score"))
					model.setScore(2, Integer.parseInt(sSplit[1].trim()));
				else if(sSplit[0].trim().toLowerCase().equals("p3score"))
					model.setScore(3, Integer.parseInt(sSplit[1].trim()));
				else if(sSplit[0].trim().toLowerCase().equals("p4score"))
					model.setScore(4, Integer.parseInt(sSplit[1].trim()));
				
				//----tile placed-num player
				else if(sSplit[0].trim().toLowerCase().equals("placed tile number")){
					model.setTilePlaced(Integer.parseInt(sSplit[1].trim()));
					model.getTilePlacedCoordinates().clear();
					model.getExpantionMapCoordinates().clear();
					model.getTownList().clear();
					model.getTownList().add(new Town());
					model.getRoadList().clear();
					model.getRoadList().add(new Road());
					model.getChurchList().clear();
					model.getChurchList().add(new Church());
				}
				else if(sSplit[0].trim().toLowerCase().equals("num players"))
					model.setTilePlaced(Integer.parseInt(sSplit[1].trim()));
				else if(sSplit[0].trim().toLowerCase().equals("getxultimopezzo"))
					model.setXUltimoPezzo(Integer.parseInt(sSplit[1].trim()));
				else if(sSplit[0].trim().toLowerCase().equals("getyultimopezzo"))
					model.setYUltimoPezzo(Integer.parseInt(sSplit[1].trim()));
				else if(sSplit[0].trim().toLowerCase().equals("currentplayer"))
					model.setCurrentPlayer(Integer.parseInt(sSplit[1].trim()));
				else if(sSplit[0].trim().toLowerCase().equals("annull")){
					if(sSplit[1].trim().equals("true"))
						model.annul();
					}
				else if(sSplit[0].trim().toLowerCase().equals("first step")){
					if(sSplit[1].trim().equals("true")){
						model.firstStepStart();
					}else{
						model.firstStepEnd();
						}
					}
			
				else if(sSplit[0].trim().toLowerCase().equals(""+index+"tile")){
					 System.out.println("loadTile----"+sSplit[0].trim().toLowerCase());
					 model.loadTileListFromFile(sSplit[1].trim().toLowerCase(), index);
					 index++;
					 System.out.println("loadTile----if");
				 }
				
				else if(sSplit[0].trim().toLowerCase().equals(""+pointer+"tcp.x"))
						x = (Integer.parseInt(sSplit[1].trim()));
				else if(sSplit[0].trim().toLowerCase().equals(""+pointer+"tcp.y")){
						y = (Integer.parseInt(sSplit[1].trim()));
						model.getTilePlacedCoordinates().add(new Point(x, y));
						 System.out.println("add");
					}
					else if(sSplit[0].trim().toLowerCase().equals(""+pointer+"tile")){
						model.setTileInArray(model.getTileList().get(pointer), x, y);
					}
					else if(sSplit[0].trim().toLowerCase().equals(""+pointer+"rotations")){
						model.setRotations((Integer.parseInt(sSplit[1].trim())), x, y);
						pointer++;
					}
					else if(sSplit[0].trim().toLowerCase().equals(""+(pointer-1)+"color")){
						
						switch ((Integer.parseInt(sSplit[1].trim()))) {
						case 1:
							model.setArrayFollowerPlaced(model.getBlueList().getFirst(), y, x);
							model.getBlueList().getFirst().setCoordinates(y, x);
							model.getBlueList().removeFirst();
							break;
						case 2:
							model.setArrayFollowerPlaced(model.getRedList().getFirst(), y, x);
							model.getRedList().getFirst().setCoordinates(y, x);
							model.getRedList().removeFirst();
							break;
						case 3:
							model.setArrayFollowerPlaced(model.getGreenList().getFirst(), y, x);
							model.getGreenList().getFirst().setCoordinates(y, x);
							model.getGreenList().removeFirst();
							break;
						case 4:
							model.setArrayFollowerPlaced(model.getYellowList().getFirst(), y, x);
							model.getYellowList().getFirst().setCoordinates(y, x);
							model.getYellowList().removeFirst();
							break;

						}
					}
					else if(sSplit[0].trim().toLowerCase().equals(""+(pointer-1)+"followeronside")){
							model.getFollowerFromArray(y, x).setFollowerOnSide(Integer.parseInt(sSplit[1].trim()));
							model.getFollowerFromArray(y, x).rotate(((Integer.parseInt(sSplit[1].trim()))%4)+1);
							 System.out.println("follower "+Integer.parseInt(sSplit[1].trim()));
					}
				else if(sSplit[0].trim().toLowerCase().equals(""+indexExpation+"emc.x"))
						x = (Integer.parseInt(sSplit[1].trim()));
				else if(sSplit[0].trim().toLowerCase().equals(""+indexExpation+"emc.y")){
						y = (Integer.parseInt(sSplit[1].trim()));
						model.getExpantionMapCoordinates().add(new Point(x, y));
						 System.out.println("add");
					}
				else if(sSplit[0].trim().toLowerCase().equals(""+indexExpation+"possiblecell")){
					if(sSplit[1].trim().equals("false"))
						model.setFalseInArrayPossibleCell(x, y);
					indexExpation++;
				}
				//------------------------------load town-----------------------------------------------------
				else if(sSplit[0].trim().toLowerCase().equals("citta"+indexTown+"ttp.x"+ placedDimention)){
					x = (Integer.parseInt(sSplit[1].trim()));
				}
				else if(sSplit[0].trim().toLowerCase().equals("citta"+indexTown+"ttp.y"+ placedDimention)){
					y = (Integer.parseInt(sSplit[1].trim()));
					model.getTownList().getFirst().getTTP().add(new Point(x, y));
					placedDimention++;
				}
				else if(sSplit[0].trim().toLowerCase().equals("citta"+indexTown+"tde.x"+ expantionDimention)){
					x = (Integer.parseInt(sSplit[1].trim()));
					
				}
				else if(sSplit[0].trim().toLowerCase().equals("citta"+indexTown+"tde.y"+ expantionDimention)){
					y = (Integer.parseInt(sSplit[1].trim()));
					model.getTownList().getFirst().getTDE().add(new Point(x, y));
					expantionDimention++;
				}
				else if(sSplit[0].trim().toLowerCase().equals("citta"+indexTown+"playerconqueror.x"+followerDimention)){
					x = (Integer.parseInt(sSplit[1].trim()));
				}
				else if(sSplit[0].trim().toLowerCase().equals("citta"+indexTown+"playerconqueror.y"+followerDimention)){
					y = (Integer.parseInt(sSplit[1].trim()));
					
				}
				else if(sSplit[0].trim().toLowerCase().equals("citta"+indexTown+"playerconqueror"+followerDimention)){
					model.getTownList().getFirst().getPlayerConqueror().addFirst(model.getFollowerFromArray(x, y));
					followerDimention++;
				}
				
				
				else if(sSplit[0].trim().toLowerCase().equals("citta"+indexTown+"numshield")){
					model.getTownList().getFirst().setNumShield(Integer.parseInt(sSplit[1].trim()));
					System.out.println("scudi caticati");
				}
				else if(sSplit[0].trim().toLowerCase().equals("citta"+indexTown+"scoretmp")){
					model.getTownList().getFirst().setScoreTmp(Integer.parseInt(sSplit[1].trim()));
				
					placedDimention=0;
					expantionDimention=0;
					followerDimention=0;
					model.getTownList().addFirst(new Town());
					indexTown=indexTown+1;
				}
			
			//------------------------------load road-----------------------------------------------------
			else if(sSplit[0].trim().toLowerCase().equals("strada"+indexRoad+"rtp.x"+ placedDimention)){
				x = (Integer.parseInt(sSplit[1].trim()));
			}
			else if(sSplit[0].trim().toLowerCase().equals("strada"+indexRoad+"rtp.y"+ placedDimention)){
				y = (Integer.parseInt(sSplit[1].trim()));
				model.getRoadList().getFirst().getRTP().add(new Point(x, y));
				placedDimention++;
			}
			else if(sSplit[0].trim().toLowerCase().equals("strada"+indexRoad+"rde.x"+ expantionDimention)){
				x = (Integer.parseInt(sSplit[1].trim()));
				
			}
			else if(sSplit[0].trim().toLowerCase().equals("strada"+indexRoad+"rde.y"+ expantionDimention)){
				y = (Integer.parseInt(sSplit[1].trim()));
				model.getRoadList().getFirst().getRDE().add(new Point(x, y));
				expantionDimention++;
			}
			else if(sSplit[0].trim().toLowerCase().equals("strada"+indexRoad+"playerconqueror.x"+followerDimention)){
				x = (Integer.parseInt(sSplit[1].trim()));
			}
			else if(sSplit[0].trim().toLowerCase().equals("strada"+indexRoad+"playerconqueror.y"+followerDimention)){
				y = (Integer.parseInt(sSplit[1].trim()));
			}
			else if(sSplit[0].trim().toLowerCase().equals("strada"+indexRoad+"playerconqueror"+followerDimention)){
					model.getRoadList().getFirst().getPlayerConqueror().addFirst(model.getFollowerFromArray(x, y));
				followerDimention++;	
			}
			else if(sSplit[0].trim().toLowerCase().equals("strada"+indexRoad+"score")){
				model.getRoadList().getFirst().setScoreTmp(Integer.parseInt(sSplit[1].trim()));
			
				placedDimention=0;
				expantionDimention=0;
				followerDimention=0;
				model.getRoadList().addFirst(new Road());
				indexRoad=indexRoad+1;
			}
			//------------------------------load church-----------------------------------------------------
			else if(sSplit[0].trim().toLowerCase().equals("monastero"+indexChurch+"ctp.x"+ placedDimention)){
				x = (Integer.parseInt(sSplit[1].trim()));
			}
			else if(sSplit[0].trim().toLowerCase().equals("monastero"+indexChurch+"ctp.y"+ placedDimention)){
				y = (Integer.parseInt(sSplit[1].trim()));
				model.getChurchList().getFirst().getCTP().add(new Point(x, y));
				placedDimention++;
			}
			else if(sSplit[0].trim().toLowerCase().equals("monastero"+indexChurch+"cde.x"+ expantionDimention)){
				x = (Integer.parseInt(sSplit[1].trim()));
				
			}
			else if(sSplit[0].trim().toLowerCase().equals("monastero"+indexChurch+"cde.y"+ expantionDimention)){
				y = (Integer.parseInt(sSplit[1].trim()));
				model.getChurchList().getFirst().getCDE().add(new Point(x, y));
				expantionDimention++;
			}
			else if(sSplit[0].trim().toLowerCase().equals("monastero"+indexChurch+"playerconqueror.x")){
				x = (Integer.parseInt(sSplit[1].trim()));
			}
			else if(sSplit[0].trim().toLowerCase().equals("monastero"+indexChurch+"playerconqueror.y")){
				y = (Integer.parseInt(sSplit[1].trim()));
			}
			else if(sSplit[0].trim().toLowerCase().equals("monastero"+indexChurch+"playerconqueror")){
					model.getChurchList().getFirst().getPlayerConqueror().addFirst(model.getFollowerFromArray(x, y));
				followerDimention++;
			}
			else if(sSplit[0].trim().toLowerCase().equals("monastero"+indexChurch+"score")){
				model.getChurchList().getFirst().setScoreTmp(Integer.parseInt(sSplit[1].trim()));
			
				placedDimention=0;
				expantionDimention=0;
				followerDimention=0;
				model.getChurchList().addFirst(new Church());
				indexChurch=indexChurch+1;
			}
		}
}
			
		


