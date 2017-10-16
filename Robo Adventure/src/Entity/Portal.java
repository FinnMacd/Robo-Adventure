package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Portal extends MapObject{

	public Portal(TileMap tileMap,int x, int y) {
		super(tileMap);
		this.x = x;
		this.y = y;
		init();
	}
	
	public void init(){
		
		height = cheight = width = cwidth = tileSize;
		
		BufferedImage[] sprites = new BufferedImage[3];
		
		try{
			BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream("/Tiles/Portal.png"));
			for(int i = 0; i < 3; i++){
				sprites[i] = sheet.getSubimage(tileSize*i, 0, tileSize, tileSize);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(200);
		
	}
	
	public void update(){
		animation.update();
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}

}
