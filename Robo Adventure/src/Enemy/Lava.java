package Enemy;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.Animation;
import TileMap.TileMap;

public class Lava extends Enemy{
	
	public Lava(TileMap tileMap, int type) {
		super(tileMap);
		hurtState = type;
		init();
	}
	
	public void init(){
		
		animation = new Animation();
		
		getShot = false;
		
		BufferedImage[] sprite = null;
		BufferedImage sheet;
		
		if(hurtState == HURT){
			try {
				sheet = ImageIO.read(getClass().getResourceAsStream("/Tiles/LavaTop.png"));
				sprite = new BufferedImage[sheet.getWidth()/tileSize];
				for(int i = 0; i < sheet.getWidth()/tileSize; i++){
					sprite[i] = sheet.getSubimage(tileSize * i, 0, tileSize, tileSize);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				sheet = ImageIO.read(getClass().getResourceAsStream("/Tiles/LavaBody.png"));
				sprite = new BufferedImage[sheet.getWidth()/tileSize];
				for(int i = 0; i < sheet.getWidth()/tileSize; i++){
					sprite[i] = sheet.getSubimage(tileSize * i, 0, tileSize, tileSize);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		animation.setFrames(sprite);
		animation.setDelay(100);
		
		health = 1000;
		
		cwidth = width = height = tileSize;
		cheight = tileSize-16;
	}
	
	public void update(){
		
		animation.update();
		
	}
	
	public void draw(Graphics2D g){
		super.draw(g);
	}

}
