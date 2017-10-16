package Enemy;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.Animation;
import TileMap.TileMap;

public class Creeper extends Enemy{
	
	public Creeper(TileMap tileMap) {
		super(tileMap);
		init();
	}
	
	public void init(){
		
		height = cheight = width = tileSize;
		
		cwidth = 30;
		
		getShot = true;
		
		health = 3;
		
		BufferedImage[] sprites = new BufferedImage[2];
		
		try {
			BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream("/Enemy/Creeper.png"));
			
			sprites[0] = sheet.getSubimage(0, 0, tileSize, tileSize);
			sprites[1] = sheet.getSubimage(tileSize, 0, tileSize, tileSize);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(200);
		
		facingRight = true;
		dx = 0.7;
		dy = 0;
		
	}
	
	public void update() {
		animation.update();
		
		checkDir();
		checkTileMapCollision();
		setPos(xtemp, ytemp);
		
	}
	
	public void checkDir(){
		if (dx == 0 && facingRight) {
			dx -= 0.7;
			facingRight = false;
		} else if (dx == 0 && !facingRight) {
			dx += 0.7;
			facingRight = true;
		} else {

			double tempx = x;

			if (!facingRight) {
				x -= 31;
			}
			if (facingRight) {
				x += 31;
			}
			falling = false;
			checkTileMapCollision();

			if (falling) {
				if (facingRight) {
					dx = -0.7;
					facingRight = false;
				}

				else if (!facingRight) {
					dx = 0.7;
					facingRight = true;
				}

			}else{
				if(facingRight)dx = 0.7;
				if(!facingRight)dx = -0.7;
			}

			x = tempx;

		}
	}
	
	public void draw(Graphics2D g){
		super.draw(g);
	}

}
