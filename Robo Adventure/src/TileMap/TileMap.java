package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParsePosition;

import javax.imageio.ImageIO;

import GameState.PlayState;
import Main.GamePanel;

public class TileMap {
	
	public PlayState state;
	
	//position
	private double x, y;
	
	//bounds
	private int xmin,ymin,xmax,ymax;
	
	private double smoother;
	
	//map
	private int[][] map;
	private int tileSize,width,height,numCols,numRows,numColsToDraw,numRowsToDraw,rowOffset,colOffset;
	
	//Tileset
	private Tile[][] tiles;
	private BufferedImage sheet;
	private int numTiles;
	
	public TileMap(PlayState state,int tilesize){
		this.state = state;
		this.tileSize = tilesize;
		numColsToDraw = GamePanel.WIDTH/tilesize+2;
		numRowsToDraw = GamePanel.HEIGHT/tilesize+2;
	}
	
	public void loadTiles(String s){
		
		try{
			
			sheet = ImageIO.read(getClass().getResourceAsStream(s));
			numTiles = sheet.getWidth()/tileSize;
			tiles = new Tile[2][numTiles];
			
			for(int row = 0; row < 2;row++){
				
				for(int col = 0; col<numTiles;col++){
					
					BufferedImage img = sheet.getSubimage(col*tileSize, row*tileSize, tileSize, tileSize);
					
					tiles[row][col] = new Tile(img,row);
					
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String s){
		
		try{
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(s)));
			
			numRows = Integer.parseInt(reader.readLine());
			numCols = Integer.parseInt(reader.readLine());
			width = numCols*tileSize;
			height = numRows*tileSize;
			
			map = new int[numRows][numCols];
			
			xmin = GamePanel.WIDTH-width;
			xmax = 0;
			ymin = GamePanel.HEIGHT-height;
			ymax = 0;
			
			for(int row = 0;row < numRows;row++){
				
				String line = reader.readLine();
				String[] tokens = line.split(" ");
				
				for(int col = 0; col <tokens.length; col++){
					if (!isNumeric(tokens[col])){
						String[] entityType = tokens[col].split("");
						
						if (entityType[0].equals("{")) {
							if (entityType[1].equals("p")) {
								state.addPowerup(Integer.parseInt(entityType[2]), col* tileSize, row * tileSize);
								map[row][col] = Integer.parseInt(entityType[4]);
							}
							
							if(entityType[1].equals("P")){
								state.setPlayerPos(col*tileSize, row*tileSize);
								directSetPosition(GamePanel.WIDTH/2-col*tileSize, GamePanel.HEIGHT/2-row*tileSize);
								map[row][col] = Integer.parseInt(entityType[3]);
							}
							
							if(entityType[1].equals("e")){
								state.addEnemy(Integer.parseInt(entityType[2]), col*tileSize, row*tileSize);
								map[row][col] = Integer.parseInt(entityType[4]);
							}
							
						}
						
						if(entityType[0].equals("l")){
							state.addEnemy(Integer.parseInt(entityType[1]), col*tileSize, row*tileSize);
						}
						
						
						
						if(entityType[0].equals("E")){
							state.setPortal((col)*tileSize, row*tileSize);
						}
						
						if(entityType[0].equals("s")){
							state.addSavePoint(col*tileSize, row*tileSize);
						}
						
					}else if (isNumeric(tokens[col])) {
						map[row][col] = Integer.parseInt(tokens[col]);
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public boolean isNumeric(String s) {
				
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(s, pos);
		return s.length() == pos.getIndex();

	}

	public void setPosition(double x, double y){
		
		this.x += (x - this.x)*smoother;
		this.y += (y - this.y)*smoother;
		
		fixBounds();
		
		rowOffset = (int)-this.y/tileSize;
		colOffset = (int)-this.x/tileSize;
		
	}
	
	public void fixBounds(){
		
		if(x<xmin)x = xmin;
		if(x>xmax)x = xmax;
		if(y<ymin)y = ymin;
		if(y>ymax)y = ymax;
		
	}
	
	public void draw(Graphics2D g){
		
		for(int row = rowOffset-1;row < rowOffset-1+numRowsToDraw;row++){
			
			if(row >= numRows){
				break;
			}
			if(row<0){
				continue;
			}
			
			for(int col = colOffset-1;col < colOffset-1+numColsToDraw;col++){
				
				if(col >= numCols){
					break;
				}
				if(col<0){
					continue;
				}
				
				if(map[row][col] == 0)continue;
				
				int rc = map[row][col];
				int r = (int)rc/numTiles;
				int c = rc%numTiles;
				if (rc == 4) {
					g.drawImage(tiles[r][c].getImage(), (int) x + col
							* tileSize, (int) y + row * tileSize,tileSize*2,tileSize*2, null);
				} else {
					g.drawImage(tiles[r][c].getImage(), (int) x + col
							* tileSize, (int) y + row * tileSize, null);
				}
			}
			
		}
		
	}
	
	public void setSmothness(double d){
		smoother = d;
	}
	
	public void directSetPosition(double x,double y){
		this.x = x;
		this.y = y;
		
		fixBounds();
		
		rowOffset = (int)-this.y/tileSize;
		colOffset = (int)-this.x/tileSize;
		
	}
	
	public int checkState(int row, int col){
		
		int rc = map[row][col];
		int r = (int)rc/numTiles;
		int c = rc%numTiles;
		
		return tiles[r][c].getState();
		
	}
	
	public int getTileSize(){
		return tileSize;
	}
	
	public double getX(){return x;}
	
	public double getY(){return y;}
	
}
