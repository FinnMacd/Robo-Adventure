package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class SavePoint extends MapObject{
	
	BufferedImage[] imgs;
	
	private boolean saved;
	
	public SavePoint(TileMap tileMap,int x, int y) {
		super(tileMap);
		this.x = x;
		this.y = y;
		init();
	}
	
	public void init(){
		
		height = cheight = width = cwidth = tileSize;
		
		try{
			
			BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream("/Tiles/Save.png"));
			
			imgs = new BufferedImage[2];
			
			imgs[0] = sheet.getSubimage(0, 0, tileSize, tileSize);
			imgs[1] = sheet.getSubimage(tileSize, 0, tileSize, tileSize);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(imgs);
		animation.setDelay(-1);
		
		saved = false;
		
	}
	
	public void update(){
		
		if(saved)animation.setFrame(1);
		if(!saved)animation.setFrame(0);
		
		animation.update();
		
	}
	
	public void setSaved(boolean saved){
		this.saved = saved;
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
	
}
