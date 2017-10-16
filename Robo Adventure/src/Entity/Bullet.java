package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Bullet extends MapObject{
	
	BufferedImage sprite;
	BufferedImage[] shooting, hitWall;
	private Player player;
	
	private boolean hit;
	
	public Bullet(TileMap tileMap,Player p) {
		super(tileMap);
		player = p;
		init();
	}
	
	public void init(){
		
		height = width = tileSize;
		cwidth = 12;
		cheight = 8;
		
		shooting = new BufferedImage[1];
		hitWall = new BufferedImage[5];
		
		try{
			
			sprite = ImageIO.read(getClass().getResourceAsStream("/Player/Bullet.png"));
			
			shooting[0] = sprite;
			
			sprite = ImageIO.read(getClass().getResourceAsStream("/Player/Exploding.png"));
			
			for(int i = 0; i < 5; i++){
				hitWall[i] = sprite.getSubimage(i*tileSize, 0, tileSize, tileSize);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(shooting);
		animation.setDelay(-1);
		
		facingRight = player.getFacingRight();
		if(facingRight == true){
			dx = 12;
		}else{
			dx = -12;
		}
		
		x = player.getX();
		y = player.getY();
		
		hit = false;
		
	}
	
	public void update(){
		
		checkTileMapCollision();
		setPos(xtemp,ytemp);
		
		if(dx == 0&&!hit){
			animation.setFrames(hitWall);
			animation.setDelay(50);
			hit = true;
		}
		
		if(animation.playedOnce){
			dead = true;
		}
		
		animation.update();
		
	}
	
	public void setHit(){
		dx = 0;
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
	
	public boolean getHit(){
		return hit;
	}
	
}
