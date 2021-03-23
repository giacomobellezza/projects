package jarcassonne.viewController;

import jarcassonne.model.Follower;
import jarcassonne.model.Model;
import jarcassonne.model.Tile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Cursor;
import java.awt.Point;




public class MainGUI extends JFrame {

	public final int DIMENTION_PIXEL_TILE = 100;
	private MapPanel mapPanel;
	public JPanel previewPanel;

	public static JLabel jlabPlayer1;
	public static JLabel jlabPlayer2;
	public static JLabel jlabPlayer3;
	public static JLabel jlabPlayer4;
	
	public static JLabel jlabFollower1;
	public static JLabel jlabFollower2;
	public static JLabel jlabFollower3;
	public static JLabel jlabFollower4;
	
	private JButton jbutSalva;
	private JButton jbutConferma;	
	private JButton jbutSalta;
	private JButton jbutAnnulla;
	
	private Rectangle2D.Double rect;
	
	public boolean pezzoPiazzato;
	public boolean seguacePiazzato;
	protected boolean resized;
	
	protected int driftVectorX;
	protected int driftVectorY;
	protected int dx;
	protected int dy;
	public int mouseClickX;
	public int mouseClickY;

	protected String fileName;
	protected BufferedImage img;
	protected Cursor cursore;
	protected Cursor defaultCursor;
	public boolean released;
	protected boolean startGame;

	private ImageIcon image; 
	private ImageIcon unipg;
	
	
	
