package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import GameState.PlayState;
import TileMap.TileMap;

public class Player extends MapObject{
	
	private ArrayList<BufferedImage[]> images;
	
	public static final int STATIC = 0,INAIR =1,WALKING = 2,CROUCHING = 3;
	private int currentState;
	
	private ArrayList<Bullet> bullets;
	
	public int[] frames = {1,1,2,1};
	
	private boolean firing,fireP,jumpP,dJumpP,tempDown;
	
	private long start,elapsed;
	
	private PlayState state;
	
	private String[] upgrades = {"Press the up key to jump to high spaces.","Double-tap up to jump twice in a row.","Press space to shoot deadly lasers."};
	
	public Player(TileMap tm,ArrayList<Bullet> bullets,PlayState state) {
		super(tm);
		this.bullets = bullets;
		this.state = state;
	}
	
	public void init(){
		
		moveSpeed = 4;
		jumpStart = -8.72;
		fallSpeed = .42;
		maxFallSpeed = 20;
		stopSpeed = 1;
		slideSpeed = 0.05;
		
		height = 32;
		width = 32;
		cheight = 32;
		cwidth = 12;
		
		fireP = jumpP = dJumpP = false;//false
		
		BufferedImage sheet;
		BufferedImage[] sprites;
		
		images = new ArrayList<BufferedImage[]>();
		
		try{
			
			sheet = ImageIO.read(getClass().getResourceAsStream("/Player/Player.png"));
			
			for(int i = 0; i < 4; i++){
				sprites = new BufferedImage[frames[i]];
				for(int j = 0; j < frames[i];j++){
					sprites[j] = sheet.getSubimage(j*width, i*height, width, height);
				}
				images.add(sprites);
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(images.get(currentState));
		animation.setDelay(-1);
		
		start = System.nanoTime();
		
	}
	
	public void movement(){
		
		if(left&&!down){
			if (dx < -moveSpeed) {
				dx += slideSpeed;
			}else dx = -moveSpeed;
			facingRight = false;
		}else if(right&&!down){
			if (dx > moveSpeed) {
				dx -= slideSpeed;
			}else dx = moveSpeed;
			facingRight = true;
		}else{
			if(down){
				if(dx < 0){
					if(!tempDown){
						tempDown = true;
						dx-=1;
					}
					dx += slideSpeed;
				}else if(dx>0){
					if(!tempDown){
						tempDown = true;
						dx+=1;
					}
					dx-= slideSpeed;
				}
			} else {
				dx = 0;
			}
		}
		if(!down){
			tempDown = false;
		}
		if(jumping && !falling){
			dy = jumpStart;
			falling = true;
			candjump = true;
			state.playSound(PlayState.JUMP);
		}
		if(djumping && candjump){
			dy = jumpStart;
			candjump = false;
			state.playSound(PlayState.JUMP);
		}
		if(falling){
			dy+=fallSpeed;
			if(dy > 0){
				jumping = djumping = false;
			}
			if(dy >= maxFallSpeed){
				dy = maxFallSpeed;
			}
		}
		
	}
	
	public void update(){
		
		elapsed = System.nanoTime()-start;
		
		movement();
		checkTileMapCollision();
		setPos(xtemp,ytemp);
		
		if (dy != 0) {
			if (currentState != INAIR) {
				currentState = INAIR;
				animation.setFrames(images.get(currentState));
				animation.setDelay(-1);
			}
		} else if (down) {
			if (currentState != CROUCHING) {
				currentState = CROUCHING;
				animation.setFrames(images.get(currentState));
				animation.setDelay(-1);
			}
		} else if (dx != 0) {
			if (currentState != WALKING) {
				currentState = WALKING;
				animation.setFrames(images.get(currentState));
				animation.setDelay(100);
			}
		} else if (dx == 0 && dy == 0) {
			if (currentState != STATIC) {
				currentState = STATIC;
				animation.setFrames(images.get(currentState));
				animation.setDelay(-1);
			}
		}
		
		if(elapsed/400000000>1&&firing){
			start = System.nanoTime();
			bullets.add(new Bullet(tileMap,this));
			state.playSound(PlayState.SHOOT);
		}
		
		animation.update();
		
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
	
	public void setFiring(boolean f){
		if(!fireP)return;
		firing = f;
	}
	
	public void setJumping(boolean b){
		if(!jumpP)return;
		jumping = b;
		if(!dJumpP)return;
		if(falling){
			djumping = true;
		}
	}
	
	public void addPowerup(int type){
		if(type == Powerup.JUMPP){
			jumpP = true;
		}else if(type == Powerup.SHOOTP){
			fireP = true;
		}else if(type == Powerup.DJUMPP){
			dJumpP = true;
		}
		state.setDisplayText(upgrades[type]);
	}
	
}
