package TileMap;

import java.awt.image.BufferedImage;

public class Tile {
	
	private BufferedImage sprite;
	
	private int state;
	
	public static final int SOLID = 1, PASSABLE = 0;
	
	public Tile(BufferedImage bi,int state){
		sprite = bi;
		this.state = state;
	}
	
	public BufferedImage getImage(){
		return sprite;
	}
	
	public int getState(){
		return state;
	}
	
}