	public MainGUI(){
		super("Click to START");
		Model.getInstance().firstStepStart();
		this.dx=0;
		this.dy=0;
		this.driftVectorX=50;
		this.driftVectorY=50;
		this.mouseClickX=0;
		this.mouseClickY=0;
		this.pezzoPiazzato=false;
		this.seguacePiazzato=false;
		this.startGame=true;
		initComponents();
		setLocationRelativeTo(null);
		this.fileName = "FollowerCursor.png";
		this.defaultCursor = Cursor.getDefaultCursor();
		try {
			img = ImageIO.read(NewGameWindow.class.getResourceAsStream("/jarcassonne/img/Seguaci/FollowerCursor.png"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Toolkit t = Toolkit.getDefaultToolkit();
		this.cursore = t.createCustomCursor(img,new Point(0,0),"cur");
		scrollArrow();
		setIconImage(unipg.getImage());
		addComponentListener(new ComponentAdapter() 
		{  
		        public void componentResized(ComponentEvent evt) {
		            resized = true;		          
		        }
		});
	}
	
	private void initComponents() {		
        mapPanel = new MapPanel();
        jbutSalva = new javax.swing.JButton();
        jbutSalta = new javax.swing.JButton();
        jbutConferma = new javax.swing.JButton();
        jbutAnnulla = new javax.swing.JButton();
        previewPanel = new javax.swing.JPanel();
        jlabPlayer1 = new javax.swing.JLabel();
        jlabPlayer2 = new javax.swing.JLabel();
        jlabPlayer3 = new javax.swing.JLabel();
        jlabPlayer4 = new javax.swing.JLabel();
        jlabFollower1 = new javax.swing.JLabel();
        jlabFollower2 = new javax.swing.JLabel();
        jlabFollower3 = new javax.swing.JLabel();
        jlabFollower4 = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
               ViewControllerForModel.getInstance().showExitMessage();              
            }            
          });
        Dimension ss=Toolkit.getDefaultToolkit().getScreenSize();
        ss.height=ss.height-20;
        ss.width=ss.width-50;
        setPreferredSize(ss);
        setMinimumSize(new Dimension(800,600));
        this.image = new javax.swing.ImageIcon(getClass().getResource("/jarcassonne/img/freccia.gif"));
        this.unipg = new javax.swing.ImageIcon(getClass().getResource("/jarcassonne/img/logo.gif"));    
        javax.swing.GroupLayout mapPanelLayout = new javax.swing.GroupLayout(mapPanel);
        mapPanelLayout.setHorizontalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1239, Short.MAX_VALUE)
        );
        mapPanelLayout.setVerticalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
       jbutSalva.setText("SAVE");
       jbutSalva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewControllerForModel.getInstance().saveGame();
            }
        });
        javax.swing.GroupLayout previewPanelLayout = new javax.swing.GroupLayout(previewPanel);
        previewPanel.setLayout(previewPanelLayout);
        previewPanelLayout.setHorizontalGroup(
            previewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        previewPanelLayout.setVerticalGroup(
            previewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jlabPlayer1.setForeground(new java.awt.Color(0, 0, 255));
        jlabPlayer1.setText(Model.getInstance().getNamePlayer1()+": "+ Model.getInstance().getScore(Model.P1));
        jlabPlayer1.setFont(new Font("Courier", Font.BOLD, 12));
       
        jlabPlayer2.setForeground(new java.awt.Color(255, 0, 0));
        jlabPlayer2.setText(Model.getInstance().getNamePlayer2()+": "+ Model.getInstance().getScore(Model.P2));
        jlabPlayer2.setFont(new Font("Courier", Font.BOLD, 12));
        
        jlabPlayer3.setForeground(new java.awt.Color(102, 153, 0));
        jlabPlayer3.setText(Model.getInstance().getNamePlayer3()+": "+ Model.getInstance().getScore(Model.P3));
        jlabPlayer3.setFont(new Font("Courier", Font.BOLD, 12));
        
        jlabPlayer4.setForeground(new java.awt.Color(255, 153, 0));
        jlabPlayer4.setText(Model.getInstance().getNamePlayer4()+": "+ Model.getInstance().getScore(Model.P4));
        jlabPlayer4.setFont(new Font("Courier", Font.BOLD, 12));
        
        jlabFollower1.setForeground(new java.awt.Color(0, 0, 255));
        jlabFollower1.setText("Followers  :  "+Model.getInstance().getBlueList().size());

        jlabFollower2.setForeground(new java.awt.Color(255, 0, 0));
        jlabFollower2.setText("Followers  :  "+Model.getInstance().getRedList().size());

        jlabFollower3.setForeground(new java.awt.Color(102, 153, 0));
        jlabFollower3.setText("Followers  :  "+Model.getInstance().getGreenList().size());

        jlabFollower4.setForeground(new java.awt.Color(255, 153, 0));
        jlabFollower4.setText("Followers  :  "+Model.getInstance().getYellowList().size());

        jbutAnnulla.setText("UNDO");
        
        jbutSalta.setText("SKIP");
        
        jbutConferma.setText("CONFIRM");

        jbutAnnulla.addActionListener(new ActionListener() {
			@Override
				public void actionPerformed(ActionEvent e){
					if(resized==false){
						if(Model.getInstance().getFirstStep()==true){
							if(pezzoPiazzato==true)	{
								
								if (Model.getInstance().getAnnul()==false){
									Model.getInstance().setTileInArray(null,Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo());		
									Model.getInstance().TilePlacedMinus();
									Model.getInstance().annul();
									refreshPreviewTile();
									mapPanel.paintPossibleCell();
									pezzoPiazzato=false;
									Model.getInstance().setXUltimoPezzo( Model.getInstance().getTilePlacedCoordinates().getLast().x);
									Model.getInstance().setYUltimoPezzo( Model.getInstance().getTilePlacedCoordinates().getLast().y);
																		
								}
							}
						}
					}
				}
		});
		
		jbutSalta.addActionListener(new ActionListener() {
			@Override
				public void actionPerformed(ActionEvent e){
				if(resized==false){
					if (Model.getInstance().getFirstStep()==false)
						if( Model.getInstance().getTilePlaced()<Model.getInstance().getSizeOfTileList()){
							refreshPreviewTile();
							mapPanel.paintPossibleCell();
							if(ViewControllerForModel.getInstance().getImpossibleTilePlaced()==true){
								refreshPreviewTile();
								ViewControllerForModel.getInstance().setImpossibleTilePlaced(false);
							}
							Model.getInstance().firstStepStart();
							mapPanel.setCursor(defaultCursor);
							released=false;
							scrollArrow();
							ViewControllerForModel.getInstance().checkCompleteTown();
							ViewControllerForModel.getInstance().checkCompleteRoad();
							ViewControllerForModel.getInstance().checkCompleteChurch();
						}else{
							mapPanel.setCursor(defaultCursor);
							ViewControllerForModel.getInstance().closeMainGUI();
							ViewControllerForModel.getInstance().endGame();
						}
				}
			}
		});
		
		jbutConferma.addActionListener(new ActionListener() {
			@Override
				public void actionPerformed(ActionEvent e){
				if(resized==false){
					if(Model.getInstance().getFirstStep()==true){
						if (pezzoPiazzato==true && seguacePiazzato==false && released==false){
							if(Model.getInstance().getTilePlaced()!=0){		
									pezzoPiazzato=false;
									Model.getInstance().refreshMapList();
									Model.getInstance().setFalseInArrayPossibleCell(Model.getInstance().getXUltimoPezzo(), Model.getInstance().getYUltimoPezzo());
									Model.getInstance().firstStepEnd();
									mapPanel.setCursor(cursore);		
									ViewControllerForModel.getInstance().addTownOrNewTown();
									ViewControllerForModel.getInstance().addRoadOrNewRoad();
							}
							ViewControllerForModel.getInstance().newChurch();
							ViewControllerForModel.getInstance().addTileInChurchDomine();
						}
					}
				}
			}
		});
       
      
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlabPlayer2)
                            .addComponent(jlabFollower2)
                            .addComponent(jlabPlayer3)
                            .addComponent(jlabFollower3)
                            .addComponent(jlabPlayer4)
                            .addComponent(jlabFollower4)
                            .addComponent(jlabFollower1)
                            .addComponent(jlabPlayer1))
                        .addGap(5, 5, 5))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(previewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbutSalva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbutSalta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbutConferma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbutAnnulla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(previewPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbutAnnulla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jbutConferma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbutSalta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jlabPlayer1)
                .addGap(5, 5, 5)
                .addComponent(jlabFollower1)
                .addGap(45, 45, 45)
                .addComponent(jlabPlayer2)
                .addGap(5, 5, 5)
                .addComponent(jlabFollower2)
                .addGap(40, 40, 40)
                .addComponent(jlabPlayer3)
                .addGap(5, 5, 5)
                .addComponent(jlabFollower3)
                .addGap(40, 40, 40)
                .addComponent(jlabPlayer4)
                .addGap(5, 5, 5)
                .addComponent(jlabFollower4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addComponent(jbutSalva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        
        if (Model.getInstance().getNumPlayer()==3){
        	jlabPlayer4.setVisible(false);
        	jlabFollower4.setVisible(false);
        }
        
        if (Model.getInstance().getNumPlayer()==2){
        	jlabPlayer3.setVisible(false);
        	jlabFollower3.setVisible(false);
        	jlabPlayer4.setVisible(false);
        	jlabFollower4.setVisible(false);
        }
       pack();
    }// </editor-fold>  
	
	public void refreshPreviewTile(){
		try{
		Model.getInstance().getCurrentTile().paint(this.previewPanel.getGraphics());
		}catch(Exception e){	
		}
	}
	
	public void paintWhiteOnPreview(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		this.rect = new Rectangle2D.Double();
		rect.setRect(0, 0, DIMENTION_PIXEL_TILE, DIMENTION_PIXEL_TILE);
		g2d.setColor(Color.white); // contorno
		g2d.fill(this.rect);	   // interno
		g2d.setColor(Color.gray);
		g2d.draw(this.rect);
	}
	
	public void paintWhite(Graphics g,int x,int y){
		Graphics2D g2d = (Graphics2D)g;
		this.rect = new Rectangle2D.Double();
		rect.setRect((y-1)*DIMENTION_PIXEL_TILE+10, (x-1)*DIMENTION_PIXEL_TILE+10, DIMENTION_PIXEL_TILE, DIMENTION_PIXEL_TILE);
		try{
		g2d.setColor(Color.white); // contorno
		g2d.fill(this.rect);	   // interno
		}catch(NullPointerException e){
			//do-nothing
		}
	}
	
	public void paintRect(Graphics g,Color color,int x, int y, int dx, int dy ){
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(this.rect);		
		rect.setRect(dx, dy, x, y);
		try{		
			g2d.setColor(color); // contorno
			g2d.draw(this.rect); // interno
		}catch(NullPointerException e){
			//do-nothing
		}
	}

	public void repaintFollowerLabel(){
		switch (Model.getInstance().getCurrentPlayer()) {
		case Model.P1:
			MainGUI.jlabFollower1.setText("Followers  :  "+Model.getInstance().getBlueList().size());
			break;
		case Model.P2:
			MainGUI.jlabFollower2.setText("Followers  :  "+Model.getInstance().getRedList().size());
			break;
		case Model.P3:
			MainGUI.jlabFollower3.setText("Followers  :  "+Model.getInstance().getGreenList().size());
			break;
		case Model.P4:
			MainGUI.jlabFollower4.setText("Followers  :  "+Model.getInstance().getYellowList().size());
			break;		
			}
	}
	
	public void scrollArrow(){
		switch(Model.getInstance().getCurrentPlayer()){
		case Model.P1:
			jlabFollower1.setIcon(null);
			jlabFollower2.setHorizontalTextPosition(JLabel.LEFT);
			jlabFollower2.setIcon(image);
			break;
		case Model.P2:
			jlabFollower2.setIcon(null);
			if(Model.getInstance().getNumPlayer()==2){
				jlabFollower1.setHorizontalTextPosition(JLabel.LEFT);
				jlabFollower1.setIcon(image);
				}
			if(Model.getInstance().getNumPlayer()>2){
				jlabFollower3.setHorizontalTextPosition(JLabel.LEFT);
				jlabFollower3.setIcon(image);
			}
			break;
		case Model.P3:
			jlabFollower3.setIcon(null);
			if(Model.getInstance().getNumPlayer()==3){
				jlabFollower1.setHorizontalTextPosition(JLabel.LEFT);
				jlabFollower1.setIcon(image);
				}
			if(Model.getInstance().getNumPlayer()>3){
				jlabFollower4.setHorizontalTextPosition(JLabel.LEFT);
				jlabFollower4.setIcon(image);
			}
			break;
		case Model.P4:		
			jlabFollower4.setIcon(null);
			jlabFollower1.setHorizontalTextPosition(JLabel.LEFT);
			jlabFollower1.setIcon(image);
			break;
		}
	}

	public void repaintTile(int x, int y){
		Model.getInstance().getTileFromArray(x, y).resetRotation();
		Model.getInstance().getTileFromArray(x, y).rotate(Model.getInstance().getRotations(x, y));		
		Model.getInstance().getTileFromArray(x, y).paintTile(mapPanel.getGraphics(), (x-1+dy-driftVectorY)*DIMENTION_PIXEL_TILE+10, (y-1+dx-driftVectorX)*DIMENTION_PIXEL_TILE+10);
	}
	
	public JPanel getPreviewPanel(){
		return this.previewPanel;
	}
	
	public void systemOut(){
		System.out.println("__________________________________________________________________________________________________________");
		System.out.println("_________________________________FINE TURNO: "+(Model.getInstance().getTilePlaced()-1)+" _____________________________________________");
		System.out.println("__________________________________________________________________________________________________________");
		System.out.println("");
		System.out.println("x ultimo pezzo: "+Model.getInstance().getXUltimoPezzo());
		System.out.println("y ultimo pezzo: "+Model.getInstance().getYUltimoPezzo());	
		System.out.println("");
		int y =0;
		for (int i =0; i<Model.getInstance().getTownList().size();i++){
			for (int j=0; j<Model.getInstance().getTownList().get(i).getTTP().size();j++){
		System.out.println("	TownTilePlaced città n." + i + ":"+Model.getInstance().getTownList().get(i).getTTP().get(j));
			}}
		for (int i =0; i<Model.getInstance().getTownList().size();i++){
			for (int j=0; j<Model.getInstance().getTownList().get(i).getTDE().size();j++){
				y++;
		System.out.println("TownDirectionExpandable città n." + i+ ", "+y+ ":"+Model.getInstance().getTownList().get(i).getTDE().get(j));
			}}
		System.out.println("****************************");
		for (int i =0; i<Model.getInstance().getRoadList().size();i++){
			for (int j=0; j<Model.getInstance().getRoadList().get(i).getRTP().size();j++){
		System.out.println("	RoadTilePlaced strada n." + i + ":"+Model.getInstance().getRoadList().get(i).getRTP().get(j));
			}
			if(!Model.getInstance().getRoadList().get(i).getPlayerConqueror().isEmpty())
			System.out.println("strada 	COLORE FOLLOWER " + i + ":"+Model.getInstance().getRoadList().get(i).getPlayerConqueror().getFirst().getFollowerColor());}
		for (int i =0; i<Model.getInstance().getRoadList().size();i++){
			for (int j=0; j<Model.getInstance().getRoadList().get(i).getRDE().size();j++){
		System.out.println("RoadDirectionExpandable strada n."+i+":"+Model.getInstance().getRoadList().get(i).getRDE().get(j));
			}}
		System.out.println("****************************");
		for(int i =0; i<Model.getInstance().getChurchList().size();i++){
			for (int k=0;k<Model.getInstance().getChurchList().get(i).getCTP().size();k++){
				System.out.println("	ChurchTilePlaced monastero n."+i+": "+Model.getInstance().getChurchList().get(i).getCTP().get(k));
			}
			for (int k=0;k<Model.getInstance().getChurchList().get(i).getCDE().size();k++){
				System.out.println("ChurchDirectionExpandable monastero n."+i+": "+Model.getInstance().getChurchList().get(i).getCDE().get(k));
			}
		}
		System.out.println("****************************");
		System.out.println("punti : \n"+
				"player1: "+Model.getInstance().getScore(1)+"\n"+
				"player2: "+Model.getInstance().getScore(2)+"\n"+
				"player3: "+Model.getInstance().getScore(3)+"\n"+
				"player4: "+Model.getInstance().getScore(4)+"\n");
		
	}
	
	//----------------------------------
	// INNER CLASS
	//----------------------------------
		
		public class MapPanel extends JPanel implements MouseInputListener, ComponentListener {
		
			private final static int X_MARGIN=10;
			private final static int Y_MARGIN=10;
			private boolean showGrid;
			private int NUM_COLONNE; //numero di caselle da 100 px che entrano sulla schermata
			private int NUM_RIGHE;
			
			private Line2D.Double line;	
			
			protected boolean dragged;
			protected boolean endGame;
			private boolean sG;
			protected JLabel jl;
			protected ImageIcon img2;
			
			protected int deltaX;
			protected int deltaY;
			protected int clickX;
			protected int clickY;
			
			
			public MapPanel(){
				super();
				this.showGrid=false;
				this.line= new Line2D.Double();
				addMouseListener(this);
				addMouseMotionListener(this);
				addComponentListener(this);
				setBackground(Color.white);
				this.endGame=false;
				this.sG=false;
				this.img2 = new javax.swing.ImageIcon(getClass().getResource("/jarcassonne/img/play.png"));
		        this.jl = new JLabel(img2);
		        add(this.jl);
			}
			
			public void setGridUnit() {
				this.NUM_COLONNE = (int)((getWidth() - 2 * X_MARGIN) / DIMENTION_PIXEL_TILE);
				this.NUM_RIGHE = (int)((getHeight() - 2 * Y_MARGIN) / DIMENTION_PIXEL_TILE);
			}
			
			public void repaintMap() {
				paintWhiteAll();
				if(pezzoPiazzato==false && Model.getInstance().getFirstStep())
					paintPossibleCell();
				for(int i=0;i<Model.getInstance().getTilePlacedCoordinates().size();i++){
					int x=Model.getInstance().getTilePlacedCoordinates().get(i).x;
					int y=Model.getInstance().getTilePlacedCoordinates().get(i).y;
							Model.getInstance().getTileFromArray(x, y).resetRotation();
							Model.getInstance().getTileFromArray(x, y).rotate(Model.getInstance().getRotations(x, y));					
							Model.getInstance().getTileFromArray(x, y).paintTile(super.getGraphics(), (x-1+dy-driftVectorY)*DIMENTION_PIXEL_TILE+X_MARGIN, (y-1+dx-driftVectorX)*DIMENTION_PIXEL_TILE+Y_MARGIN);	
							if(((x-1+dy-driftVectorY)*DIMENTION_PIXEL_TILE+X_MARGIN)<X_MARGIN || ((y-1+dx-driftVectorX)*DIMENTION_PIXEL_TILE+Y_MARGIN)<Y_MARGIN || (mapPanel.getHeight()-((x-1+dy-driftVectorY)*DIMENTION_PIXEL_TILE+X_MARGIN))<DIMENTION_PIXEL_TILE || mapPanel.getWidth()-((y-1+dx-driftVectorX)*DIMENTION_PIXEL_TILE+Y_MARGIN)<DIMENTION_PIXEL_TILE){
								paintRed(mapPanel.getGraphics(), x+dy-driftVectorY, y+dx-driftVectorX);
							}
				}
				int x=Model.getInstance().getXUltimoPezzo();
				int y=Model.getInstance().getYUltimoPezzo();
					Model.getInstance().getTileFromArray(x, y).resetRotation();
					Model.getInstance().getTileFromArray(x, y).rotate(Model.getInstance().getRotations(x, y));					
					Model.getInstance().getTileFromArray(x, y).paintTile(super.getGraphics(), (x-1+dy-driftVectorY)*DIMENTION_PIXEL_TILE+X_MARGIN, (y-1+dx-driftVectorX)*DIMENTION_PIXEL_TILE+Y_MARGIN);	
				if(((x-1+dy-driftVectorY)*DIMENTION_PIXEL_TILE+X_MARGIN)<X_MARGIN || ((y-1+dx-driftVectorX)*DIMENTION_PIXEL_TILE+Y_MARGIN)<Y_MARGIN || (mapPanel.getHeight()-((x-1+dy-driftVectorY)*DIMENTION_PIXEL_TILE+X_MARGIN))<DIMENTION_PIXEL_TILE || mapPanel.getWidth()-((y-1+dx-driftVectorX)*DIMENTION_PIXEL_TILE+Y_MARGIN)<100){
						paintRed(mapPanel.getGraphics(), x+dy-driftVectorY, y+dx-driftVectorX);
				}
				repaintFollower();
			}
			
			public void paintWhiteAll(){			
				setBackground(Color.white);
				paintComponent(super.getGraphics());
			}
			
			public void paintGray(Graphics g,int x,int y){
				Graphics2D g2d = (Graphics2D)g;
				rect = new Rectangle2D.Double();
				rect.setRect((y-1)*DIMENTION_PIXEL_TILE+Y_MARGIN, (x-1)*DIMENTION_PIXEL_TILE+X_MARGIN, DIMENTION_PIXEL_TILE, DIMENTION_PIXEL_TILE);
				try{
				g2d.setColor(Color.lightGray); // contorno
				g2d.fill(rect);	   // interno
				rect.setRect((y-1)*DIMENTION_PIXEL_TILE+20, (x-1)*DIMENTION_PIXEL_TILE+20, 80, 80);
				g2d.setColor(Color.white);
				g2d.fill(rect);
				}catch(NullPointerException e){
					//do-nothing
				}
			}
			public void paintRed(Graphics g,int x,int y){
				Graphics2D g2d = (Graphics2D)g;
				rect = new Rectangle2D.Double();
				rect.setRect((y-1)*DIMENTION_PIXEL_TILE+Y_MARGIN, (x-1)*DIMENTION_PIXEL_TILE+X_MARGIN, 98, 98);
				try{
				g2d.setColor(Color.RED); // contorno
				g2d.draw(rect);	   // interno
				rect.setRect((y-1)*DIMENTION_PIXEL_TILE+15, (x-1)*DIMENTION_PIXEL_TILE+15, 88, 88);
				g2d.draw(rect);
				}catch(NullPointerException e){
					//do-nothing
				}
			}

			public void paintTileOutOfPanel(int x,int y){
				if(x<X_MARGIN || y<Y_MARGIN || (mapPanel.getHeight()-x)<(mapPanel.getHeight()-X_MARGIN)%DIMENTION_PIXEL_TILE || (mapPanel.getWidth()-y)<(mapPanel.getWidth()-Y_MARGIN)%DIMENTION_PIXEL_TILE){
					paintRed(mapPanel.getGraphics(), mouseClickY+1, mouseClickX+1);
				}
			}
			
			public void paintPossibleCell(){
				boolean compatibleCell;
				if(Model.getInstance().getTilePlaced()!=0){
					try{
					Tile previewTile= Tile.createPreviewTile(Model.getInstance().getCurrentTile().getName());
					Model.getInstance().possibleCell(previewTile);
					}catch(Exception e){
						//do-nothing
					}
					for(int i=0;i<Model.getInstance().getExpantionMapCoordinates().size();i++){
						int x=Model.getInstance().getExpantionMapCoordinates().get(i).x;
						int y=Model.getInstance().getExpantionMapCoordinates().get(i).y;
							compatibleCell=Model.getInstance().getArrayPossibleCell(x, y);
							if(compatibleCell==true)
								paintGray(getGraphics(), x+dy-driftVectorY, y+dx-driftVectorX);
							if(compatibleCell==false && (
								Model.getInstance().getTileFromArray(x+1, y)!=null ||
								Model.getInstance().getTileFromArray(x, y+1)!=null ||
								Model.getInstance().getTileFromArray(x-1, y)!=null ||
								Model.getInstance().getTileFromArray(x, y-1)!=null))
								paintWhite(getGraphics(), x+dy-driftVectorY, y+dx-driftVectorY);
					}
				}
			}
			
			public void repaintFollower(){
				int side=0;
				for(int i=0;i<Model.getInstance().getTilePlacedCoordinates().size();i++){
						int x=Model.getInstance().getTilePlacedCoordinates().get(i).y;
						int y=Model.getInstance().getTilePlacedCoordinates().get(i).x;
						if(Model.getInstance().getFollowerFromArray(x, y)!=null){
						side = Model.getInstance().getFollowerFromArray(x, y).getFollowerOnSide();
						if(side==Follower.NORTH)
						Model.getInstance().getFollowerFromArray(x, y).paintFollower(getGraphics(), (y-1+dy-driftVectorY)*DIMENTION_PIXEL_TILE+10, (x-1+dx-driftVectorX)*DIMENTION_PIXEL_TILE+40);
						if(side==Follower.EAST)
							Model.getInstance().getFollowerFromArray(x, y).paintFollower(getGraphics(), (y-1+dy-driftVectorY)*DIMENTION_PIXEL_TILE+40, (x-1+dx-driftVectorX)*DIMENTION_PIXEL_TILE+70);
						if(side==Follower.SOUTH)
							Model.getInstance().getFollowerFromArray(x, y).paintFollower(getGraphics(), (y-1+dy-driftVectorY)*DIMENTION_PIXEL_TILE+70, (x-1+dx-driftVectorX)*DIMENTION_PIXEL_TILE+40);
						if(side==Follower.WEST)
							Model.getInstance().getFollowerFromArray(x, y).paintFollower(getGraphics(), (y-1+dy-driftVectorY)*DIMENTION_PIXEL_TILE+40, (x-1+dx-driftVectorX)*DIMENTION_PIXEL_TILE+10);
						if(side==Follower.CENTRE)
							Model.getInstance().getFollowerFromArray(x, y).paintFollower(getGraphics(), (y-1+dy-driftVectorY)*DIMENTION_PIXEL_TILE+40, (x-1+dx-driftVectorX)*DIMENTION_PIXEL_TILE+40);
						}	
					}
			}

			// Returns the column index of the selected pixel.
			// Returns -1 if the selected pixel does not belong to the matrix (grid).
			public int getRowIndex(int y) {
				int i = 0;
				i = (int)((double)(y - Y_MARGIN)/DIMENTION_PIXEL_TILE)+1;
				if (y < Y_MARGIN)
				i=-1;
				else if (i> NUM_RIGHE+1)
				i = -1;
				return i;
			}

			public int getColumnIndex(int x) {
				int j = 0;
				j = (int)((double)(x - X_MARGIN) / DIMENTION_PIXEL_TILE)+1;
				if (x < X_MARGIN)
				j=-1;
				else if ( j > (NUM_COLONNE+1))
				j = -1;
				return j;
			}
		
			private void paintGrid(Graphics2D g2d) {
				Color oldColor = g2d.getColor();
				g2d.setColor(Color.gray);
				// Paint the horizontal grid lines
				for (int i = 0; i <= NUM_RIGHE; i++) {
					this.line.setLine(X_MARGIN, Y_MARGIN + i * DIMENTION_PIXEL_TILE,
									  X_MARGIN + NUM_COLONNE*DIMENTION_PIXEL_TILE , Y_MARGIN + i * DIMENTION_PIXEL_TILE);
					g2d.draw(this.line);
				}
				// Paint the vertical grid lines
				for (int j = 0; j <= NUM_COLONNE; j++) {
					this.line.setLine(X_MARGIN + j * DIMENTION_PIXEL_TILE, Y_MARGIN,
							          X_MARGIN + j * DIMENTION_PIXEL_TILE, Y_MARGIN + NUM_RIGHE*DIMENTION_PIXEL_TILE);
					g2d.draw(this.line);
				}
				g2d.setColor(oldColor);				
			} // end method paintGrid()
		
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				// Insert here our drawing
				Graphics2D g2d = (Graphics2D)g;
				if (this.showGrid)
					paintGrid(g2d);
				this.setGridUnit();
			}
			
		//--------------------------------------
		//   MOUSE EVENT
		//--------------------------------------

		@Override
		public void mousePressed(MouseEvent e) {
			clickX=(e.getX()-X_MARGIN)/DIMENTION_PIXEL_TILE-dx;
			clickY=(e.getY()-Y_MARGIN)/DIMENTION_PIXEL_TILE-dy;
			mouseClickX=(e.getX()-X_MARGIN)/DIMENTION_PIXEL_TILE;
			mouseClickY=(e.getY()-Y_MARGIN)/DIMENTION_PIXEL_TILE;
			
			if(startGame==true){
				startGame=false;
                setTitle("JARCASSONNE ENJOY!");
				refreshPreviewTile();
				jl.setVisible(false);
				resized=false;
				sG=true;
			}else if (resized==true){
				resized=false;
				repaintMap();
				refreshPreviewTile();
			}else{			
			ViewControllerForModel.getInstance().mousePressed(e,
															  dx, dy, driftVectorX, driftVectorY ,
															  this, cursore, defaultCursor);
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			if(startGame==false){
				if (Model.getInstance().getFirstStep()==false && Model.getInstance().getTilePlaced()<Model.getInstance().getSizeOfTileList()){
				setCursor(cursore);
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setCursor(defaultCursor);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if(sG==true){
				refreshPreviewTile();
				repaintMap();
				sG=false;
			}else{
			ViewControllerForModel.getInstance().mouseReleased(this, e);
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
				dragged=true;						
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		
		}

		@Override
		public void componentHidden(ComponentEvent e) {
			// TODO Auto-generated method stub	
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			// TODO Auto-generated method stub	
		}

		@Override
		public void componentResized(ComponentEvent e) {
			if(startGame==false){
				jl.setVisible(false);
				img2 = new javax.swing.ImageIcon(getClass().getResource("/jarcassonne/img/resume.png"));
		        jl = new JLabel(img2);
		        add(this.jl);
				resized=true;
			}else{
				resized=true;
			}
		}

		@Override
		public void componentShown(ComponentEvent e) {
			// TODO Auto-generated method stub
		}
	}
	//----------------------------------
	//END INNER CLASS
	//----------------------------------
			
		
}
		

		


