package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Powerup extends MapObject{
	
	public static final int JUMPP = 0,SHOOTP = 2,DJUMPP = 1;
	
	private final int type;
	
	private Player player;
	
	private BufferedImage[] sprite;
	
	public Powerup(TileMap tileMap,Player player,int type) {
		super(tileMap);
		
		this.type = type;
		this.player = player;
		init();
		
	}
	
	public void init(){
		
		width = height = cwidth = cheight = tileSize;
		
		sprite = new BufferedImage[1];
		try{
			
			BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream("/Tiles/Upgrades.png"));
			sprite[0] = sheet.getSubimage(type*tileSize, 0, tileSize, tileSize);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprite);
		animation.setDelay(-1);
		
	}
	
	public void update(){
		animation.update();
		if(collision(player)){
			dead = true;
			player.addPowerup(type);
		}
		
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
	
}
